package com.parking.service;

import com.parking.dto.ParkingAutoPayReq;
import com.parking.dto.ParkingAutoPayRsp;

// 主扫二维码下单 支付
public interface ParkingQrCodeCbPayTradeOrderService {

    ParkingAutoPayRsp payCbTrade(ParkingAutoPayReq mreq) throws Exception;
}
