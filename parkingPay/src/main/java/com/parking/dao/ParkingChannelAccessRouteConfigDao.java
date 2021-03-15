package com.parking.dao;

import com.parking.domain.ParkingChannelAccessRouteConfig;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * 无感渠道路由配置表
 */
@Repository("parkingChannelAccessRouteConfigDao")
public interface ParkingChannelAccessRouteConfigDao {

    //插入数据
    public int insertParkingChannelAccessRouteConfig(ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfig) throws SQLException;
    //更新数据
    public int updateParkingChannelAccessRouteConfig(ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfig) throws SQLException;
    //查询数据 单个对象
    public ParkingChannelAccessRouteConfig selectParkingChannelAccessRouteConfigById(ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfig) throws SQLException;
    //查询列表
    public List<ParkingChannelAccessRouteConfig> selectParkingChannelAccessRouteConfigByList() throws SQLException;
    //查询列表
    public List<ParkingChannelAccessRouteConfig> selectParkingChannelAccessRouteConfigByParkIdList(ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfig) throws SQLException;
    // 根据 第三方 渠道 车场id 查询数据 单个对象
    public ParkingChannelAccessRouteConfig selectParkingChannelAccessRouteConfigPartById(ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfig) throws SQLException;

}
