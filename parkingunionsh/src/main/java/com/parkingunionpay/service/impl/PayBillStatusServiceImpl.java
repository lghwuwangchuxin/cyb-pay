package com.parkingunionpay.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.parkingunionpay.dto.PayBillReq;
import com.parkingunionpay.dto.PayBillRsp;
import com.parkingunionpay.dto.PayStatusReq;
import com.parkingunionpay.dto.PayStatusRsp;
import com.parkingunionpay.service.PayBillStatusService;
import com.parkingunionpay.util.ConfigUtil;
import com.parkingunionpay.util.DateUtil;
import com.parkingunionpay.util.HMacMD5;
import com.parkingunionpay.util.HttpUtil;
import com.parkingunionpay.util.RespUtil;

@Service("payBillStatusService")
public class PayBillStatusServiceImpl implements PayBillStatusService {

	private static final Logger logger = LoggerFactory.getLogger(PayBillStatusServiceImpl.class);

	@Override
	public PayBillRsp pushPayBill(PayBillReq req) {
		PayBillRsp rsp = new PayBillRsp();
		if (req == null) {
			rsp.setRespCode(RespUtil.noResult);
			rsp.setRespDesc("接收到停车系统推送支付订单服务数据为空");
			logger.info("接收到停车系统推送支付订单服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统推送支付订单服务:" + req.getService() + ",数据为:" + req.toString());
		JSONObject jsonObject = new JSONObject(new LinkedHashMap<String, Object>());
		try {
			// 封装数据
			jsonObject.put("appId", req.getAppId());// 应用商户代码
			jsonObject.put("industryCode", req.getIndustryCode());// 发起扣款请求的商户号
			// data数据
			JSONObject jsonData = new JSONObject();
			jsonData.put("syncId", req.getSyncId());// 支付请求唯一序列号
			jsonData.put("plateNumber", req.getPlateNumber());// 车牌号
			jsonData.put("orderId", req.getOrderId());// 订单号
			jsonData.put("payAmount", Integer.parseInt(req.getPayAmount()));// 订单金额
			jsonData.put("serviceAmount", Integer.parseInt(req.getServiceAmount()));// 总金额
			jsonData.put("orderDate", req.getOrderDate());// 订单生成时间
			jsonData.put("startTime", req.getStartTime());// 车辆进入停车场时间
			jsonData.put("endTime", req.getEndTime());// 车辆离开停车场时间
			jsonData.put("parkId", req.getParkId());// 运营商分配给停车场的id
			jsonData.put("parkName", req.getParkName());// 运营商分配给停车场的名称
			jsonData.put("payCallback", req.getPayCallback());// 支付完成后，智慧通行平台向此url推送支付结果
			jsonData.put("accSplitData", req.getAccSplitData());// 分账域
			jsonObject.put("data", jsonData);
			// 请求发送时间
			jsonObject.put("timestamp", DateUtil.getSecondTimestampTwo(new Date()));
			jsonObject = HMacMD5.getAscObjct(jsonObject);// 参数名ASCII码排序
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.parseEx);
			rsp.setRespDesc("封装推送支付订单服务发送数据时发生异常");
			logger.error("封装推送支付订单服务发送数据时发生异常",e);
			return rsp;
		}
		try {
			String data = jsonObject.toString();
			String signature = HMacMD5.encodeMac(data, req.getPrivateKey());// 生成签名
			jsonObject.put("signature", signature);
			logger.info("发送到智慧通行平台推送支付订单服务数据为:" + jsonObject.toString());
			String result = HttpUtil.HttpPost(ConfigUtil.getValue("PayBillURL"), jsonObject.toString());
			logger.info("接收到智慧通行平台推送支付订单服务响应数据为:" + result);
			if (StringUtils.isNotEmpty(result)) {
				JSONObject jsonResult = JSONObject.parseObject(result); // 将结果集转换为JSONObject
				String resultCode = jsonResult.getString("resultCode"); // 获取状态码
				String description = jsonResult.getString("description");
				if ("0".equals(resultCode)) {
					logger.info("智慧通行平台接收推送支付订单服务成功");
					// 封装BaseRsp响应数据
					rsp.setRespCode(RespUtil.successCode);
					rsp.setRespDesc(description);
					// 封装响应数据
					rsp.setResultCode(resultCode);
					rsp.setDescription(description);
				} else {
					rsp.setRespCode(RespUtil.parseEx);
					rsp.setRespDesc(description);
					// 封装响应数据
					rsp.setResultCode(resultCode);
					rsp.setDescription(description);
					logger.info("智慧通行平台接收推送支付订单服务失败");
				}
			} else {
				rsp.setRespCode(RespUtil.noResult);
				rsp.setRespDesc("接收到智慧通行平台推送支付订单服务响应数据为空");
				logger.info("接收到智慧通行平台推送支付订单服务响应数据为空");
			}
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.unknownError);
			rsp.setRespDesc("推送支付订单服务未知错误");
			logger.error("推送支付订单服务解析数据时发生错误",e);
		}
		return rsp;
	}

	@Override
	public PayStatusRsp getPayStatus(PayStatusReq req) {
		PayStatusRsp rsp = new PayStatusRsp();
		if (req == null) {
			rsp.setRespCode(RespUtil.noResult);
			rsp.setRespDesc("接收到停车系统查询支付订单状态服务数据为空");
			logger.info("接收到停车系统查询支付订单状态服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统查询支付订单状态服务:" + req.getService() + ",数据为:" + req.toString());
		JSONObject jsonObject = new JSONObject(new LinkedHashMap<String, Object>());
		try {
			jsonObject.put("appId", req.getAppId());// 应用商户代码
			jsonObject.put("orderId", req.getOrderId());// 订单号
			jsonObject.put("timestamp", DateUtil.getSecondTimestampTwo(new Date()));// 请求发送时间
			jsonObject = HMacMD5.getAscObjct(jsonObject);// 参数名ASCII码排序
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.parseEx);
			rsp.setRespDesc("封装查询支付订单状态服务发送数据时发生异常");
			logger.info("封装查询支付订单状态服务发送数据时发生异常");
			e.printStackTrace();
			return rsp;
		}

		try {
			String data = jsonObject.toString();
			String signature = HMacMD5.encodeMac(data, req.getPrivateKey());// 生成签名
			jsonObject.put("signature", signature);
			logger.info("发送到智慧通行平台查询支付订单状态服务数据为:" + jsonObject.toString());
			String result = HttpUtil.HttpGet(ConfigUtil.getValue("PayStatusURL"), jsonObject);
			logger.info("接收到智慧通行平台查询支付订单状态服务响应数据为:" + result);
			if (StringUtils.isNotEmpty(result)) {
				JSONObject jsonResult = JSONObject.parseObject(result); // 将结果集转换为JSONObject
				String resultCode = jsonResult.getString("resultCode"); // 获取状态码
				String description = jsonResult.getString("description");
				if ("0".equals(resultCode)) {
					logger.info("查询支付订单状态服务成功");
					// 解析获取数据
					JSONObject jsonData = jsonResult.getJSONObject("data");
					String orderId = jsonData.getString("orderId");// 订单号
					String upOrderId = jsonData.getString("upOrderId");// 全渠道订单号
					String payAmount = jsonData.getString("payAmount");// 订单金额
					String orderDate = jsonData.getString("orderDate");// 订单支付时间
					String payStatus = jsonData.getString("payStatus");// 订单状态
					// reserved
					JSONObject jsonReserved = jsonData.getJSONObject("reserved");
					if (jsonReserved != null) {
						String discountAmount = jsonReserved.getString("discountAmount");// 优惠金额
						rsp.setDiscountAmount(discountAmount);
					}
					// 封装BaseRsp响应数据
					rsp.setRespCode(RespUtil.successCode);
					rsp.setRespDesc(description);
					// 封装响应数据
					rsp.setResultCode(resultCode);
					rsp.setDescription(description);
					rsp.setOrderId(orderId);
					rsp.setUpOrderId(upOrderId);
					rsp.setPayAmount(payAmount);
					rsp.setOrderDate(orderDate);
					rsp.setPayStatus(payStatus);

				} else {
					rsp.setRespCode(RespUtil.parseEx);
					rsp.setRespDesc(description);
					// 封装响应数据
					rsp.setResultCode(resultCode);
					rsp.setDescription(description);
					logger.info("查询支付订单状态服务失败");
				}
			} else {
				rsp.setRespCode(RespUtil.noResult);
				rsp.setRespDesc("接收到智慧通行平台查询支付订单状态服务响应数据为空");
				logger.info("接收到智慧通行平台查询支付订单状态服务响应数据为空");
			}
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.unknownError);
			rsp.setRespDesc("查询支付订单状态服务未知错误");
			logger.info("查询支付订单状态服务解析数据时发生错误！");
			e.printStackTrace();
		}
		return rsp;
	}

}
