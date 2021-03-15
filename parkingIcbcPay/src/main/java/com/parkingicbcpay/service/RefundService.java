package com.parkingicbcpay.service;

import com.parkingicbcpay.dto.CommonRsp;
import com.parkingicbcpay.dto.RefundReq;

/**
 * 向E捷通平台推送退款通知
 * @author lengyul
 * @date 2019年4月15日 上午9:41:39
 */
public interface RefundService {
	
	CommonRsp pushRefundInfo(RefundReq req);
	
}
