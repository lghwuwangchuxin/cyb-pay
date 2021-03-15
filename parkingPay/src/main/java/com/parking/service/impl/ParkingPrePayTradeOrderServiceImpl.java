package com.parking.service.impl;

import com.parking.dao.*;
import com.parking.domain.*;
import com.parking.dto.*;
import com.parking.dto.icbc.CommonRsp;
import com.parking.dto.icbc.ExitReq;
import com.parking.service.*;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("parkingPrePayTradeOrderService")
public class ParkingPrePayTradeOrderServiceImpl implements ParkingPrePayTradeOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ParkingPrePayTradeOrderServiceImpl.class);

    @Autowired
    private ParkingPreChannelPayRouteConfigDao parkingPreChannelPayRouteConfigDao;
    @Autowired
    private ParkingTradeOrderDao parkingTradeOrderDao;
    @Autowired
    private ParkingPreMchntConfigDao parkingPreMchntConfigDao;
    @Autowired
    private ParkingAliMchntConfigDao parkingAliMchntConfigDao;
    @Autowired
    private ParkingWxMchntConfigDao parkingWxMchntConfigDao;
    @Autowired
    private SeqService seqService;
    @Autowired
    private PrPosTongService prPosTongService;
    @Autowired
    private ParkingChannelQueryOrderService parkingChannelQueryOrderService;
    @Autowired
    private PrIcBcPayService prIcBcPayService;
    @Autowired
    private ParkingH5ChannelPayTradeProxyService parkingH5ChannelPayTradeProxyService;

    @Override
    public ParkingPrePayOrderRsp prePayTradeOrder(ParkingPrePayOrderReq mreq) throws Exception {
        logger.info("进入多渠道主扫或者 公众号支付 h5 下单 服务接口-------------prePayTradeOrder");
        ParkingPrePayOrderRsp mrsp = new ParkingPrePayOrderRsp();
        setInitRspParams(mrsp, mreq);
        if (checkParamsIsNull(mreq)< -1) {
            setRspParams(mrsp,RespUtil.noResult, CommEnum.IS_NULL.getRspMsg());
            return mrsp;
        }

        // 检查渠道 路由
        ParkingPreChannelPayRouteConfig preRoutConfig = findParkingPreChannelPayRouteConfig(mreq.getParkId());
        if (null == preRoutConfig) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.PRE_CHANNEL_ROUTE_CONFIG_NO_DESC.getRspMsg());
            return mrsp;
        }

        // 检查 渠道商户 配置渠道配置参数
        ParkingQrcodeMchntConfig qrcodeMchnt = findPreParkingQrcodeMchntConfig(preRoutConfig.getChannelSelect(), preRoutConfig.getMchntNo());
         if (null == qrcodeMchnt) {
             setRspParams(mrsp, RespUtil.noResult, CommEnum.PRE_CHANNEL_ROUTE_CONFIG_MCHN_NO_DESC.getRspMsg());
             return mrsp;
         }

         mreq.setMchntId(qrcodeMchnt.getMchntId()); //  渠道商户号
         Object obj = getObj(mreq);
         if (null == obj || obj instanceof Boolean) {
             setRspParams(mrsp, RespUtil.noResult, CommEnum.PRE_CHANNEL_ROUTE_CONFIG_MCHN_NO_DESC.getRspMsg());
             return mrsp;
        }
        setDecodeDnicode(mreq);
        // 线下请求流水号 检查 去重复 是否是 同一个客户端请求
        ParkingTradeOrder tradeOrder = findParkingTradeOrder(mreq.getSerialNumber());
        if (null != tradeOrder) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.ORDER_GENE_EXCEPTION_DESC.getRspMsg());
            return mrsp;
        }

        // 开始 生成订单号
        String tradeId= seqService.getTradeSequenceId("TRADE_ID");

        mreq.setTradeId(tradeId);
        boolean reslutl = false;
        // 开始 组装 请求银商渠道 下单业务 这个地方 后面优化
        if (CommEnum.CHANNEL_MCHNT_NAME_BE_PAY_MIS_PSTPAY.getRspCode().equals(preRoutConfig.getChannelSelect())) {
            // 组装银商业务参数 //组装 下单数据
            tradeId = qrcodeMchnt.getRsrvStr2()+tradeId;
            mreq.setTradeId(tradeId);
            String reqXml = geneYshH5PayTrade(mreq,qrcodeMchnt,preRoutConfig,  obj);
            String rspXml = prPosTongService.yshangH5PayTrade(reqXml);
            WxAliYuionpayOrderPayRsp payRsp = (WxAliYuionpayOrderPayRsp) XmlUtil.XmlToObj(rspXml,WxAliYuionpayOrderPayRsp.class);
            if ( null == payRsp || !CommEnum.SUCCESS_COID.getRspCode().equals(payRsp.getRespCode())) {
                setRspParams(mrsp, RespUtil.dberror, "订单签名处理异常");
                return mrsp;
            }
            setRspParams(mrsp, RespUtil.successCode, "订单签名成功");
            mrsp.setAuthorization(payRsp.getAuthorization());
            mrsp.setH5PayParams(payRsp.getH5PayParams());
            mrsp.setChannelType(qrcodeMchnt.getRsrvStr6()); // 返回收单渠道类型
            mrsp.setFrowdToRediToInput(CommEnum.H5_PAY_METHO_1.getRspCode());
            reslutl = true;
        } else if (CommEnum.ICBC_PAY_CHANNEL.getRspCode().equals(preRoutConfig.getChannelSelect())) {

            // 组装 工行 提前付 或扫码 h5
            String reqXml = geneIcBcH5PayTrade(mreq,qrcodeMchnt,preRoutConfig, obj);
            String rspXml = prIcBcPayService.uniCallProvider(reqXml);
            CommonRsp commonRsp = (CommonRsp) XmlUtil.XmlToObj(rspXml,CommonRsp.class);
            if (null == commonRsp || !CommEnum.SUCCESS_COID.getRspCode().equals(commonRsp.getRespCode())) {
                setRspParams(mrsp, RespUtil.dberror, "工行提前付或扫码付订单异常");
                return mrsp;
            }
            setRspParams(mrsp, RespUtil.successCode, "工行下单成功");
            setRspPreOrder(mrsp, commonRsp.getData(), qrcodeMchnt.getRsrvStr6());
            mrsp.setFrowdToRediToInput(CommEnum.H5_PAY_METHO_2.getRspCode());
            reslutl = true;
        } else if (QrCodeMapUtils.checkChannelType(preRoutConfig.getChannelSelect())) {
            try {
                mreq.setTradeId(tradeId);
                preRoutConfig.setService("qrcodeTradePay");
                ApplyOrderChannelBaseDTO dto  = parkingH5ChannelPayTradeProxyService.payTradeService(mreq, preRoutConfig, qrcodeMchnt);
                if (null == dto || !StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), dto.getRespCode())) {
                    setRspParams(mrsp, RespUtil.timeOut, CommEnum.ORDER_FAIL_DESC.getRspMsg());
                    return mrsp;
                }
                setRspParams(mrsp, RespUtil.successCode, "下单成功");
                mrsp.setFrowdToRediToInput(dto.getFrowdToRediToInput());
                setRspPreOrder(mrsp, dto.getH5PayParams(), qrcodeMchnt.getRsrvStr6());
                reslutl = true;
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("下单异常：" +e.getMessage());
            }
        }

        if (reslutl) {
            // 本地受理时
            String acceptDate = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode()).format(new Date());
            int iResult = parkingTradeOrderDao.insertParkingOrder(insertParkingTradeOrder(mreq, tradeId, acceptDate, qrcodeMchnt.getMchntName(), qrcodeMchnt.getMchntNo(), preRoutConfig.getChannelSelect(), qrcodeMchnt.getRsrvStr6()));
            if (iResult == 0) {
                setRspParams(mrsp, RespUtil.dberror, "订单处理异常");
                return mrsp;
            }
        }

        if (reslutl) {
            // 开启 线程池
            QrCodeH5CbQueryPayStatesServiceThread  qrCodeH5CbQueryPayStatesServiceThread = new QrCodeH5CbQueryPayStatesServiceThread();
            qrCodeH5CbQueryPayStatesServiceThread.setDelayTime(preRoutConfig.getDelayTime()); // 延时时间
            qrCodeH5CbQueryPayStatesServiceThread.setParkingChannelQueryOrderService(parkingChannelQueryOrderService);
            qrCodeH5CbQueryPayStatesServiceThread.setQueryReq(getQueryOrder(tradeId));
            qrCodeH5CbQueryPayStatesServiceThread.setPayNotifyReq(getPayNotifyReq(mreq, tradeId, qrcodeMchnt.getAppChannel(), qrcodeMchnt.getRsrvStr6()));
            QrCodeQueryPayStatesServiceThreadPool.executeThread(qrCodeH5CbQueryPayStatesServiceThread);
            return mrsp;
        }
        setRspParams(mrsp, RespUtil.noResult, "匹配渠道异常");
        return mrsp;
    }

    private void setRspPreOrder(ParkingPrePayOrderRsp mrsp, String data, String channelSelect) {
        mrsp.setH5PayParams(data);
        mrsp.setChannelType(channelSelect);
    }

    /** 组装 工行 提前付 h5报文
     * @param mreq
     * @param qrcodeMchnt
     * @param preRoutConfig
     * @param obj
     * @return
     */
    private String geneIcBcH5PayTrade(ParkingPrePayOrderReq mreq, ParkingQrcodeMchntConfig qrcodeMchnt, ParkingPreChannelPayRouteConfig preRoutConfig, Object obj) throws JAXBException {
        ExitReq req = new ExitReq();
        req.setService("icbch5pre");
        req.setBilling(mreq.getPayAmt());
        req.setStartTime(mreq.getInTime());
        req.setEndTime(mreq.getOutTime());
        req.setTradeId(mreq.getTradeId());
        req.setParkingId(preRoutConfig.getPartParkId());
        req.setPlateNumber(mreq.getCarPlate());
        req.setPlateType(mreq.getCarPlateColor());
        req.setSecretKey(qrcodeMchnt.getMchntHtreeKey());
        req.setAppKey(qrcodeMchnt.getMchntAppId());
        req.setPreUrl(preRoutConfig.getPayOrderUrl());
        req.setReturnUrl(preRoutConfig.getRsrvStr1());
        String reqXml = XmlUtil.ObjToXml(req,ExitReq.class);
        return reqXml;
    }
    // 中文名称解码
    private void setDecodeDnicode(ParkingPrePayOrderReq mreq) {
        mreq.setCarPlate(Utility.decodeUnicode(mreq.getCarPlate()));
        mreq.setParkName(Utility.decodeUnicode(mreq.getParkName()));
        mreq.setStayTime(Utility.decodeUnicode(mreq.getStayTime()));
    }
    // 组装 通知报文
    private PayNotifyReq getPayNotifyReq(ParkingPrePayOrderReq mreq, String tradeId, String payType, String channelNum) {
        PayNotifyReq  payNotifyReq = new PayNotifyReq();
        payNotifyReq.setService("payNotifyResult");
        payNotifyReq.setResType("parkingPre");
        payNotifyReq.setCarPlate(mreq.getCarPlate());
        payNotifyReq.setParkId(mreq.getParkId());
        payNotifyReq.setParkName(mreq.getParkName());
        payNotifyReq.setOrderId(tradeId);
        payNotifyReq.setPayType(String.valueOf(ParkingChannelPayType.getDecsitPayTypeMap().get(payNotifyReq.getResType())));
        payNotifyReq.setPayChannel(String.valueOf(ParkingChannelPayType.getDecsitChannelPayTypeMap().get(mreq.getAppChannelId())));
        payNotifyReq.setChannelNum(channelNum);
        payNotifyReq.setTxnAmt(mreq.getTxnAmt());
        payNotifyReq.setPayAmt(mreq.getPayAmt());
        payNotifyReq.setSerialNumber(mreq.getSerialNumber());
        payNotifyReq.setNotifySysUrl(mreq.getNotifySysUrl());
        payNotifyReq.setParkMchntSysNumber(mreq.getParkMchntSysNumber());

        payNotifyReq.setStayTime(mreq.getStayTime());
        return payNotifyReq;
    }

    // 组装 查询 报文
    private ParkingTradeOrderQueryReq getQueryOrder(String tradeId) {
        ParkingTradeOrderQueryReq req = new ParkingTradeOrderQueryReq();
        req.setOrderId(tradeId);
        return  req;
    }

    private Object getObj(ParkingPrePayOrderReq mreq) throws SQLException {
        ParkingAliMchntConfig aliMchntConfig = null;
        ParkingWxMchntConfig wxMchntConfig = null;

        // 检查 子渠道 理由配置
        if ("12".equals(mreq.getAppChannelId())) {
            aliMchntConfig = findParkingAliMchntConfig(mreq.getParkId(), mreq.getAppChannelId());
            if (null == aliMchntConfig) {
                //setRspParams(mrsp, RespUtil.noResult, CommEnum.PRE_CHANNEL_ROUTE_CONFIG_MCHN_NO_DESC.getRspMsg());
                return false;
            }
            return  aliMchntConfig;
        } else if ("13".equals(mreq.getAppChannelId())) {
            wxMchntConfig = findParkingWxMchntConfig(mreq.getParkId(),mreq.getAppChannelId());
            if (null == wxMchntConfig) {
                //etRspParams(mrsp, RespUtil.noResult, CommEnum.PRE_CHANNEL_ROUTE_CONFIG_MCHN_NO_DESC.getRspMsg());
                return false;
            }
            mreq.setSubAppId(null == wxMchntConfig.getWxAppId() ? "" : wxMchntConfig.getWxAppId());
            return wxMchntConfig;
        }
        return 1;
    }

    // 组装 渠道 报文
    private String geneYshH5PayTrade(ParkingPrePayOrderReq mreq, ParkingQrcodeMchntConfig qrcodeMchnt, ParkingPreChannelPayRouteConfig payRouteConfig, Object obj) throws JAXBException {
        WxAliYuionpayOrderPayReq payReq = new WxAliYuionpayOrderPayReq();
        if (obj instanceof ParkingAliMchntConfig) {
        }
        if (obj instanceof  ParkingWxMchntConfig) {
        }
        payReq.setAppId(qrcodeMchnt.getMchntAppId());// 银商
        payReq.setAppKey(qrcodeMchnt.getMchntHtreeKey());//
        payReq.setMerOrderId(mreq.getTradeId());
        payReq.setMid(qrcodeMchnt.getMchntId()); // 商户号
        payReq.setTid(qrcodeMchnt.getMchntHtreeNo()); // 终端号
        payReq.setInstMid(qrcodeMchnt.getRsrvStr1());// 业务类型
        payReq.setTotalAmount(mreq.getPayAmt());
        payReq.setSerialNumber(mreq.getSerialNumber());
        payReq.setPayOrderUrl(payRouteConfig.getPayOrderUrl());
        payReq.setNotifyUrl(payRouteConfig.getNotifyUrl());
        if ("13".equals(mreq.getAppChannelId())) payReq.setSubOpenId(mreq.getUserId());
        payReq.setChannelId(mreq.getAppChannelId());
        String reqXml = XmlUtil.ObjToXml(payReq,WxAliYuionpayOrderPayReq.class);
        return reqXml;
    }

    private ParkingTradeOrder insertParkingTradeOrder(
            ParkingPrePayOrderReq mreq, String tradeId, String acceptDate, String mchntName, String mchntNo, String payType, String channelNum) {
        ParkingTradeOrder  tradeOrder = new ParkingTradeOrder();
        tradeOrder.setTradeId(tradeId); //交易单号
        tradeOrder.setOrderId(tradeId); //交易订单号
        tradeOrder.setTxnAmt(mreq.getTxnAmt()); //交易金额  车易泊返回
        tradeOrder.setPayAmt(mreq.getPayAmt()); //支付金额
        tradeOrder.setMchntSysNumber(mreq.getSerialNumber());
        tradeOrder.setParkMchntSysNumber(mreq.getParkMchntSysNumber()); // 进出场流水号
        tradeOrder.setAcceptMonth(acceptDate.substring(5, 7)); //月份
        tradeOrder.setMchntId(mreq.getMchntId()); // 渠道商户id
        tradeOrder.setMchntName(mchntName); //商户名称
        tradeOrder.setParkId(mreq.getParkId());  //停车场id
        tradeOrder.setParkName(mreq.getParkName()); // 停车场名称
        tradeOrder.setCarPlate(mreq.getCarPlate()); //车牌
        tradeOrder.setResName("临停车["+tradeOrder.getCarPlate()+"]"); //描述
        tradeOrder.setTimeLong(mreq.getStayTime());//时长
        tradeOrder.setMchntNo(mchntNo); // 自定义商户号
        tradeOrder.setEffectTimes("3");
        tradeOrder.setOverTime("60");
        tradeOrder.setResType("parkingPre"); //业务子类型
        tradeOrder.setStates("1");
        tradeOrder.setCreatedTime(acceptDate); //创建时间
        tradeOrder.setNotifyUrl(mreq.getNotifySysUrl());  // 通知地址地址
        tradeOrder.setPayType(payType); //银行卡 自身渠道 预定义，有时第三方支付类型
        tradeOrder.setSubPayType(mreq.getAppChannelId());
        tradeOrder.setChannelNum(channelNum);// 收单渠道编码 配置商户直接配置
        tradeOrder.setTradeStatus("Created");
        return tradeOrder;
    }

    // 查询 公众号 微信 子商户参数  配置表
    private ParkingWxMchntConfig findParkingWxMchntConfig(String parkId, String appChannelId) throws SQLException {
        ParkingWxMchntConfig wxMchntConfig = new ParkingWxMchntConfig();
        wxMchntConfig.setParkId(parkId);
        wxMchntConfig.setSubPayType(appChannelId);
        wxMchntConfig = parkingWxMchntConfigDao.selectParkingWxMchntConfigById(wxMchntConfig);
        return wxMchntConfig;
    }

    // 查询 ali 子 商户配置参数
    private ParkingAliMchntConfig findParkingAliMchntConfig(String parkId, String appChannelId) throws SQLException {
        ParkingAliMchntConfig aliMchntConfig = new ParkingAliMchntConfig();
        aliMchntConfig.setParkId(parkId);
        aliMchntConfig.setSubPayType(appChannelId);
        aliMchntConfig = parkingAliMchntConfigDao.selectParkingAliMchntConfigById(aliMchntConfig);
        return aliMchntConfig;
    }

    // 查询 一级 渠道 商户配置参数
    private ParkingQrcodeMchntConfig findPreParkingQrcodeMchntConfig(String channelSelect, String mchntNo) throws SQLException {
        ParkingQrcodeMchntConfig qrcodeMchnt = new ParkingQrcodeMchntConfig();
        qrcodeMchnt.setAppChannel(channelSelect);
        qrcodeMchnt.setMchntNo(mchntNo);
        qrcodeMchnt = parkingPreMchntConfigDao.selectParkingPreMchntConfig(qrcodeMchnt);
        return  qrcodeMchnt;
    }

    // 查询 订单交易表 是否本次请求
    private ParkingTradeOrder findParkingTradeOrder(String serialNumber) throws SQLException {
        ParkingTradeOrder tradeOrder = new ParkingTradeOrder();
        tradeOrder.setMchntSysNumber(serialNumber);
        tradeOrder = parkingTradeOrderDao.selectParkingOrderByMchnt(tradeOrder);
        return  tradeOrder;
    }

    // 操作主扫 公众号渠道 理由
    private ParkingPreChannelPayRouteConfig findParkingPreChannelPayRouteConfig(String parkId) throws SQLException {
        // 检查渠道路由
        ParkingPreChannelPayRouteConfig preRoutConfig = new ParkingPreChannelPayRouteConfig();
        preRoutConfig.setParkId(parkId);
        preRoutConfig.setStates("1");
        return parkingPreChannelPayRouteConfigDao.selectParkingPreChannelPayRouteConfig(preRoutConfig);
    }

    // 置公共值
    private void setRspParams(ParkingPrePayOrderRsp mrsp, String noResult, String rspMsg) {
        mrsp.setRespCode(noResult);
        mrsp.setRespDesc(rspMsg);
    }
    //  检查业务 请求参数
    private int checkParamsIsNull(ParkingPrePayOrderReq mreq) {
        if (StringUtil.checkNullString(mreq.getAppChannelId()) ||
            StringUtil.checkNullString(mreq.getParkId()) ||
            StringUtil.checkNullString(mreq.getParkName()) ||
            StringUtil.checkNullString(mreq.getStayTime()) ||
            StringUtil.checkNullString(mreq.getTxnAmt()) ||
            StringUtil.checkNullString(mreq.getPayAmt()) ||
            StringUtil.checkNullString(mreq.getCarPlate()) ||
            StringUtil.checkNullString(mreq.getInTime()) ||
            StringUtil.checkNullString(mreq.getSerialNumber()) ||
            StringUtil.checkNullString(mreq.getParkMchntSysNumber()) ||
            StringUtil.checkNullString(mreq.getService()) ||
                StringUtil.checkNullString(mreq.getNotifySysUrl())) {
            return -1;
        }
        return 1;
    }

    // 置统一公共返回值
    private void setInitRspParams(ParkingPrePayOrderRsp mrsp, ParkingPrePayOrderReq mreq) {
        mrsp.setRespCode(RespUtil.successCode);
        mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
        mrsp.setSerialNumber(mreq.getSerialNumber());
    }
}
