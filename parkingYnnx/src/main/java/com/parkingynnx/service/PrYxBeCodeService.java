package com.parkingynnx.service;

public interface PrYxBeCodeService {
	//对外暴露服务统一入口，同步方式
	public String YxBeCodeCallCenterSync(String msg) throws Exception;	
	//对外暴露服务统一入口，异步方式
	public String YxBeCodeCallCenterAsyn(String msg) throws Exception;	
}
