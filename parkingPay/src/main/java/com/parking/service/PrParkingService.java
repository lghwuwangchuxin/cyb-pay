package com.parking.service;

public interface PrParkingService {
	// feign对外暴露服务统一入口，同步方式
	public String parkingCallCenterSync(String msg) throws Exception;

	// fegin对外暴露服务统一入口，异步方式
	public String parkingCallCenterAsyn(String msg) throws Exception;
}
