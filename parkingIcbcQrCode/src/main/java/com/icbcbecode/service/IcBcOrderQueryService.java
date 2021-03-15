package com.icbcbecode.service;

import com.icbcbecode.dto.IcBcBaseReq;
import com.icbcbecode.dto.IcBcBaseRsp;

public interface IcBcOrderQueryService {
	
	/**
	 * 订单支付状态查询
	 * @param paramIcBcBaseReq
	 * @return
	 */
	IcBcBaseRsp getOrderStatus(IcBcBaseReq paramIcBcBaseReq);

	
}
