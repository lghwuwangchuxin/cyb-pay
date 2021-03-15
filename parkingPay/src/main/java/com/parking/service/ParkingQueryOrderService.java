package com.parking.service;

import com.parking.dto.ParkingTradeOrderQueryReq;
import com.parking.dto.ParkingTradeOrderQueryRsp;

/**
 *     无感订单 渠道公共 订单查询
 */


public interface ParkingQueryOrderService {
   
	public ParkingTradeOrderQueryRsp  queryParkingChannelTradeOrder(ParkingTradeOrderQueryReq mreq) throws Exception;
}

