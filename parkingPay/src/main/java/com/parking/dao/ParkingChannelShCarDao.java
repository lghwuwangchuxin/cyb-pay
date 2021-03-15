package com.parking.dao;

import com.parking.domain.ParkingChannelShCar;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 *
 */
@Repository("parkingChannelShCarDao")
public interface ParkingChannelShCarDao {

    //插入数据
    public int insertParkingChannelShCar(ParkingChannelShCar parkingChannelShCar) throws SQLException;
    //更新数据
    public int updateParkingChannelShCar(ParkingChannelShCar parkingChannelShCar) throws SQLException;
    //查询单个对象
    public ParkingChannelShCar selectParkingChannelShCarById(ParkingChannelShCar parkingChannelShCar) throws SQLException;
    //查询列表
    public List<ParkingChannelShCar> selectParkingChannelShCarByList(ParkingChannelShCar parkingChannelShCar) throws SQLException;

}
