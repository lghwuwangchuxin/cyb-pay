/**
 * ParkingMulitChannelAccessService.java
 * com.parking.unsens.channel.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-9-3 		Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/
/**
 * ParkingMulitChannelAccessService.java
 * com.parking.unsens.channel.service
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-9-3 Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/


package com.parking.unsens.pulbicity.channel;
/*
 *    公共 停车场接入多渠道无感  只提供入场通知 、出场通知 、车牌查询开通情况接口 、公共业务接口 
 */


public interface ParkingMulitChannelAccessService {
	
	//渠道入场通知
	public  Object enterNotify(Object ... objects) throws Exception;
	
	//渠道出场通知
	public Object outNotify(Object ... objects) throws Exception;
	
	//查询车牌无感
	public Object queryPermitState(Object ... objects) throws Exception;
	
	//无感绑定开通通知
	public Object noFeelingBindingNotify(Object objects) throws Exception;
	
	//无感解绑通知
	public Object noFeelingCancelBindingNotify(Object objects) throws Exception;
}

