package com.parkingicbcpay.service;

public interface PrParkingIcbcPayService {
	//fegin对外暴露服务统一入口，同步方式
	public String parkingIcbcPayCallCenterSync(String msg) throws Exception;
	//fegin对外暴露服务统一入口，异步方式
	public String parkingIcbcPayCallCenterAsyn(String msg) throws Exception;
}
