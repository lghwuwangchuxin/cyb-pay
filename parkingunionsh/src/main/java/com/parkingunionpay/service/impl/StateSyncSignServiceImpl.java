package com.parkingunionpay.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.parkingunionpay.service.PrParkingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import com.parkingunionpay.dto.StateSyncSignReq;
import com.parkingunionpay.dto.StateSyncSignRsp;
import com.parkingunionpay.service.QueryShSignKeyService;
import com.parkingunionpay.service.StateSyncSignService;
import com.parkingunionpay.util.HMacMD5;
import com.parkingunionpay.util.HttpUtil;
import com.parkingunionpay.util.XmlUtil;

@Service("stateSyncSignService")
public class StateSyncSignServiceImpl implements StateSyncSignService {

	private static final Logger logger = LoggerFactory.getLogger(StateSyncSignServiceImpl.class);

	@Autowired
	private PrParkingService prParkingService;// 停车服务调用
	@Autowired
	private QueryShSignKeyService queryShSignKeyService;// 获取秘钥服务

	@Override
	public String stateSyncSign(HttpServletRequest request) {
		JSONObject returnJson = new JSONObject();
		StateSyncSignReq req = new StateSyncSignReq();
		try {
			String reqData = HttpUtil.getRequestMessage(request);
			logger.info("接收到智慧通行无感支付状态同步接口数据" + reqData);
			if (StringUtils.isEmpty(reqData)) {
				logger.info("接收到智慧通行平台无感支付状态同步接口数据为空");
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
			String signNo = data.getString("signNo");// 用户签约号
			String plateNumber = data.getString("plateNumber");// 车牌号
			String status = data.getString("status");// 无感支付签约状态
			String statusDescription = data.getString("statusDescription");// 无感支付签约状态
			// 封装发送数据
			req.setAppId(appId);
			req.setTimestamp(timestamp);
			req.setSignNo(signNo);
			req.setPlateNumber(plateNumber);
			req.setStatus(status);
			req.setStatusDescription(statusDescription);
		} catch (Exception e) {
			logger.error("解析无感支付状态同步接口请求数据时发生异常",e);
			returnJson.put("resultCode", "1003");
			returnJson.put("message", "解析无感支付状态同步接口请求数据时发生异常");
			return returnJson.toString();
		}
		// 调用dubbo服务响应
		try {
			logger.info("调用停车系统无感支付状态同步服务:shStateSyncNotify");
			req.setService("shStateSyncNotify");// 无感支付状态同步服务
			String sendData = XmlUtil.ObjToXml(req, StateSyncSignReq.class);

			logger.info("发送停车系统无感支付状态同步服务数据为" + sendData);
			String result = prParkingService.parkingCallCenterSync(sendData);
			StateSyncSignRsp rsp = (StateSyncSignRsp) XmlUtil.XmlToObj(result, StateSyncSignRsp.class);
			logger.info("停车系统无感支付状态同步服务响应数据为:code:" + rsp.getRespCode() + "desc:" + rsp.getRespDesc());
			if ("000000".equals(rsp.getRespCode())) {
				returnJson.put("resultCode", "0000");
				returnJson.put("message", "无感支付状态同步成功");
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
		logger.info("智慧平台无感支付状态同步接口响应数据为:" + returnJson.toString());
		return returnJson.toString();
	}

}
