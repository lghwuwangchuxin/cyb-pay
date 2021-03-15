package com.parking.service;
/**
 *  统一多 渠道判断绑定 和解绑通知  业务类
 */


public interface ParkingAsynBindCanceNotifyService {
	
	public int bindOrCanceNotify(String parkId, String carPlate, String state, String channelId) throws Exception;

}

