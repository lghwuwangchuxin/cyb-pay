package com.parking.service;

import com.parking.dto.*;

public interface ParkingQueryService {

	// 当前 停车费用查询 和  进出场 记录 查询 只用作费用查看 不用做 金额缴费
	public GetParkingPayRsp  getParkingPayDetailInRecord(GetParkingPayReq mreq)throws Exception;
		
   
}
