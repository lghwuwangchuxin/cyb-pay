package com.parkinghx.service;

import com.parkinghx.dto.HxbFileDownLoadReq;
import com.parkinghx.dto.HxbFileDownLoadRsp;
import com.parkinghx.util.RespUtil;

public interface HxbFileDownLoadService {

	/** 异常响应 */
	HxbFileDownLoadRsp REQ_IS_NULL = new HxbFileDownLoadRsp(RespUtil.noResult, "接收到前置支付系统请求华夏银行商户对账文件下载服务数据为空");
	HxbFileDownLoadRsp PARSE_EX = new HxbFileDownLoadRsp(RespUtil.parseEx, "封装请求华夏银行商户对账文件下载服务发送数据时发生异常");
	HxbFileDownLoadRsp UNKNOWN_ERROR = new HxbFileDownLoadRsp(RespUtil.unknownError, "请求华夏银行商户对账文件下载服务发生异常");
	
	HxbFileDownLoadRsp fileDownLoad(HxbFileDownLoadReq req);
	
}
