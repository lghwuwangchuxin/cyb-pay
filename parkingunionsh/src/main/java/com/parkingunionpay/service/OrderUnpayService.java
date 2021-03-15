package com.parkingunionpay.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 主动缴费
 * @author lengyul
 *
 */
public interface OrderUnpayService {
	/**
	 * 查询停车费 
	 * @param request
	 * @return
	 */
	String getOrderUnpay(HttpServletRequest request);
}
