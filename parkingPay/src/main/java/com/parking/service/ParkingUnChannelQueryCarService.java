package com.parking.service;

import com.parking.dto.ParkingChannelCarReq;
import com.parking.dto.ParkingChannelCarRsp;

/**

 *   统一渠道无感car状态查询业务 
 */


public interface ParkingUnChannelQueryCarService {
  
	//统一入口无感car状态查询
	public ParkingChannelCarRsp unParkingChannelQuery(ParkingChannelCarReq mreq) throws Exception;
}

