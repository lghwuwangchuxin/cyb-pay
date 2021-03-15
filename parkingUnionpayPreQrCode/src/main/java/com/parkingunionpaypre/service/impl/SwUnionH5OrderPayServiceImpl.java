package com.parkingunionpaypre.service.impl;

import com.parkingunionpaypre.dto.SwUnionOrderPayReq;
import com.parkingunionpaypre.dto.SwUnionOrderPayRsp;
import com.parkingunionpaypre.service.SwUnionH5OrderPayService;
import com.parkingunionpaypre.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service("swUnionH5OrderPayService")
public class SwUnionH5OrderPayServiceImpl implements SwUnionH5OrderPayService {

    private static final Logger logger = LoggerFactory.getLogger(SwUnionH5OrderPayServiceImpl.class);


    /**
     *  微信公众号
     * @param mreq
     * @return
     */
    @Override
    public SwUnionOrderPayRsp preWxH5SwUionOrderPayRsp(SwUnionOrderPayReq mreq) {
        logger.info("进入SW 中国银联条码前置 微信公众号下单 业务处理类：【"+mreq.getSerialNumber()+"】-------------");
        SwUnionOrderPayRsp mrsp = new SwUnionOrderPayRsp();
        setParamsRsp(mrsp, RespUtil.noResult, "银联条码前置公众号下单支付失败");

        if(!isWxNulls(mreq)) {
            setParamsRsp(mrsp, RespUtil.noResult, "银联条码前置微信公众号下单参数缺失");
            return mrsp;
        }
        try {
            SwUnionOrderPayReq sreq = new SwUnionOrderPayReq();
            sreq.setService("pay.weixin.jspay");     //接口名
            sreq.setMch_id(mreq.getMch_id());   //商户号
            sreq.setIs_raw("1"); // 默认值 js
            sreq.setIs_minipg("0");// 公众号内支付
            sreq.setOut_trade_no(mreq.getOut_trade_no());   //商户订单号
            sreq.setBody(mreq.getBody());    //商品信息
            sreq.setSub_openid(mreq.getSub_openid()); // 微信openid
            sreq.setSub_appid(mreq.getSub_appid());// 微信公众号 appid
            sreq.setTotal_fee(mreq.getTotal_fee());   //订单金额   单位：分
            sreq.setMch_create_ip(mreq.getMch_create_ip());    //ip地址
            sreq.setNotify_url(mreq.getNotify_url());    // 支付通知回调地址通知商户
            sreq.setNonce_str(StringUtil.getRomNum());

            Map<String, Object> map = SortUtil.objectToMap2(sreq);
            map = SortUtil.mapParamFilter(map);
            String  str = SortUtil.getSignStr(map);
            logger.info("银联条码前置微信公众号待签名的字符串为："+str);

            //sign= Md5(原字符串&key=商户密钥).toUpperCase
            String signA = SortUtil.sign(str, mreq.getKey(), "utf-8");
            map.put("sign", signA);
            String reqParams = SortUtil.toXml(map);
            logger.info("开始  中国银联条码前置 微信公众号 下单支付---["+mreq.getSerialNumber()+"],请求参数为："+reqParams);
            HttpEntity httpEntity = new StringEntity(reqParams, "UTF-8");
            SSLHttpClient httpClient = new SSLHttpClient();
            String result = httpClient.post(ConfigUtil.getValue("OrderPayAddress"), httpEntity);
            logger.info("结束 SW 中国银联 微信公众号条码前置 下单支付---["+mreq.getSerialNumber()+"],响应参数为："+result);
            if(StringUtil.checkNullString(result) || !XmlUtil.isXML(result)) {
                logger.info("中国银联条码前置微信公众号响应参数为空或格式有误");
                mrsp.setRespDesc("中国银联响应参数为空或格式有误");
                return mrsp;
            }
            Map<String,Object> resultMap = SortUtil.xmlStrToMap(result);
            if(!"0".equals(resultMap.get("status"))) {
                mrsp.setRespDesc((String) resultMap.get("message"));
                return mrsp;
            }
            if(resultMap.containsKey("sign")) {
                boolean i = SortUtil.checkParam(resultMap, mreq.getKey());
                logger.info("银联条码前置微信公众号下单验签结果为--i:"+i);
                if(i && resultMap.containsKey("result_code")) {
                    if(!"0".equals((String) resultMap.get("result_code"))) {
                        setParamsRsp(mrsp, resultMap.containsKey("err_code") ? (String) resultMap.get("err_code") : RespUtil.noResult, resultMap.containsKey("err_msg") ? (String) resultMap.get("err_msg") : "下单失败");
                        return mrsp;
                    }
                    mrsp = (SwUnionOrderPayRsp) XmlUtil.XmlToObj(result, SwUnionOrderPayRsp.class);
                    setParamsRsp(mrsp,RespUtil.successCode, "下单成功");
                }else {
                    mrsp.setRespDesc("银联条码前置微信公众号验签失败或下单失败--i:"+i);
                }
            }else {
                setParamsRsp(mrsp, (String) resultMap.get("err_code"), (String) resultMap.get("err_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("银联条码前置微信公众号下单处理异常：" +e.getMessage());
        }

        return mrsp;
    }
    private boolean isWxNulls(SwUnionOrderPayReq mreq) {
        if(StringUtil.checkNullString(mreq.getMch_id()) || StringUtil.checkNullString(mreq.getOut_trade_no()) || StringUtil.checkNullString(mreq.getBody())
                || StringUtil.checkNullString(mreq.getTotal_fee()) || StringUtil.checkNullString(mreq.getMch_create_ip()) || StringUtil.checkNullString(mreq.getNotify_url())
                || StringUtil.checkNullString(mreq.getKey())
                || StringUtil.checkNullString(mreq.getSub_openid())
                || StringUtil.checkNullString(mreq.getSub_appid())) {

            return false;
        }
        return true;
    }

    private void setParamsRsp(SwUnionOrderPayRsp mrsp, String respCode, String respDesc) {
        mrsp.setRespCode(respCode);
        mrsp.setRespDesc(respDesc);
    }

    /**
     *  支付宝 服务窗 下单 h5 js
     * @param mreq
     * @return
     */
    @Override
    public SwUnionOrderPayRsp preAliH5SwUionOrderPayRsp(SwUnionOrderPayReq mreq) {
        logger.info("进入SW 中国银联条码前置 支付宝服务窗下单 业务处理类：【"+mreq.getSerialNumber()+"】-------------");
        SwUnionOrderPayRsp mrsp = new SwUnionOrderPayRsp();
        setParamsRsp(mrsp, RespUtil.noResult, "银联条码前置支付宝服务窗下单支付失败");

        if(!isAliNulls(mreq)) {
            setParamsRsp(mrsp, RespUtil.noResult, "银联条码前置支付宝服务窗下单参数缺失");
            return mrsp;
        }
        try {
            SwUnionOrderPayReq sreq = new SwUnionOrderPayReq();
            sreq.setService("pay.alipay.jspay");     //接口名
            sreq.setMch_id(mreq.getMch_id());   //商户号
            sreq.setOut_trade_no(mreq.getOut_trade_no());   //商户订单号
            sreq.setBody(mreq.getBody());    //商品信息
            sreq.setTotal_fee(mreq.getTotal_fee());   //订单金额   单位：分
            sreq.setMch_create_ip(mreq.getMch_create_ip());    //ip地址
            sreq.setNotify_url(mreq.getNotify_url());    // 支付通知回调地址通知商户
            sreq.setNonce_str(StringUtil.getRomNum());

            Map<String, Object> map = SortUtil.objectToMap2(sreq);
            map = SortUtil.mapParamFilter(map);
            String  str = SortUtil.getSignStr(map);
            logger.info("银联条码前置支付宝服务窗待签名的字符串为："+str);

            //sign= Md5(原字符串&key=商户密钥).toUpperCase
            String signA = SortUtil.sign(str, mreq.getKey(), "utf-8");
            map.put("sign", signA);
            String reqParams = SortUtil.toXml(map);
            logger.info("开始  中国银联条码前置 支付宝服务窗 下单支付---["+mreq.getSerialNumber()+"],请求参数为："+reqParams);
            HttpEntity httpEntity = new StringEntity(reqParams, "UTF-8");
            SSLHttpClient httpClient = new SSLHttpClient();
            String result = httpClient.post(ConfigUtil.getValue("OrderPayAddress"), httpEntity);
            logger.info("结束 SW 中国银联 支付宝服务窗条码前置 下单支付---["+mreq.getSerialNumber()+"],响应参数为："+result);
            if(StringUtil.checkNullString(result) || !XmlUtil.isXML(result)) {
                logger.info("中国银联条码前置支付宝服务窗响应参数为空或格式有误");
                mrsp.setRespDesc("中国银联响应参数为空或格式有误");
                return mrsp;
            }
            Map<String,Object> resultMap = SortUtil.xmlStrToMap(result);
            if(!"0".equals(resultMap.get("status"))) {
                mrsp.setRespDesc((String) resultMap.get("message"));
                return mrsp;
            }
            if(resultMap.containsKey("sign")) {
                boolean i = SortUtil.checkParam(resultMap, mreq.getKey());
                logger.info("银联条码前置支付宝服务窗下单验签结果为--i:"+i);
                if(i && resultMap.containsKey("result_code")) {
                    if(!"0".equals((String) resultMap.get("result_code"))) {
                        setParamsRsp(mrsp, resultMap.containsKey("err_code") ? (String) resultMap.get("err_code") : RespUtil.noResult, resultMap.containsKey("err_msg") ? (String) resultMap.get("err_msg") : "下单失败");
                        return mrsp;
                    }
                    mrsp = (SwUnionOrderPayRsp) XmlUtil.XmlToObj(result, SwUnionOrderPayRsp.class);
                    setParamsRsp(mrsp,RespUtil.successCode, "下单成功");
                }else {
                    mrsp.setRespDesc("银联条码前置支付宝服务窗验签失败或下单失败--i:"+i);
                }
            }else {
                setParamsRsp(mrsp, (String) resultMap.get("err_code"), (String) resultMap.get("err_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("银联条码前置支付宝服务窗下单处理异常：" +e.getMessage());
        }
        return mrsp;
    }
    private boolean isAliNulls(SwUnionOrderPayReq mreq) {
        if(StringUtil.checkNullString(mreq.getMch_id())
                || StringUtil.checkNullString(mreq.getOut_trade_no())
                || StringUtil.checkNullString(mreq.getBody())
                || StringUtil.checkNullString(mreq.getTotal_fee())
                || StringUtil.checkNullString(mreq.getMch_create_ip())
                || StringUtil.checkNullString(mreq.getNotify_url())
                || StringUtil.checkNullString(mreq.getKey())
               ) {

            return false;
        }
        return true;
    }

    /**
     *  银联云闪付 js
     * @param mreq
     * @return
     */
    @Override
    public SwUnionOrderPayRsp preUnionpayH5SwUionOrderPayRsp(SwUnionOrderPayReq mreq) {
        logger.info("进入SW 中国银联条码前置 银联云闪付js下单 业务处理类：【"+mreq.getSerialNumber()+"】-------------");
        SwUnionOrderPayRsp mrsp = new SwUnionOrderPayRsp();
        setParamsRsp(mrsp, RespUtil.noResult, "银联条码前置银联云闪付js下单支付失败");

        if(!isUnionpayNulls(mreq)) {
            setParamsRsp(mrsp, RespUtil.noResult, "银联条码前置银联云闪付js下单参数缺失");
            return mrsp;
        }
        try {
            SwUnionOrderPayReq sreq = new SwUnionOrderPayReq();
            sreq.setService("pay.unionpay.jspay");     //接口名
            sreq.setMch_id(mreq.getMch_id());   //商户号
            sreq.setOut_trade_no(mreq.getOut_trade_no());   //商户订单号
            sreq.setBody(mreq.getBody());    //商品信息
            sreq.setCustomer_ip(mreq.getCustomer_ip()); // 用户外面ip
            sreq.setTotal_fee(mreq.getTotal_fee());   //订单金额   单位：分
            sreq.setMch_create_ip(mreq.getMch_create_ip());    //ip地址
            sreq.setNotify_url(mreq.getNotify_url());    // 支付通知回调地址通知商户
            sreq.setNonce_str(StringUtil.getRomNum());

            Map<String, Object> map = SortUtil.objectToMap2(sreq);
            map = SortUtil.mapParamFilter(map);
            String  str = SortUtil.getSignStr(map);
            logger.info("银联条码前置银联云闪付js待签名的字符串为："+str);

            //sign= Md5(原字符串&key=商户密钥).toUpperCase
            String signA = SortUtil.sign(str, mreq.getKey(), "utf-8");
            map.put("sign", signA);
            String reqParams = SortUtil.toXml(map);
            logger.info("开始  中国银联条码前置 银联云闪付js 下单支付---["+mreq.getSerialNumber()+"],请求参数为："+reqParams);
            HttpEntity httpEntity = new StringEntity(reqParams, "UTF-8");
            SSLHttpClient httpClient = new SSLHttpClient();
            String result = httpClient.post(ConfigUtil.getValue("OrderPayAddress"), httpEntity);
            logger.info("结束 SW 中国银联 银联云闪付js条码前置 下单支付---["+mreq.getSerialNumber()+"],响应参数为："+result);
            if(StringUtil.checkNullString(result) || !XmlUtil.isXML(result)) {
                logger.info("中国银联条码前置银联云闪付js响应参数为空或格式有误");
                mrsp.setRespDesc("中国银联响应参数为空或格式有误");
                return mrsp;
            }
            Map<String,Object> resultMap = SortUtil.xmlStrToMap(result);
            if(!"0".equals(resultMap.get("status"))) {
                mrsp.setRespDesc((String) resultMap.get("message"));
                return mrsp;
            }
            if(resultMap.containsKey("sign")) {
                boolean i = SortUtil.checkParam(resultMap, mreq.getKey());
                logger.info("银联条码前置银联云闪付js下单验签结果为--i:"+i);
                if(i && resultMap.containsKey("result_code")) {
                    if(!"0".equals((String) resultMap.get("result_code"))) {
                        setParamsRsp(mrsp, resultMap.containsKey("err_code") ? (String) resultMap.get("err_code") : RespUtil.noResult, resultMap.containsKey("err_msg") ? (String) resultMap.get("err_msg") : "下单失败");
                        return mrsp;
                    }
                    mrsp = (SwUnionOrderPayRsp) XmlUtil.XmlToObj(result, SwUnionOrderPayRsp.class);
                    setParamsRsp(mrsp,RespUtil.successCode, "下单成功");
                }else {
                    mrsp.setRespDesc("银联条码前置银联云闪付js验签失败或下单失败--i:"+i);
                }
            }else {
                setParamsRsp(mrsp, (String) resultMap.get("err_code"), (String) resultMap.get("err_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("银联条码前置银联云闪付js下单处理异常：" +e.getMessage());
        }
        return mrsp;
    }
    private boolean isUnionpayNulls(SwUnionOrderPayReq mreq) {
        if(StringUtil.checkNullString(mreq.getMch_id()) || StringUtil.checkNullString(mreq.getOut_trade_no()) || StringUtil.checkNullString(mreq.getBody())
                || StringUtil.checkNullString(mreq.getTotal_fee()) || StringUtil.checkNullString(mreq.getMch_create_ip()) || StringUtil.checkNullString(mreq.getNotify_url())
                || StringUtil.checkNullString(mreq.getKey())
        ) {

            return false;
        }
        return true;
    }

}
