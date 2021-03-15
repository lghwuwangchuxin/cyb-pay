package com.parkingunionpay.service.impl;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.parkingunionpay.dto.RefundBillReq;
import com.parkingunionpay.dto.RefundBillRsp;
import com.parkingunionpay.dto.RefundStatusReq;
import com.parkingunionpay.dto.RefundStatusRsp;
import com.parkingunionpay.service.RefundBillStatusService;
import com.parkingunionpay.util.ConfigUtil;
import com.parkingunionpay.util.DateUtil;
import com.parkingunionpay.util.HMacMD5;
import com.parkingunionpay.util.HttpUtil;
import com.parkingunionpay.util.RespUtil;

@Service("refundBillStatusService")
public class RefundBillStatusServiceImpl implements RefundBillStatusService {

	private static final Logger logger = LoggerFactory.getLogger(RefundBillStatusServiceImpl.class);

	@Override
	public RefundBillRsp pushRefundBill(RefundBillReq req) {
		RefundBillRsp rsp = new RefundBillRsp();
		if (req == null) {
			rsp.setRespCode(RespUtil.noResult);
			rsp.setRespDesc("接收到停车系统申请订单退款服务数据为空");
			logger.info("接收到停车系统申请订单退款服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统申请订单退款服务:" + req.getService() + ",数据为:" + req.toString());
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("appId", req.getAppId());
			// data数据
			JSONObject jsonData = new JSONObject();
			jsonData.put("plateNumber", req.getPlateNumber());// 车牌号
			jsonData.put("refundAmount", Integer.parseInt(req.getRefundAmount()));// 实际退款金额
			jsonData.put("refundId", req.getRefundId());// 退款流水号
			jsonData.put("orderId", req.getOrderId());// 已经支付成功的订单号
			jsonData.put("refundCallback", req.getRefundCallback());// 退款完成后，运营商接收退款结果通知的url
			jsonObject.put("data", jsonData);
			// 请求发送时间
			jsonObject.put("timestamp", DateUtil.getSecondTimestampTwo(new Date()));
			jsonObject = HMacMD5.getAscObjct(jsonObject);// 参数名ASCII码排序
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.parseEx);
			rsp.setRespDesc("封装申请订单退款服务发送数据时发生异常");
			logger.error("封装申请订单退款服务发送数据时发生异常",e);
			return rsp;
		}

