package com.parking.qrcode.service.prejs.impl;

import com.parking.domain.ParkingPreChannelPayRouteConfig;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.ParkingPrePayOrderReq;
import com.parking.dto.WxH5PayInfo;
import com.parking.dto.unionpre.SwUnionOrderPayReq;
import com.parking.dto.unionpre.SwUnionOrderPayRsp;
import com.parking.dto.unionpre.SwUnionOrderQueryReq;
import com.parking.dto.unionpre.SwUnionOrderQueryRsp;
import com.parking.qrcode.service.prejs.QrCodeH5SwUnionPayTradeService;
import com.parking.service.InvokeInteService;
import com.parking.service.PrSwiftUnionPayService;
import com.parking.util.*;
import com.parking.util.unionpay.CommSwUnionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 *  银联条码前置 h5 下单
 */
@Service("qrCodeH5SwUnionPayTradeService")
public class QrCodeH5SwUnionPayTradeServiceImpl implements QrCodeH5SwUnionPayTradeService {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeH5SwUnionPayTradeServiceImpl.class);

    @Autowired
    private PrSwiftUnionPayService prSwiftUnionPayService;
    @Autowired
    private InvokeInteService invokeInteService;

    @Override
    public Object tradePay(Object... obj) throws Exception {
        logger.info("进入组装银联条码前置h5统一下单报文开始---tradePay");
        ApplyOrderChannelBaseDTO dto = new ApplyOrderChannelBaseDTO();
        dto.setRespCode(RespUtil.codeError);
        dto.setTradeCode(RespUtil.codeError);
        dto.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());

        SwUnionOrderPayReq sreq = new SwUnionOrderPayReq();
        sreq.setService(CommSwUnionEnum.INTERFACE_NAME04.getCode());   // H5订单支付

        if(obj[0] instanceof ParkingPreChannelPayRouteConfig) {
            ParkingPreChannelPayRouteConfig payRouteConfig = (ParkingPreChannelPayRouteConfig) obj[0];
            sreq.setNotify_url(payRouteConfig.getNotifyUrl());
            sreq.setPayOrderUrl(payRouteConfig.getPayOrderUrl());
        }

        if(obj[1] instanceof ParkingPrePayOrderReq) {
            ParkingPrePayOrderReq prePayOrderReq = (ParkingPrePayOrderReq) obj[1];
            sreq.setTotal_fee(prePayOrderReq.getTxnAmt());   //支付金额   单位：分
            sreq.setOut_trade_no(prePayOrderReq.getTradeId());
            sreq.setTrade_type(prePayOrderReq.getAppChannelId());
            if ("13".equals(prePayOrderReq.getAppChannelId())) {
                sreq.setSub_appid(prePayOrderReq.getSubAppId());
                sreq.setSub_openid(prePayOrderReq.getUserId());
            } else if ("14".equals(prePayOrderReq.getAppChannelId())) {
                sreq.setCustomer_ip("");
            }
        }

        if(obj[2] instanceof ParkingQrcodeMchntConfig) {
            ParkingQrcodeMchntConfig mmchntConfig = (ParkingQrcodeMchntConfig) obj[2];
            sreq.setMch_id(mmchntConfig.getMchntId());   //商户号
            sreq.setMch_create_ip(mmchntConfig.getRsrvStr1());   //ip地址
            sreq.setCustomer_ip(mmchntConfig.getRsrvStr1());
            sreq.setBody(mmchntConfig.getRsrvStr2());    //商品描述
            sreq.setKey(mmchntConfig.getMchntPrivateKey());   //密钥
        }
        try {
            String reqXml = XmlUtil.ObjToXml(sreq, SwUnionOrderPayReq.class);
            logger.info("组装---Sw 中国银联条码前置h5下单支付,请求参数为："+reqXml);
            String result = prSwiftUnionPayService.prUnionpayPreContent(reqXml);
            logger.info("组装---Sw 中国银联条码前置h5下单支付,请求参数为："+result);
            Map<String,String> map = invokeInteService.parseResp(result);
            if("1".equals(map.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
                logger.info("Sw 中国银联条码前置h5下单支付,响应参数Object→XML："+map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode()));
                SwUnionOrderPayRsp srsp = (SwUnionOrderPayRsp) XmlUtil.XmlToObj(map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode()), SwUnionOrderPayRsp.class);
                if(CommEnum.SUCCESS_COID.getRspCode().equals(srsp.getRespCode())) {
                    dto.setRespCode(CommEnum.SUCCESS_COID.getRspCode());
                    dto.setPayId(srsp.getTransaction_id());   //平台订单号
                    String payInfoParams = getPayInfoParams(sreq.getTrade_type(), srsp);
                    logger.info("银联条码前置支付h5支付参数：" +payInfoParams);
                    dto.setH5PayParams(payInfoParams);
                    dto.setFrowdToRediToInput(CommEnum.H5_PAY_METHO_2.getRspCode());
                    //dto.setH5PayParams(null != srsp.getPay_info() ? srsp.getPay_info() : srsp.getPay_url());
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

    private String getPayInfoParams(String tradeType, SwUnionOrderPayRsp srsp) throws IOException {
        if ("12".equals(tradeType)) {
            // 读取本地 替换参数
            WxH5PayInfo wxH5PayInfo = (WxH5PayInfo) FastJSONUtil.parseObject(srsp.getPay_info(), WxH5PayInfo.class);
            String aliJs = getAliPayJs(wxH5PayInfo);
            return aliJs;
        } else if ("13".equals(tradeType)) {
           // 读取本地 替换参数
            WxH5PayInfo wxH5PayInfo = (WxH5PayInfo) FastJSONUtil.parseObject(srsp.getPay_info(), WxH5PayInfo.class);
            String wxJs = getWxjsRep(wxH5PayInfo);
            return wxJs;
        } else if ("14".equals(tradeType)) {
             return srsp.getPay_url();
        }
        return null;
    }

    /**
     *  支付宝 js 文件 字符替换
     * @param wxH5PayInfo
     * @return
     * @throws IOException
     */
    private String getAliPayJs(WxH5PayInfo wxH5PayInfo) throws IOException {
        String alijs = FileUtil.readResourceFile("aliPayOrder")
                .replace("${orde}", wxH5PayInfo.getTradeNO());
        return alijs;
    }

    /**
     *  微信 js 文件 字符替换
     * @param wxH5PayInfo
     * @return
     * @throws IOException
     */
    private String getWxjsRep(WxH5PayInfo wxH5PayInfo) throws IOException {
        String wxJs = FileUtil.readResourceFile("wxpayOrder")
                .replace("${appId}", wxH5PayInfo.getAppId())
                .replace("${timeStamp}", wxH5PayInfo.getTimeStamp())
                .replace("${nonceStr}", wxH5PayInfo.getNonceStr())
                .replace("${packageWeChat}", wxH5PayInfo.getPackages())
                .replace("${paySign}", wxH5PayInfo.getPaySign())
                .replace("${signType}", wxH5PayInfo.getSignType());
        return wxJs;
    }

    @Override
    public Object queryTrade(Object... obj) throws Exception {
        logger.info("进入银联条码h5下单支付结果查询 【Sw 中国银联】渠道：---订单支付状态 查询");
        ApplyOrderChannelBaseDTO apporderdto = new ApplyOrderChannelBaseDTO();
        apporderdto.setRespCode(RespUtil.codeError);
        apporderdto.setTradeCode(RespUtil.codeError);
        apporderdto.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());

        SwUnionOrderQueryReq sreq = new SwUnionOrderQueryReq();
        sreq.setService(CommSwUnionEnum.INTERFACE_NAME02.getCode());    //订单支付状态查询

        if(obj[0] instanceof ParkingPreChannelPayRouteConfig) {
            ParkingPreChannelPayRouteConfig payRouteConfig = (ParkingPreChannelPayRouteConfig) obj[0];
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
            logger.info("组装---Sw 中国银联条码前置h5订单支付状态 查询,请求参数为："+reqXml);
            String result = prSwiftUnionPayService.prUnionpayPreContent(reqXml);
            logger.info("组装---Sw 中国银联条码前置h5订单支付状态 查询,请求参数为："+result);
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
}
