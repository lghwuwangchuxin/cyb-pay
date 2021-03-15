package com.parkingunionpay.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单支付/退款结果通知接口
 * @author lengyul
 *
 */
public interface PayRefundResultService {
	
	/**
	 * 支付结果通知
	 * @param request
	 * @return
	 */
	String payResultNotify(HttpServletRequest request);
	
	/**
	 * 退款结果通知
	 * @param request
	 * @return
	 */
	String refundResultNotify(HttpServletRequest request);
}
