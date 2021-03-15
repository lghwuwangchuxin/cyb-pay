package com.parking.qrcode.service.qrcodecb.impl;

import com.parking.domain.ParkingQrCodeCbChannelPayRouteConfig;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.ParkingAutoPayReq;
import com.parking.dto.unionpre.SwUnionOrderPayReq;
import com.parking.dto.unionpre.SwUnionOrderPayRsp;
import com.parking.dto.unionpre.SwUnionOrderQueryReq;
import com.parking.dto.unionpre.SwUnionOrderQueryRsp;
import com.parking.qrcode.service.qrcodecb.QrCodeCbSwUnionPayTradeService;
import com.parking.service.InvokeInteService;
import com.parking.service.PrSwiftUnionPayService;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.XmlUtil;
import com.parking.util.unionpay.CommSwUnionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service("qrCodeCbSwUnionPayTradeService")
public class QrCodeCbSwUnionPayTradeServiceImpl implements QrCodeCbSwUnionPayTradeService {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeCbSwUnionPayTradeServiceImpl.class);

    @Autowired
    private PrSwiftUnionPayService prSwiftUnionPayService;
    @Autowired
    private InvokeInteService invokeInteService;

    @Override
    public Object tradePay(Object... obj) throws Exception {
        logger.info("进入组装银联条码前置主扫下单报文开始---tradePay");
        ApplyOrderChannelBaseDTO dto = newApplyOrderChannel();

        SwUnionOrderPayReq sreq = new SwUnionOrderPayReq();
        sreq.setService(CommSwUnionEnum.INTERFACE_NAME03.getCode());   //订单支付

        if(obj[0] instanceof ParkingQrCodeCbChannelPayRouteConfig) {
            ParkingQrCodeCbChannelPayRouteConfig payRouteConfig = (ParkingQrCodeCbChannelPayRouteConfig) obj[0];
            sreq.setNotify_url(payRouteConfig.getNotifyUrl());
            sreq.setPayOrderUrl(payRouteConfig.getPayOrderUrl());
        }

        if(obj[1] instanceof ParkingAutoPayReq) {
            ParkingAutoPayReq applyOrderReq = (ParkingAutoPayReq) obj[1];
            sreq.setTotal_fee(applyOrderReq.getTxnAmt());   //支付金额   单位：分
            sreq.setOut_trade_no(applyOrderReq.getTradeId());
        }

        if(obj[2] instanceof ParkingQrcodeMchntConfig) {
            ParkingQrcodeMchntConfig mmchntConfig = (ParkingQrcodeMchntConfig) obj[2];
            sreq.setMch_id(mmchntConfig.getMchntId());   //商户号
            sreq.setMch_create_ip(mmchntConfig.getRsrvStr1());   //ip地址
            sreq.setBody(mmchntConfig.getRsrvStr2());    //商品描述
            sreq.setKey(mmchntConfig.getMchntPrivateKey());   //密钥
        }
        try {
            String reqXml = XmlUtil.ObjToXml(sreq, SwUnionOrderPayReq.class);
            logger.info("组装---Sw 中国银联 渠道主扫下单支付,请求参数为："+reqXml);
            String result = prSwiftUnionPayService.prUnionpayPreContent(reqXml);
            logger.info("组装---Sw 中国银联 渠道主扫下单支付,请求参数为："+result);
            Map<String,String> map = invokeInteService.parseResp(result);
            if("1".equals(map.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
                logger.info("Sw 中国银联  渠道主扫下单支付,响应参数Object→XML："+map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode()));
                SwUnionOrderPayRsp srsp = (SwUnionOrderPayRsp) XmlUtil.XmlToObj(map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode()), SwUnionOrderPayRsp.class);
                if(CommEnum.SUCCESS_COID.getRspCode().equals(srsp.getRespCode())) {
                    dto.setRespCode(CommEnum.SUCCESS_COID.getRspCode());
                    dto.setPayId(srsp.getTransaction_id());   //平台订单号
                    dto.setQrCodeUrl(srsp.getCode_url().replace("amp;",""));
                }else {
                    dto.setTradeCode(srsp.getRespCode());  //渠道返回码
                    dto.setTradeDesc(srsp.getRespDesc());   //渠道返回描述
                }
            }else {
                dto.setTradeCode(map.get(CommEnum.RESP_CODE.getRspCode()));
                dto.setTradeDesc(map.get(CommEnum.RESP_DESC.getRspCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    @Override
    public Object queryTrade(Object... obj) throws Exception {
        logger.info("进入被扫聚合主扫下单支付结果查询 【Sw 中国银联】渠道：---订单支付状态 查询");
        ApplyOrderChannelBaseDTO apporderdto = new ApplyOrderChannelBaseDTO();
        apporderdto.setRespCode(RespUtil.codeError);
        apporderdto.setTradeCode(RespUtil.codeError);
        apporderdto.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());

        SwUnionOrderQueryReq sreq = new SwUnionOrderQueryReq();
        sreq.setService(CommSwUnionEnum.INTERFACE_NAME02.getCode());    //订单支付状态查询

        if(obj[0] instanceof ParkingQrCodeCbChannelPayRouteConfig) {
            ParkingQrCodeCbChannelPayRouteConfig payRouteConfig = (ParkingQrCodeCbChannelPayRouteConfig) obj[0];
        }
        if(obj[1] instanceof ParkingQrcodeMchntConfig) {
            ParkingQrcodeMchntConfig mchntConfig = (ParkingQrcodeMchntConfig) obj[1];
            sreq.setMch_id(mchntConfig.getMchntId());   // 商户号
            sreq.setKey(mchntConfig.getMchntPrivateKey());   //密钥
        }
        if(obj[2] instanceof ParkingTradeOrder) {
            ParkingTradeOrder parkingTradeOrder = (ParkingTradeOrder) obj[2];
            sreq.setOut_trade_no(parkingTradeOrder.getOrderId());  //订单号
            sreq.setTransaction_id(parkingTradeOrder.getOutTradeNo());     //平台号
            sreq.setSerialNumber(parkingTradeOrder.getMchntSysNumber());    //流水号
        }
        try {
            String reqXml = XmlUtil.ObjToXml(sreq, SwUnionOrderQueryReq.class);
            logger.info("组装---Sw 中国银联渠道订单支付状态 查询,请求参数为："+reqXml);
            String result = prSwiftUnionPayService.prUnionpayPreContent(reqXml);
            logger.info("组装---Sw 中国银联渠道订单支付状态 查询,请求参数为："+result);
            Map<String,String> map = invokeInteService.parseResp(result);
            if("1".equals(map.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
                logger.info("Sw 中国银联渠道订单支付状态查询,响应参数Object→XML："+map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode()));
                SwUnionOrderQueryRsp srsp = (SwUnionOrderQueryRsp) XmlUtil.XmlToObj(map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode()), SwUnionOrderQueryRsp.class);
                if("SUCCESS".equals(srsp.getTrade_state())) {
                    apporderdto.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
                    apporderdto.setRespCode(CommEnum.SUCCESS_COID.getRspCode());
                    apporderdto.setTradeCode(srsp.getRespCode());
                    apporderdto.setTradeDesc(srsp.getRespDesc());
                    apporderdto.setPayId(srsp.getTransaction_id());   //平台订单号
                    apporderdto.setPayTime(srsp.getTime_end());   //支付完成时间
                    apporderdto.setPayType(getPayTypes(srsp.getTrade_type()));
                }else {
                    apporderdto.setTradeCode(srsp.getRespCode());  //渠道返回码
                    apporderdto.setTradeDesc(srsp.getRespDesc());   //渠道返回描述
                }
            }else {
                apporderdto.setTradeCode(map.get(CommEnum.RESP_CODE.getRspCode()));
                apporderdto.setTradeDesc(map.get(CommEnum.RESP_DESC.getRspCode()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apporderdto;
    }

    private String getPayTypes(String trade_type) {
        if ("pay.alipay.native".equals(trade_type)) {
            return "12";
        } else if ("pay.weixin.jspay".equals(trade_type)) {
            return "13";
        } else {
            return "14";
        }
    }

    private ApplyOrderChannelBaseDTO newApplyOrderChannel() {
        ApplyOrderChannelBaseDTO instance = new ApplyOrderChannelBaseDTO();
        instance.setRespCode(RespUtil.codeError);
        instance.setTradeCode(RespUtil.codeError);
        instance.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
        return instance;
    }
}
