package com.parkingunionpaypre.service.impl;

import com.parkingunionpaypre.dto.SwUnionOrderPayReq;
import com.parkingunionpaypre.dto.SwUnionOrderPayRsp;
import com.parkingunionpaypre.service.SwUnionH5OrderPayService;
import com.parkingunionpaypre.service.UnSwUnionH5OrderPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("unSwUnionH5OrderPayService")
public class UnSwUnionH5OrderPayServiceImpl implements UnSwUnionH5OrderPayService {

    private static final Logger logger = LoggerFactory.getLogger(UnSwUnionH5OrderPayServiceImpl.class);

    @Autowired
    private SwUnionH5OrderPayService swUnionH5OrderPayService;

    /**
     * 统一 银联条码前置 下单
     *
     * @param mreq
     * @return
     */
    @Override
    public SwUnionOrderPayRsp preH5SwUionOrderPayRsp(SwUnionOrderPayReq mreq) {
        logger.info("进入银联条码前置统一h5下单服务---preH5SwUionOrderPayRsp");
        if ("12".equals(mreq.getTrade_type())) {
            return swUnionH5OrderPayService.preAliH5SwUionOrderPayRsp(mreq);
        } else if ("13".equals(mreq.getTrade_type())) {
            return swUnionH5OrderPayService.preWxH5SwUionOrderPayRsp(mreq);
        } else {
            return swUnionH5OrderPayService.preUnionpayH5SwUionOrderPayRsp(mreq);
        }
    }
}
