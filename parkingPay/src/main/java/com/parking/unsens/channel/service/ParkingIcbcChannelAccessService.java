/**
 * ParkingIcbcChannelAccessService.java
 * com.parking.unsens.channel.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-12-28 		Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/
/**
 * ParkingIcbcChannelAccessService.java
 * com.parking.unsens.channel.service
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2018-12-28 Administrator
 *
 * Copyright (c) 2018, TNT All Rights Reserved.
*/


package com.parking.unsens.channel.service;

import com.parking.unsens.pulbicity.channel.ParkingMulitChannelAccessService;

/**
  选择实现
 *  工商银行渠道 共 停车场接入多渠道无感  只提供入场通知 、出场通知 、车牌查询开通情况接口 、查询 车辆出入场记录 公共业务接口 
 */


public interface ParkingIcbcChannelAccessService extends ParkingMulitChannelAccessService {
	
	public Object queryParkingInOutRecorId(Object obj) throws Exception;
	

}

