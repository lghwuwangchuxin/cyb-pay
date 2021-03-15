package com.parking.service;


import com.parking.dtosh.QueryShSignKeyReq;
import com.parking.dtosh.QueryShSignKeyRsp;

/**
 *   上海智慧平台渠道 查询商户密钥
 */


public interface ParkingChannelShQuerySignKeyService {
    
	//查询商户密钥
   public QueryShSignKeyRsp queryAppIdSignKey(QueryShSignKeyReq mreq) throws Exception;
}