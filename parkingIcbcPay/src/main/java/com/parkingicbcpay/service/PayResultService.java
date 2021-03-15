package com.parkingicbcpay.service;

import com.parkingicbcpay.dto.CommonRsp;
import com.parkingicbcpay.dto.PayResultReq;

/**
 * 向E捷通平台查询支付结果
 * @author lengyul
 * @date 2019年4月16日 下午2:56:41
 */
public interface PayResultService {
	
	CommonRsp getPayResult(PayResultReq req);

	/**
	 *  h5支付结果 查询  提前付活扫码付
	 * @param req
	 * @return
	 */
	CommonRsp queryPrePayResult(PayResultReq req);

	/**
	 * 接收提前付 模式 推送结果 支付结果  工行推送
	 */

	String recPrePayNotifyResult(String data);
	
}
