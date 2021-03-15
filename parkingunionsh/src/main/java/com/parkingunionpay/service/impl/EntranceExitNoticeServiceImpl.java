package com.parkingunionpay.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.parkingunionpay.dto.EntranceReq;
import com.parkingunionpay.dto.EntranceRsp;
import com.parkingunionpay.dto.ExitReq;
import com.parkingunionpay.dto.ExitRsp;
import com.parkingunionpay.service.EntranceExitNoticeService;
import com.parkingunionpay.util.ConfigUtil;
import com.parkingunionpay.util.DateUtil;
import com.parkingunionpay.util.HMacMD5;
import com.parkingunionpay.util.HttpUtil;
import com.parkingunionpay.util.RespUtil;

@Service("noticeEntranceExitService")
public class EntranceExitNoticeServiceImpl implements EntranceExitNoticeService {

	private static final Logger logger = LoggerFactory.getLogger(EntranceExitNoticeServiceImpl.class);

	@Override
	public EntranceRsp pushEntranceNotice(EntranceReq req) {
		EntranceRsp rsp = new EntranceRsp();
		if (req == null) {
			rsp.setRespCode(RespUtil.noResult);
			rsp.setRespDesc("接收到停车系统车辆入场通知服务数据为空");
			logger.info("接收到停车系统车辆入场通知服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统车辆入场通知服务:" + req.getService() + ",数据为:" + req.toString());
		JSONObject jsonObject = new JSONObject();
		try {
			// 封装数据
			jsonObject.put("appId", req.getAppId());
			// data数据
			JSONObject jsonData = new JSONObject();
			jsonData.put("syncId", req.getSyncId());// 停车记录唯一标识
			jsonData.put("plateNumber", req.getPlateNumber());// 车牌号
			jsonData.put("startTime", req.getStartTime());// 车辆进入停车场时间
			jsonData.put("parkId", req.getParkId());// 运营商分配给停车场的id
			jsonData.put("parkName", req.getParkName());// 运营商分配给停车场的名称
			jsonObject.put("data", jsonData);
			jsonObject.put("timestamp", DateUtil.getSecondTimestampTwo(new Date()));// 请求发送时间
			jsonObject = HMacMD5.getAscObjct(jsonObject);// 参数名ASCII码排序
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.parseEx);
			rsp.setRespDesc("封装车辆入场通知服务发送数据时发生异常");
			logger.error("封装车辆入场通知服务发送数据时发生异常",e);
			return rsp;
		}

		try {
			String data = jsonObject.toString();
			String signature = HMacMD5.encodeMac(data, req.getPrivateKey());// 生成签名
			jsonObject.put("signature", signature);
			logger.info("发送到智慧通行平台车辆入场通知服务数据为:" + jsonObject.toString());
			String result = HttpUtil.HttpPost(ConfigUtil.getValue("NoticeEntranceURL"), jsonObject.toString(), null);
			logger.info("接收到智慧通行平台车辆入场通知服务响应数据:" + result);
			if (StringUtils.isNotEmpty(result)) {
				JSONObject jsonResult = JSONObject.parseObject(result); // 将结果集转换为JSONObject
				String resultCode = jsonResult.getString("resultCode"); // 获取状态码
				String description = jsonResult.getString("description");
				if ("0".equals(resultCode)) {
					logger.info("智慧通行平台接收车辆入场通知服务成功");
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
					logger.info("智慧通行平台接收车辆入场通知服务失败");
				}
			} else {
				rsp.setRespCode(RespUtil.noResult);
				rsp.setRespDesc("接收到智慧通行平台车辆入场通知服务响应数据为空");
				logger.info("接收到智慧通行平台车辆入场通知服务响应数据为空");
			}
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.unknownError);
			rsp.setRespDesc("车辆入场通知服务未知错误");
			logger.error("车辆入场通知服务解析数据时发生错误",e);
		}
		return rsp;
	}

	@Override
	public ExitRsp pushExitNotice(ExitReq req) {
		ExitRsp rsp = new ExitRsp();
		if (req == null) {
			rsp.setRespCode(RespUtil.noResult);
			rsp.setRespDesc("接收到停车系统车辆出场通知服务数据为空");
			logger.info("接收到停车系统车辆出场通知服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统车辆出场通知服务:" + req.getService() + ",数据为:" + req.toString());
		JSONObject jsonObject = new JSONObject();
		try {
			// 封装数据
			jsonObject.put("appId", req.getAppId());
			// data数据
			JSONObject jsonData = new JSONObject();
			jsonData.put("syncId", req.getSyncId());// 停车记录唯一标识
			jsonData.put("plateNumber", req.getPlateNumber());// 车牌号
			jsonData.put("startTime", req.getStartTime());// 车辆进入停车场时间
			jsonData.put("endTime", req.getEndTime());// 车辆离开停车场时间
			jsonData.put("parkId", req.getParkId());// 运营商分配给停车场的id
			jsonData.put("parkName", req.getParkName());// 运营商分配给停车场的名称
			jsonObject.put("data", jsonData);
			// 请求发送时间
			jsonObject.put("timestamp", DateUtil.getSecondTimestampTwo(new Date()));
			jsonObject = HMacMD5.getAscObjct(jsonObject);// 参数名ASCII码排序
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.parseEx);
			rsp.setRespDesc("封装车辆出场通知服务发送数据时发生异常");
			logger.error("封装车辆出场通知服务发送数据时发生异常",e);
			return rsp;
		}

		try {
			String data = jsonObject.toString();
			String signature = HMacMD5.encodeMac(data, req.getPrivateKey());// 生成签名
			jsonObject.put("signature", signature);
			logger.info("发送到智慧通行平台车辆出场通知服务数据为:" + jsonObject.toString());
			String result = HttpUtil.HttpPost(ConfigUtil.getValue("NoticeExitURL"), jsonObject.toString());
			logger.info("接收到智慧通行平台车辆出场通知服务响应数据为:" + result);
			if (StringUtils.isNotEmpty(result)) {
				JSONObject jsonResult = JSONObject.parseObject(result); // 将结果集转换为JSONObject
				String resultCode = jsonResult.getString("resultCode"); // 获取状态码
				String description = jsonResult.getString("description");
				if ("0".equals(resultCode)) {
					logger.info("智慧通行平台接收车辆出场通知服务成功");
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
					logger.info("智慧通行平台接收车辆出场通知服务失败");
				}
			} else {
				rsp.setRespCode(RespUtil.noResult);
				rsp.setRespDesc("接收到智慧通行平台车辆出场通知服务响应数据为空");
				logger.info("接收到智慧通行平台车辆出场通知服务响应数据为空");
			}
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.unknownError);
			rsp.setRespDesc("车辆出场通知服务未知错误");
			logger.error("车辆出场通知服务解析数据时发生错误",e);
		}
		return rsp;
	}

}
