package com.parkingunionpay.service.impl;

import com.parkingunionpay.service.PrParkingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkingunionpay.dto.QueryShSignKeyReq;
import com.parkingunionpay.dto.QueryShSignKeyRsp;
import com.parkingunionpay.service.QueryShSignKeyService;
import com.parkingunionpay.util.XmlUtil;

@Service("queryShSignKeyService")
public class QueryShSignKeyServiceImpl implements QueryShSignKeyService {

	private static final Logger logger = LoggerFactory.getLogger(QueryShSignKeyServiceImpl.class);

	@Autowired
	private PrParkingService prParkingService;// 停车服务调用

	@Override
	public String getShSignKey(String appId) {
		logger.info("调用停车系统获取秘钥服务:shGetAppIdSignKkey");
		if (StringUtils.isEmpty(appId)) {
			logger.info("获取appId为空!");
			return null;
		}
		QueryShSignKeyReq req = new QueryShSignKeyReq();
		String signKey = null;
		try {
			req.setService("shGetAppIdSignKkey");
			req.setAppId(appId);
			String sendData = XmlUtil.ObjToXml(req, QueryShSignKeyReq.class);
			logger.info("发送停车系统获取秘钥服务数据为" + sendData);

			String result = prParkingService.parkingCallCenterSync(sendData);
			QueryShSignKeyRsp rsp = (QueryShSignKeyRsp) XmlUtil.XmlToObj(result, QueryShSignKeyRsp.class);
			signKey = rsp.getSignKey();// 秘钥赋值
			logger.info("停车系统获取秘钥服务响应数据为:code:" + rsp.getRespCode() + "desc:" + rsp.getRespDesc() + "data:"
					+ rsp.toString());
		} catch (Exception e) {
			logger.error("dubbo内部调用异常,获取秘钥失败",e);
		}
		return signKey;
	}

}
