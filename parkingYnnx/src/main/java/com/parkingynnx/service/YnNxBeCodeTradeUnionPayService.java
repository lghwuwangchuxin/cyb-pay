package com.parkingynnx.service;

import com.parkingynnx.dto.BeCodeUnionPayReq;
import com.parkingynnx.dto.BeCodeUnionPayRsp;

/**

 *	  被扫支付、
 *   银联线路（2）
 */


public interface YnNxBeCodeTradeUnionPayService {
    
    /**
     *  被扫接口 -银联
     * @param mreq
     * @return
     */
    public BeCodeUnionPayRsp getYunBeCodeUnionPay(BeCodeUnionPayReq mreq);

}

