package com.parking.dao;

import com.parking.domain.ParkingWhiteTempCar;
import org.springframework.stereotype.Repository;
import java.sql.SQLException;

@Repository("parkingWhiteTempCarDao")
public interface ParkingWhiteTempCarDao {

    // 插入临时白名单数据
    public int insertParkingWhiteTempCar(ParkingWhiteTempCar parkingWhiteTempCar) throws SQLException;

    // 更新数据
    public int updateParkingWhiteTempCar(ParkingWhiteTempCar parkingWhiteTempCar) throws SQLException;

    // 查询数据
    public ParkingWhiteTempCar selectParkingWhiteTempCarById(ParkingWhiteTempCar parkingWhiteTempCar) throws SQLException;

    public int deleteParkingWhiteTempCarById(ParkingWhiteTempCar parkingWhiteTempCar)throws SQLException;
}
