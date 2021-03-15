package com.parking.service;

import com.parking.dto.UnPayNotifyReq;
import com.parking.dto.UnPayNotifyRsp;

public interface UnionQrCodeNotifyService {
	
	/**
	 *  支付通知修改订单 支付状态
	 * @param mreq
	 * @return
	 */
	public UnPayNotifyRsp notifyQrCodeTradeOrderStatus(UnPayNotifyReq mreq)throws Exception;


}
