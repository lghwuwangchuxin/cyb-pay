package com.parkingunionpaypre.service;


import com.parkingunionpaypre.dto.SwUnionRefundOrderReq;
import com.parkingunionpaypre.dto.SwUnionRefundOrderRsp;

public interface SwiftUnionOrderRefService {
	
	/**
	 * 订单退款
	 * @param mreq
	 * @return
	 */
	public SwUnionRefundOrderRsp getSwUnionRefundOrderRsp(SwUnionRefundOrderReq mreq);

}
