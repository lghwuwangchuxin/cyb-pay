package com.parking.service.impl;

import com.parking.dao.ParkingQrCodeCbChannelPayRouteConfigDao;
import com.parking.dao.ParkingQrCodeCbMchntConfigDao;
import com.parking.dao.ParkingTradeOrderDao;
import com.parking.domain.ParkingQrCodeCbChannelPayRouteConfig;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.*;
import com.parking.qrcode.service.qrcodecb.QrCodeCbSwUnionPayTradeService;
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

@Service("parkingQrCodeCbPayTradeOrderService")
public class ParkingQrCodeCbPayTradeOrderServiceImpl implements ParkingQrCodeCbPayTradeOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ParkingQrCodeCbPayTradeOrderServiceImpl.class);

    @Autowired
    private SeqService seqService;
    @Autowired
    private ParkingTradeOrderDao parkingTradeOrderDao;
    @Autowired
    private ParkingQrCodeCbChannelPayRouteConfigDao parkingQrCodeCbChannelPayRouteConfigDao;
    @Autowired
    private ParkingQrCodeCbMchntConfigDao parkingQrcodeCbMchntConfigDao;
    @Autowired
    private PrPosTongService prPosTongService;
    @Autowired
    private ParkingChannelQueryOrderService parkingChannelQueryOrderService;
    @Autowired
    private PrHxbQrCodeService prHxbQrCodeService;
    @Autowired
    private QrCodeCbChannelPayTradeProxyService qrCodeCbChannelPayTradeProxyService;


    @Override
    public ParkingAutoPayRsp payCbTrade(ParkingAutoPayReq mreq) throws Exception {
        logger.info("进入二维码聚合主扫下单服务————————————————payCbTrade");
        ParkingAutoPayRsp mrsp = new ParkingAutoPayRsp();
        setRspParams(mrsp, RespUtil.codeError, CommEnum.RESP_DESC_ERROR.getRspMsg());
        mrsp.setSerialNumber(mreq.getSerialNumber());

        // 检查 参数
        if (checkIsNull(mreq) < 0) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.IS_NULL.getRspMsg());
            return mrsp;
        }

        // 检查  主扫 二维码 渠道路由
        ParkingQrCodeCbChannelPayRouteConfig parkingQrCodeCbChannelPayRouteConfig = findParkingQrCodeCbChannelPayRouteConfig(mreq.getTermId(), mreq.getParkId());
        if (null == parkingQrCodeCbChannelPayRouteConfig) {
            setRspParams(mrsp, RespUtil.noResult, "未配置终端渠道路由");
            return mrsp;
        }

        // 检查 商户参数
        ParkingQrcodeMchntConfig parkingQrcodeMchntConfig = findParkingQrcodeMchntConfig(parkingQrCodeCbChannelPayRouteConfig.getChannelSelect(), parkingQrCodeCbChannelPayRouteConfig.getMchntNo());
        if (null == parkingQrcodeMchntConfig) {
            setRspParams(mrsp, RespUtil.noResult, "渠道商户未配置");
            return mrsp;
        }
        setDecodeDnicode(mreq); //解码
        mreq.setMchntId(parkingQrcodeMchntConfig.getMchntId());

        // 检查 本次订单流水号
        ParkingTradeOrder tradeOrder = findParkingTradeOrder(mreq.getSerialNumber());
        if (null != tradeOrder) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.ORDER_GENE_EXCEPTION_DESC.getRspMsg());
            return mrsp;
        }

        // 开始 存储订单流失
        String tradeId= seqService.getTradeSequenceId("TRADE_ID");
        //组装 银商渠道 二维码 下单 参数
        if (StringUtil.checkStringsEqual(CommEnum.MIS_POSTPAY_CODE.getRspCode(), parkingQrCodeCbChannelPayRouteConfig.getChannelSelect())) {
           //组装 下单数据
            tradeId = parkingQrcodeMchntConfig.getRsrvStr2()+tradeId;
            String reqXml = geneQrCodeCbTrade(mreq, parkingQrCodeCbChannelPayRouteConfig, parkingQrcodeMchntConfig, tradeId);
            String rspXml = prPosTongService.yshangPayCbTrade(reqXml);
            logger.info("银商子系统二维码下单返回：" +rspXml);
            if (StringUtil.checkNullString(rspXml)) {
                setRspParams(mrsp, RespUtil.timeOut, CommEnum.REQ_OVER_TIME_ERROR_DESC.getRspMsg());
                return mrsp;
            }

            WxAliYuionpayOrderPayRsp payRsp = (WxAliYuionpayOrderPayRsp) XmlUtil.XmlToObj(rspXml, WxAliYuionpayOrderPayRsp.class);
            if (null == payRsp || !StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), payRsp.getRespCode())) {
                setRspParams(mrsp, payRsp.getRespCode(), payRsp.getRespDesc());
                return mrsp;
            }
            mrsp.setQrCodeUrl(payRsp.getBillQRCode());
        } else if (CommEnum.HXBPAY_CODE.getRspCode().equals(parkingQrCodeCbChannelPayRouteConfig.getChannelSelect())) {
            //  华夏 银行渠道
            // 组装 华夏银行报文  订单号截取保证是10位
            //tradeId = ConfigUtil.getValue("_PREFIX") +tradeId.substring(10); // 截取订单号
            String reqXml = geneHxCbTrade(mreq,parkingQrCodeCbChannelPayRouteConfig, parkingQrcodeMchntConfig,tradeId);
            String rspXml = prHxbQrCodeService.cbPayTrade(reqXml);
            logger.info("华夏银行渠道主扫返回报文：" +rspXml);
            if (StringUtil.checkNullString(rspXml)) {
                setRspParams(mrsp, RespUtil.timeOut, CommEnum.REQ_OVER_TIME_ERROR_DESC.getRspMsg());
                return mrsp;
            }
            HxbScanPayRsp payRsp = (HxbScanPayRsp) XmlUtil.XmlToObj(rspXml, HxbScanPayRsp.class);
            if (null == payRsp || !StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), payRsp.getRespCode())) {
                setRspParams(mrsp, payRsp.getRespCode(), payRsp.getRespDesc());
                return mrsp;
            }
            mrsp.setQrCodeUrl(payRsp.getQrCode());
        } else if (QrCodeMapUtils.checkChannelType(parkingQrCodeCbChannelPayRouteConfig.getChannelSelect())){ // 多渠道 主扫

            try {
                mreq.setTradeId(tradeId);
                parkingQrCodeCbChannelPayRouteConfig.setService("qrcodeTradePay");
                ApplyOrderChannelBaseDTO dto  = qrCodeCbChannelPayTradeProxyService.payTradeService(mreq, parkingQrCodeCbChannelPayRouteConfig, parkingQrcodeMchntConfig);
                if (null != dto && StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), dto.getRespCode())) {
                    mrsp.setQrCodeUrl(dto.getQrCodeUrl());
                } else {
                    setRspParams(mrsp, RespUtil.timeOut, CommEnum.ORDER_FAIL_DESC.getRspMsg());
                    return mrsp;
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("下单异常：" +e.getMessage());
            }
        }

        // 本地受理时
        String acceptDate = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode()).format(new Date());
        int iResult = parkingTradeOrderDao.insertParkingOrder(insertParkingTradeOrder(mreq, tradeId, acceptDate, parkingQrcodeMchntConfig.getMchntName(), parkingQrcodeMchntConfig.getMchntNo(), parkingQrcodeMchntConfig.getAppChannel(), mrsp.getQrCodeUrl(), parkingQrcodeMchntConfig.getRsrvStr6()));
        if (iResult == 0) {
            setRspParams(mrsp, RespUtil.dberror, "订单处理异常");
            return mrsp;
        }
        mrsp.setTradeStatus(CommEnum.PAY_ORDER_CREATED.getRspCode());
        mrsp.setOrderId(tradeId);
        setRspParams(mrsp, RespUtil.successCode, "二维码下单成功");

        // 开启线程 池处理
        QrCodeH5CbQueryPayStatesServiceThread qrCodeH5CbQueryPayStatesServiceThread = new QrCodeH5CbQueryPayStatesServiceThread();
        qrCodeH5CbQueryPayStatesServiceThread.setParkingChannelQueryOrderService(parkingChannelQueryOrderService);
        qrCodeH5CbQueryPayStatesServiceThread.setPayNotifyReq(getPayNotifyReq(mreq, tradeId, parkingQrcodeMchntConfig.getAppChannel(), parkingQrcodeMchntConfig.getRsrvStr6()));
        qrCodeH5CbQueryPayStatesServiceThread.setDelayTime(parkingQrCodeCbChannelPayRouteConfig.getDelayTime());
        qrCodeH5CbQueryPayStatesServiceThread.setQueryReq(getQueryOrder(tradeId));
        QrCodeQueryPayStatesServiceThreadPool.executeThread(qrCodeH5CbQueryPayStatesServiceThread);
        return mrsp;
    }

    // 组装 华夏 银行报文
    private String geneHxCbTrade(ParkingAutoPayReq mreq, ParkingQrCodeCbChannelPayRouteConfig parkingQrCodeCbChannelPayRouteConfig, ParkingQrcodeMchntConfig parkingQrcodeMchntConfig, String tradeId) throws JAXBException {
        HxbScanPayReq spReq = new HxbScanPayReq();
        spReq.setService(CommonHxbPayQrCodePayEnum.HXB_CB_SERVICE.getRspCode());
        spReq.setAppKey(parkingQrcodeMchntConfig.getMchntAppId());  // 密钥 appid
        spReq.setMerchantNo(parkingQrcodeMchntConfig.getMchntId()); // 商户号
        spReq.setSubject(parkingQrcodeMchntConfig.getRsrvStr1()); // 订单标题
        spReq.setTradeType(parkingQrcodeMchntConfig.getRsrvStr2()); // 请求交易码
        spReq.setOrderNo(tradeId); // 商户订单号
        spReq.setAmount(mreq.getPayAmt());
        spReq.setPayOrderUrl(parkingQrCodeCbChannelPayRouteConfig.getPayOrderUrl());
        spReq.setNotifyUrl(parkingQrCodeCbChannelPayRouteConfig.getNotifyUrl());
        String reqXml = XmlUtil.ObjToXml(spReq, HxbScanPayReq.class);

        return reqXml;
    }

    // 中文名称解码
    private void setDecodeDnicode(ParkingAutoPayReq mreq) {
        mreq.setCarPlate(Utility.decodeUnicode(mreq.getCarPlate()));
        mreq.setParkName(Utility.decodeUnicode(mreq.getParkName()));
        mreq.setStayTime(Utility.decodeUnicode(mreq.getStayTime()));
    }

    // 组装 查询 报文
    private ParkingTradeOrderQueryReq getQueryOrder(String tradeId) {
        ParkingTradeOrderQueryReq queryReq = new ParkingTradeOrderQueryReq();
        queryReq.setOrderId(tradeId);
        return  queryReq;
    }

    // 通知报文
    private PayNotifyReq getPayNotifyReq(ParkingAutoPayReq mreq, String tradeId, String payType, String channelNum) {
        PayNotifyReq  payNotifyReq = new PayNotifyReq();
        payNotifyReq.setService("payNotifyResult");
        payNotifyReq.setResType("parkingCb");
        payNotifyReq.setCarPlate(mreq.getCarPlate());
        payNotifyReq.setParkId(mreq.getParkId());
        payNotifyReq.setParkName(mreq.getParkName());
        payNotifyReq.setOrderId(tradeId);
        payNotifyReq.setPayType(String.valueOf(ParkingChannelPayType.getDecsitPayTypeMap().get(payNotifyReq.getResType())));
        //payNotifyReq.setPayChannel(ParkingChannelPayType.getDecsitChannelPayTypeMap().get());
        payNotifyReq.setChannelNum(channelNum);
        payNotifyReq.setTxnAmt(mreq.getTxnAmt());
        payNotifyReq.setPayAmt(mreq.getPayAmt());
        payNotifyReq.setSerialNumber(mreq.getSerialNumber());
        payNotifyReq.setNotifySysUrl(mreq.getNotifySysUrl());
        payNotifyReq.setParkMchntSysNumber(mreq.getParkMchntSysNumber());

        payNotifyReq.setStayTime(mreq.getStayTime());
        return payNotifyReq;
    }

    private String geneQrCodeCbTrade(ParkingAutoPayReq mreq, ParkingQrCodeCbChannelPayRouteConfig parkingQrCodeCbChannelPayRouteConfig, ParkingQrcodeMchntConfig parkingQrcodeMchntConfig, String tradeId) throws JAXBException {
        WxAliYuionpayOrderPayReq payReq = new WxAliYuionpayOrderPayReq();
        payReq.setAppId(parkingQrcodeMchntConfig.getMchntAppId());// 银商
        payReq.setAppKey(parkingQrcodeMchntConfig.getMchntHtreeKey());//
        payReq.setMerOrderId(tradeId);
        payReq.setMid(parkingQrcodeMchntConfig.getMchntId()); // 商户号
        payReq.setTid(parkingQrcodeMchntConfig.getMchntHtreeNo()); // 终端号
        payReq.setInstMid(parkingQrcodeMchntConfig.getRsrvStr1());// 业务类型
        payReq.setTotalAmount(mreq.getPayAmt());
        payReq.setSerialNumber(mreq.getSerialNumber());
        payReq.setPayOrderUrl(parkingQrCodeCbChannelPayRouteConfig.getPayOrderUrl());
        payReq.setNotifyUrl(parkingQrCodeCbChannelPayRouteConfig.getNotifyUrl());
        String reqXml = XmlUtil.ObjToXml(payReq, WxAliYuionpayOrderPayReq.class);
        return reqXml;
    }

    private ParkingQrcodeMchntConfig findParkingQrcodeMchntConfig(String channelSelect, String mchntNo) throws SQLException {
        ParkingQrcodeMchntConfig parkingQrcodeMchntConfig = new ParkingQrcodeMchntConfig();
        parkingQrcodeMchntConfig.setAppChannel(channelSelect);
        parkingQrcodeMchntConfig.setMchntNo(mchntNo);
        parkingQrcodeMchntConfig = parkingQrcodeCbMchntConfigDao.selectParkingCbMchntConfig(parkingQrcodeMchntConfig);
        return parkingQrcodeMchntConfig;

    }

    private ParkingTradeOrder insertParkingTradeOrder(
            ParkingAutoPayReq mreq, String tradeId, String acceptDate, String mchntName, String mchntNo, String appChannelId, String qrCodeUrl, String channelNum) {
        ParkingTradeOrder  tradeOrder = new ParkingTradeOrder();
        tradeOrder.setTradeId(tradeId); //交易单号
        tradeOrder.setOrderId(tradeId); //交易订单号
        tradeOrder.setTxnAmt(mreq.getTxnAmt()); //交易金额  车易泊返回
        tradeOrder.setPayAmt(mreq.getPayAmt()); //支付金额
        tradeOrder.setMchntSysNumber(mreq.getSerialNumber()); // 本系统唯一 请求流水号
        tradeOrder.setParkMchntSysNumber(mreq.getParkMchntSysNumber()); // 进出场流水号
        tradeOrder.setAcceptMonth(acceptDate.substring(5, 7)); //月份
        tradeOrder.setMchntId(mreq.getMchntId()); // 渠道 商户id
        tradeOrder.setMchntName(mchntName); //商户名称
        tradeOrder.setParkId(mreq.getParkId());  //停车场id
        tradeOrder.setParkName(mreq.getParkName()); // 停车场名称
        tradeOrder.setCarPlate(mreq.getCarPlate()); //车牌
        tradeOrder.setInTime(mreq.getInTime()); // 入场时间
        tradeOrder.setOutTime(mreq.getOutTime());// 出场时间
        tradeOrder.setResName("主扫停车["+tradeOrder.getCarPlate()+"]"); //描述
        tradeOrder.setTimeLong(mreq.getStayTime());//时长
        tradeOrder.setTermId(mreq.getTermId());
        tradeOrder.setMchntNo(mchntNo); // 自定义商户id
        tradeOrder.setEffectTimes("1");
        tradeOrder.setOverTime("60");
        tradeOrder.setResType("parkingCb"); //业务子类型
        tradeOrder.setQrNo(qrCodeUrl);
        tradeOrder.setStates("1");
        tradeOrder.setCreatedTime(acceptDate); //创建时间
        tradeOrder.setNotifyUrl(mreq.getNotifySysUrl());  // 通知云端地址
        tradeOrder.setPayType(appChannelId); //银行卡 自身渠道 预定义，有时第三方支付类型
        tradeOrder.setChannelNum(channelNum);
        tradeOrder.setTradeStatus("Created");
        return tradeOrder;
    }

    // 查询 订单交易表 是否本次请求
    private ParkingTradeOrder findParkingTradeOrder(String serialNumber) throws SQLException {
        ParkingTradeOrder tradeOrder = new ParkingTradeOrder();
        tradeOrder.setMchntSysNumber(serialNumber);
        tradeOrder = parkingTradeOrderDao.selectParkingOrderByMchnt(tradeOrder);
        return  tradeOrder;
    }

    private ParkingQrCodeCbChannelPayRouteConfig findParkingQrCodeCbChannelPayRouteConfig(String termId, String parkId) throws SQLException {
        ParkingQrCodeCbChannelPayRouteConfig payRouteConfig = new ParkingQrCodeCbChannelPayRouteConfig();
        payRouteConfig.setTermId(termId);
        payRouteConfig.setParkId(parkId);
        payRouteConfig.setStates("1");
        payRouteConfig = parkingQrCodeCbChannelPayRouteConfigDao.selectParkingQrCodeCbChannelPayRouteConfig(payRouteConfig);
        return payRouteConfig;
    }

    // 检查 参数
    private int checkIsNull(ParkingAutoPayReq mreq) {
       if (StringUtil.checkNullString(mreq.getParkMchntSysNumber()) ||
           StringUtil.checkNullString(mreq.getParkName()) ||
           StringUtil.checkNullString(mreq.getParkId())||
           StringUtil.checkNullString(mreq.getTxnAmt()) ||
           StringUtil.checkNullString(mreq.getPayAmt()) ||
           StringUtil.checkNullString(mreq.getCarPlate()) ||
           StringUtil.checkNullString(mreq.getInTime()) ||
           StringUtil.checkNullString(mreq.getOutTime()) ||
           StringUtil.checkNullString(mreq.getSerialNumber()) ||
           StringUtil.checkNullString(mreq.getTermId()) ||
           StringUtil.checkNullString(mreq.getNotifySysUrl()) ||
           StringUtil.checkNullString(mreq.getStayTime())) {
            return  -1;
       }
        return 1;
    }

    private void setRspParams(ParkingAutoPayRsp mrsp, String codeError, String respDescError) {
        mrsp.setRespDesc(respDescError);
        mrsp.setRespCode(codeError);
    }
}
