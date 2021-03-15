package com.parkingunionpay.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.parkingunionpay.service.PrParkingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import com.parkingunionpay.dto.PayResultReq;
import com.parkingunionpay.dto.PayResultRsp;
import com.parkingunionpay.dto.RefundResultReq;
import com.parkingunionpay.dto.RefundResultRsp;
import com.parkingunionpay.service.PayRefundResultService;
import com.parkingunionpay.service.QueryShSignKeyService;
import com.parkingunionpay.util.HMacMD5;
import com.parkingunionpay.util.HttpUtil;
import com.parkingunionpay.util.XmlUtil;

@Service("payRefundResultService")
public class PayRefundResultServiceImpl implements PayRefundResultService {

	private static final Logger logger = LoggerFactory.getLogger(PayRefundResultServiceImpl.class);

	@Autowired
	private PrParkingService prParkingService;// 停车服务调用
	@Autowired
	private QueryShSignKeyService queryShSignKeyService;// 获取秘钥服务

	@Override
	public String payResultNotify(HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		PayResultReq req = new PayResultReq();
		try {
			String reqData = HttpUtil.getRequestMessage(request);
			logger.info("接收到智慧通行平台支付结果通知接口数据为:" + reqData);
			if (StringUtils.isEmpty(reqData)) {
				logger.info("接收到智慧通行平台支付结果通知接口数据为空");
				returnJson.put("resultCode", "1001");
				returnJson.put("message", "接收到数据为空");
				return returnJson.toString();
			}
			JSONObject jsonObject = JSONObject.parseObject(reqData);
			String appId = jsonObject.getString("appId");// 商户号
			// 验证签名
			boolean sign = HMacMD5.checkSign(reqData, queryShSignKeyService.getShSignKey(appId));
			if (!sign) {
				logger.info("签名验证失败");
				returnJson.put("resultCode", "1004");
				returnJson.put("message", "签名验证失败");
				return returnJson.toString();
			}
			String timestamp = jsonObject.getString("timestamp");// 请求发送时间
			JSONObject data = jsonObject.getJSONObject("data");
			String syncId = data.getString("syncId");// 订单流水号
			String orderId = data.getString("orderId");// 订单号
			String upOrderId = data.getString("upOrderId");// 全渠道订单号
			String payAmount = data.getString("payAmount");// 订单金额，精确到分
			String orderDate = data.getString("orderDate");// 订单支付时间
			String payStatus = data.getString("payStatus");// 订单状态
			JSONObject jsonReserved = data.getJSONObject("reserved");
			if (jsonReserved != null) {
				String discountAmount = jsonReserved.getString("discountAmount");// 优惠金额
				req.setDiscountAmount(discountAmount);
			}
			// 封装数据
			req.setAppId(appId);
			req.setTimestamp(timestamp);
			req.setSyncId(syncId);
			req.setOrderId(orderId);
			req.setUpOrderId(upOrderId);
			req.setPayAmount(payAmount);
			req.setOrderDate(orderDate);
			req.setPayStatus(payStatus);
		} catch (Exception e) {
			logger.error("解析支付结果通知接口请求数据时发生异常",e);
			returnJson.put("resultCode", "1003");
			returnJson.put("message", "解析支付结果通知接口请求数据时发生异常");
			return returnJson.toString();
		}
		// 调用dubbo服务响应
		try {
			logger.info("调用停车系统无感支付结果通知服务:shPayNotify");
			req.setService("shPayNotify");// 无感支付结果通知服务
			String sendData = XmlUtil.ObjToXml(req, PayResultReq.class);
			logger.info("发送停车系统无感支付结果通知服务数据为" + sendData);

			String result = prParkingService.parkingCallCenterSync(sendData);
			PayResultRsp rsp = (PayResultRsp) XmlUtil.XmlToObj(result, PayResultRsp.class);
			logger.info("停车系统无感支付结果通知服务响应数据为:code:" + rsp.getRespCode() + "desc:" + rsp.getRespDesc());
			if ("000000".equals(rsp.getRespCode())) {
				returnJson.put("resultCode", "0000");
				returnJson.put("message", "支付通知结果成功");
			} else {
				returnJson.put("resultCode", rsp.getRespCode());
				returnJson.put("message", rsp.getRespDesc());
			}
		} catch (Exception e) {
			logger.error("dubbo内部调用异常",e);
			returnJson.put("resultCode", "1003");
			returnJson.put("message", "系统错误");
			return returnJson.toString();
		}
		logger.info("智慧平台无感支付结果通知接口响应数据为:" + returnJson.toString());
		return returnJson.toString();
	}

