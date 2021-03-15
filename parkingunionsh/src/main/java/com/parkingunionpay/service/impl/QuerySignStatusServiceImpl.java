package com.parkingunionpay.service.impl;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.parkingunionpay.dto.SignStatusReq;
import com.parkingunionpay.dto.SignStatusRsp;
import com.parkingunionpay.service.QuerySignStatusService;
import com.parkingunionpay.util.ConfigUtil;
import com.parkingunionpay.util.DateUtil;
import com.parkingunionpay.util.HMacMD5;
import com.parkingunionpay.util.HttpUtil;
import com.parkingunionpay.util.RespUtil;

@Service("querySignStatusService")
public class QuerySignStatusServiceImpl implements QuerySignStatusService {

	private static final Logger logger = LoggerFactory.getLogger(QuerySignStatusServiceImpl.class);

	@Override
	public SignStatusRsp getUnionPayStatus(SignStatusReq req) {
		SignStatusRsp rsp = new SignStatusRsp();
		if (req == null) {
			rsp.setRespCode(RespUtil.noResult);
			rsp.setRespDesc("接收到停车系统查询无感支付签约状态服务数据为空");
			logger.info("接收到停车系统查询无感支付签约状态服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统查询无感支付签约状态服务:" + req.getService() + ",数据为:" + req.toString());
		JSONObject jsonObject = new JSONObject();
		try {
			// 封装数据
			jsonObject.put("appId", req.getAppId()); // 应用商户代码
			jsonObject.put("signNo", req.getSignNo()); // 用户签约号
			jsonObject.put("timestamp", DateUtil.getSecondTimestampTwo(new Date()));// 请求发送时间
			jsonObject = HMacMD5.getAscObjct(jsonObject);// 参数名ASCII码排序
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.parseEx);
			rsp.setRespDesc("封装查询无感支付签约状态服务发送数据时发生异常");
			logger.error("封装查询无感支付签约状态服务发送数据时发生异常",e);
			return rsp;
		}

		try {
			String data = jsonObject.toString();
			String signature = HMacMD5.encodeMac(data, req.getPrivateKey());// 生成签名
			jsonObject.put("signature", signature);
			logger.info("发送到智慧通行平台无感支付签约状态服务数据为:" + jsonObject.toString());
			String result = HttpUtil.HttpGet(ConfigUtil.getValue("SignStatusURL"), jsonObject);
			logger.info("接收到智慧通行平台无感支付签约状态服务响应数据为:" + result);
			if (StringUtils.isNotEmpty(result)) {
				JSONObject jsonResult = JSONObject.parseObject(result); // 将结果集转换为JSONObject
				String resultCode = jsonResult.getString("resultCode"); // 获取状态码
				String description = jsonResult.getString("description");
				if ("0".equals(resultCode)) {
					// 解析获取数据
					JSONObject jsonData = jsonResult.getJSONObject("data");
					String signTime = jsonData.getString("signTime");
					String status = jsonData.getString("status");
					String statusDescription = jsonData.getString("statusDescription");
					// 封装BaseRsp响应数据
					rsp.setRespCode(RespUtil.successCode);
					rsp.setRespDesc(description);
					// 封装响应数据
					rsp.setResultCode(resultCode);
					rsp.setDescription(description);
					rsp.setSignTime(signTime);
					rsp.setStatus(status);
					rsp.setStatusDescription(statusDescription);
					logger.info("查询无感支付签约状态服务成功");
				} else {
					rsp.setRespCode(RespUtil.parseEx);
					rsp.setRespDesc(description);
					// 封装响应数据
					rsp.setResultCode(resultCode);
					rsp.setDescription(description);
					logger.info("查询无感支付签约状态服务失败");
				}
			} else {
				rsp.setRespCode(RespUtil.noResult);
				rsp.setRespDesc("接收到智慧通行平台查询无感支付签约状态服务响应数据为空");
				logger.info("接收到智慧通行平台查询无感支付签约状态服务响应数据为空");
			}
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.unknownError);
			rsp.setRespDesc("查询无感支付签约状态服务未知错误");
			logger.info("查询无感支付签约状态服务解析数据时发生错误",e);
		}
		return rsp;
	}

}
