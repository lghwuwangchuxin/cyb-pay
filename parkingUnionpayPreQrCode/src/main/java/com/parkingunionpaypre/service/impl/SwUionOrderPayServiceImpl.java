package com.parkingunionpaypre.service.impl;

import java.util.Map;

import com.parkingunionpaypre.dto.SwUnionOrderPayReq;
import com.parkingunionpaypre.dto.SwUnionOrderPayRsp;
import com.parkingunionpaypre.service.SwUnionOrderPayService;
import com.parkingunionpaypre.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("swUnionOrderPayService")
public class SwUionOrderPayServiceImpl implements SwUnionOrderPayService {
	
	private static final Logger logger = LoggerFactory.getLogger(SwUionOrderPayServiceImpl.class);

	@Override
	public SwUnionOrderPayRsp getSwUionOrderPayRsp(SwUnionOrderPayReq mreq) {
		logger.info("进入SW 中国银联被扫 被扫订单下单支付 业务处理类：【"+mreq.getSerialNumber()+"】-------------");
		SwUnionOrderPayRsp mrsp = new SwUnionOrderPayRsp();
		setParamsRsp(mrsp, RespUtil.noResult, "下单支付失败");
		
		if(isBcNulls(mreq)) {
			try {
				SwUnionOrderPayReq sreq = new SwUnionOrderPayReq();
				sreq.setService("unified.trade.micropay");     //接口名
				sreq.setMch_id(mreq.getMch_id());   //商户号
				sreq.setOut_trade_no(mreq.getOut_trade_no());   //订单号
				sreq.setBody(mreq.getBody());    //商品信息
				sreq.setTotal_fee(mreq.getTotal_fee());   //订单金额   单位：分
				sreq.setMch_create_ip(mreq.getMch_create_ip());    //ip地址
				sreq.setAuth_code(mreq.getAuth_code());    //二维条码
				sreq.setNonce_str(StringUtil.getRomNum());
				
				Map<String, Object> map = SortUtil.objectToMap2(sreq);
				map = SortUtil.mapParamFilter(map);
				String  str = SortUtil.getSignStr(map);    
				logger.info("待签名的字符串为："+str);
				
				//sign= Md5(原字符串&key=商户密钥).toUpperCase
				String signA = SortUtil.sign(str, mreq.getKey(), "utf-8");
				map.put("sign", signA);
				String reqParams = SortUtil.toXml(map);
				logger.info("开始  中国银联 聚合被扫 下单支付---["+mreq.getSerialNumber()+"],请求参数为："+reqParams);
				HttpEntity httpEntity = new StringEntity(reqParams, "UTF-8");    		
				SSLHttpClient httpClient = new SSLHttpClient();
				String result = httpClient.post(ConfigUtil.getValue("OrderPayAddress"), httpEntity);
				logger.info("结束 SW 中国银联 聚合 下单支付---["+mreq.getSerialNumber()+"],响应参数为："+result);
				if(StringUtil.checkNullString(result) || !XmlUtil.isXML(result)) {
					logger.info("中国银联响应参数为空或格式有误");
					mrsp.setRespDesc("中国银联响应参数为空或格式有误");
					return mrsp;
				}
				Map<String,Object> resultMap = SortUtil.xmlStrToMap(result);
				if("0".equals(resultMap.get("status"))) {
					if(resultMap.containsKey("sign")) {
						boolean i = SortUtil.checkParam(resultMap, mreq.getKey());
						logger.info("验签结果为--i:"+i);
						if(i) {
							if("N".equals((String) resultMap.get("need_query")) || !"0".equals((String) resultMap.get("result_code"))) {
								setParamsRsp(mrsp, (String) resultMap.get("err_code"), (String) resultMap.get("err_msg"));
								return mrsp;
							}
							mrsp = (SwUnionOrderPayRsp) XmlUtil.XmlToObj(result, SwUnionOrderPayRsp.class);
							setParamsRsp(mrsp,RespUtil.successCode, "下单支付响应成功");
						}else {
							mrsp.setRespDesc("验签失败--i:"+i);
						}						
					}else {
						setParamsRsp(mrsp, (String) resultMap.get("err_code"), (String) resultMap.get("err_msg"));
					}
				}else {
					mrsp.setRespDesc((String) resultMap.get("message"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}else {
			mrsp.setRespDesc("下单参数缺失");
		}	
		return mrsp;
	}

	/**
	 * 主扫 下单支付
	 *
	 * @param mreq
	 * @return
	 */
	@Override
	public SwUnionOrderPayRsp getCbSwUionOrderPayRsp(SwUnionOrderPayReq mreq) {
		logger.info("进入SW 中国银联 主扫下单 业务处理类：【"+mreq.getSerialNumber()+"】-------------");
		SwUnionOrderPayRsp mrsp = new SwUnionOrderPayRsp();
		setParamsRsp(mrsp, RespUtil.noResult, "下单支付失败");

		if(isCbNulls(mreq)) {
			try {
				SwUnionOrderPayReq sreq = new SwUnionOrderPayReq();
				sreq.setService("unified.trade.native");     //接口名
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
				logger.info("待签名的字符串为："+str);

				//sign= Md5(原字符串&key=商户密钥).toUpperCase
				String signA = SortUtil.sign(str, mreq.getKey(), "utf-8");
				map.put("sign", signA);
				String reqParams = SortUtil.toXml(map);
				logger.info("开始  中国银联条码前置 聚合主扫 下单支付---["+mreq.getSerialNumber()+"],请求参数为："+reqParams);
				HttpEntity httpEntity = new StringEntity(reqParams, "UTF-8");
				SSLHttpClient httpClient = new SSLHttpClient();
				String result = httpClient.post(ConfigUtil.getValue("OrderPayAddress"), httpEntity);
				logger.info("结束 SW 中国银联 聚合主扫条码前置 下单支付---["+mreq.getSerialNumber()+"],响应参数为："+result);
				if(StringUtil.checkNullString(result) || !XmlUtil.isXML(result)) {
					logger.info("中国银联响应参数为空或格式有误");
					mrsp.setRespDesc("中国银联响应参数为空或格式有误");
					return mrsp;
				}
				Map<String,Object> resultMap = SortUtil.xmlStrToMap(result);
				if("0".equals(resultMap.get("status"))) {
					if(resultMap.containsKey("sign")) {
						boolean i = SortUtil.checkParam(resultMap, mreq.getKey());
						logger.info("主扫下单验签结果为--i:"+i);
						if(i && resultMap.containsKey("result_code")) {
							if(!"0".equals((String) resultMap.get("result_code"))) {
								setParamsRsp(mrsp, resultMap.containsKey("err_code") ? (String) resultMap.get("err_code") : RespUtil.noResult, resultMap.containsKey("err_msg") ? (String) resultMap.get("err_msg") : "交易未知");
								return mrsp;
							}
							mrsp = (SwUnionOrderPayRsp) XmlUtil.XmlToObj(result, SwUnionOrderPayRsp.class);
							mrsp.setCode_url("<![CDATA["+mrsp.getCode_url()+"]]>");
							setParamsRsp(mrsp,RespUtil.successCode, "下单成功");
						}else {
							mrsp.setRespDesc("验签失败或下单失败--i:"+i);
						}
					}else {
						setParamsRsp(mrsp, (String) resultMap.get("err_code"), (String) resultMap.get("err_msg"));
					}
				}else {
					mrsp.setRespDesc((String) resultMap.get("message"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			mrsp.setRespDesc("下单参数缺失");
		}
		return mrsp;
	}

	private void setParamsRsp(SwUnionOrderPayRsp mrsp, String respCode, String respDesc) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
	}


	private boolean isBcNulls(SwUnionOrderPayReq mreq) {
		if(StringUtil.checkNullString(mreq.getMch_id()) || StringUtil.checkNullString(mreq.getOut_trade_no()) || StringUtil.checkNullString(mreq.getBody())
				|| StringUtil.checkNullString(mreq.getTotal_fee()) || StringUtil.checkNullString(mreq.getMch_create_ip()) || StringUtil.checkNullString(mreq.getAuth_code())
				|| StringUtil.checkNullString(mreq.getKey())) {
			
			return false;
		}
		return true;
	}

	private boolean isCbNulls(SwUnionOrderPayReq mreq) {
		if(StringUtil.checkNullString(mreq.getMch_id()) || StringUtil.checkNullString(mreq.getOut_trade_no()) || StringUtil.checkNullString(mreq.getBody())
				|| StringUtil.checkNullString(mreq.getTotal_fee()) || StringUtil.checkNullString(mreq.getMch_create_ip()) || StringUtil.checkNullString(mreq.getNotify_url())
				|| StringUtil.checkNullString(mreq.getKey())) {

			return false;
		}
		return true;
	}
}
