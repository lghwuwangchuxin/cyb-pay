package com.parking.service;

import com.parking.domain.ParkingQrCodeCbChannelPayRouteConfig;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.ParkingAutoPayReq;

public interface QrCodeCbChannelPayTradeProxyService {

    // 公共渠道处理业务类订单支付下单接口， 用户扫码 商户二维码进行扣款
    public ApplyOrderChannelBaseDTO payTradeService(ParkingAutoPayReq mreq, ParkingQrCodeCbChannelPayRouteConfig payRouteConfig, ParkingQrcodeMchntConfig mchntConfig) throws Exception;
    // 公共渠道处理业务类查询订单交易状态接口
    public ApplyOrderChannelBaseDTO queryTradeService(ParkingQrCodeCbChannelPayRouteConfig payRouteConfig, ParkingQrcodeMchntConfig mchntConfig, ParkingTradeOrder parkingTradeOrder) throws Exception;
}
