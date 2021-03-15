package com.parkingyshang.service;


import com.parkingyshang.dto.RefundOrderPaysReq;
import com.parkingyshang.dto.RefundOrderPaysRsp;

public interface RefundOrderPaysService {
	
	public RefundOrderPaysRsp getRefundOrderPaysRsp(RefundOrderPaysReq mreq);

}
