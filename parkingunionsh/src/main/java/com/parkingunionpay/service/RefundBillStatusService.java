package com.parkingunionpay.service;

import com.parkingunionpay.dto.RefundBillReq;
import com.parkingunionpay.dto.RefundBillRsp;
import com.parkingunionpay.dto.RefundStatusReq;
import com.parkingunionpay.dto.RefundStatusRsp;

/**
 * 运营商接口申请退款和订单退款状态查询
 * @author lengyul
 *
 */
public interface RefundBillStatusService {
		
	/**
	 * 申请退款
	 * @param req
	 * @return
	 */
		RefundBillRsp pushRefundBill(RefundBillReq req);
	
	/**
	 * 订单退款接口查询
	 * @param req
	 * @return
	 */
		RefundStatusRsp getRefundStatus(RefundStatusReq req);
}
