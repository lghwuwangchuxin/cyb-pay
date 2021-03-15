package com.parkingicbcpay.service.impl;

import java.util.SortedMap;
import javax.servlet.http.HttpServletRequest;
import com.parkingicbcpay.service.PrParkingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.parkingicbcpay.dao.IcbcMchntConfigDao;
import com.parkingicbcpay.domain.IcbcMchntConfig;
import com.parkingicbcpay.dto.OrderInfoReq;
import com.parkingicbcpay.dto.OrderInfoRsp;
import com.parkingicbcpay.service.QueryOrderInfoService;
import com.parkingicbcpay.util.CommonDataUtil;
import com.parkingicbcpay.util.EJTRequest;
import com.parkingicbcpay.util.RespUtil;
import com.parkingicbcpay.util.SignatureUtils;
import com.parkingicbcpay.util.XmlUtil;


@Service("queryOrderInfoService")
public class QueryOrderInfoServiceImpl implements QueryOrderInfoService {

	private static final Logger logger = LoggerFactory.getLogger(QueryOrderInfoServiceImpl.class);

	@Autowired
	private IcbcMchntConfigDao icbcMchntConfigDao;
	
	@Autowired
	private PrParkingService prParkingService;
	
	@Override
	public OrderInfoRsp getOrderInfo(HttpServletRequest request) {
		OrderInfoReq req = new OrderInfoReq();
		IcbcMchntConfig config = null;
		try {
			String appKey = request.getHeader("appKey");
			SortedMap<String, String> parameters = EJTRequest.getUrlParams(request);
			String json = EJTRequest.getRequestMessage(request);
			JSONObject jsonObject = JSONObject.parseObject(json);
			logger.info("接收到工行平台查询订单接口数据为：[header: "+ appKey+"] [urlParams: "+ parameters +"] [data: "+jsonObject.toString()+"]");
			if (StringUtils.isEmpty(appKey) || StringUtils.isEmpty(jsonObject.toString())) {
				logger.info("接收到工行平台查询订单接口数据为空");
				return RSP_NULL;
			}
			config = icbcMchntConfigDao.selectIcbcMchntConfByAppKey(appKey);
			if (config == null) {
				logger.info("停车系统未配置该商户ID或未启动");
				return RSP_NOTFOUND;
			}			
			/*Map dataMap = JSONObject.toJavaObject(jsonObject, Map.class);
			parameters.putAll(dataMap);*/
			parameters.put("appKey", appKey);
			parameters.put("secretKey", config.getSignKey());
			
			boolean verify = SignatureUtils.checkSign(parameters);
			if (!verify) {
				logger.info("验证签名失败");
				return RSP_SIGN_FAIL;
			}
			req = JSONObject.toJavaObject(jsonObject, OrderInfoReq.class);
		} catch (Exception e) {
			logger.info("解析工行平台查询订单接口请求数据时发生异常");
			e.printStackTrace();
			return RSP_ERROR;
		}
		// 调用服务响应
		try {
			logger.info("调用停车系统查询订单接口服务:" + CommonDataUtil.QUERY_ORDER_SERVICE);
			req.setService(CommonDataUtil.QUERY_ORDER_SERVICE);// 查询订单接口服务
			String sendData = XmlUtil.ObjToXml(req, OrderInfoReq.class);
			logger.info("发送停车系统查询订单接口服务数据为:" + sendData);
			
			String result = prParkingService.parkingCallCenterSync(sendData);
			OrderInfoRsp rsp = (OrderInfoRsp) XmlUtil.XmlToObj(result, OrderInfoRsp.class);
			logger.info("停车系统支付成功通知服务响应数据为: code:"+rsp.getRespCode()+"desc: "+rsp.getRespDesc()+"data: "+rsp.toString());
			if (RespUtil.successCode.equals(rsp.getRespCode())) {
				rsp.setMessagecode("0");
				rsp.setMessage("请求成功");
				rsp.setStatus("ok");
				return rsp; 
			} else {
				return RSP_ERROR; 
			}
		} catch (Exception e) {
			logger.info("内部调用异常");
			e.printStackTrace();
			return RSP_ERROR;
		}
	}

	
	
}
