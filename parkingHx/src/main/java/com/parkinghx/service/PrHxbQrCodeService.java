package com.parkinghx.service;

public interface PrHxbQrCodeService {
	//fein对外暴露服务统一入口，同步方式
	public String hxbQrCodeCallCenterSync(String msg) throws Exception;
}
