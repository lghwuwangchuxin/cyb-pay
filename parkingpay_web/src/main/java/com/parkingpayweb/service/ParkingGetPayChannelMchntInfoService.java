package com.parkingpayweb.service;

import com.parkingpayweb.dto.ParkingGetChannelMchntReq;
import com.parkingpayweb.dto.ParkingGetChannelMchntRsp;

import java.sql.SQLException;

public interface ParkingGetPayChannelMchntInfoService {


    //  根据停车场id获取 商户信息
    public ParkingGetChannelMchntRsp getParkingGetChannelMchntInfo(ParkingGetChannelMchntReq req) throws Exception;
}
