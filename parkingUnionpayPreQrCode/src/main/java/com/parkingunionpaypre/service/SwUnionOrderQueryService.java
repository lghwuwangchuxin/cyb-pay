package com.parkingunionpaypre.service;


import com.parkingunionpaypre.dto.SwUnionOrderQueryReq;
import com.parkingunionpaypre.dto.SwUnionOrderQueryRsp;

public interface SwUnionOrderQueryService {
	
	/**
	 * 订单支付状态查询
	 * @param mreq
	 * @return
	 */
	public SwUnionOrderQueryRsp getSwUnionOrderQueryRsp(SwUnionOrderQueryReq mreq);

}
