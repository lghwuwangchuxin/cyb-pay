package com.parkingicbcpay.service;

import com.parkingicbcpay.dto.TradeDetailReq;
import com.parkingicbcpay.dto.TradeDetailRsp;

public interface QueryTradeService {
	//交易详情查询
	public TradeDetailRsp getTradeDetail(TradeDetailReq mreq) throws Exception;
}
