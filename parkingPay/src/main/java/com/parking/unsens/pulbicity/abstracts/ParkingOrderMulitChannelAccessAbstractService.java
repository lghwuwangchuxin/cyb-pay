package com.parking.unsens.pulbicity.abstracts;

import com.parking.unsens.pulbicity.channel.ParkingOrderMulitChannelAccessService;

/**

 *   通过 提供 一个 抽象 类 接口 适配模式  解决 选择 实现 方法 
 */


public abstract class ParkingOrderMulitChannelAccessAbstractService implements ParkingOrderMulitChannelAccessService {
	
	//推送停车账单、渠道支付，自动代扣
	public  Object billPay(Object ... objects) throws Exception { return null;}
	
	//订单查询
	public  Object queryPayOrder(Object ... objects) throws Exception { return null;}
	
	//预缴费订单查询  渠道方用户查询主动支付
	public  Object getPreOrder(Object objects) throws Exception { return null;}
	
	//账单支付通知
	public  Object billPayNotify(Object objects) throws Exception { return null;}
		
}

