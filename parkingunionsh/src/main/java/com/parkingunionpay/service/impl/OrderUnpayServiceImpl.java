package com.parkingunionpay.service.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.parkingunionpay.service.PrParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.parkingunionpay.dto.OrderUnpayReq;
import com.parkingunionpay.dto.OrderUnpayRsp;
import com.parkingunionpay.service.OrderUnpayService;
import com.parkingunionpay.service.QueryShSignKeyService;
import com.parkingunionpay.util.HMacMD5;
import com.parkingunionpay.util.XmlUtil;

@Service("orderUnpayService")
public class OrderUnpayServiceImpl implements OrderUnpayService {

	private static final Logger logger = LoggerFactory.getLogger(OrderUnpayServiceImpl.class);

	@Autowired
	private PrParkingService prParkingService;// 停车服务调用
	@Autowired
	private QueryShSignKeyService queryShSignKeyService;// 获取秘钥服务

	@SuppressWarnings("deprecation")
	@Override
	public String getOrderUnpay(HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		OrderUnpayReq req = new OrderUnpayReq();
		try {
			String plateNumber = request.getParameter("plateNumber");// 车牌号
			plateNumber = new String(plateNumber.getBytes("iso-8859-1"), "UTF-8");
			String appId = request.getParameter("appId");// 商户号
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));// 当前页码
			Integer count = Integer.parseInt(request.getParameter("count"));// 最大条数
			String signature = request.getParameter("signature");// 签名串
			signature = URLEncoder.encode(signature);

			String reqData = "{\"plateNumber\":'" + plateNumber + "',\"appId\":'" + appId + "',\"pageNo\":" + pageNo
					+ ",\"count\":" + count + ",\"signature\":'" + signature + "'}";
			logger.info("接收到智慧通行平台查询用户停车费信息接口数据" + reqData);
			JSONObject jsonSort = JSONObject.parseObject(reqData); // 数据转JSONObject验证签名排序
			// 验证签名
			boolean sign = HMacMD5.checkSign(HMacMD5.getAscObjct(jsonSort).toString(),
					queryShSignKeyService.getShSignKey(appId));
			if (!sign) {
				logger.info("签名验证失败");
				returnJson.put("resultCode", "1004");
				returnJson.put("message", "签名验证失败");
				return returnJson.toString();
			}
			// String plateNumber = jsonObject.getString("plateNumber");//车牌号
			// String appId = jsonObject.getString("appId");//运营商给智慧通行平台颁发的商户号
			// String pageNo = jsonObject.getString("pageNo");//pageNo代表当前页码
			// String count = jsonObject.getString("count");//count代表当前页记录的最大条数
			// 封装发送数据
			req.setPlateNumber(plateNumber);
			req.setAppId(appId);
			req.setPageNo(pageNo.toString());
			req.setCount(count.toString());
		} catch (Exception e) {
			logger.error("解析查询用户停车费信息接口请求数据时发生异常",e);
			returnJson.put("resultCode", "1003");
			returnJson.put("message", "解析查询用户停车费信息接口请求数据时发生异常");
			return returnJson.toString();
		}
		// 调用dubbo服务响应
		try {
			logger.info("调用停车系统查询临时车预缴停车费服务:shGetPreOrder");
			req.setService("shGetPreOrder");// 查询临时车预缴停车费服务
			String sendData = XmlUtil.ObjToXml(req, OrderUnpayReq.class);

			logger.info("发送停车系统查询临时车预缴停车费服务数据为" + sendData);
			String result = prParkingService.parkingCallCenterSync(sendData);
			OrderUnpayRsp rsp = (OrderUnpayRsp) XmlUtil.XmlToObj(result, OrderUnpayRsp.class);
			/*
			 * OrderUnpayRsp rsp = new OrderUnpayRsp();
			 * rsp.setRespCode("000000"); rsp.setAppId("BZHTCH");
			 * rsp.setSyncId("111111"); rsp.setIndustryCode("802110048991169");
			 * rsp.setPlateNumber("琼CD2605"); rsp.setOrderId("111111004203");
			 * rsp.setPayAmount("1"); rsp.setServiceAmount("1");
			 * rsp.setOrderDate("2018-07-19T04:10:55.290Z");
			 * rsp.setStartTime("2018-07-19T02:10:55.290Z");
			 * rsp.setEndTime("2018-07-19T04:10:55.290Z"); rsp.setParkId("123");
			 * rsp.setParkName("测试停车场"); rsp.setPayCallback(
			 * "http://xxxx/parkingunionpay/payNotify");//
			 * 推送支付结果url rsp.setAccSplitData(
			 * "{\"accSplitType\":\"2\",\"accSplitRuleId\":\"123456789012345\"}"
			 * );
			 */
			logger.info("停车系统查询临时车预缴停车费服务响应数据为:code:" + rsp.getRespCode() + "desc:" + rsp.getRespDesc() + "data:"
					+ rsp.toString());
			if ("000000".equals(rsp.getRespCode())) {
				returnJson.put("resultCode", "0000");
				returnJson.put("message", "查询停车费信息成功");
				JSONObject jsonData = new JSONObject();
				jsonData.put("syncId", rsp.getSyncId());// 支付请求唯一序列号，由运营商生成
				jsonData.put("appId", rsp.getAppId());// 应用商户代码，由智慧通行平台分配
				jsonData.put("industryCode", rsp.getIndustryCode());// 停车场对应的商户号
				jsonData.put("plateNumber", rsp.getPlateNumber());// 车牌号
				jsonData.put("orderId", rsp.getOrderId());// 订单号，由运营商生成
				jsonData.put("payAmount", Integer.parseInt(rsp.getPayAmount()));// 实际支付费用，精确到分，以分为单位
				jsonData.put("serviceAmount", Integer.parseInt(rsp.getServiceAmount()));// 总金额
				jsonData.put("orderDate", rsp.getOrderDate());// 订单生成时间
				jsonData.put("startTime", rsp.getStartTime());// 车辆进入停车场时间
				jsonData.put("endTime", rsp.getEndTime());// 车辆离开停车场时间
				jsonData.put("parkId", rsp.getParkId());// 运营商分配给停车场的id
				jsonData.put("parkName", rsp.getParkName());// 运营商分配给停车场的名称
				jsonData.put("payCallback", rsp.getPayCallback());// 支付完成后，智慧通行平台向此url推送支付结果
				jsonData.put("accSplitData", rsp.getAccSplitData());// 分账域
				// data数据为array类型
				JSONArray jsonArray = new JSONArray();
				jsonArray.add(jsonData);
				returnJson.put("data", jsonArray);
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
		logger.info("智慧平台查询临时车预缴停车费接口响应数据为:" + returnJson.toString());
		return returnJson.toString();
	}

}
