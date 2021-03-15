package com.parkingyshang.service;

import com.parkingyshang.dto.RevokeOrderPaysReq;
import com.parkingyshang.dto.RevokeOrderPaysRsp;

public interface RevokeOrderPaysService {
	
	/**
	 * 支付撤销
	 * @param mreq
	 * @return
	 */
	public RevokeOrderPaysRsp getRevokeBeCodeOrderRsp(RevokeOrderPaysReq mreq);

}
