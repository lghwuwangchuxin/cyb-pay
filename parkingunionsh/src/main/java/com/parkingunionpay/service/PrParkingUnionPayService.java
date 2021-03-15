package com.parkingunionpay.service;

public interface PrParkingUnionPayService {
	//fegin对外暴露服务统一入口，同步方式
	public String parkingUnionPayCallCenterSync(String msg) throws Exception;
	//fegin对外暴露服务统一入口，异步方式
	public String parkingUnionPayCallCenterAsyn(String msg) throws Exception;
}
