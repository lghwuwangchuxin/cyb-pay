package com.parking.service;

import com.parking.dto.QueryAtmResultReq;
import com.parking.dto.QueryAtmResultRsp;
public interface QrCodeQueryOrderService {

	// 订单查询接口（提供给被扫线下统一）C-B查询状态接口
	public QueryAtmResultRsp queryAtmResultService(QueryAtmResultReq mreq) throws Exception;
}
