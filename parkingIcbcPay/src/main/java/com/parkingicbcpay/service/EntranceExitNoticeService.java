package com.parkingicbcpay.service;

import com.parkingicbcpay.dto.CommonRsp;
import com.parkingicbcpay.dto.EntranceReq;
import com.parkingicbcpay.dto.ExitReq;
import com.parkingicbcpay.dto.EntranceExitRsp;

/**
 * 向E捷通平台推送车辆出入场通知
 * @author lengyul
 */
public interface EntranceExitNoticeService {

	/**
	 * 推送入场通知
	 * @param req
	 * @return
	 */
		EntranceExitRsp pushEntranceNotice(EntranceReq req); 
	
	/**
	 * 推送出场通知（支付）
	 * @param req
	 * @return
	 */
		EntranceExitRsp pushExitNotice(ExitReq req);

	/**
	 *  提前付 或者扫码付
	 * @param req
	 * @return
	 */
	  CommonRsp prePay(ExitReq req);
}
