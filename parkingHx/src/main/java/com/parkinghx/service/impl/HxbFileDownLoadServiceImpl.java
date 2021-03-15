package com.parkinghx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.parkinghx.dto.HxbFileDownLoadReq;
import com.parkinghx.dto.HxbFileDownLoadRsp;
import com.parkinghx.service.HxbFileDownLoadService;
import com.parkinghx.util.HttpUtil;
import com.parkinghx.util.HxbConstants;
import com.parkinghx.util.MD5Utils;
import com.parkinghx.util.SortUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.SortedMap;
import java.util.TreeMap;

@Service("hxbFileDownLoadService")
public class HxbFileDownLoadServiceImpl implements HxbFileDownLoadService {

	private static final Logger logger = LoggerFactory.getLogger(HxbFileDownLoadServiceImpl.class);
	
	@Override
	public HxbFileDownLoadRsp fileDownLoad(HxbFileDownLoadReq req) {
		if (req == null) {
			logger.info("接收到停车系统请求华夏银行商户对账文件下载服务数据为空");
			return REQ_IS_NULL;
		}
		logger.info("接收到停车系统请求华夏银行商户对账文件下载服务:" + req.getService() + ",数据为:" + req.toString());
		SortedMap<String, String> dataMap = new TreeMap<String, String>();
		try {
			// obj 转换 map
			dataMap = SortUtil.objectToMap2(req);

		} catch (Exception e) {
			logger.error("封装请求华夏银行商户对账文件下载服务发送数据时发生异常:", e);
			return PARSE_EX;
		}
		final String appKey = req.getAppKey();
		try {
			MD5Utils.sign(dataMap, appKey);
			String dataJson = JSONObject.toJSONString(dataMap);
			logger.info("发送到请求华夏银行商户对账文件下载服务数据为:" + dataJson);
			String result = HttpUtil.HttpPost(HxbConstants.FILE_DOWNLOAD_URL, dataJson);
			logger.info("接收到请求华夏银行商户对账文件下载服务响应数据为:" + result);
			HxbFileDownLoadRsp scanPayRsp = HxbConstants.commonResultHandler(appKey, result, HxbFileDownLoadRsp.class);
			HxbConstants.commonRspHandler(scanPayRsp); // 业务状态修改
			return scanPayRsp;
		} catch (Exception e) {
			logger.error("请求华夏银行商户对账文件下载服务发生异常:", e);
			return UNKNOWN_ERROR;
		}
	}

}
