package com.parking.unsens.pulbicity.channel;
/**

 *   多渠道 公共账单推送订单、支付订单查询 、预缴费订单查询 接口 
 */


public interface ParkingOrderMulitChannelAccessService {
	
	//推送停车账单、渠道支付，自动代扣
	public Object billPay(Object ... objects) throws Exception;
	
	//订单查询
	public Object queryPayOrder(Object ... objects) throws Exception;
	
	//预缴费订单查询  渠道方用户查询主动支付
	public Object getPreOrder(Object objects) throws Exception;
	
	//账单支付通知
	public Object billPayNotify(Object objects) throws Exception;
		
}

