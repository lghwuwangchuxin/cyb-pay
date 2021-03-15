package com.parking.service;

import com.parking.dto.EnterParkingReq;
import com.parking.dto.EnterParkingRsp;
import com.parking.dto.OutParkingReq;
import com.parking.dto.OutParkingRsp;

/**
 *   统一进出场通知服务
 */
public interface ParkingInOutService {

    // 进场通知 多渠道
    public EnterParkingRsp enterParkingNotify(EnterParkingReq mreq) throws Exception;

    //出场通知 多渠道
    public OutParkingRsp outParkingNotify(OutParkingReq mreq) throws Exception;


}
