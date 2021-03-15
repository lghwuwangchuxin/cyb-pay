package com.parkingunionpaypre.service.impl;

import java.util.Map;

import com.parkingunionpaypre.dto.SwUnionRefundOrderReq;
import com.parkingunionpaypre.dto.SwUnionRefundOrderRsp;
import com.parkingunionpaypre.service.SwiftUnionOrderRefService;
import com.parkingunionpaypre.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service("swiftUnionOrderRefService")
public class SwiftUnionOrderRefServiceImpl implements SwiftUnionOrderRefService {
	
	private static final Logger logger = LoggerFactory.getLogger(SwiftUnionOrderRefServiceImpl.class);

	@Override
	public SwUnionRefundOrderRsp getSwUnionRefundOrderRsp(SwUnionRefundOrderReq mreq) {
		logger.info("进入SW 中国银联 订单支付 状态查询 业务处理类：【"+mreq.getSerialNumber()+"】-------------");
		SwUnionRefundOrderRsp mrsp = new SwUnionRefundOrderRsp();
		mrsp.setRespCode(RespUtil.noResult);
		mrsp.setRespDesc("订单退款失败");
		
		if(isNulls(mreq)) {
			try {
				SwUnionRefundOrderReq sreq = new SwUnionRefundOrderReq();
				sreq.setService("unified.trade.refund");     //接口名
				sreq.setMch_id(mreq.getMch_id());    //商户号
				sreq.setOut_trade_no(mreq.getOut_trade_no());   //订单号
				sreq.setOut_refund_no(mreq.getOut_refund_no());    //退款单号
				sreq.setNonce_str(StringUtil.getRomNum());
				sreq.setTotal_fee(mreq.getTotal_fee());    //订单总金额，单位为分
				sreq.setRefund_fee(mreq.getTotal_fee());    //退款总金额,单位为分,可以做部分退款
				sreq.setOp_user_id(mreq.getMch_id());   //操作员帐号,默认为商户号
				
				Map<String, Object> map = SortUtil.objectToMap2(sreq);
				map = SortUtil.mapParamFilter(map);
				String  str = SortUtil.getSignStr(map);    //flase 不用url encode
				logger.info("待签名的字符串为："+str);
				
				//sign= Md5(原字符串&key=商户密钥).toUpperCase
				String signA = SortUtil.sign(str, mreq.getKey(), "utf-8");
				map.put("sign", signA);
				String reqParams = SortUtil.toXml(map);
				logger.info("开始 SW 中国银联 订单退款---["+mreq.getSerialNumber()+"],请求参数为："+reqParams);
				HttpEntity httpEntity = new StringEntity(reqParams, "UTF-8");    		
				SSLHttpClient httpClient = new SSLHttpClient();
				String result = httpClient.post(ConfigUtil.getValue("OrderPayAddress"), httpEntity);
				logger.info("结束 SW 中国银联 订单退款---["+mreq.getSerialNumber()+"],响应参数为："+result);
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
								mrsp = (SwUnionRefundOrderRsp) XmlUtil.XmlToObj(result, SwUnionRefundOrderRsp.class);
								mrsp.setRespCode(RespUtil.successCode);
								mrsp.setRespDesc("退款申请接收成功");								
							}else {
								mrsp.setRespCode((String) resultMap.get("err_code"));
								mrsp.setRespDesc((String) resultMap.get("err_msg"));
							}
						}else {
							mrsp.setRespDesc("验签失败--i:"+i);
						}						
					}else {
						mrsp.setRespCode((String) resultMap.get("err_code"));
						mrsp.setRespDesc((String) resultMap.get("err_msg"));
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
	
	public boolean isNulls(SwUnionRefundOrderReq mreq) {
		if(StringUtil.checkNullString(mreq.getMch_id()) || StringUtil.checkNullString(mreq.getOut_trade_no()) || StringUtil.checkNullString(mreq.getOut_refund_no())
				|| StringUtil.checkNullString(mreq.getTotal_fee()) || StringUtil.checkNullString(mreq.getRefund_fee()) ) {
			return false;
		}
		return true;
	}

}
