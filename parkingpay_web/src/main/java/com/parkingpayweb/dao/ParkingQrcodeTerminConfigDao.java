package com.parkingpayweb.dao;

import com.parkingpayweb.domain.ParkingQrcodeTerminConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository("parkingQrcodeTerminConfigDao")
public interface ParkingQrcodeTerminConfigDao {
    //插入
    public int insertParkingTerminConfig(ParkingQrcodeTerminConfig parkingTerminConfig)throws SQLException;
    //更新
    public int updateParkingTerminConfig(ParkingQrcodeTerminConfig parkingTerminConfig)throws SQLException;
    //查询对象
    public ParkingQrcodeTerminConfig selectParkingTerminConfigById(ParkingQrcodeTerminConfig parkingTerminConfig)throws SQLException;
    //查询终端列表
    public List<ParkingQrcodeTerminConfig> selectParkingTerminConfigByList(ParkingQrcodeTerminConfig parkingTerminConfig)throws SQLException;

}
