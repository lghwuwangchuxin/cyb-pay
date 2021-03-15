package com.parkingunionpay.service;

import com.parkingunionpay.dto.PayBillReq;
import com.parkingunionpay.dto.PayBillRsp;
import com.parkingunionpay.dto.PayStatusReq;
import com.parkingunionpay.dto.PayStatusRsp;

/**
 * 运营商推送订单和订单状态查询
 * @author lengyul
 *
 */
public interface PayBillStatusService {
		
	/**
	 * 推送订单流水到智慧通行平台
	 * @param req
	 * @return
	 */
	   PayBillRsp pushPayBill(PayBillReq req);
	   
	   /**
	    * 查询订单状态
	    * @param req
	    * @return
	    */
	   PayStatusRsp getPayStatus(PayStatusReq req);
}
