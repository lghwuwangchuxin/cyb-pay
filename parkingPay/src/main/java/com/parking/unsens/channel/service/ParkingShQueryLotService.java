package com.parking.unsens.channel.service;


import com.parking.dtosh.ParkingStateReq;
import com.parking.dtosh.ParkingStateRsp;

/**

 *    上海金融事业部智慧平台查询getLot 停车场状态信息 
 */


public interface ParkingShQueryLotService {

	//查询停车场状态信息
	public ParkingStateRsp getParkingLotStateInfo(ParkingStateReq mreq) throws Exception;
}

