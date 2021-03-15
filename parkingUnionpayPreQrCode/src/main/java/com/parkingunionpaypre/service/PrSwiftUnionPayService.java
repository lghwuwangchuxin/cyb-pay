package com.parkingunionpaypre.service;

public interface PrSwiftUnionPayService {
	
	/**
	 *  统一入口, 同步方式
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public String swunionpayCallCenterSync(String msg) throws Exception;
	

}
