package com.parking.service;

import com.parking.dto.ParkingTradeOrderQueryReq;
import com.parking.dto.ParkingTradeOrderQueryRsp;

public interface ParkingChannelQueryOrderService {

    public ParkingTradeOrderQueryRsp queryOrder(ParkingTradeOrderQueryReq msg)throws Exception;
}
