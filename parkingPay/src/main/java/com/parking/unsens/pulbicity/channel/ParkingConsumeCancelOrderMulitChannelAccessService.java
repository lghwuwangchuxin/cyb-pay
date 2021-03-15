package com.parking.unsens.pulbicity.channel;
/**

 *   多渠道 公共消费 订单撤销 接口 
 *   、和撤销交易查询 
 */


public interface ParkingConsumeCancelOrderMulitChannelAccessService {
	
	//消费撤销
	public Object consumeCancel(Object ...objects) throws Exception;
		
	//消费撤销查询
	public Object queryConsumeCancel(Object ... objects) throws Exception;

}

