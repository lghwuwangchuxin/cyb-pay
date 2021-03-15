package com.parkingpayweb.dao;

import com.parkingpayweb.domain.ParkingChannelQrcodeTerminPayRouteConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 *  被扫 渠道路由
 */
@Repository("parkingChannelQrcodeTerminPayRouteConfigDao")
public interface ParkingChannelQrcodeTerminPayRouteConfigDao {

    //插入数据
    public int insertParkingChannelQrcodeTerminPayRouteConfig(ParkingChannelQrcodeTerminPayRouteConfig parkingChannelPayRouteConfig)throws SQLException;

    //更新数据
    public int updateParkingChannelQrcodeTerminPayRouteConfig(ParkingChannelQrcodeTerminPayRouteConfig parkingChannelPayRouteConfig)throws SQLException;

    //查询单个对象
    public ParkingChannelQrcodeTerminPayRouteConfig selectParkingChannelQrcodeTerminPayRouteConfigById(ParkingChannelQrcodeTerminPayRouteConfig parkingChannelPayRouteConfig)throws SQLException;

    //查询集合
    public List<ParkingChannelQrcodeTerminPayRouteConfig> selectParkingChannelQrcodeTerminPayRouteConfigByList()throws SQLException;
}
