package com.parkingunionpay.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 无感支付状态同步
 * @author lengyul
 *
 */
public interface StateSyncSignService {
	/**
	 * 无感支付状态同步   
	 * @param request
	 * @return
	 */
	String stateSyncSign(HttpServletRequest request);
}
