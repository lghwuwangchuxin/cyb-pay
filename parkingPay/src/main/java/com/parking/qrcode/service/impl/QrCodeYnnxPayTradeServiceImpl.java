package com.parking.qrcode.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.ApplyOrderReq;
import com.parking.dto.QueryAtmResultReq;
import com.parking.dto.ynnx.BeCodeUnionPayReq;
import com.parking.dto.ynnx.BeCodeUnionPayRsp;
import com.parking.dto.ynnx.QueryTradeReq;
import com.parking.dto.ynnx.QueryTradeRsp;
import com.parking.qrcode.service.QrCodeYnnxPayTradeService;
import com.parking.service.InvokeInteService;
import com.parking.service.PrYxBeCodeService;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

@Service("qrCodeYnnxPayTradeService")
public class QrCodeYnnxPayTradeServiceImpl implements QrCodeYnnxPayTradeService {


    private static final Logger logger= LoggerFactory.getLogger(QrCodeYnnxPayTradeServiceImpl.class);

    @Inject
    private InvokeInteService invokeInteService;
    @Autowired
    private PrYxBeCodeService prYxBeCodeService;

    @Override
    public Object tradePay(Object... obj) throws Exception {
        logger.info("进入云南农信被扫支付下单");
        ApplyOrderChannelBaseDTO applyOrderChannelBaseDTO = new ApplyOrderChannelBaseDTO();
        applyOrderChannelBaseDTO.setRespCode(RespUtil.codeError);
        applyOrderChannelBaseDTO.setTradeCode(RespUtil.noResult);
        applyOrderChannelBaseDTO.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());

        BeCodeUnionPayReq beCodeUnionPayReq = new BeCodeUnionPayReq();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonGodds = new JSONObject();
        jsonGodds.put("goodsName", "停车支付"); //商户名称
        jsonGodds.put("goodsSelectNum", "1"); //商户数量
        jsonGodds.put("goodsCode", "0000000"); //商品条形吗

        beCodeUnionPayReq.setOrderDesc("停车场被扫支付");
        beCodeUnionPayReq.setService("BeCodeUnionPay");
        if(obj[0] instanceof ApplyOrderReq) {
            ApplyOrderReq applyOrderReq = (ApplyOrderReq) obj[0];
            beCodeUnionPayReq.setSerialNumber(applyOrderReq.getSerialNumber());
            jsonGodds.put("goodsPrice", applyOrderReq.getPayAmt()); //商品单价
            beCodeUnionPayReq.setAmt(applyOrderReq.getPayAmt()) ;//支付金额
            beCodeUnionPayReq.setQrNo(applyOrderReq.getQrCodeConTent());//二维码内容
            beCodeUnionPayReq.setPayNb(QrCodeChannelUtil.getYnnxSubPayType(applyOrderReq.getRecCode())); //支付类型
        }
        if(obj[1] instanceof ParkingQrcodeMchntConfig) {
            ParkingQrcodeMchntConfig mchntConfig = (ParkingQrcodeMchntConfig) obj[1];
            beCodeUnionPayReq.setTermId(mchntConfig.getRsrvStr3()); //对应渠道终端号
            beCodeUnionPayReq.setApproveId(mchntConfig.getMchntId()); //商户号
            beCodeUnionPayReq.setMerName(mchntConfig.getMchntName());
            beCodeUnionPayReq.setOperatorAccount(mchntConfig.getRsrvStr1()); //操作账号
            beCodeUnionPayReq.setOperatorNickName(mchntConfig.getRsrvStr2());//操作名称
        }
        if(obj[2] instanceof ParkingTradeOrder) {
            ParkingTradeOrder parkingTradeOrder = (ParkingTradeOrder) obj[2];
            //or.setOutTradeNo(parkingTradeOrder.getTradeId()); 		//订单号
        }
        jsonArray.add(jsonGodds);
        beCodeUnionPayReq.setGoods(jsonArray.toJSONString()); //JSON串
        String xmlPayReq = XmlUtil.ObjToXml(beCodeUnionPayReq, BeCodeUnionPayReq.class);
        logger.info("调用云南农信被扫支付请求报文:" +xmlPayReq);
        try {
            String reqXml = prYxBeCodeService.prYnnxContent(xmlPayReq);
            Map<String, String> respMap= invokeInteService.parseResp(reqXml);
            BeCodeUnionPayRsp beCodeUnionPayRsp = null;
            if ("1".equals(respMap.get("tag"))) { //成功
                String resultMsg = (String) respMap.get("msg");
                logger.info("解析云南农信报文:" +resultMsg);
                beCodeUnionPayRsp = (BeCodeUnionPayRsp) XmlUtil.XmlToObj(resultMsg, BeCodeUnionPayRsp.class);
                applyOrderChannelBaseDTO.setTradeCode(beCodeUnionPayRsp.getCode()); // 渠道返回码
                applyOrderChannelBaseDTO.setTradeDesc(beCodeUnionPayRsp.getMgs()); //渠道返回描述
                // 0 成功 1失败 2 已撤销
                //String status = !StringUtil.checkNullString(beCodeUnionPayRsp.getStatus()) ? beCodeUnionPayRsp.getStatus() : ""; //渠道返回状态
                String payTime = !StringUtil.checkNullString(beCodeUnionPayRsp.getOrderTime()) ? beCodeUnionPayRsp.getOrderTime() : "";//订单支付时间
                String outTradeNo = !StringUtil.checkNullString(beCodeUnionPayRsp.getOrderNo()) ? beCodeUnionPayRsp.getOrderNo() : ""; //渠道方订单号
                // state 成功3   失败5  14 查询   ，无论何种状态 都需查询， 当
                //state = StringUtil.checkNullString(outTradeNo) ? "5" : "14";
                applyOrderChannelBaseDTO.setPayId(outTradeNo); 		//第三方订单号
                applyOrderChannelBaseDTO.setPayTime(payTime); 		//支付时间
            } else  { //失败
                applyOrderChannelBaseDTO.setRespCode(respMap.get(CommEnum.RESP_CODE.getRspCode()));
                applyOrderChannelBaseDTO.setTradeDesc(respMap.get(CommEnum.RESP_DESC.getRspCode()));
            }
        } catch (Exception e) {
           e.printStackTrace();
        }

