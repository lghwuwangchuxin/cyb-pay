package com.parkingicbcpay.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.parkingicbcpay.dto.CommonRsp;
import com.parkingicbcpay.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.parkingicbcpay.dao.IcbcMchntConfigDao;
import com.parkingicbcpay.domain.IcbcMchntConfig;
import com.parkingicbcpay.dto.EntranceReq;
import com.parkingicbcpay.dto.ExitReq;
import com.parkingicbcpay.dto.EntranceExitRsp;
import com.parkingicbcpay.service.EntranceExitNoticeService;
import com.parkingicbcpay.util.CommonDataUtil;
import com.parkingicbcpay.util.EJTRequest;
import com.parkingicbcpay.util.RespUtil;
import tk.mybatis.mapper.util.StringUtil;

@Service("entranceExitNoticeService")
public class EntranceExitNoticeServiceImpl implements EntranceExitNoticeService {

	private static final Logger logger = LoggerFactory.getLogger(EntranceExitNoticeServiceImpl.class);

	@Autowired
	private IcbcMchntConfigDao icbcMchntConfigDao;
	
	@Override
	public EntranceExitRsp pushEntranceNotice(EntranceReq req) {
		EntranceExitRsp rsp = new EntranceExitRsp();
		if (req == null) {
			rsp.setRespCode(RespUtil.noResult);
			rsp.setRespDesc("接收到停车系统推送工行平台入场通知服务数据为空");
			logger.info("接收到停车系统推送工行平台入场通知服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统推送工行平台入场通知服务:" + req.getService() + ",数据为:" + req.toString());
		Map<String, String> dataMap = new LinkedHashMap();
		IcbcMchntConfig config = null;
		try {
			IcbcMchntConfig condition = new IcbcMchntConfig(req.getParkingId(), CommonDataUtil.STATE);
			config = icbcMchntConfigDao.selectIcbcMchntConfParkIdById(condition);
			if (config == null) {
				rsp.setRespCode(RespUtil.APP_NOT_FIND);
				rsp.setRespDesc("停车系统未配置该停车场ID或未启动");
				logger.info("停车系统未配置该停车场ID或未启动");
				return rsp;
			}
			// 封装请求数据
			dataMap.put("parkCode", config.getPartParkId());// 停车场编号
			dataMap.put("parkingNum", req.getParkingNum());// 停车记录编号
			dataMap.put("plateNumber", req.getPlateNumber());// 车牌号
			dataMap.put("startTime", req.getStartTime());// 入场时间
			dataMap.put("userStatus", req.getUserStatus());// 用户类型
			dataMap.put("plateType", "99".equals(req.getPlateType())? "02" : req.getPlateType()); // 车牌类型 颜色
			// 封装配置数据
			dataMap.put("secretKey", config.getSignKey());
			dataMap.put("appKey", config.getRsrvStr1());
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.parseEx);
			rsp.setRespDesc("封装推送工行平台入场通知服务发送数据时发生异常");
			logger.info("封装推送工行平台入场通知服务发送数据时发生异常");
			e.printStackTrace();
			return rsp;
		}
		try {
			String requestUrl = config.getInUrl();
			logger.info("发送到工行平台推送入场通知服务数据为:" + dataMap.toString());
			String result = EJTRequest.callEJT(requestUrl, dataMap);
			logger.info("接收到工行平台推送入场通知服务响应数据为:" + result);
			if (StringUtils.isNotEmpty(result)) {
				JSONObject jsonResult = JSONObject.parseObject(result); // 将结果集转换为JSONObject
				String messagecode = jsonResult.getString("messagecode");
				String message = jsonResult.getString("message");
				String status = jsonResult.getString("status");
				if ("10000".equals(messagecode)) {
					logger.info("推送工行平台入场通知服务成功");
					// 封装BaseRsp响应数据
					rsp.setRespCode(RespUtil.successCode);
					rsp.setRespDesc(message);
					String threshold = jsonResult.getString("threshold");
					rsp.setThreshold(threshold);
				} else {
					logger.info("推送工行平台入场通知服务失败");
					rsp.setRespCode(RespUtil.parseEx);
					if (message == null) {
						rsp.setRespDesc(status);
					} else {
						rsp.setRespDesc(message);						
					}
				}
				// 封装响应数据
				rsp.setMessagecode(messagecode);
				rsp.setMessage(message);
				rsp.setStatus(status);
			} else {
				rsp.setRespCode(RespUtil.noResult);
				rsp.setRespDesc("接收到工行平台推送入场通知服务响应数据为空");
				logger.info("接收到工行平台推送入场通知服务响应数据为空");
			}
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.unknownError);
			rsp.setRespDesc("推送工行平台入场通知服务未知错误");
			logger.info("推送工行平台入场通知服务解析数据时发生错误！");
			e.printStackTrace();
		}
		return rsp;
	}

	@Override
	public EntranceExitRsp pushExitNotice(ExitReq req) {
		EntranceExitRsp rsp = new EntranceExitRsp();
		rsp.setChannelNum(CommonDataUtil.CHANNEL_NUM);
		if (req == null) {
			rsp.setRespCode(RespUtil.noResult);
			rsp.setRespDesc("接收到停车系统推送工行平台出场通知服务数据为空");
			logger.info("接收到停车系统推送工行平台出场通知服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统推送工行平台出场通知服务:" + req.getService() + ",数据为:" + req.toString());
		Map<String, String> dataMap = new LinkedHashMap();
		IcbcMchntConfig config = null;
		try {
			IcbcMchntConfig condition = new IcbcMchntConfig(req.getParkingId(), CommonDataUtil.STATE);
			config = icbcMchntConfigDao.selectIcbcMchntConfParkIdById(condition);
			if (config == null) {
				rsp.setRespCode(RespUtil.APP_NOT_FIND);
				rsp.setRespDesc("停车系统未配置该停车场ID或未启动");
				logger.info("停车系统未配置该停车场ID或未启动");
				return rsp;
			}
			rsp.setChannelNum(config.getRsrvStr2());
			// 封装请求数据
			dataMap.put("parkCode", config.getPartParkId());// 停车场编号
			dataMap.put("parkingNum", req.getParkingNum());// 停车记录编号
			dataMap.put("plateNumber", req.getPlateNumber());// 车牌号
			dataMap.put("endTime", req.getEndTime());// 出场时间
			dataMap.put("duration", req.getDuration());// 停车时长
			dataMap.put("billing", req.getBilling());// 停车费用
			dataMap.put("plateType", "99".equals(req.getPlateType())? "02" : req.getPlateType()); // 车牌类型 颜色
			// 封装配置数据
			dataMap.put("secretKey", config.getSignKey());
			dataMap.put("appKey", config.getRsrvStr1());
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.parseEx);
			rsp.setRespDesc("封装推送工行平台出场通知服务发送数据时发生异常");
			logger.info("封装推送工行平台出场通知服务发送数据时发生异常");
			e.printStackTrace();
			return rsp;
		}
		try {
			String requestUrl = config.getOutUrl();
			logger.info("发送到工行平台推送出场通知服务数据为:" + dataMap.toString());
			String result = EJTRequest.callEJT(requestUrl, dataMap);
			logger.info("接收到工行平台推送出场通知服务响应数据为:" + result);
			if (StringUtils.isNotEmpty(result)) {
				JSONObject jsonResult = JSONObject.parseObject(result); // 将结果集转换为JSONObject
				String messagecode = jsonResult.getString("messagecode");
				String message = jsonResult.getString("message");
				String status = jsonResult.getString("status");
				if ("20000".equals(messagecode) || "20002".equals(messagecode)) {
					logger.info("推送工行平台出场通知服务成功");
					// 封装BaseRsp响应数据
					rsp.setRespCode(RespUtil.successCode);
					rsp.setRespDesc(message);
				} else {
					logger.info("推送工行平台出场通知服务失败");
					rsp.setRespCode(RespUtil.parseEx);
					if (message == null) {
						rsp.setRespDesc(status);
					} else {
						rsp.setRespDesc(message);						
					}
				}
				// 封装响应数据
				rsp.setMessagecode(messagecode);
				rsp.setMessage(message);
				rsp.setStatus(status);
			} else {
				rsp.setRespCode(RespUtil.noResult);
				rsp.setRespDesc("接收到工行平台推送出场通知服务响应数据为空");
				logger.info("接收到工行平台推送出场通知服务响应数据为空");
			}
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.unknownError);
			rsp.setRespDesc("推送工行平台出场通知服务未知错误");
			logger.info("推送工行平台出场通知服务解析数据时发生错误！");
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * 提前付 或者扫码付
	 *
	 * @param req
	 * @return
	 */
	@Override
	public CommonRsp prePay(ExitReq req) {
		logger.info("进入工行提前付或扫码付");
		CommonRsp commonRsp = new CommonRsp();
		try{
			Map<String,String> map = new LinkedHashMap<>(16);
			map.put("secretKey", req.getSecretKey());
			map.put("appKey", req.getAppKey());
			map.put("parkCode", req.getParkingId());
			map.put("orderId", req.getTradeId());
			map.put("amount",req.getBilling());//金额（分）
			map.put("redirectUrl", req.getReturnUrl());
			map.put("plateNumber",req.getPlateNumber());//
			map.put("plateType", "99".equals(req.getPlateType())? "02" : req.getPlateType());
			map.put("startTime",req.getStartTime());
			map.put("endTime",req.getEndTime()); //
			map.put("duration", TimeUtil.getTimeDifference(map.get("startTime"),map.get("endTime")));
			logger.info("扫码付/提前付请求参数："+map);
			String result = EJTRequest.callEJT(req.getPreUrl(), map);
			logger.info("扫码付/提前付请求结果："+result);
			if (!StringUtil.isEmpty(result)){
				JSONObject json = JSONObject.parseObject(result);
				if (json.containsKey("messagecode") && "0".equals(json.getString("messagecode"))) {
					String data  = JSONObject.parseObject(json.getString("data")).getString("value");
					logger.info("解析返回data-value内容：" +data);
					commonRsp.setData(data);
					setRspParams(commonRsp, RespUtil.successCode, "下单成功");
				} else {
					setRspParams(commonRsp, RespUtil.orderPayFail, "下单失败");
				}
			}else{
				setRspParams(commonRsp, RespUtil.orderPayFail, "请求异常或下单失败");
			}
		}catch (Exception e){
			logger.error("扫码付请求工行扣费数据组装错误",e);
			setRspParams(commonRsp, RespUtil.orderPayFail, "下单异常");
		}
		return commonRsp;
	}

	private void setRspParams(CommonRsp commonRsp, String appNotFind, String respDesc) {
		commonRsp.setRespCode(appNotFind);
		commonRsp.setRespDesc(respDesc);
	}

}
