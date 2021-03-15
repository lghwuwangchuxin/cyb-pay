package com.parking.dao;

import com.parking.domain.ParkingQrcodeMchntConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository("parkingQrcodeCbMchntConfigDao")
public interface ParkingQrCodeCbMchntConfigDao {
    //  根据 自定义商户号
    public ParkingQrcodeMchntConfig selectParkingCbMchntConfig (ParkingQrcodeMchntConfig mchntConfig)throws SQLException;

}
