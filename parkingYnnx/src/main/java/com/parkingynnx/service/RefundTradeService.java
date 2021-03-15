package com.parkingynnx.service;

import com.parkingynnx.dto.RefundTradeReq;
import com.parkingynnx.dto.RefundTradeRsp;

public interface RefundTradeService {
    
    public RefundTradeRsp refundTrade(RefundTradeReq mreq);

}
