package com.parking.service;

import com.parking.dto.ParkingPrePayOrderReq;
import com.parking.dto.ParkingPrePayOrderRsp;

/**
 *  主扫 预缴 多渠道 下单支付 业务类 根据路由 判断 下单的地方
 */
public interface ParkingPrePayTradeOrderService {
    // 多渠道下单 主扫 公众号
    public ParkingPrePayOrderRsp prePayTradeOrder(ParkingPrePayOrderReq mreq) throws Exception;


}
