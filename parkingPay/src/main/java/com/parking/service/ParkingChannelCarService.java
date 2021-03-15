package com.parking.service;

import com.parking.dto.ParkingChannelCarReq;
import com.parking.dto.ParkingChannelCarRsp;

public interface ParkingChannelCarService {
	
	
	/**
	 * 无感签约状态查询
	 * @param mreq
	 * @return
	 * @throws Exception
	 */
	public ParkingChannelCarRsp queryPermitState(ParkingChannelCarReq mreq) throws Exception ;
}
