package com.parkingunionpaypre.service;

import com.parkingunionpaypre.dto.SwUnionOrderPayReq;
import com.parkingunionpaypre.dto.SwUnionOrderPayRsp;

/**
 * 条码前置h5 支付
 */
public interface SwUnionH5OrderPayService {

    // 微信 公众号
    public SwUnionOrderPayRsp preWxH5SwUionOrderPayRsp(SwUnionOrderPayReq mreq);
    // 支付宝 服务窗支付
    public SwUnionOrderPayRsp preAliH5SwUionOrderPayRsp(SwUnionOrderPayReq mreq);

    // 银联js 支付
    public SwUnionOrderPayRsp preUnionpayH5SwUionOrderPayRsp(SwUnionOrderPayReq mreq);


}
