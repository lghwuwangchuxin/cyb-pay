package com.parking.unsens.pulbicity.channel;
/**

 *  多渠道公共退款渠道  、查询退款
 */


public interface ParkingRefundOrderMulitChannelAccessService {
	
	//订单退款
	public Object refund(Object ...objects) throws Exception;
		
	//退款订单查询
	public Object queryRefund(Object ... objects) throws Exception;
	
	//退款通知
	public Object refundNotify(Object objects) throws Exception;
}

