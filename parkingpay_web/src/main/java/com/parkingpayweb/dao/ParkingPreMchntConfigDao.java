package com.parkingpayweb.dao;

import com.parkingpayweb.domain.ParkingQrcodeMchntConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * 预缴多渠道商户配置表
 */
@Repository("parkingPreMchntConfigDao")
public interface ParkingPreMchntConfigDao {
    //微信商户号，微信appid，获取微信的key
    public ParkingQrcodeMchntConfig selectParkingPreMchntConfig (ParkingQrcodeMchntConfig mchntConfig)throws SQLException;

}
