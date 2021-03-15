package com.parkingpayweb.dao;

import com.parkingpayweb.domain.ParkingWxMchntConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository("parkingWxMchntConfigDao")
public interface ParkingWxMchntConfigDao {
    public int insertParkingWxMchntConfig(ParkingWxMchntConfig parkingWxMchntConfig) throws SQLException;

    public int updateParkingWxMchntConfig(ParkingWxMchntConfig parkingWxMchntConfig) throws SQLException;

    public ParkingWxMchntConfig selectParkingWxMchntConfigById(ParkingWxMchntConfig parkingWxMchntConfig) throws SQLException;

}