        return applyOrderChannelBaseDTO;
    }

    @Override
    public Object queryTrade(Object... obj) throws Exception {
        logger.info("进入云南农信被扫支付支付结果查询");
        ApplyOrderChannelBaseDTO applyOrderChannelBaseDTO = new ApplyOrderChannelBaseDTO();
        applyOrderChannelBaseDTO.setRespCode(RespUtil.codeError);
        applyOrderChannelBaseDTO.setTradeCode(RespUtil.noResult);
        applyOrderChannelBaseDTO.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());

        QueryTradeReq queryTradeReq = new QueryTradeReq();

        if(obj[0] instanceof QueryAtmResultReq) {
            QueryAtmResultReq queryAtmResultReq = (QueryAtmResultReq) obj[0];
            queryTradeReq.setSerialNumber(queryAtmResultReq.getSerialNumber());
        }

        if(obj[1] instanceof ParkingQrcodeMchntConfig) {
            ParkingQrcodeMchntConfig mchntConfig = (ParkingQrcodeMchntConfig) obj[1];
            queryTradeReq.setApproveId(mchntConfig.getMchntId()); //渠道方商户号
        }
        if(obj[2] instanceof ParkingTradeOrder) {
            ParkingTradeOrder parkingTradeOrder = (ParkingTradeOrder) obj[2];
            queryTradeReq.setOrderNo(parkingTradeOrder.getOutTradeNo()); //第三方渠道订单号
            queryTradeReq.setPayNb(getYnnxSubPayType(parkingTradeOrder.getSubPayType()));  //二维码类型
        }

        try{

            queryTradeReq.setService(CommEnum.PARKING_YUNNPAY_QUERY_ORDER_BY_POST_SERVICE.getRspCode()); //查询
            String xmlQueryReq = XmlUtil.ObjToXml(queryTradeReq, QueryTradeReq.class);
            logger.info("调用云南农信订单查询请求报文:"+xmlQueryReq);
            String reqXml = prYxBeCodeService.prYnnxContent(xmlQueryReq);
            Map<String, String> respMap= invokeInteService.parseResp(reqXml);
            QueryTradeRsp queryTradeRsp = null;
            if (CommEnum.TAG_SUECCESS.getRspCode().equals(respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {  // 查询交易状态
                String resultXml = (String) respMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
                logger.info("调用云南农信订单查询返回报文:" +resultXml);
                queryTradeRsp = (QueryTradeRsp) XmlUtil.XmlToObj(resultXml, QueryTradeRsp.class);
                if (StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), queryTradeRsp.getCode()) && StringUtil.checkStringsEqual("0", queryTradeRsp.getOrderStatus())) {
                    applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
                    applyOrderChannelBaseDTO.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
                    applyOrderChannelBaseDTO.setRespCode(CommEnum.SUCCESS_COID.getRspCode());
                } else {
                    if (StringUtil.checkStringsEqual("1", queryTradeRsp.getOrderStatus()) || StringUtil.checkStringsEqual("7", queryTradeRsp.getOrderStatus())) {
                        applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_5.getRspCode());
                        applyOrderChannelBaseDTO.setTradeStatus(CommEnum.PAY_CLOSED_CODE.getRspCode()); //直接置值失败Closed
                    } else if (StringUtil.checkStringsEqual("8", queryTradeRsp.getOrderStatus())) {
                        applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode()); //不改变Paid
                        applyOrderChannelBaseDTO.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
                        applyOrderChannelBaseDTO.setRespCode(CommEnum.SUCCESS_COID.getRspCode());
                    } else if (StringUtil.checkStringsEqual("4", queryTradeRsp.getOrderStatus()) || StringUtil.checkStringsEqual("5", queryTradeRsp.getOrderStatus()) || StringUtil.checkStringsEqual("6", queryTradeRsp.getOrderStatus())) {
                        applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode()); // 待查询 14
                    } else if (StringUtil.checkStringsEqual("3", queryTradeRsp.getOrderStatus())) { //退款
                        //退货处理成功 6成功、7失败、8处理中
                        String refundStatus = "Finished"; //已退款 //退款状态
                        applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_6.getRspCode());
                        applyOrderChannelBaseDTO.setTradeStatus(CommEnum.REFUNDED.getRspCode()); //
                    } else if (StringUtil.checkStringsEqual("2", queryTradeRsp.getOrderStatus())) {
                        // 11消费申请处理中 12 消费撤销成功  13消费撤销失败
                        applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_12.getRspCode());
                        applyOrderChannelBaseDTO.setTradeStatus(CommEnum.CONSUMEUN.getRspCode()); // //已撤销
                    }else {
                        applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_5.getRspCode());
                        applyOrderChannelBaseDTO.setTradeStatus(CommEnum.PAY_CLOSED_CODE.getRspCode()); //直接置值失败Closed
                    }
                }
                String code = queryTradeRsp.getCode(); //渠道返回码
                // 支付状态为（0-成功  1-失败
                // 2-已撤销 3-已退款 4-待支付 5-未使用（用 于判定动态二维码有效 期）6-退款中 7-已关闭 8-退款失败）
                String desc = queryTradeRsp.getMgs() + "[" +queryTradeRsp.getOrderStatus()+"]";;  //渠道返回描述
                applyOrderChannelBaseDTO.setTradeCode(code);
                applyOrderChannelBaseDTO.setTradeDesc(desc);
            } else {
                String code = (String) respMap.get(CommEnum.RESP_CODE.getRspCode());
                String desc = (String) respMap.get(CommEnum.RESP_DESC.getRspCode());
                logger.info("调用农信渠道返回码：" +code+"|" +desc);
                applyOrderChannelBaseDTO.setTradeCode(code);
                applyOrderChannelBaseDTO.setTradeDesc(desc);
                applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_5.getRspCode());
                applyOrderChannelBaseDTO.setTradeStatus(CommEnum.PAY_CLOSED_CODE.getRspCode()); //直接置值失败Closed
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return applyOrderChannelBaseDTO;
    }

    private String getYnnxSubPayType(String subPayType) {
        if ("12".equals(subPayType)) {
            return "002"; // 支付宝
        } else if ("13".equals(subPayType)) {
            return "003"; // 微信
        } else {
            return "100";
        }
    }
}
