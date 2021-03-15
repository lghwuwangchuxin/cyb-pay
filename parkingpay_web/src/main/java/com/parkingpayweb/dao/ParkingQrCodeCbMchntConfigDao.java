package com.parkingpayweb.dao;

import com.parkingpayweb.domain.ParkingQrcodeMchntConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository("parkingQrcodeCbMchntConfigDao")
public interface ParkingQrCodeCbMchntConfigDao {
    //  根据 自定义商户号
    public ParkingQrcodeMchntConfig selectParkingCbMchntConfig (ParkingQrcodeMchntConfig mchntConfig)throws SQLException;

}
