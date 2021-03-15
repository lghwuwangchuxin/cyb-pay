package com.parking.dao;

import com.parking.domain.ParkingChannelShParamsConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 *  上海金融事业部
 */
@Repository("ParkingChannelShParamsConfigDao")
public interface ParkingChannelShParamsConfigDao {

    //插入数据
    public int insertParkingChannelShParamsConfig(ParkingChannelShParamsConfig parkingChannelShParamsConfig)throws SQLException;
    //更新数据
    public int updateParkingChannelShParamsConfig(ParkingChannelShParamsConfig parkingChannelShParamsConfig)throws SQLException;
    //查询数据 单个对象
    public ParkingChannelShParamsConfig selectParkingChannelShParamsConfigById(ParkingChannelShParamsConfig parkingChannelShParamsConfig)throws SQLException;
    //查询列表
    public List<ParkingChannelShParamsConfig> selectParkingChannelShParamsConfigByList()throws SQLException;
    //根据商户应用代码查询
    public ParkingChannelShParamsConfig selectParkingChannelShParamsConfigAppIdById(ParkingChannelShParamsConfig parkingChannelShParamsConfig) throws SQLException;

}
