package com.parking.service;
/**

 *   工商   银行渠道 无感 查询  同时也是入场通知   绑卡 通知
 */


public interface ParkingChannelIcbcCarService extends ParkingChannelCarService{
	
	// 绑卡 通知 
	public String bindPermitCarNotify(String carPlate, String parkId);

}

