package com.parkingynnx.service;


import com.parkingynnx.dto.QueryTradeReq;
import com.parkingynnx.dto.QueryTradeRsp;

/**
 * 查询交易
 */
public interface QueryTradeService {
	
    /**
     * 查询交易-get请求
     * @param mreq
     * @return
     * @throws Exception
     */
	public QueryTradeRsp queryTrade(QueryTradeReq mreq)throws Exception;
}
