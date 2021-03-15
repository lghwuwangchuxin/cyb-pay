package com.parkinghx.service.impl;

import com.parkinghx.dto.HxFiles;
import com.parkinghx.dto.HxbScanPayReq;
import com.parkinghx.dto.HxbScanPayRsp;
import com.parkinghx.service.HxbNewScanPayService;
import com.parkinghx.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.MessageFormat;

@Service("hxbNewScanPayService")
public class HxbNewScanPayServiceImpl implements HxbNewScanPayService {

    private static final Logger logger = LoggerFactory.getLogger(HxbNewScanPayServiceImpl.class);

    @Override
    public HxbScanPayRsp scanPay(HxbScanPayReq req) throws Exception {
        if (null == req) {
            logger.info("接收参数为空");
            return REQ_IS_NULL;
        }

       // 组装 body数据
        JSONObject reqJsonBody = reqJsonBody(req);
        logger.info("组装华夏银行渠道被扫下单报文明文数据前" +reqJsonBody.toString());
        // 证书路径扫描
        HxFiles hxFiles = ConfigUtil.scanFilesWithRecursion(MessageFormat.format(ConfigUtil.getValue("mchntPaht"), req.getMerchantNo()));
        logger.info("证书扫描路径："+hxFiles);
        String signatureStr = HxUtil.signStr(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装华夏银行渠道被扫签名数据:" +signatureStr);
        String encStr = HxUtil.makeEnvelope(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装华夏银行渠道被扫body加密数据：" +encStr);
        // 组装 json 报文数据
        JSONObject reqJson = new JSONObject();
        reqJson.put("version", "1.0"); // 版本号
        reqJson.put("appid", req.getAppKey());// appid
        reqJson.put("signature", signatureStr);
        reqJson.put(HxbConstants.RSP_BODY, encStr);

        String reqJsonStr = reqJson.toString();
        try {
            logger.info("请求华夏银行被扫渠道下单数据报文：" +reqJsonStr);
            String rsp = HttpUtil.HttpPost(ConfigUtil.getValue("hxUrl") , reqJsonStr);
            logger.info("华夏银行被扫渠道下单响应报文：" +rsp);
            if (null == rsp) {
                logger.info("接收到数据异常");
                return  UNKNOWN_ERROR;
            }

            // 解析数据 解密数据 验证签名
            JSONObject rspJson = JSONObject.fromObject(rsp);
            if (!rspJson.containsKey(HxbConstants.RSP_BODY)) {
                logger.info("华夏银行被扫下单响应数据body异常");
                return  UNKNOWN_ERROR;
            }
            // 解密数据 body数据
            String body = HxUtil.openEnvelope(rspJson.getString(HxbConstants.RSP_BODY), req.getMerchantNo(), hxFiles);
            logger.info("华夏银行被扫下单解密body数据：" +body);
            // 验证签名数据
            if (!HxUtil.verifyPublicJson(body, rspJson.getString("signature"), req.getMerchantNo(), hxFiles)) {
                logger.info("华夏银行被扫下单响应数据验证签名结果失败false");
                return SIGN_DATA_FAIL;
            }
            // 解析数据
            rspJson = JSONObject.fromObject(body);
            if (!rspJson.containsKey(HxbConstants.RSP_BODY_BODY)) {
                return WAIL;
            }
            logger.info("BODY" +rspJson.getString(HxbConstants.RSP_BODY_BODY));
            rspJson = JSONObject.fromObject(rspJson.getString(HxbConstants.RSP_BODY_BODY));
            if (rspJson.containsKey(HxbConstants.RESP_CODE) && "00".equals(rspJson.getString(HxbConstants.RESP_CODE))) {
                SUCCESS_DESC.setTradeDes(rspJson.getString("RESP_MSG"));
                SUCCESS_DESC.setTradeNo(rspJson.getString("MERCH_ORDER_NO"));
                return SUCCESS_DESC;
            }
            return WAIL;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return REQ_IS_NULL;
    }

    private JSONObject reqJsonBody(HxbScanPayReq req) {
        JSONObject reqJsonBody = new JSONObject();

        JSONObject sysHeadJson = new JSONObject();
        sysHeadJson.put("SERVICE_CODE","MbsdMpos"); // 固定值
        sysHeadJson.put("MESSAGE_TYPE",req.getPayMethod().substring(0,4));//  请求交易码
        sysHeadJson.put("MESSAGE_CODE", req.getPayMethod().substring(4,8)); //

        reqJsonBody.put("SYS_HEAD",sysHeadJson);

        JSONObject busBody = new JSONObject();
        busBody.put("MERCH_NO", req.getMerchantNo()); // 商户号
        // 金额 补零操作  必须12位
        req.setAmount(HxUtil.parseLeftZero(req.getAmount(), 12));
        busBody.put("TRANAMTG", req.getAmount()); // 交易金额
        busBody.put("QRCODE", req.getAuthCode()); // 授权码
        busBody.put("TITLE", req.getSubject()); // 订单标题
        busBody.put("ORGTRANTRACE", req.getOrderNo());// 商户请求流水号
        busBody.put("IP",req.getIp()); //请求ip
        reqJsonBody.put("BODY",busBody);
        return reqJsonBody;
    }

    /**
     * 主扫 支付
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public HxbScanPayRsp scanCBPay(HxbScanPayReq req) throws Exception {

        if (null == req) {
            logger.info("接收主扫参数为空");
            return REQ_CB_IS_NULL;
        }

        // 组装 body数据
        JSONObject reqJsonBody = reqCbJsonBody(req);
        logger.info("组装华夏银行渠道主扫下单报文明文数据前" +reqJsonBody.toString());
        HxFiles hxFiles = null;
        try {
            // 证书路径扫描
            hxFiles = ConfigUtil.scanFilesWithRecursion(MessageFormat.format(ConfigUtil.getValue("mchntPaht"), req.getMerchantNo()));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("加载证书异常请检查证书-----");
            return FIND_FILE_NO_EXCEPTION;
        }
        logger.info("证书扫描路径："+hxFiles);
        String signatureStr = HxUtil.signStr(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装华夏银行渠道主扫签名数据:" +signatureStr);
        String encStr = HxUtil.makeEnvelope(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装华夏银行渠道主扫body加密数据：" +encStr);
        // 组装 json 报文数据
        JSONObject reqJson = new JSONObject();
        reqJson.put("version", "1.0"); // 版本号
        reqJson.put("appid", req.getAppKey());// appid
        reqJson.put("signature", signatureStr);
        reqJson.put(HxbConstants.RSP_BODY, encStr);

        String reqJsonStr = reqJson.toString();
        try {
            logger.info("请求华夏银行主扫渠道下单数据报文：" +reqJsonStr);
            String rsp = HttpUtil.HttpPost(req.getPayOrderUrl() , reqJsonStr);
            logger.info("华夏银行主扫渠道下单响应报文：" +rsp);
            if (null == rsp) {
                logger.info("接收到数据异常");
                return  UNKNOWN_CB_ERROR;
            }

            // 解析数据 解密数据 验证签名
            JSONObject rspJson = JSONObject.fromObject(rsp);
            if (!rspJson.containsKey(HxbConstants.RSP_BODY)) {
                logger.info("华夏银行主扫下单响应数据body异常");
                return  UNKNOWN_CB_ERROR;
            }
            // 解密数据 body数据
            String body = HxUtil.openEnvelope(rspJson.getString(HxbConstants.RSP_BODY), req.getMerchantNo(), hxFiles);
            logger.info("华夏银行主扫下单解密body数据：" +body);
            // 验证签名数据
            if (!HxUtil.verifyPublicJson(body, rspJson.getString("signature"), req.getMerchantNo(), hxFiles)) {
                logger.info("华夏银行主扫下单响应数据验证签名结果失败false");
                return SIGN_DATA_FAIL;
            }
            // 解析数据
            rspJson = JSONObject.fromObject(body);
            if (!rspJson.containsKey(HxbConstants.RSP_BODY_BODY)) {
                return WAIL;
            }
            logger.info("BODY" +rspJson.getString(HxbConstants.RSP_BODY_BODY));
            rspJson = JSONObject.fromObject(rspJson.getString(HxbConstants.RSP_BODY_BODY));
            if (rspJson.containsKey(HxbConstants.QRCODE) ) {
                SUCCESS_CB_DESC.setQrCode(rspJson.getString(HxbConstants.QRCODE));
                return SUCCESS_CB_DESC;
            }
            return WAIL;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return REQ_CB_IS_NULL;
    }

     // 主扫 下单 报文
    private JSONObject reqCbJsonBody(HxbScanPayReq req) {
        JSONObject reqJsonBody = new JSONObject();

        JSONObject sysHeadJson = new JSONObject();
        sysHeadJson.put("SERVICE_CODE","MbsdMpos"); // 固定值
        sysHeadJson.put("MESSAGE_TYPE",req.getPayMethod().substring(0,4));//  请求交易码
        sysHeadJson.put("MESSAGE_CODE", req.getPayMethod().substring(4,8)); //

        reqJsonBody.put("SYS_HEAD",sysHeadJson);

        JSONObject busBody = new JSONObject();
        busBody.put("MERCH_NO", req.getMerchantNo()); // 商户号
        // 金额 补零操作  必须12位
        req.setAmount(HxUtil.parseLeftZero(req.getAmount(), 12));
        busBody.put("TRANAMT", req.getAmount()); // 交易金额
        busBody.put("NOTIFY_URL", req.getNotifyUrl()); //  通知地址
        busBody.put("TITLE", req.getSubject()); // 订单标题
        busBody.put("ORGTRANTRACE", req.getOrderNo());// 商户请求流水号
        busBody.put("IP",req.getIp()); //请求ip
        reqJsonBody.put("BODY",busBody);
        return reqJsonBody;
    }

    /**
     *  h5 下单 支付  接入 统一条码
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public HxbScanPayRsp preH5Pay(HxbScanPayReq req) throws Exception {
        if (null == req) {
            logger.info("接收参数为空");
            return REQ_IS_H5NULL;
        }

        // 组装 body数据
        JSONObject reqJsonBody = reqH5JsonBody(req);
        logger.info("组装华夏银行渠道微信公众号下单报文明文数据前" +reqJsonBody.toString());
        // 证书路径扫描
        HxFiles hxFiles = ConfigUtil.scanFilesWithRecursion(MessageFormat.format(ConfigUtil.getValue("mchntPaht"), req.getMerchantNo()));
        logger.info("证书扫描路径："+hxFiles);
        String signatureStr = HxUtil.signStr(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装华夏银行渠道公众号签名数据:" +signatureStr);
        String encStr = HxUtil.makeEnvelope(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装华夏银行渠道公众号body加密数据：" +encStr);
        // 组装 json 报文数据
        JSONObject reqJson = new JSONObject();
        reqJson.put("version", "1.0"); // 版本号
        reqJson.put("appid", req.getAppKey());// appid 华夏银联分配id
        reqJson.put("signature", signatureStr);
        reqJson.put(HxbConstants.RSP_BODY, encStr);

        String reqJsonStr = reqJson.toString();
        try {
            logger.info("请求华夏银行公众号渠道下单数据报文：" +reqJsonStr);
            String rsp = HttpUtil.HttpPost(req.getPayOrderUrl() +req.getTradeType() , reqJsonStr);
            logger.info("华夏银行公众号渠道下单响应报文：" +rsp);
            if (null == rsp) {
                logger.info("接收到数据异常");
                return  UNKNOWN_H5ERROR;
            }

            // 解析数据 解密数据 验证签名
            JSONObject rspJson = JSONObject.fromObject(rsp);
            if (!rspJson.containsKey(HxbConstants.RSP_BODY)) {
                logger.info("华夏银行公众号下单响应数据body异常");
                return  UNKNOWN_H5ERROR;
            }
            // 解密数据 body数据
            String body = HxUtil.openEnvelope(rspJson.getString(HxbConstants.RSP_BODY), req.getMerchantNo(), hxFiles);
            logger.info("华夏银行公众号下单解密body数据：" +body);
            // 验证签名数据
            if (!HxUtil.verifyPublicJson(body, rspJson.getString("signature"), req.getMerchantNo(), hxFiles)) {
                logger.info("华夏银行公众号下单响应数据验证签名结果失败false");
                return SIGN_DATA_FAIL;
            }
            // 解析数据
            rspJson = JSONObject.fromObject(body);

            if (rspJson.containsKey(HxbConstants.RESPCODE) && RespUtil.successCode.equals(rspJson.getString(HxbConstants.RESPCODE))) {
                if (rspJson.containsKey(HxbConstants.wxPayInfo)) {
                    SUCCESS_H5DESC.setTradeDes(rspJson.getString("respMsg"));
                    SUCCESS_H5DESC.setWxPayInfo(rspJson.getString(HxbConstants.wxPayInfo));
                    return SUCCESS_H5DESC;
                }
            }
            return WAIL;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return REQ_IS_H5NULL;
    }

    // 组装 微信下单 报文
    private JSONObject reqH5JsonBody(HxbScanPayReq req) {
        JSONObject reqJsonBody = new JSONObject();
        reqJsonBody.put("pcsDate", DateUtil.getYYYYMMDDHHMMSSNowTime());// 请求时间
        reqJsonBody.put("merchantNo", req.getMerchantNo()); // 商户号
        reqJsonBody.put("orderNo",req.getOrderNo()); // 订单号
        reqJsonBody.put("amount", fenToYunPayAmt(req.getAmount())); // 交易金额
        reqJsonBody.put("subject", req.getSubject()); // 订单标题
        reqJsonBody.put("notifyUrl", req.getNotifyUrl()); // 支付通知地址
        reqJsonBody.put("subOpenId", req.getSubOpenId()); // 微信用户openid
        reqJsonBody.put("subAppid", req.getSubAppid()); // 合作方微信公众账号 ID
        return reqJsonBody;
    }

    /**
     *  被扫接入  统一条码
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public HxbScanPayRsp scanBcQrCodePay(HxbScanPayReq req) throws Exception {
        if (null == req) {
            logger.info("接收参数为空");
            return REQ_IS_NULL;
        }

        // 组装 body数据
        JSONObject reqJsonBody = reqBcJsonBody(req);
        logger.info("组装华夏银行渠道被扫下单报文明文数据前" +reqJsonBody.toString());
        // 证书路径扫描
        HxFiles hxFiles = ConfigUtil.scanFilesWithRecursion(MessageFormat.format(ConfigUtil.getValue("mchntPaht"), req.getMerchantNo()));
        logger.info("证书扫描路径："+hxFiles);
        String signatureStr = HxUtil.signStr(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装华夏银行渠道被扫签名数据:" +signatureStr);
        String encStr = HxUtil.makeEnvelope(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装华夏银行渠道被扫body加密数据：" +encStr);
        // 组装 json 报文数据
        JSONObject reqJson = new JSONObject();
        reqJson.put("version", "1.0"); // 版本号
        reqJson.put("appid", req.getAppKey());// appid
        reqJson.put("signature", signatureStr);
        reqJson.put(HxbConstants.RSP_BODY, encStr);

        String reqJsonStr = reqJson.toString();
        try {
            logger.info("请求华夏银行被扫渠道下单数据报文：" +reqJsonStr);
            String rsp = HttpUtil.HttpPost(ConfigUtil.getValue("hxUrl") +req.getTradeType() , reqJsonStr);
            logger.info("华夏银行被扫渠道下单响应报文：" +rsp);
            if (null == rsp) {
                logger.info("接收到数据异常");
                return  UNKNOWN_ERROR;
            }

            // 解析数据 解密数据 验证签名
            JSONObject rspJson = JSONObject.fromObject(rsp);
            if (!rspJson.containsKey(HxbConstants.RSP_BODY)) {
                logger.info("华夏银行被扫下单响应数据body异常");
                return  UNKNOWN_ERROR;
            }
            // 解密数据 body数据
            String body = HxUtil.openEnvelope(rspJson.getString(HxbConstants.RSP_BODY), req.getMerchantNo(), hxFiles);
            logger.info("华夏银行被扫下单解密body数据：" +body);
            // 验证签名数据
            if (!HxUtil.verifyPublicJson(body, rspJson.getString("signature"), req.getMerchantNo(), hxFiles)) {
                logger.info("华夏银行被扫下单响应数据验证签名结果失败false");
                return SIGN_DATA_FAIL;
            }
            // 解析数据
            rspJson = JSONObject.fromObject(body);

            if (rspJson.containsKey(HxbConstants.RESPCODE) && RespUtil.successCode.equals(rspJson.getString(HxbConstants.RESPCODE))) {
                 if (rspJson.containsKey(HxbConstants.STATUESCODE) && "OO".equals(rspJson.getString(HxbConstants.STATUESCODE))) {
                     SUCCESS_DESC.setTradeDes(rspJson.getString("respMsg"));
                     SUCCESS_DESC.setTradeNo(rspJson.getString("tradeNo")); // 支付渠道订单号
                     SUCCESS_DESC.setSuccessTime(rspJson.getString("successTime")); // 支付时间
                     return SUCCESS_DESC;
                 }
            }
            return WAIL;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return REQ_IS_NULL;
    }

    // 组装被扫 body 业务参数
    private JSONObject reqBcJsonBody(HxbScanPayReq req) {

        JSONObject reqJsonBody = new JSONObject();
        reqJsonBody.put("pcsDate", DateUtil.getYYYYMMDDHHMMSSNowTime());// 请求时间
        reqJsonBody.put("merchantNo", req.getMerchantNo()); // 商户号
        reqJsonBody.put("orderNo",req.getOrderNo()); // 订单号
        reqJsonBody.put("authCode", req.getAuthCode()); // 二维码信息
        reqJsonBody.put("amount", fenToYunPayAmt(req.getAmount())); // 交易金额
        reqJsonBody.put("subject", req.getSubject()); //
        reqJsonBody.put("termId", req.getTermId()); // 终端号

        return  reqJsonBody;
    }

    /**
     *  主扫 下单交易  综合支付 统一条码
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public HxbScanPayRsp scanCbQrCodePay(HxbScanPayReq req) throws Exception {
        logger.info("进入主扫下单----------scanCbQrCodePay");
        if (null == req) {
            logger.info("接收参数为空");
            return REQ_IS_NULL;
        }

        // 组装 body数据
        JSONObject reqJsonBody = reqCbQrCodeJsonBody(req);
        logger.info("组装华夏银行渠道主扫下单报文明文数据前" +reqJsonBody.toString());
        // 证书路径扫描
        HxFiles hxFiles = ConfigUtil.scanFilesWithRecursion(MessageFormat.format(ConfigUtil.getValue("mchntPaht"), req.getMerchantNo()));
        logger.info("证书扫描路径："+hxFiles);
        String signatureStr = HxUtil.signStr(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装华夏银行渠道主扫签名数据:" +signatureStr);
        String encStr = HxUtil.makeEnvelope(reqJsonBody.toString(), req.getMerchantNo(), hxFiles);
        logger.info("组装华夏银行渠道主扫body加密数据：" +encStr);
        // 组装 json 报文数据
        JSONObject reqJson = new JSONObject();
        reqJson.put("version", "1.0"); // 版本号
        reqJson.put("appid", req.getAppKey());// appid
        reqJson.put("signature", signatureStr);
        reqJson.put(HxbConstants.RSP_BODY, encStr);

        String reqJsonStr = reqJson.toString();
        try {
            logger.info("请求华夏银行主扫渠道下单数据报文：" +reqJsonStr);
            String rsp = HttpUtil.HttpPost(req.getPayOrderUrl() +req.getTradeType() , reqJsonStr);
            logger.info("华夏银行主扫渠道下单响应报文：" +rsp);
            if (null == rsp) {
                logger.info("接收到数据异常");
                return  UNKNOWN_ERROR;
            }

            // 解析数据 解密数据 验证签名
            JSONObject rspJson = JSONObject.fromObject(rsp);
            if (!rspJson.containsKey(HxbConstants.RSP_BODY)) {
                logger.info("华夏银行主扫下单响应数据body异常");
                return  UNKNOWN_ERROR;
            }
            // 解密数据 body数据
            String body = HxUtil.openEnvelope(rspJson.getString(HxbConstants.RSP_BODY), req.getMerchantNo(), hxFiles);
            logger.info("华夏银行主扫下单解密body数据：" +body);
            // 验证签名数据
            if (!HxUtil.verifyPublicJson(body, rspJson.getString("signature"), req.getMerchantNo(), hxFiles)) {
                logger.info("华夏银行主扫下单响应数据验证签名结果失败false");
                return SIGN_DATA_FAIL;
            }
            // 解析数据
            rspJson = JSONObject.fromObject(body);

            if (rspJson.containsKey(HxbConstants.RESPCODE) && RespUtil.successCode.equals(rspJson.getString(HxbConstants.RESPCODE))) {
                SUCCESS_CB_DESC.setTradeDes(rspJson.getString("respMsg"));
                SUCCESS_CB_DESC.setQrCode(rspJson.getString("qrcode")); //  二维码 信息
                return SUCCESS_CB_DESC;
            }
            return WAIL;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return REQ_IS_NULL;
    }

    // 主扫 下单  统一条码
    private JSONObject reqCbQrCodeJsonBody(HxbScanPayReq req) {
        JSONObject reqJsonBody = new JSONObject();
        reqJsonBody.put("pcsDate", DateUtil.getYYYYMMDDHHMMSSNowTime());// 请求时间
        reqJsonBody.put("merchantNo", req.getMerchantNo()); // 商户号
        reqJsonBody.put("orderNo",req.getOrderNo()); // 订单号
        reqJsonBody.put("amount", fenToYunPayAmt(req.getAmount())); // 交易金额
        reqJsonBody.put("notifyUrl", req.getNotifyUrl()); // 支付通知地址url
        return reqJsonBody;
    }

    // 分to元操作
    public String fenToYunPayAmt(String payAmt) {
        BigDecimal bigDecimal = new BigDecimal(payAmt); //
        //元To分操作
        //String payAmt=bigDecimal.movePointRight(2).toString();
        //分To元操作
        String payAmtTo = bigDecimal.movePointLeft(2).toString();
        return payAmtTo;
    }

}
