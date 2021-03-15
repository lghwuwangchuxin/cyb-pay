package com.parking.unsens.channel.service;

import com.parking.dtosh.OrderUnpayReq;
import com.parking.dtosh.OrderUnpayRsp;
import com.parking.dtosh.PayBillReq;
import com.parking.dtosh.PayBillRsp;
import com.parking.dtosh.PayResultReq;
import com.parking.dtosh.PayResultRsp;
import com.parking.dtosh.PayStatusReq;
import com.parking.dtosh.PayStatusRsp;

/**

 *  上海金融智慧平台接入账单 相关 业务处理类 ，
 */


public interface ParkingShOrderPayService {
   
	//账单支付
	public PayBillRsp tradePayBill (PayBillReq mreq) throws Exception;
	
	//账单状态查询
	public PayStatusRsp queryPayStatusBill(PayStatusReq mreq) throws Exception;
	
	//支付通知
	public PayResultRsp notifyPayAResult(PayResultReq mreq) throws Exception;
	
	//临时车预缴费停车场查询
	public OrderUnpayRsp getPrePayBill(OrderUnpayReq mreq) throws Exception;
}