	@Override
	public String refundResultNotify(HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		RefundResultReq req = new RefundResultReq();
		try {
			String reqData = HttpUtil.getRequestMessage(request);
			logger.info("接收到智慧通行平台退款结果通知接口数据为:" + reqData);
			if (StringUtils.isEmpty(reqData)) {
				logger.info("接收到智慧通行平台退款结果通知接口数据为空");
				returnJson.put("resultCode", "1001");
				returnJson.put("message", "接收到数据为空");
				return returnJson.toString();
			}
			JSONObject jsonObject = JSONObject.parseObject(reqData);
			String appId = jsonObject.getString("appId");// 商户号
			// 验证签名
			boolean sign = HMacMD5.checkSign(reqData, queryShSignKeyService.getShSignKey(appId));
			if (!sign) {
				logger.info("签名验证失败");
				returnJson.put("resultCode", "1004");
				returnJson.put("message", "签名验证失败");
				return returnJson.toString();
			}
			String timestamp = jsonObject.getString("timestamp");// 请求发送时间
			JSONObject data = jsonObject.getJSONObject("data");
			String syncId = data.getString("syncId");// 订单流水号
			String orderId = data.getString("orderId");// 订单号
			String upRefundId = data.getString("upRefundId");// 退款流水号
			String refundAmount = data.getString("refundAmount");// 实际退款金额
			String orderDate = data.getString("orderDate");// 订单支付时间
			String payStatus = data.getString("payStatus");// 订单状态
			// 封装数据
			req.setAppId(appId);
			req.setTimestamp(timestamp);
			req.setSyncId(syncId);
			req.setOrderId(orderId);
			req.setUpRefundId(upRefundId);
			req.setRefundAmount(refundAmount);
			req.setOrderDate(orderDate);
			req.setPayStatus(payStatus);
		} catch (Exception e) {
			logger.error("解析退款结果通知接口请求数据时发生异常",e);
			returnJson.put("resultCode", "1003");
			returnJson.put("message", "解析退款结果通知接口请求数据时发生异常");
			return returnJson.toString();
		}
		// 调用dubbo服务响应
		try {
			logger.info("调用停车系统无感支付退款结果通知服务:shRefundNotify");
			req.setService("shRefundNotify");// 无感支付退款通知
			String sendData = XmlUtil.ObjToXml(req, RefundResultReq.class);

			logger.info("发送停车系统无感支付退款结果通知服务数据为" + sendData);
			String result = prParkingService.parkingCallCenterSync(sendData);
			RefundResultRsp rsp = (RefundResultRsp) XmlUtil.XmlToObj(result, RefundResultRsp.class);
			logger.info("停车系统无感支付退款结果通知服务响应数据为:code:" + rsp.getRespCode() + "desc:" + rsp.getRespDesc());
			if ("000000".equals(rsp.getRespCode())) {
				returnJson.put("resultCode", "0000");
				returnJson.put("message", "退款通知结果成功");
			} else {
				returnJson.put("resultCode", rsp.getRespCode());
				returnJson.put("message", rsp.getRespDesc());
			}
		} catch (Exception e) {
			logger.error("dubbo内部调用异常",e);
			returnJson.put("resultCode", "1003");
			returnJson.put("message", "系统错误");
			return returnJson.toString();
		}
		logger.info("智慧平台无感支付退款通知接口响应数据为:" + returnJson.toString());
		return returnJson.toString();
	}

}
