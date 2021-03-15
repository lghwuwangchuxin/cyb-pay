package com.parking.unsens.channel.service;

import com.parking.dtosh.*;

/**

 *   上海智慧平台账单  退款交易业务处理 
 */


public interface ParkingShOrderRefundService {
 
	//退款
	public RefundBillRsp refundBill(RefundBillReq mreq) throws Exception;
	
	//退款查询
	public RefundStatusRsp queryRefundBillStatus(RefundStatusReq mreq) throws Exception;
	
	//通知退款结果
	public RefundResultRsp notifyRefundResult(RefundResultReq mreq) throws Exception;
}

