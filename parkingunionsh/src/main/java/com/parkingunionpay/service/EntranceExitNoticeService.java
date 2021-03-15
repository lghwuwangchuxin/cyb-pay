package com.parkingunionpay.service;

import com.parkingunionpay.dto.EntranceReq;
import com.parkingunionpay.dto.EntranceRsp;
import com.parkingunionpay.dto.ExitReq;
import com.parkingunionpay.dto.ExitRsp;

/**
 * 运营商向智慧通行平台推送车辆出入场通知
 * @author lengyul
 *
 */
public interface EntranceExitNoticeService {
	
	/**
	 * 推送入场通知
	 * @param req
	 * @return
	 */
	   EntranceRsp pushEntranceNotice(EntranceReq req);
	/**
	 *  推送出场通知
	 * @param req
	 * @return
	 */
	   ExitRsp pushExitNotice(ExitReq req);
}