		try {
			String data = jsonObject.toString();
			String signature = HMacMD5.encodeMac(data, req.getPrivateKey());// 生成签名
			jsonObject.put("signature", signature);
			logger.info("发送到智慧通行平台申请订单退款服务数据为:" + jsonObject.toString());
			String result = HttpUtil.HttpPost(ConfigUtil.getValue("RefundBillURL"), jsonObject.toString());
			logger.info("接收到智慧通行平台申请订单退款服务响应数据为:" + result);
			if (StringUtils.isNotEmpty(result)) {
				JSONObject jsonResult = JSONObject.parseObject(result); // 将结果集转换为JSONObject
				String resultCode = jsonResult.getString("resultCode"); // 获取状态码
				String description = jsonResult.getString("description");
				if ("0".equals(resultCode)) {
					logger.info("智慧通行平台接收申请退款服务成功");
					// 解析获取数据
					JSONObject jsonData = jsonResult.getJSONObject("data");
					String orderId = jsonData.getString("orderId");// 订单号
					String upRefundId = jsonData.getString("upRefundId");// 由智慧通行平台生成的退款流水号
					// 封装BaseRsp响应数据
					rsp.setRespCode(RespUtil.successCode);
					rsp.setRespDesc(description);
					// 封装响应数据
					rsp.setResultCode(resultCode);
					rsp.setDescription(description);
					rsp.setOrderId(orderId);
					rsp.setUpRefundId(upRefundId);
				} else {
					rsp.setRespCode(RespUtil.parseEx);
					rsp.setRespDesc(description);
					// 封装响应数据
					rsp.setResultCode(resultCode);
					rsp.setDescription(description);
					logger.info("智慧通行平台接收申请退款服务失败");
				}
			} else {
				rsp.setRespCode(RespUtil.noResult);
				rsp.setRespDesc("接收到智慧通行平台申请退款服务响应数据为空");
				logger.info("接收到智慧通行平台申请退款服务响应数据为空");
			}
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.unknownError);
			rsp.setRespDesc("申请退款服务未知错误");
			logger.error("申请退款服务解析数据时发生错误",e);
			e.printStackTrace();
		}

		return rsp;
	}

	@Override
	public RefundStatusRsp getRefundStatus(RefundStatusReq req) {
		RefundStatusRsp rsp = new RefundStatusRsp();
		if (req == null) {
			rsp.setRespCode(RespUtil.noResult);
			rsp.setRespDesc("接收到停车系统查询退款订单状态服务数据为空");
			logger.info("接收到停车系统查询退款订单状态服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统查询退款订单状态服务:" + req.getService() + ",数据为:" + req.toString());
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("appId", req.getAppId());// 应用商户代码
			jsonObject.put("orderId", req.getOrderId());// 订单号
			jsonObject.put("upRefundId", req.getUpRefundId());// 退款流水号
			jsonObject.put("timestamp", DateUtil.getSecondTimestampTwo(new Date())); // 请求发送时间
			jsonObject = HMacMD5.getAscObjct(jsonObject);// 参数名ASCII码排序
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.parseEx);
			rsp.setRespDesc("封装查询退款订单状态服务发送数据时发生异常");
			logger.info("封装查询退款订单状态服务发送数据时发生异常");
			e.printStackTrace();
			return rsp;
		}

		try {
			String data = jsonObject.toString();
			String signature = HMacMD5.encodeMac(data, req.getPrivateKey());// 生成签名
			jsonObject.put("signature", signature);
			logger.info("发送到智慧通行平台查询退款订单状态服务数据为:" + jsonObject.toString());
			String result = HttpUtil.HttpGet(ConfigUtil.getValue("RefundStatusURL"), jsonObject);
			logger.info("接收到智慧通行平台查询退款订单状态服务响应数据为:" + result);
			if (StringUtils.isNotEmpty(result)) {
				JSONObject jsonResult = JSONObject.parseObject(result); // 将结果集转换为JSONObject
				String resultCode = jsonResult.getString("resultCode"); // 获取状态码
				String description = jsonResult.getString("description");
				if ("0".equals(resultCode)) {
					logger.info("查询退款订单状态服务成功");
					// 解析获取数据
					JSONObject jsonData = jsonResult.getJSONObject("data");
					String orderId = jsonData.getString("orderId");// 订单号
					String upRefundId = jsonData.getString("upRefundId");// 由智慧通行平台生成的退款流水号
					String refundAmount = jsonData.getString("refundAmount");// 实际退款金额，精确到分，以分为单位
					String orderDate = jsonData.getString("orderDate");// 订单支付时间
					String payStatus = jsonData.getString("payStatus");// 订单状态
					// 封装BaseRsp响应数据
					rsp.setRespCode(RespUtil.successCode);
					rsp.setRespDesc(description);
					// 封装响应数据
					rsp.setResultCode(resultCode);
					rsp.setDescription(description);
					rsp.setOrderId(orderId);
					rsp.setUpRefundId(upRefundId);
					rsp.setRefundAmount(refundAmount);
					rsp.setOrderDate(orderDate);
					rsp.setPayStatus(payStatus);
				} else {
					rsp.setRespCode(RespUtil.parseEx);
					rsp.setRespDesc(description);
					// 封装响应数据
					rsp.setResultCode(resultCode);
					rsp.setDescription(description);
					logger.info("查询退款订单状态服务失败");
				}
			} else {
				rsp.setRespCode(RespUtil.noResult);
				rsp.setRespDesc("接收到智慧通行平台查询退款订单状态服务响应数据为空");
				logger.info("接收到智慧通行平台查询退款订单状态服务响应数据为空");
			}
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.unknownError);
			rsp.setRespDesc("查询退款订单状态服务未知错误");
			logger.info("查询退款订单状态服务解析数据时发生错误！");
			e.printStackTrace();
		}
		return rsp;
	}

}
