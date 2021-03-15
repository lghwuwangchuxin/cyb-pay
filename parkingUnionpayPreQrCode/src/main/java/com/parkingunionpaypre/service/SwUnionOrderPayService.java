package com.parkingunionpaypre.service;


import com.parkingunionpaypre.dto.SwUnionOrderPayReq;
import com.parkingunionpaypre.dto.SwUnionOrderPayRsp;

public interface SwUnionOrderPayService {
	
	/**
	 * 被扫订单支付
	 * @param mreq
	 * @return
	 */
	public SwUnionOrderPayRsp getSwUionOrderPayRsp(SwUnionOrderPayReq mreq);

	/**
	 *  主扫 下单支付
	 * @param mreq
	 * @return
	 */
	public SwUnionOrderPayRsp getCbSwUionOrderPayRsp(SwUnionOrderPayReq mreq);

}
