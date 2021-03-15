package com.parking.dao;

import com.parking.domain.ParkingServiceMchntInfo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository("parkingServiceMchntInfoDao")
public interface ParkingServiceMchntInfoDao {

    public ParkingServiceMchntInfo selectParkingServiceMchntInfoById(ParkingServiceMchntInfo ParkingServiceMchntInfo) throws SQLException;
}
