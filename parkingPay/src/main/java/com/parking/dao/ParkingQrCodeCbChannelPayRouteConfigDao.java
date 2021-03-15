package com.parking.dao;

import com.parking.domain.ParkingQrCodeCbChannelPayRouteConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository("parkingQrCodeCbChannelPayRouteConfigDao")
public interface ParkingQrCodeCbChannelPayRouteConfigDao {

    public int insertParkingQrCodeCbChannelPayRouteConfig(ParkingQrCodeCbChannelPayRouteConfig parkingQrCodeCbChannelPayRouteConfig) throws SQLException;

    public int updateParkingQrCodeCbChannelPayRouteConfig(ParkingQrCodeCbChannelPayRouteConfig parkingQrCodeCbChannelPayRouteConfig) throws SQLException;

    public ParkingQrCodeCbChannelPayRouteConfig selectParkingQrCodeCbChannelPayRouteConfig(ParkingQrCodeCbChannelPayRouteConfig parkingQrCodeCbChannelPayRouteConfig) throws SQLException;
}
