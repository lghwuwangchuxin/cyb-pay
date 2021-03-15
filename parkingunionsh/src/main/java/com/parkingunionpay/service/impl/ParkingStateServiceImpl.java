package com.parkingunionpay.service.impl;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import com.parkingunionpay.service.PrParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.parkingunionpay.dto.ParkingStateReq;
import com.parkingunionpay.dto.ParkingStateRsp;
import com.parkingunionpay.service.ParkingStateService;
import com.parkingunionpay.service.QueryShSignKeyService;
import com.parkingunionpay.util.HMacMD5;
import com.parkingunionpay.util.XmlUtil;

@Service("parkingStateService")
public class ParkingStateServiceImpl implements ParkingStateService {

	private static final Logger logger = LoggerFactory.getLogger(ParkingStateServiceImpl.class);

	@Autowired
	private PrParkingService prParkingService;// 停车服务调用
	@Autowired
	private QueryShSignKeyService queryShSignKeyService;// 获取秘钥服务

	@Override
	public String getParkingState(HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		ParkingStateReq req = new ParkingStateReq();
		try {
			String appId = request.getParameter("appId");// 商户号
			String parkId = request.getParameter("parkId");// 停车场的Id
			String signature = request.getParameter("signature");// 签名串
			signature = URLEncoder.encode(signature);

			String reqData = "{\"appId\":'" + appId + "',\"parkId\":'" + parkId + "',\"signature\":'" + signature
					+ "'}";
			logger.info("接收到智慧通行平台查询停车场的剩余车辆状态接口数据" + reqData);
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
			/*
			 * String appId = jsonObject.getString("appId ");//商户号 String parkId
			 * = jsonObject.getString("parkId");//停车场的Id
			 */
			// 封装发送数据
			req.setAppId(appId);
			req.setParkId(parkId);
		} catch (Exception e) {
			logger.error("解析查询停车场的剩余车辆状态接口请求数据时发生异常",e);
			returnJson.put("resultCode", "1003");
			returnJson.put("message", "解析查询停车场的剩余车辆状态接口请求数据时发生异常");
			return returnJson.toString();
		}
		// 调用dubbo服务响应
		try {
			logger.info("调用停车系统查询停车场实时状态服务:shGetParking");
			req.setService("shGetParking");// 查询停车场实时状态服务
			String sendData = XmlUtil.ObjToXml(req, ParkingStateReq.class);

			logger.info("发送停车系统查询停车场实时状态服务数据为" + sendData);
			String result = prParkingService.parkingCallCenterSync(sendData);
			ParkingStateRsp rsp = (ParkingStateRsp) XmlUtil.XmlToObj(result, ParkingStateRsp.class);
			/*
			 * ParkingStateRsp rsp = new ParkingStateRsp();
			 * rsp.setRespCode("000000"); rsp.setAppId("BZHTCH");
			 * rsp.setParkId("123"); rsp.setParkName("测试停车场");
			 * rsp.setLat("39.872748"); rsp.setLon("116.429718");
			 * rsp.setCapacityStatus("1"); rsp.setTotalSpace("100");
			 * rsp.setFreeSpace("20"); rsp.setBusinessHours("00:00-24:00");
			 * rsp.setCityName("武汉市"); rsp.setProvinceName("湖北省");
			 * rsp.setChargingDescription("武昌区");
			 * rsp.setChargingDescription("4");
			 * rsp.setUpdateTime(DateUtil.getDateUTCFormat(new Date()));
			 */
			logger.info("停车系统查询停车场实时状态服务响应数据为:code:" + rsp.getRespCode() + "desc:" + rsp.getRespDesc() + "data:"
					+ rsp.toString());
			if ("000000".equals(rsp.getRespCode())) {
				returnJson.put("resultCode", "0000");
				returnJson.put("message", "查询停车状态信息成功");
				JSONObject jsonData = new JSONObject();
				jsonData.put("appId", rsp.getAppId());// 应用商户代码
				jsonData.put("parkId", rsp.getParkId());// 停车场的id
				jsonData.put("parkName", rsp.getParkName());// 停车场的名称
				jsonData.put("lat", Float.parseFloat(rsp.getLat()));// 纬度坐标
				jsonData.put("lon", Float.parseFloat(rsp.getLon()));// 经度坐标
				jsonData.put("capacityStatus", rsp.getCapacityStatus());// 车位状态
				jsonData.put("totalSpace", rsp.getTotalSpace());// 总车位数
				jsonData.put("freeSpace", rsp.getFreeSpace());// 空闲车位数
				jsonData.put("businessHours", rsp.getBusinessHours());// 营业时间
				jsonData.put("cityName", rsp.getCityName());// 城市名称
				jsonData.put("provinceName", rsp.getProvinceName());// 省名
				jsonData.put("districtName", rsp.getDistrictName());// 区名
				jsonData.put("chargingDescription", rsp.getChargingDescription());// 收费标准
				jsonData.put("updateTime", rsp.getUpdateTime());// 停车场状态更新时间
				returnJson.put("data", jsonData);
			} else {
				returnJson.put("resultCode", rsp.getRespCode());
				returnJson.put("message", rsp.getRespDesc());
			}
		} catch (Exception e) {
			logger.error("fegin内部调用异常",e);
			returnJson.put("resultCode", "1003");
			returnJson.put("message", "系统错误");
			return returnJson.toString();
		}
		logger.info("智慧平台查询停车场实时状态接口响应数据为:" + returnJson.toString());
		return returnJson.toString();
	}

}
