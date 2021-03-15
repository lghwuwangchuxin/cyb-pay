package com.parking.service;

import com.parking.domain.ParkingPreChannelPayRouteConfig;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.ParkingPrePayOrderReq;

public interface ParkingH5ChannelPayTradeProxyService {

    // 公共渠道处理业务类订单支付下单接口， h5 下单
    public ApplyOrderChannelBaseDTO payTradeService(ParkingPrePayOrderReq mreq, ParkingPreChannelPayRouteConfig payRouteConfig, ParkingQrcodeMchntConfig mchntConfig) throws Exception;
    // 公共渠道处理业务类查询订单交易状态接口
    public ApplyOrderChannelBaseDTO queryTradeService(ParkingPreChannelPayRouteConfig payRouteConfig, ParkingQrcodeMchntConfig mchntConfig, ParkingTradeOrder parkingTradeOrder) throws Exception;
}
