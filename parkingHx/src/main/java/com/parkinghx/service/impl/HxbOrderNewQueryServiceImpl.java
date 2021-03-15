package com.parkinghx.service.impl;

import com.parkinghx.dto.HxFiles;
import com.parkinghx.dto.HxbOrderQueryReq;
import com.parkinghx.dto.HxbOrderQueryRsp;
import com.parkinghx.service.HxbOrderNewQueryService;
import com.parkinghx.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service("hxbOrderNewQueryService")
public class HxbOrderNewQueryServiceImpl implements HxbOrderNewQueryService {

    private static final Logger logger = LoggerFactory.getLogger(HxbOrderNewQueryServiceImpl.class);

    @Override
    public HxbOrderQueryRsp queryTrade(HxbOrderQueryReq req) throws Exception {

        if (null == req) {
            logger.info("接收查询订单参数为空");
            return REQ_IS_NULL;
        }

        // 组装 body数据
        JSONObject reqJsonBody = reqJsonBody(req);
        logger.info("组装被扫body数据前"+reqJsonBody.toString());
        // 证书路径扫描
        HxFiles hxFiles = null;
        try {
             hxFiles = ConfigUtil.scanFilesWithRecursion(MessageFormat.format(ConfigUtil.getValue("mchntPaht"), req.getMerchantNo()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("加载华夏银行证书异常-------");
            return FIND_FILE_NO_EXCEPTION;
        }
        logger.info("证书扫描路径："+hxFiles);
        String signatureStr = HxUtil.signStr(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装订单查询签名数据:" +signatureStr);
        String enStr = HxUtil.makeEnvelope(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装被扫订单查询加密数据" +enStr);
        // 组装 json 报文数据
        JSONObject reqJson = new JSONObject();
        reqJson.put("version", "1.0"); // 版本号
        reqJson.put("appid", req.getAppKey());// appid
        reqJson.put("signature", signatureStr);
        reqJson.put(HxbConstants.RSP_BODY, enStr);

        try {
            String reqJsonStr = reqJson.toString();
            logger.info("请求华夏银行渠道订单查询数据报文：" +reqJsonStr);
            String rsp = HttpUtil.HttpPost(ConfigUtil.getValue("hxUrl") , reqJsonStr);
            logger.info("华夏银行渠道订单查询响应报文：" +rsp);
            if (null == rsp) {
                logger.info("接收到数据异常");
                return  UNKNOWN_ERROR;
            }

            // 解析数据 解密数据 验证签名

            JSONObject rspJson = JSONObject.fromObject(rsp);
            if (!rspJson.containsKey(HxbConstants.RSP_BODY)) {
                logger.info("华夏银行订单查询响应数据异常");
                return  UNKNOWN_ERROR;
            }
            // 解密数据 body数据
            String body = HxUtil.openEnvelope(rspJson.getString(HxbConstants.RSP_BODY), req.getMerchantNo(), hxFiles);
            logger.info("华夏银行订单查询解密body数据：" +body);

            // 验证签名数据
            if (!HxUtil.verifyPublicJson(body, rspJson.getString("signature"), req.getMerchantNo(), hxFiles)) {
                logger.info("华夏银行订单查询响应数据验证签名结果失败false");
                return SIGN_DATA_FAIL;
            }
            // 解析数据
            rspJson = JSONObject.fromObject(body);
            if (!rspJson.containsKey(HxbConstants.RSP_BODY_BODY)) {
                return WAIL;
            }
            logger.info("BODY数据：" +rspJson.getString(HxbConstants.RSP_BODY_BODY));
            rspJson = JSONObject.fromObject(rspJson.getString(HxbConstants.RSP_BODY_BODY));
            if (rspJson.containsKey(HxbConstants.RESP_CODE) && "00".equals(rspJson.getString(HxbConstants.RESP_CODE))) {
                SUCCESS_DESC.setTradeDes(rspJson.containsKey("RESP_MSG") ? rspJson.getString("RESP_MSG") : rspJson.getString("DISCRIPTION"));
                SUCCESS_DESC.setTradeNo(rspJson.getString("MERCH_ORDER_NO"));
                SUCCESS_DESC.setSuccessTime(rspJson.getString("TRANS_TIME"));
                SUCCESS_DESC.setPayType(getPayTypeTo(rspJson.getString("PAY_TYPE")));
                return SUCCESS_DESC;
            }
            return WAIL;
        } catch (Exception e) {
            e.printStackTrace();
            return UNKNOWN_ERROR;
        }

    }
    // 作个支付类型转化
    private String getPayTypeTo(String pay_type) {
        if ("WX".equals(pay_type)) {
           return  "13";
        } else if("AL".equals(pay_type)) {
            return  "12";
        } else if("CP".equals(pay_type)){
            return  "14";
        }
        return  "";
    }

    private JSONObject reqJsonBody(HxbOrderQueryReq req) {
        JSONObject reqJsonBody = new JSONObject();

        JSONObject sysHeadJson = new JSONObject();
        sysHeadJson.put("SERVICE_CODE","MbsdMpos"); // 固定值
        sysHeadJson.put("MESSAGE_TYPE",req.getPayMethod().substring(0,4));//  请求交易码
        sysHeadJson.put("MESSAGE_CODE", req.getPayMethod().substring(4,8)); //

        reqJsonBody.put("SYS_HEAD",sysHeadJson);

        JSONObject busBody = new JSONObject();
        busBody.put("MERCH_NO", req.getMerchantNo()); // 商户号
        busBody.put("TREMTRACE", req.getOrderNo());// 商户请求流水号
        //busBody.put("MERCH_ORDER_NO", req.getOrderNo());// 商户订单号
        busBody.put("IP",req.getIp()); //请求ip

        reqJsonBody.put("BODY",busBody);
        return reqJsonBody;
    }

    // 统一订单查询 接入条码前置
    @Override
    public HxbOrderQueryRsp queryPayTrade(HxbOrderQueryReq req) throws Exception {
        if (null == req) {
            logger.info("接收查询订单参数为空");
            return REQ_IS_NULL;
        }

        // 组装 body数据
        JSONObject reqJsonBody = reqQueryOrderJsonBody(req);
        logger.info("组装被扫body数据前"+reqJsonBody.toString());
        // 证书路径扫描
        HxFiles hxFiles = null;
        try {
            hxFiles = ConfigUtil.scanFilesWithRecursion(MessageFormat.format(ConfigUtil.getValue("mchntPaht"), req.getMerchantNo()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("加载华夏银行证书异常-------");
            return FIND_FILE_NO_EXCEPTION;
        }
        logger.info("证书扫描路径："+hxFiles);
        String signatureStr = HxUtil.signStr(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装订单查询签名数据:" +signatureStr);
        String enStr = HxUtil.makeEnvelope(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装被扫订单查询加密数据" +enStr);
        // 组装 json 报文数据
        JSONObject reqJson = new JSONObject();
        reqJson.put("version", "1.0"); // 版本号
        reqJson.put("appid", req.getAppKey());// appid
        reqJson.put("signature", signatureStr);
        reqJson.put(HxbConstants.RSP_BODY, enStr);

        try {
            String reqJsonStr = reqJson.toString();
            logger.info("请求华夏银行渠道订单查询数据报文：" +reqJsonStr);
            String rsp = HttpUtil.HttpPost(ConfigUtil.getValue("hxUrl")+ req.getTradeType() , reqJsonStr);
            logger.info("华夏银行渠道订单查询响应报文：" +rsp);
            if (null == rsp) {
                logger.info("接收到数据异常");
                return  UNKNOWN_ERROR;
            }

            // 解析数据 解密数据 验证签名

            JSONObject rspJson = JSONObject.fromObject(rsp);
            if (!rspJson.containsKey(HxbConstants.RSP_BODY)) {
                logger.info("华夏银行订单查询响应数据异常");
                return  UNKNOWN_ERROR;
            }
            // 解密数据 body数据
            String body = HxUtil.openEnvelope(rspJson.getString(HxbConstants.RSP_BODY), req.getMerchantNo(), hxFiles);
            logger.info("华夏银行订单查询解密body数据：" +body);

            // 验证签名数据
            if (!HxUtil.verifyPublicJson(body, rspJson.getString("signature"), req.getMerchantNo(), hxFiles)) {
                logger.info("华夏银行订单查询响应数据验证签名结果失败false");
                return SIGN_DATA_FAIL;
            }
            // 解析数据
            rspJson = JSONObject.fromObject(body);
            if (rspJson.containsKey(HxbConstants.RESPCODE) && RespUtil.successCode.equals(rspJson.getString(HxbConstants.RESPCODE))) {
                SUCCESS_DESC.setTradeDes(rspJson.getString("respMsg"));
                if (rspJson.containsKey(HxbConstants.STATUESCODE) && "OO".equals(rspJson.getString(HxbConstants.STATUESCODE))) {
                    SUCCESS_DESC.setTradeNo(rspJson.getString("tradeNo")); // 微信 支付宝 云闪付订单号
                    SUCCESS_DESC.setSuccessTime(rspJson.getString("successTime"));
                    SUCCESS_DESC.setPayType(getPayTypes(rspJson.getString("payMethod").replace(" ","")));
                    SUCCESS_DESC.setTradeDes(rspJson.getString("statusMsg"));
                    return SUCCESS_DESC;
                }
            }
            return WAIL;
        } catch (Exception e) {
            e.printStackTrace();
            return UNKNOWN_ERROR;
        }
    }

    // 作个支付类型转化
    private String getPayTypes(String pay_type) {
        if ("WXPAY".equals(pay_type)) {
            return  "13";
        } else if("ALIPAY".equals(pay_type)) {
            return  "12";
        } else if("YLPAY".equals(pay_type)){
            return  "14";
        }
        return  "";
    }

    /**
     * 组装 查询报文 只能查询到 支付的报文
     * @param req
     * @return
     */
    private JSONObject reqQueryOrderJsonBody(HxbOrderQueryReq req) {
        JSONObject reqJsonBody = new JSONObject();
        reqJsonBody.put("pcsDate", DateUtil.getYYYYMMDDHHMMSSNowTime());// 请求时间
        reqJsonBody.put("merchantNo", req.getMerchantNo()); // 商户号
        reqJsonBody.put("orderNo",req.getOrderNo()); // 订单号
        reqJsonBody.put("orgPcsDate", req.getOrgPcsDate()); // 原交易日期
        reqJsonBody.put("tradeType","1");
        return reqJsonBody;
    }
}
