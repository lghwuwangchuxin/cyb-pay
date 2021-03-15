package com.parking.dao;

import com.parking.domain.ParkingQrcodeMchntConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository("parkingQrcodeMchntConfigDao")
public interface ParkingQrcodeMchntConfigDao {
    //  根据微信商户号，微信appid，获取微信的key
    public ParkingQrcodeMchntConfig selectParkingQrcodeMchntConfig (ParkingQrcodeMchntConfig mchntConfig)throws SQLException;
    // 效检商户号
    public List<ParkingQrcodeMchntConfig> selectMParkingQrcodeMchntConfigList (ParkingQrcodeMchntConfig mchntConfig) throws SQLException;
}
