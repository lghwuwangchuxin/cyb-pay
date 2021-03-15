package com.parking.dao;

import com.parking.domain.ParkingChannelCar;
import java.sql.SQLException;
import java.util.Map;

public interface ParkingChannelCarDao {
	//无感开通记录
	public int insertParkingChannelCar(ParkingChannelCar parkingChannelCar) throws SQLException;
	//无感开通更新
	public int updateParkingChannelCar(ParkingChannelCar parkingChannelCar) throws SQLException;
	//
	public ParkingChannelCar selecttParkingChannelCar(Map map) throws SQLException;
	

}
