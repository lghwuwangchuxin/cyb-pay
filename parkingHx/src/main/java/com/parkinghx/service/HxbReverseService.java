package com.parkinghx.service;

import com.parkinghx.dto.HxbReverseReq;
import com.parkinghx.dto.HxbReverseRsp;
import com.parkinghx.util.RespUtil;

public interface HxbReverseService {

	/** 异常响应 */
	HxbReverseRsp REQ_IS_NULL = new HxbReverseRsp(RespUtil.noResult, "接收到前置支付系统请求华夏银行交易撤销服务数据为空");
	HxbReverseRsp PARSE_EX = new HxbReverseRsp(RespUtil.parseEx, "封装请求华夏银行交易撤销服务发送数据时发生异常");
	HxbReverseRsp UNKNOWN_ERROR = new HxbReverseRsp(RespUtil.unknownError, "请求华夏银行交易撤销服务发生异常");

	
	HxbReverseRsp toReverse(HxbReverseReq req);
	
}
