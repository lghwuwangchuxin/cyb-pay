package com.parkingunionpaypre.service.impl;

import java.util.Map;

import com.parkingunionpaypre.dto.SwUnionOrderQueryReq;
import com.parkingunionpaypre.dto.SwUnionOrderQueryRsp;
import com.parkingunionpaypre.service.SwUnionOrderQueryService;
import com.parkingunionpaypre.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("swUnionOrderQueryService")
public class SwUnionOrderQueryServiceImpl implements SwUnionOrderQueryService {

	private static final Logger logger = LoggerFactory.getLogger(SwUnionOrderQueryServiceImpl.class);
	
	@Override
	public SwUnionOrderQueryRsp getSwUnionOrderQueryRsp(SwUnionOrderQueryReq mreq) {
		logger.info("进入SW 中国银联 订单支付 状态查询 业务处理类：【"+mreq.getSerialNumber()+"】-------------");
		SwUnionOrderQueryRsp mrsp = new SwUnionOrderQueryRsp();
		setRspParams(mrsp, RespUtil.noResult, "订单支付 状态查询失败");
		
		if(isNulls(mreq)) {
			try {
				SwUnionOrderQueryReq sreq = new SwUnionOrderQueryReq();
				sreq.setService("unified.trade.query");     //接口名
				sreq.setMch_id(mreq.getMch_id());    //商户号
				sreq.setOut_trade_no(mreq.getOut_trade_no());   //订单号
				sreq.setTransaction_id(mreq.getTransaction_id());    //平台订单号 
				sreq.setNonce_str(StringUtil.getRomNum());
				
				Map<String, Object> map = SortUtil.objectToMap2(sreq);
				map = SortUtil.mapParamFilter(map);
				String  str = SortUtil.getSignStr(map);    //flase 不用url encode
				logger.info("待签名的字符串为："+str);
				
				//sign= Md5(原字符串&key=商户密钥).toUpperCase
				String signA = SortUtil.sign(str, mreq.getKey(), "utf-8");
				map.put("sign", signA);
				String reqParams = SortUtil.toXml(map);
				logger.info("开始 SW 中国银联 订单支付 状态查询---["+mreq.getSerialNumber()+"],请求参数为："+reqParams);
				HttpEntity httpEntity = new StringEntity(reqParams, "UTF-8");    		
				SSLHttpClient httpClient = new SSLHttpClient();
				String result = httpClient.post(ConfigUtil.getValue("OrderPayAddress"), httpEntity);
				logger.info("结束 SW 中国银联 订单支付 状态查询---["+mreq.getSerialNumber()+"],响应参数为："+result);
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
							if("0".equals((String) resultMap.get("result_code"))) {								
								mrsp = (SwUnionOrderQueryRsp) XmlUtil.XmlToObj(result, SwUnionOrderQueryRsp.class);
								setRspParams(mrsp, RespUtil.successCode, "订单支付 状态查询响应成功");
							}else {
								setRspParams(mrsp, (String) resultMap.get("err_code"), (String) resultMap.get("err_msg"));
							}
						}else {
							mrsp.setRespDesc("验签失败--i:"+i);
						}						
					}else {
						setRspParams(mrsp, (String) resultMap.get("err_code"), (String) resultMap.get("err_msg"));
					}
				}else {
					mrsp.setRespDesc((String) resultMap.get("message"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}else {
			mrsp.setRespDesc("订单支付查询参数缺失");
		}	
		return mrsp;
	}

	private void setRspParams(SwUnionOrderQueryRsp mrsp, String noResult, String respDesc) {
		mrsp.setRespCode(noResult);
		mrsp.setRespDesc(respDesc);
	}

	public boolean isNulls(SwUnionOrderQueryReq mreq) {
		if(StringUtil.checkNullString(mreq.getMch_id()) || StringUtil.checkNullString(mreq.getOut_trade_no())) {
			return false;
		}
		return true;
	}
}
