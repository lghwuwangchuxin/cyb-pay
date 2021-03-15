package com.parking.service;

import com.parking.dto.*;

public interface ParkingOrderService {

    // 处理支付通知
    public PayNotifyRsp notifyPayOrderAResult(PayNotifyReq mreq) throws Exception;
    // Terminal自动扣费订单生成
    public ParkingAutoPayRsp geneParkTerminalAutoOrder(ParkingAutoPayReq mreq) throws Exception;
    //临时车预缴费订单生成
    public ParkingPreOrderQueryRsp queryParkingPrePayOrder(ParkingPreOrderQueryReq mreq)throws Exception;

}
