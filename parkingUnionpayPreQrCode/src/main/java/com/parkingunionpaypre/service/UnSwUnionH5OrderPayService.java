package com.parkingunionpaypre.service;

import com.parkingunionpaypre.dto.SwUnionOrderPayReq;
import com.parkingunionpaypre.dto.SwUnionOrderPayRsp;

public interface UnSwUnionH5OrderPayService {

    /**
     *  统一 银联条码前置 下单
     * @param mreq
     * @return
     */
    public SwUnionOrderPayRsp preH5SwUionOrderPayRsp(SwUnionOrderPayReq mreq);
}
