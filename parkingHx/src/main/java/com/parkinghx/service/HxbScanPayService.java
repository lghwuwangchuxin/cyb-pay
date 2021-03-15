package com.parkinghx.service;

import com.parkinghx.dto.HxbScanPayReq;
import com.parkinghx.dto.HxbScanPayRsp;
import com.parkinghx.util.RespUtil;

public interface HxbScanPayService {
	
	/** 异常响应 */
	HxbScanPayRsp REQ_IS_NULL = new HxbScanPayRsp(RespUtil.noResult, "接收到前置支付系统请求华夏银行支付被扫服务数据为空");

	HxbScanPayRsp PARSE_EX = new HxbScanPayRsp(RespUtil.parseEx, "封装请求华夏银行支付被扫服务发送数据时发生异常");
	HxbScanPayRsp UNKNOWN_ERROR = new HxbScanPayRsp(RespUtil.unknownError, "请求华夏银行支付被扫服务发生异常");

	HxbScanPayRsp SUCCESS_DESC = new HxbScanPayRsp(RespUtil.successCode, "支付成功");

	HxbScanPayRsp SUCCESS_H5DESC = new HxbScanPayRsp(RespUtil.successCode, "下单成功");

	HxbScanPayRsp REQ_IS_H5NULL = new HxbScanPayRsp(RespUtil.noResult, "接收到前置支付系统请求华夏银行微信公众号下单服务数据为空");

	HxbScanPayRsp UNKNOWN_H5ERROR = new HxbScanPayRsp(RespUtil.unknownError, "请求华夏银行支付微信公众号下单服务发生异常");


	HxbScanPayRsp REQ_CB_IS_NULL = new HxbScanPayRsp(RespUtil.noResult, "接收到前置支付系统请求华夏银行支付主扫服务数据为空");
	HxbScanPayRsp PARSE_CB_EX = new HxbScanPayRsp(RespUtil.parseEx, "封装请求华夏银行支付主扫服务发送数据时发生异常");
	HxbScanPayRsp UNKNOWN_CB_ERROR = new HxbScanPayRsp(RespUtil.unknownError, "请求华夏银行支付主扫服务发生异常");
	HxbScanPayRsp SUCCESS_CB_DESC = new HxbScanPayRsp(RespUtil.successCode, "主扫下单成功");
	HxbScanPayRsp WAIL = new HxbScanPayRsp(RespUtil.payWail, "请求下单交易不确定");
	HxbScanPayRsp SIGN_DATA_FAIL = new HxbScanPayRsp(RespUtil.paySignFail, "验证签名失败");
	HxbScanPayRsp FIND_FILE_NO_EXCEPTION = new HxbScanPayRsp(RespUtil.loadSignFail, "加载加密证书异常");

	HxbScanPayRsp scanPay(HxbScanPayReq req) throws Exception;
	
}	
