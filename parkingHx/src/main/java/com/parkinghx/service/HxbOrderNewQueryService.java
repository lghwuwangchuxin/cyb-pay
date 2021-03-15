package com.parkinghx.service;

import com.parkinghx.dto.HxbOrderQueryReq;
import com.parkinghx.dto.HxbOrderQueryRsp;

public interface HxbOrderNewQueryService extends HxbOrderQueryService{

    HxbOrderQueryRsp queryPayTrade(HxbOrderQueryReq req) throws Exception;
}
