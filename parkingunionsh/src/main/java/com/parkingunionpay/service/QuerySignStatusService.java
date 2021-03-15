package com.parkingunionpay.service;

import com.parkingunionpay.dto.SignStatusReq;
import com.parkingunionpay.dto.SignStatusRsp;

/**
 * 运营商通过此接口查询无感支付签约状态
 * @author lengyul
 *
 */
public interface QuerySignStatusService {

	/**
	 * 查询无感支付签约状态
	 * @param signStatusReq
	 * @return
	 */
	SignStatusRsp getUnionPayStatus(SignStatusReq signStatusReq);
}
