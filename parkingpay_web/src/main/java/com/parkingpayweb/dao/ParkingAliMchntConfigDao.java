package com.parkingpayweb.dao;

import com.parkingpayweb.domain.ParkingAliMchntConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository("parkingAliMchntConfigDao")
public interface ParkingAliMchntConfigDao {

    public int insertParkingAliMchntConfig(ParkingAliMchntConfig parkingAliMchntConfig) throws SQLException;

    public int updateParkingAliMchntConfig(ParkingAliMchntConfig parkingAliMchntConfig) throws SQLException;

    public ParkingAliMchntConfig selectParkingAliMchntConfigById(ParkingAliMchntConfig parkingAliMchntConfig) throws SQLException;
}

