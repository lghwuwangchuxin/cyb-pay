package com.parking.service;

import com.parking.domain.ParkingTradeOrder;

/**

 *   异步通知业务类 
 */


public interface ParkingAsynNotifyService {
	
	//无感支付异步通知
	public int notifyCloudPayResult(ParkingTradeOrder tradeOrder);

}

