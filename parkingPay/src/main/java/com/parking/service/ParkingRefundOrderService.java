package com.parking.service;

import com.parking.dto.OrderRefundReq;
import com.parking.dto.OrderRefundRsp;

/**
 *   公共统一退款订单接口
 */

public interface ParkingRefundOrderService {

	//订单退款
	public OrderRefundRsp refundOrder(OrderRefundReq mreq) throws Exception;
	
	//查询退款状态
	public OrderRefundRsp queryRefundOrdr(OrderRefundReq mreq) throws Exception;
	
	//退款通知结果
	public OrderRefundRsp notifyRefundAResult(OrderRefundReq mreq) throws Exception;

}

