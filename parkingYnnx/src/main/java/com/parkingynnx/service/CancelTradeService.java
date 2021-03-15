package com.parkingynnx.service;

import com.parkingynnx.dto.CancelTradeReq;
import com.parkingynnx.dto.CancelTradeRsp;

public interface CancelTradeService {
    
    public CancelTradeRsp cancelTrade(CancelTradeReq mreq);

}
