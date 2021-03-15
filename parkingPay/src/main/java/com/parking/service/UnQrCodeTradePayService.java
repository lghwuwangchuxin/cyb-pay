package com.parking.service;

import com.parking.dto.ApplyOrderReq;
import com.parking.dto.ApplyOrderRsp;

/**
 *  停车场线下被扫渠道统一二维码处理类 ，路由
 */

public interface UnQrCodeTradePayService {
   
	//统一业务被扫 路由处理  路由处理
	// 订单支付接口，商户扫用户二维码进行扣款
	public ApplyOrderRsp unPayTradeService(ApplyOrderReq mreq) throws Exception;
}

