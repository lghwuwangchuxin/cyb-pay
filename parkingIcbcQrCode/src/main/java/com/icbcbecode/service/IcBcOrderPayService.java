package com.icbcbecode.service;

import com.icbcbecode.dto.IcBcBaseReq;
import com.icbcbecode.dto.IcBcBaseRsp;

public interface IcBcOrderPayService {
	
	/**
	 * 下单支付
	 * @param paramIcBcBaseReq
	 * @return
	 */
	IcBcBaseRsp getOrderPay(IcBcBaseReq paramIcBcBaseReq);

}
