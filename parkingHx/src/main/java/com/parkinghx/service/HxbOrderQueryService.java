package com.parkinghx.service;

import com.parkinghx.dto.HxbOrderQueryReq;
import com.parkinghx.dto.HxbOrderQueryRsp;
import com.parkinghx.util.RespUtil;

public interface HxbOrderQueryService {

	/** 异常响应 */
	HxbOrderQueryRsp REQ_IS_NULL = new HxbOrderQueryRsp(RespUtil.noResult, "接收到前置支付系统请求华夏银行订单查询服务数据为空");
	HxbOrderQueryRsp PARSE_EX = new HxbOrderQueryRsp(RespUtil.parseEx, "封装请求华夏银行订单查询服务发送数据时发生异常");
	HxbOrderQueryRsp UNKNOWN_ERROR = new HxbOrderQueryRsp(RespUtil.unknownError, "请求华夏银行订单查询服务发生异常");
	HxbOrderQueryRsp SUCCESS_DESC = new HxbOrderQueryRsp(RespUtil.successCode, "支付成功");
	HxbOrderQueryRsp WAIL = new HxbOrderQueryRsp(RespUtil.payWail, "交易暂不确定");
	HxbOrderQueryRsp SIGN_DATA_FAIL = new HxbOrderQueryRsp(RespUtil.paySignFail, "验证数据签名失败");
	HxbOrderQueryRsp FIND_FILE_NO_EXCEPTION = new HxbOrderQueryRsp(RespUtil.loadSignFail, "加载加密证书异常");


	HxbOrderQueryRsp queryTrade(HxbOrderQueryReq req) throws Exception;
}
