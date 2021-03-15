package com.parkingyshang.service;


import com.parkingyshang.dto.QueryOrderPaysStatusReq;
import com.parkingyshang.dto.QueryOrderPaysStatusRsp;

public interface QueryOrderPaysStatusService {
	
	public QueryOrderPaysStatusRsp getQueryOrderPaysStatusRsp(QueryOrderPaysStatusReq mreq);

}
