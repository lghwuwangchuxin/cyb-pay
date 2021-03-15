
package com.parking.service;

import com.parking.dto.ApplyOrderReq;
import com.parking.dto.ApplyOrderRsp;

public interface QrCodeTradePayService {
	// 订单支付接口，商户扫用户二维码进行扣款
	public ApplyOrderRsp payTradeService(ApplyOrderReq mreq) throws Exception;
	
}

