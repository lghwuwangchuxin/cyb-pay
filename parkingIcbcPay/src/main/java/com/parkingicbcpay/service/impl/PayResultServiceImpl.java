package com.parkingicbcpay.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkingicbcpay.dao.IcbcMchntConfigDao;
import com.parkingicbcpay.domain.IcbcMchntConfig;
import com.parkingicbcpay.dto.CommonRsp;
import com.parkingicbcpay.dto.PayResultReq;
import com.parkingicbcpay.service.PayResultService;
import com.parkingicbcpay.util.CommonDataUtil;
import com.parkingicbcpay.util.EJTRequest;
import com.parkingicbcpay.util.RespUtil;
import tk.mybatis.mapper.util.StringUtil;

@Service("payResultService")
public class PayResultServiceImpl implements PayResultService {

	private static final Logger logger = LoggerFactory.getLogger(PayResultServiceImpl.class);

	@Autowired
	private IcbcMchntConfigDao icbcMchntConfigDao;

	@Override
	public CommonRsp getPayResult(PayResultReq req) {
		CommonRsp rsp = new CommonRsp();
		if (req == null) {
			logger.info("接收到停车系统查询工行平台支付结果服务数据为空");
			setRspParams(rsp, RespUtil.noResult, "接收到停车系统查询工行平台支付结果服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统查询工行平台支付结果服务:" + req.getService() + ",数据为:" + req.toString());
		Map<String, String> dataMap = new LinkedHashMap(12);
		IcbcMchntConfig config = null;
		try {
			IcbcMchntConfig condition = new IcbcMchntConfig(req.getParkingId(), CommonDataUtil.STATE);
			config = icbcMchntConfigDao.selectIcbcMchntConfParkIdById(condition);
			if (config == null) {
				setRspParams(rsp, RespUtil.APP_NOT_FIND, "停车系统未配置该停车场ID或未启动");
				logger.info("停车系统未配置该停车场ID或未启动");
				return rsp;
			}
			rsp.setChannelNum(config.getRsrvStr2());
			// 封装请求数据
			dataMap.put("parkingNum", req.getParkingNum());// 停车记录编号
			dataMap.put("plateNumber", req.getPlateNumber());// 车牌号
			dataMap.put("endTime", req.getEndTime()); // 出场时间
			dataMap.put("duration", req.getDuration()); // 停车时长
			dataMap.put("billing", req.getBilling()); // 停车费用
			dataMap.put("outId", req.getOutId()); // 出场通道唯一识别号 
			// 封装配置数据
			dataMap.put("secretKey", config.getSignKey());
			dataMap.put("appKey", config.getRsrvStr1());
		} catch (Exception e) {
			setRspParams(rsp, RespUtil.parseEx, "封装查询工行平台支付结果服务发送数据时发生异常");
			logger.info("封装查询工行平台支付结果服务发送数据时发生异常");
			e.printStackTrace();
			return rsp;
		}
		try {
			String requestUrl = config.getQueryOrderUrl();
			logger.info("发送到工行平台查询支付结果服务数据为:" + dataMap.toString());
			String result = EJTRequest.callEJT(requestUrl, dataMap);
			logger.info("接收到工行平台查询支付结果服务响应数据为:" + result);
			if (StringUtils.isNotEmpty(result)) {
				rsp = CommonDataUtil.commonRspData(result, rsp);
				if (CommonDataUtil.SUCCESS.equals(rsp.getMessagecode())) {
					logger.info("查询工行平台支付结果服务成功");
					// 封装BaseRsp响应数据
					rsp.setRespCode(RespUtil.successCode);
					rsp.setRespDesc(rsp.getMessage());
				} else {
					logger.info("查询工行平台支付结果服务失败");
					rsp.setRespCode(RespUtil.parseEx);
					String message = rsp.getMessage();
					if (message == null) {
						rsp.setRespDesc(rsp.getStatus());
					} else {
						rsp.setRespDesc(message);						
					}
				}
			} else {
				setRspParams(rsp, RespUtil.noResult, "接收到工行平台查询支付结果服务响应数据为空");
				logger.info("接收到工行平台查询支付结果服务响应数据为空");
			}
		} catch (Exception e) {
			setRspParams(rsp, RespUtil.unknownError, "查询工行平台支付结果服务未知错误");
			logger.info("查询工行平台支付结果服务解析数据时发生错误！");
			e.printStackTrace();
		}
		return rsp;
	}

	/**
	 * h5支付结果 查询  提前付活扫码付
	 *
	 * @param req
	 * @return
	 */
	@Override
	public CommonRsp queryPrePayResult(PayResultReq req) {
		CommonRsp  commonRsp = new CommonRsp();
		try{
			Map<String,String> map = new HashMap<>(6);
			map.put("parkCode", req.getParkingId());
			map.put("orderId",req.getTradeId());
			map.put("secretKey", req.getSecretKey());
			map.put("appKey",req.getAppKey());
			logger.info("扫码付/提前付结果查询请求参数："+map);
			String result = EJTRequest.callEJT(req.getQueryH5Url(), map);
			logger.info("扫码付/提前付结果查询请求结果："+result);
			if (StringUtil.isEmpty(result)){
				setRspParams(commonRsp, RespUtil.queryOrderFail, "请求异常");
				return commonRsp;
			}
			JSONObject rspJson = JSONObject.parseObject(result);
			if (rspJson.containsKey("messagecode") && "0".equals(rspJson.getString("messagecode"))) {
				if (rspJson.containsKey("data") && "1".equals(rspJson.getString("data"))) {
					setRspParams(commonRsp, RespUtil.successCode, "查询订单成功");
					return commonRsp;
				}
			} else {
				setRspParams(commonRsp, RespUtil.queryOrderFail, "未支付");
			}
		}catch (Exception e){
			logger.error("扫码付请求工行扣费结果查询错误",e);
			setRspParams(commonRsp, RespUtil.timeOut, "查询订单请求处理异常");
		}
		return commonRsp;
	}

	/**
	 * 接收提前付 模式 推送结果 支付结果  工行推送
	 *
	 * @param data
	 */
	@Override
	public String recPrePayNotifyResult(String data) {
		logger.info("进入提前付支付结果接收---recPrePayNotifyResult");
		JSONObject result = new JSONObject();
		result.put("messagecode",0);
		result.put("message","请求成功");
		result.put("status",0);
		logger.info("订单支付结果接口返回数据："+result);
		return result.toString();
	}

	private void setRspParams(CommonRsp commonRsp, String appNotFind, String respDesc) {
		commonRsp.setRespCode(appNotFind);
		commonRsp.setRespDesc(respDesc);
	}

}
