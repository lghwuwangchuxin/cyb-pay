package com.parking.service.impl;

import com.parking.dao.*;
import com.parking.domain.ParkingPreChannelPayRouteConfig;
import com.parking.domain.ParkingQrCodeCbChannelPayRouteConfig;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.*;
import com.parking.dto.icbc.CommonRsp;
import com.parking.dto.icbc.PayResultReq;
import com.parking.qrcode.service.prejs.QrCodeH5SwUnionPayTradeService;
import com.parking.qrcode.service.qrcodecb.QrCodeCbSwUnionPayTradeService;
import com.parking.service.*;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("parkingChannelQueryOrderService")
public class ParkingChannelQueryOrderServiceImpl implements ParkingChannelQueryOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ParkingChannelQueryOrderServiceImpl.class);

    @Autowired
    private ParkingTradeOrderDao parkingTradeOrderDao;
    @Autowired
    private ParkingPreChannelPayRouteConfigDao parkingPreChannelPayRouteConfigDao;
    @Autowired
    private ParkingPreMchntConfigDao parkingPreMchntConfigDao;
    @Autowired
    private ParkingQrCodeCbChannelPayRouteConfigDao parkingQrCodeCbChannelPayRouteConfigDao;
    @Autowired
    private ParkingQrCodeCbMchntConfigDao parkingQrcodeCbMchntConfigDao;
    @Autowired
    private PrPosTongService prPosTongService;
    @Autowired
    private PrHxbQrCodeService prHxbQrCodeService;
    @Autowired
    private PrIcBcPayService prIcBcPayService;
    @Autowired
    private QrCodeCbChannelPayTradeProxyService qrCodeCbChannelPayTradeProxyService;
    @Autowired
    private ParkingH5ChannelPayTradeProxyService parkingH5ChannelPayTradeProxyService;

    // 进入 统一查询 订单 服务
    @Override
    public ParkingTradeOrderQueryRsp queryOrder(ParkingTradeOrderQueryReq mreq) throws Exception {
        ParkingTradeOrderQueryRsp  mrsp = new ParkingTradeOrderQueryRsp();
        logger.info("进入统一查询 订单 服务---------queryOrder");
        if (StringUtil.checkNullString(mreq.getOrderId())) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.IS_NULL.getRspMsg());
            return mrsp;
        }

        ParkingTradeOrder parkingTradeOrder = findParkingTradeOrder(mreq.getOrderId());
        if (null == parkingTradeOrder) {
            setRspParams(mrsp, RespUtil.noResult, "无此订单号");
            return mrsp;
        }
          boolean iResult = false;

        ParkingQrcodeMchntConfig  parkingQrcodeMchntConfig = null;
        ParkingQrCodeCbChannelPayRouteConfig cbpayRouteConfig = null;
        ParkingPreChannelPayRouteConfig prePayRouteConfig = null;
        String subPayType = null;
        String payId = null;
        String reqXml = null;
        String rspXml  = null;
            // 根据 支付业务类型  临时 车支付类型
            if (StringUtil.checkStringsEqual("parkingPre", parkingTradeOrder.getResType()) ) {

                prePayRouteConfig = findParkingPreChannelPayRouteConfig(parkingTradeOrder.getParkId());
                if ( null == prePayRouteConfig) {
                    setRspParams(mrsp, RespUtil.noResult, "未配置渠道路由");
                    return mrsp;
                }

                // 查询 商户 配置
                parkingQrcodeMchntConfig = findParkingPreMchntConfig(parkingTradeOrder.getPayType(), parkingTradeOrder.getMchntNo());
                 if ( null == parkingQrcodeMchntConfig) {
                     setRspParams(mrsp, RespUtil.noResult, "未配置h5商户");
                     return mrsp;
                 }
                 // 根据不同渠道组装报文  判断 是否是 银商 渠道
                if (StringUtil.checkStringsEqual(CommEnum.MIS_POSTPAY_CODE.getRspCode(),parkingTradeOrder.getPayType())) {
                    // 组装 h5 支付时订单结果查询 报文；
                    reqXml = geneH5QuerOrder(parkingTradeOrder, parkingQrcodeMchntConfig, prePayRouteConfig);
                    logger.info("银商渠道查询支付结果请求报文：" +reqXml);
                    try {
                        rspXml = prPosTongService.yshangQuerOrder(reqXml);
                        logger.info("银商渠道查询支付结果返回：" +rspXml);
                    } catch (Exception e) {
                        e.printStackTrace();
                        setRspParams(mrsp, RespUtil.timeOut, "查询订单请求异常了");
                        return mrsp;
                    }


                    if (null == rspXml) {
                        setRspParams(mrsp, RespUtil.timeOut, "请求超时了");
                        return mrsp;
                    }
                    WxAliYuionpayQueryOrderRsp rsp = (WxAliYuionpayQueryOrderRsp) XmlUtil.XmlToObj(rspXml, WxAliYuionpayQueryOrderRsp.class);
                    if (null == rsp || !StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), rsp.getRespCode())) {
                        setRspParams(mrsp, RespUtil.noResult, "未查询支付结果或则支付失败");
                        return mrsp;
                    }
                    // 更新 数据库
                    iResult = true;
                } else if (CommEnum.HXBPAY_CODE.getRspCode().equals(parkingTradeOrder.getPayType())) {
                    // 组装 华夏银行 h5订单查询报文
                    reqXml = geneHxH5Trade(parkingTradeOrder, prePayRouteConfig, parkingQrcodeMchntConfig);
                    try {
                        rspXml = prHxbQrCodeService.queryCbPayTrade(reqXml);
                    } catch (Exception e) {
                        setRspParams(mrsp, RespUtil.timeOut, "订单查询请求异常了");
                        return mrsp;
                    }

                    if (null == rspXml) {
                        setRspParams(mrsp, RespUtil.timeOut, "请求超时了");
                        return mrsp;
                    }
                    HxbOrderQueryRsp rsp = (HxbOrderQueryRsp) XmlUtil.XmlToObj(rspXml, HxbOrderQueryRsp.class);
                    if (null == rsp || !StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), rsp.getRespCode())) {
                        setRspParams(mrsp, RespUtil.noResult, "未查询到支付结果或则支付失败");
                        return mrsp;
                    }
                    subPayType = rsp.getPayType();
                    payId  = rsp.getTradeNo(); // 渠道系统生成的方订单号
                    // 更新 数据库
                    iResult = true;
                } else if (CommEnum.ICBC_PAY_CHANNEL.getRspCode().equals(parkingTradeOrder.getPayType())) {
                    // 组装工行 提前付 h5支付结果查询报文
                    reqXml = geneIcbcH5QuerOrder(parkingTradeOrder, parkingQrcodeMchntConfig, prePayRouteConfig);
                    try{
                        rspXml = prIcBcPayService.uniCallProvider(reqXml);
                    } catch (Exception e) {
                        setRspParams(mrsp, RespUtil.timeOut, "订单查询请求异常了");
                        return mrsp;
                    }

                    CommonRsp commonRsp = (CommonRsp) XmlUtil.XmlToObj(rspXml, CommonRsp.class);
                    if (null== commonRsp || !CommEnum.SUCCESS_COID.getRspCode().equals(commonRsp.getRespCode())) {
                        setRspParams(mrsp, RespUtil.noResult, "工行提前付支付结未查询到或则支付失败");
                        return mrsp;
                    }
                    // 更新 数据库
                    iResult = true;
                }  else if (QrCodeMapUtils.checkChannelType(parkingTradeOrder.getPayType())) {
                    try {
                        prePayRouteConfig.setService("queryAtmResult");
                        ApplyOrderChannelBaseDTO dto = parkingH5ChannelPayTradeProxyService.queryTradeService(prePayRouteConfig, parkingQrcodeMchntConfig, parkingTradeOrder);
                        if (null != dto && StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), dto.getRespCode())) {
                            subPayType = dto.getPayType();
                            payId  = dto.getPayId(); // 渠道系统生成的方订单号
                            // 更新 数据库
                            iResult = true;
                            mrsp.setSubPayType(String.valueOf(ParkingChannelPayType.getDecsitChannelPayTypeMap().get(subPayType)));
                        } else {
                            setRspParams(mrsp, RespUtil.timeOut, CommEnum.ORDER_FAIL_DESC.getRspMsg());
                            return mrsp;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("下单异常：" +e.getMessage());
                    }
                }


            } else {  // parkingCb

                cbpayRouteConfig = findQrCodeCbChannelPayRouteConfig(parkingTradeOrder.getParkId(), parkingTradeOrder.getTermId());
                if (null == cbpayRouteConfig) {
                    setRspParams(mrsp, RespUtil.noResult, "未配置主扫渠道路由");
                    return mrsp;
                }

                parkingQrcodeMchntConfig = findQrcodeCbMchntConfig(parkingTradeOrder.getPayType(),parkingTradeOrder.getMchntNo());
                 if (null == parkingQrcodeMchntConfig) {
                     setRspParams(mrsp, RespUtil.noResult, "未配置主扫商户");
                     return mrsp;
                 }
                if (StringUtil.checkStringsEqual(CommEnum.MIS_POSTPAY_CODE.getRspCode(),parkingTradeOrder.getPayType())) {
                    // 组装 主扫 订单 查询 支付结果 报文
                    reqXml = geneCbQuerOrder(parkingTradeOrder, cbpayRouteConfig, parkingQrcodeMchntConfig);
                    logger.info("银商渠道查询支付结果请求报文：" +reqXml);
                    try {
                        rspXml = prPosTongService.yshangQuerOrder(reqXml);
                        logger.info("银商渠道查询支付结果返回：" +rspXml);
                    } catch (Exception e) {
                        e.printStackTrace();
                        setRspParams(mrsp, RespUtil.timeOut, "查询订单请求异常了");
                        return mrsp;
                    }

                    if (null == rspXml) {
                        setRspParams(mrsp, RespUtil.timeOut, "请求超时了");
                        return mrsp;
                    }
                    WxAliYuionpayQueryOrderRsp rsp = (WxAliYuionpayQueryOrderRsp) XmlUtil.XmlToObj(rspXml, WxAliYuionpayQueryOrderRsp.class);
                    if (null == rsp || !StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), rsp.getRespCode())) {
                        setRspParams(mrsp, RespUtil.noResult, "未查询支付结果或则支付失败");
                        return mrsp;
                    }
                    // 更新 数据库
                    iResult = true;
                    subPayType = StringUtil.checkNullString(rsp.getSubPayType()) ? "" : rsp.getSubPayType();
                    if (!StringUtil.checkNullString(subPayType)) mrsp.setSubPayType(String.valueOf(ParkingChannelPayType.getDecsitChannelPayTypeMap().get(subPayType)));
                } else if (CommEnum.HXBPAY_CODE.getRspCode().equals(parkingTradeOrder.getPayType())) {
                    // 组装 华夏银行主扫订单查询报文
                    reqXml = geneHxCbTrade(parkingTradeOrder, cbpayRouteConfig, parkingQrcodeMchntConfig);
                    try {
                        rspXml = prHxbQrCodeService.queryCbPayTrade(reqXml);
                    } catch (Exception e) {
                        setRspParams(mrsp, RespUtil.timeOut, "订单查询请求异常了");
                        return mrsp;
                    }

                    if (null == rspXml) {
                        setRspParams(mrsp, RespUtil.timeOut, "请求超时了");
                        return mrsp;
                    }
                    HxbOrderQueryRsp rsp = (HxbOrderQueryRsp) XmlUtil.XmlToObj(rspXml, HxbOrderQueryRsp.class);
                    if (null == rsp || !StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), rsp.getRespCode())) {
                        setRspParams(mrsp, RespUtil.noResult, "未查询到支付结果或则支付失败");
                        return mrsp;
                    }
                    subPayType = rsp.getPayType();
                    payId  = rsp.getTradeNo(); // 渠道系统生成的方订单号
                    // 更新 数据库
                    iResult = true;
                    mrsp.setSubPayType(String.valueOf(ParkingChannelPayType.getDecsitChannelPayTypeMap().get(subPayType)));
                } else if (QrCodeMapUtils.checkChannelType(parkingTradeOrder.getPayType())){ // 主扫 多渠道

                    try {
                        cbpayRouteConfig.setService("queryAtmResult");
                        ApplyOrderChannelBaseDTO dto = qrCodeCbChannelPayTradeProxyService.queryTradeService(cbpayRouteConfig, parkingQrcodeMchntConfig, parkingTradeOrder);
                        if (null != dto && StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), dto.getRespCode())) {
                            subPayType = dto.getPayType();
                            payId  = dto.getPayId(); // 渠道系统生成的方订单号
                            // 更新 数据库
                            iResult = true;
                            mrsp.setSubPayType(String.valueOf(ParkingChannelPayType.getDecsitChannelPayTypeMap().get(subPayType)));
                        } else {
                            setRspParams(mrsp, RespUtil.timeOut, CommEnum.ORDER_FAIL_DESC.getRspMsg());
                            return mrsp;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("下单异常：" +e.getMessage());
                    }
                }

            }

        if ( iResult ) {
            int u = updateParkingOrder(mreq.getOrderId(), subPayType, payId);
            if (u == 0) {
                logger.info("查询渠道订单更新本地库异常");
                setRspParams(mrsp, RespUtil.dberror, "操作库失败");
                return  mrsp;
            }
            mrsp.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
            setRspParams(mrsp, RespUtil.successCode, "查询支付结果成功");
        }

        return mrsp;
    }

    /**
     * 组装 工行 查询 订单 支付结果报文 提前付 h5
     * @param parkingTradeOrder
     * @param parkingQrcodeMchntConfig
     * @param payRouteConfig
     * @return
     */
    private String geneIcbcH5QuerOrder(ParkingTradeOrder parkingTradeOrder, ParkingQrcodeMchntConfig parkingQrcodeMchntConfig, ParkingPreChannelPayRouteConfig payRouteConfig) throws JAXBException {
        PayResultReq req = new PayResultReq();
        req.setService("icbcQueryPayh5Pre");
        req.setTradeId(parkingTradeOrder.getOrderId());
        req.setParkingId(payRouteConfig.getPartParkId()); // 取渠道合作方停车场id
        req.setSecretKey(parkingQrcodeMchntConfig.getMchntHtreeKey());
        req.setAppKey(parkingQrcodeMchntConfig.getMchntAppId());
        req.setQueryH5Url(payRouteConfig.getQueryOrderUrl());
        String reqXml = XmlUtil.ObjToXml(req, PayResultReq.class);
        return reqXml;
    }

    private String geneHxH5Trade(ParkingTradeOrder parkingTradeOrder, ParkingPreChannelPayRouteConfig prePayRouteConfig, ParkingQrcodeMchntConfig parkingQrcodeMchntConfig) throws JAXBException, ParseException {
        HxbOrderQueryReq oqReq = new HxbOrderQueryReq();
        oqReq.setService(CommonHxbPayQrCodePayEnum.HXB_QUERY_PAY_TRADE_SERVICE.getRspCode());
        oqReq.setOrgPcsDate(Utility.getDateYYYYMMDD(parkingTradeOrder.getCreatedTime()));
        oqReq.setAppKey(parkingQrcodeMchntConfig.getMchntAppId());  // APPid
        oqReq.setMerchantNo(parkingQrcodeMchntConfig.getMchntId()); // 商户号
        oqReq.setTradeType(parkingQrcodeMchntConfig.getRsrvStr3()); // 交易码
        oqReq.setOrderNo(parkingTradeOrder.getTradeId()); // 交易订单号
        String reqXml = XmlUtil.ObjToXml(oqReq, HxbOrderQueryReq.class);
        return reqXml;
    }

    // 组装 华夏 银行主扫 订单 查询 报文 或者  h5
    private String geneHxCbTrade(ParkingTradeOrder parkingTradeOrder, ParkingQrCodeCbChannelPayRouteConfig cbpayRouteConfig, ParkingQrcodeMchntConfig parkingQrcodeMchntConfig) throws Exception {
        HxbOrderQueryReq oqReq = new HxbOrderQueryReq();
        oqReq.setService(CommonHxbPayQrCodePayEnum.HXB_QUERY_PAY_TRADE_SERVICE.getRspCode());
        oqReq.setOrgPcsDate(Utility.getDateYYYYMMDD(parkingTradeOrder.getCreatedTime()));
        oqReq.setAppKey(parkingQrcodeMchntConfig.getMchntAppId());  // APPid
        oqReq.setMerchantNo(parkingQrcodeMchntConfig.getMchntId()); // 商户号
        oqReq.setTradeType(parkingQrcodeMchntConfig.getRsrvStr3()); // 交易码
        oqReq.setOrderNo(parkingTradeOrder.getTradeId()); // 交易订单号
        String reqXml = XmlUtil.ObjToXml(oqReq, HxbOrderQueryReq.class);
        return reqXml;
    }


    // 更新
    private int updateParkingOrder(String orderId, String subPayType, String payId) throws SQLException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String updateTime = sim.format(new Date());
        ParkingTradeOrder parkingTradeOrder = new ParkingTradeOrder();
        parkingTradeOrder.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
        parkingTradeOrder.setUpdateTime(updateTime);
        parkingTradeOrder.setStates(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
        parkingTradeOrder.setTradeId(orderId);
        parkingTradeOrder.setOutTradeNo(null== payId ? null : payId);
        parkingTradeOrder.setSubPayType(null == subPayType ? null : subPayType);
        int u = parkingTradeOrderDao.updateParkingOrder(parkingTradeOrder);
        return u;
    }

    // 置统一返回值
    private void setRspParams(ParkingTradeOrderQueryRsp mrsp, String noResult, String rspMsg) {
         mrsp.setRespCode(noResult);
         mrsp.setRespDesc(rspMsg);
    }

    //  组装 银商渠道 二维码 查询
    private String geneCbQuerOrder(ParkingTradeOrder parkingTradeOrder, ParkingQrCodeCbChannelPayRouteConfig payRouteConfig, ParkingQrcodeMchntConfig qrcodeMchnt) throws JAXBException {
        WxAliYuionpayQueryOrderReq payReq = new WxAliYuionpayQueryOrderReq();
        payReq.setAppId(qrcodeMchnt.getMchntAppId());// 银商
        payReq.setAppKey(qrcodeMchnt.getMchntHtreeKey());//
        payReq.setMerOrderId(parkingTradeOrder.getOrderId());
        payReq.setMid(qrcodeMchnt.getMchntId()); // 商户号
        payReq.setTid(qrcodeMchnt.getMchntHtreeNo()); // 终端号
        payReq.setInstMid(qrcodeMchnt.getRsrvStr1());// 业务类型
        payReq.setSerialNumber(parkingTradeOrder.getMchntSysNumber());
        payReq.setQueryOrderUrl(payRouteConfig.getQueryOrderUrl());
        payReq.setBillDate(parkingTradeOrder.getCreatedTime().substring(0,10)); // 账单日期
        String reqXml = XmlUtil.ObjToXml(payReq, WxAliYuionpayQueryOrderReq.class);
        return reqXml;
    }

    // 主扫商户 多渠道 配置
    private ParkingQrcodeMchntConfig findQrcodeCbMchntConfig(String payType, String mchntNo) throws SQLException {
        ParkingQrcodeMchntConfig parkingQrcodeMchntConfig = new ParkingQrcodeMchntConfig();
        parkingQrcodeMchntConfig.setAppChannel(payType);
        parkingQrcodeMchntConfig.setMchntNo(mchntNo);
        parkingQrcodeMchntConfig = parkingQrcodeCbMchntConfigDao.selectParkingCbMchntConfig(parkingQrcodeMchntConfig);
        return parkingQrcodeMchntConfig;
    }

    //  查询 二维码 支付 支付渠道
    private ParkingQrCodeCbChannelPayRouteConfig findQrCodeCbChannelPayRouteConfig(String parkId, String termId) throws SQLException {
        ParkingQrCodeCbChannelPayRouteConfig  payRouteConfig = new ParkingQrCodeCbChannelPayRouteConfig();
        payRouteConfig.setParkId(parkId);
        payRouteConfig.setTermId(termId);
        payRouteConfig = parkingQrCodeCbChannelPayRouteConfigDao.selectParkingQrCodeCbChannelPayRouteConfig(payRouteConfig);
        return payRouteConfig;
    }

    // h5 支付订单 查询 组装报文 银商渠道
    private String geneH5QuerOrder(ParkingTradeOrder parkingTradeOrder, ParkingQrcodeMchntConfig qrcodeMchnt, ParkingPreChannelPayRouteConfig payRouteConfig) throws JAXBException {
        WxAliYuionpayQueryOrderReq payReq = new WxAliYuionpayQueryOrderReq();
        payReq.setAppId(qrcodeMchnt.getMchntAppId());// 银商
        payReq.setAppKey(qrcodeMchnt.getMchntHtreeKey());//
        payReq.setMerOrderId(parkingTradeOrder.getOrderId());
        payReq.setMid(qrcodeMchnt.getMchntId()); // 商户号
        payReq.setTid(qrcodeMchnt.getMchntHtreeNo()); // 终端号
        payReq.setInstMid(qrcodeMchnt.getRsrvStr1());// 业务类型
        payReq.setSerialNumber(parkingTradeOrder.getMchntSysNumber());
        payReq.setQueryOrderUrl(payRouteConfig.getQueryOrderUrl());
        String reqXml = XmlUtil.ObjToXml(payReq, WxAliYuionpayQueryOrderReq.class);
        return reqXml;
    }

    // 查询 商户 配置信息 h5
    private ParkingQrcodeMchntConfig findParkingPreMchntConfig(String payType, String mchntNo) throws SQLException {
        ParkingQrcodeMchntConfig parkingQrcodeMchntConfig = new ParkingQrcodeMchntConfig();
        parkingQrcodeMchntConfig.setAppChannel(payType);
        parkingQrcodeMchntConfig.setMchntNo(mchntNo);
        parkingQrcodeMchntConfig = parkingPreMchntConfigDao.selectParkingPreMchntConfig(parkingQrcodeMchntConfig);
        return parkingQrcodeMchntConfig;
    }

    // 查询 h5 支付 配置 路由
    private ParkingPreChannelPayRouteConfig findParkingPreChannelPayRouteConfig(String parkId) throws SQLException {
        ParkingPreChannelPayRouteConfig payRouteConfig = new ParkingPreChannelPayRouteConfig();
        payRouteConfig.setParkId(parkId);
        payRouteConfig.setStates("1");
        payRouteConfig = parkingPreChannelPayRouteConfigDao.selectParkingPreChannelPayRouteConfig(payRouteConfig);
        return payRouteConfig;
    }

    // 查询 订单号
    private ParkingTradeOrder findParkingTradeOrder(String msg) throws SQLException {
        ParkingTradeOrder  parkingTradeOrder= new ParkingTradeOrder();
        parkingTradeOrder.setOrderId(msg);
        parkingTradeOrder = parkingTradeOrderDao.selectParkingOrderById(parkingTradeOrder);
        return parkingTradeOrder;
    }
}
