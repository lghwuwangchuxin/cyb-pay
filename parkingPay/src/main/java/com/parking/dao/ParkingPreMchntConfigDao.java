package com.parking.dao;

import com.parking.domain.ParkingQrcodeMchntConfig;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;
import java.util.List;

/**
 * 预缴多渠道商户配置表
 */
@Repository("parkingPreMchntConfigDao")
public interface ParkingPreMchntConfigDao {
    //微信商户号，微信appid，获取微信的key
    public ParkingQrcodeMchntConfig selectParkingPreMchntConfig (ParkingQrcodeMchntConfig mchntConfig)throws SQLException;

}
