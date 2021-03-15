package com.parking.dao;

import com.parking.domain.ParkingPreChannelPayRouteConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository("parkingPreChannelPayRouteConfigDao")
public interface ParkingPreChannelPayRouteConfigDao {

    public int insertParkingPreChannelPayRouteConfig(ParkingPreChannelPayRouteConfig parkingPreChannelPayRouteConfig) throws SQLException;

    public int updateParkingPreChannelPayRouteConfig(ParkingPreChannelPayRouteConfig parkingPreChannelPayRouteConfig) throws SQLException;

    public ParkingPreChannelPayRouteConfig selectParkingPreChannelPayRouteConfig(ParkingPreChannelPayRouteConfig parkingPreChannelPayRouteConfig) throws SQLException;
}
