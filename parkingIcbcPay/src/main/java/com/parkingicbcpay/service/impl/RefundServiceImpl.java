package com.parkingicbcpay.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkingicbcpay.dao.IcbcMchntConfigDao;
import com.parkingicbcpay.domain.IcbcMchntConfig;
import com.parkingicbcpay.dto.CommonRsp;
import com.parkingicbcpay.dto.RefundReq;
import com.parkingicbcpay.service.RefundService;
import com.parkingicbcpay.util.CommonDataUtil;
import com.parkingicbcpay.util.EJTRequest;
import com.parkingicbcpay.util.RespUtil;

@Service("refundService")
public class RefundServiceImpl implements RefundService {

	private static final Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);

	@Autowired
	private IcbcMchntConfigDao icbcMchntConfigDao;

	@Override
	public CommonRsp pushRefundInfo(RefundReq req) {
		CommonRsp rsp = new CommonRsp();
		if (req == null) {
			rsp.setRespCode(RespUtil.noResult);
			rsp.setRespDesc("接收到停车系统推送工行平台退款通知服务数据为空");
			logger.info("接收到停车系统推送工行平台退款通知服务数据为空");
			return rsp;
		}
		logger.info("接收到停车系统推送工行平台退款通知服务:" + req.getService() + ",数据为:" + req.toString());
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
			dataMap.put("parkingNum", req.getParkingNum());// 停车记录编号
			dataMap.put("refund", req.getRefund());// 退款金额
			dataMap.put("remark", req.getRemark()); // 备注
			// 封装配置数据
			dataMap.put("secretKey", config.getSignKey());
			dataMap.put("appKey", config.getRsrvStr1());
		} catch (Exception e) {
			rsp.setRespCode(RespUtil.parseEx);
			rsp.setRespDesc("封装推送工行平台退款通知服务发送数据时发生异常");
			logger.info("封装推送工行平台退款通知服务发送数据时发生异常");
			e.printStackTrace();
			return rsp;
		}
		try {
			String requestUrl = config.getRefundUrl();
			logger.info("发送到工行平台推送退款通知服务数据为:" + dataMap.toString());
			String result = EJTRequest.callEJT(requestUrl, dataMap);
			logger.info("接收到工行平台推送退款通知服务响应数据为:" + result);
			if (StringUtils.isNotEmpty(result)) {
				rsp = CommonDataUtil.commonRspData(result, rsp);
				if (CommonDataUtil.SUCCESS0.equals(rsp.getMessagecode())) {
					logger.info("推送工行平台退款通知服务成功");
					// 封装BaseRsp响应数据
					rsp.setRespCode(RespUtil.successCode);
					rsp.setRespDesc(rsp.getMessage());
				} else {
					logger.info("推送工行平台退款通知服务失败");
					rsp.setRespCode(RespUtil.parseEx);
					String message = rsp.getMessage();
					if (message == null) {
						rsp.setRespDesc(rsp.getStatus());
					} else {
						rsp.setRespDesc(message);						
					}
				}
			} else {
				rsp.setRespCode(RespUtil.noResult);
				rsp.setRespDesc("接收到工行平台推送退款通知服务响应数据为空");
				logger.info("接收到工行平台推送退款通知服务响应数据为空");
			}
		} catch(Exception e) {
			rsp.setRespCode(RespUtil.unknownError);
			rsp.setRespDesc("推送工行平台退款通知服务未知错误");
			logger.info("推送工行平台退款通知服务解析数据时发生错误！");
			e.printStackTrace();
		}
		return rsp;
	}

}
