package com.parkinghx.service;

import com.parkinghx.dto.HxbRefundReq;
import com.parkinghx.dto.HxbRefundRsp;
import com.parkinghx.util.RespUtil;

public interface HxbApplyRefundService {
	
	/** 异常响应 */
	HxbRefundRsp REQ_IS_NULL = new HxbRefundRsp(RespUtil.noResult, "接收到前置支付系统请求华夏银行申请退款服务数据为空");
	HxbRefundRsp PARSE_EX = new HxbRefundRsp(RespUtil.parseEx, "封装请求华夏银行申请退款服务发送数据时发生异常");
	HxbRefundRsp UNKNOWN_ERROR = new HxbRefundRsp(RespUtil.unknownError, "请求华夏银行申请退款服务发生异常");
	
	HxbRefundRsp applyRefund(HxbRefundReq req);
	
}
