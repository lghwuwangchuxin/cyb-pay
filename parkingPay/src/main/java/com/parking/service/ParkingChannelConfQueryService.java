package com.parking.service;

import java.util.List;
import com.parking.domain.ParkingChannelAccessRouteConfig;


/**
 *   查询渠道参数配置
 */

public interface ParkingChannelConfQueryService {

    //获取渠道参数
    public List <ParkingChannelAccessRouteConfig> queryParkingChannelAccessRouteConfigList(String parkId) throws Exception;
    // 查询 单个 渠道 配置参数 
    public ParkingChannelAccessRouteConfig queryParkingChannelAccessRouteConfigById(String parkId, String channelId) throws Exception;
    // 查询 单个渠道 方车场id 渠道 配置参数 
    public ParkingChannelAccessRouteConfig queryParkingChannelAccessRouteConfigPartById(String partParkId, String channelId) throws Exception;

}

