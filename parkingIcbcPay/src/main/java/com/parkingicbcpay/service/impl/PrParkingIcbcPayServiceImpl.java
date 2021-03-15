package com.parkingicbcpay.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.parkingicbcpay.dto.CommonRsp;
import com.parkingicbcpay.dto.EntranceExitRsp;
import com.parkingicbcpay.dto.EntranceReq;
import com.parkingicbcpay.dto.ExitReq;
import com.parkingicbcpay.dto.PayResultReq;
import com.parkingicbcpay.dto.RefundReq;
import com.parkingicbcpay.service.EntranceExitNoticeService;
import com.parkingicbcpay.service.PayResultService;
import com.parkingicbcpay.service.PrParkingIcbcPayService;
import com.parkingicbcpay.service.RefundService;
import com.parkingicbcpay.util.DecryptForClient;
import com.parkingicbcpay.util.XmlUtil;

@Service("prParkingIcbcPayService")
public class PrParkingIcbcPayServiceImpl implements PrParkingIcbcPayService {

	private static final Logger logger = LoggerFactory.getLogger(PrParkingIcbcPayServiceImpl.class);

	@Inject
	private EntranceExitNoticeService entranceExitNoticeService;

	@Inject
	private RefundService refundService;
	
	@Inject
	private PayResultService payResultService;
	
	@Override
	public String parkingIcbcPayCallCenterSync(String msg) throws Exception {

		logger.info("进入工行渠道系统入口：ParkingIcbcPayCallCenterSync-----");
		logger.info("收到消息：" + msg);
		String rspXml = ""; // 响应报文
		Map<String, String> xmlTypeMap = new HashMap<String, String>();
		BASE64Encoder encoder = new BASE64Encoder();
		BASE64Decoder decoder = new BASE64Decoder();
		DecryptForClient decryptForClinet = new DecryptForClient();
		/*
		 * String privateKey=""; //私钥 String desKey = "";// 客户端随机生成的3Deskey
		 * desKey = decryptForClinet.decodeDesKey(msg,privateKey);
		 */
		xmlTypeMap = decryptForClinet.getHead(msg);
		String service = (String) xmlTypeMap.get("service");
		logger.info("进入工行渠道交易通知系统,调用服务[" + service + "]");
		try {
			// 各应用接收请求进行处理,并响应明文
			if ("icbcPushEntranceNotice".equals(service)) {// 接收入场通知信息
				EntranceExitRsp rsp = entranceExitNoticeService
						.pushEntranceNotice((EntranceReq) XmlUtil.XmlToObj(msg, EntranceReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(rsp, EntranceExitRsp.class));
				if (rsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(rsp, EntranceExitRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + rsp.getRespCode() + "|" + encoder.encode(rsp.getRespDesc().getBytes("utf-8"));
				}
			} else if ("icbcPushExitNotice".equals(service)) {// 接收出场通知信息
				EntranceExitRsp rsp = entranceExitNoticeService
						.pushExitNotice((ExitReq) XmlUtil.XmlToObj(msg, ExitReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(rsp, EntranceExitRsp.class));
				if (rsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(rsp, EntranceExitRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + rsp.getRespCode() + "|" + encoder.encode(rsp.getRespDesc().getBytes("utf-8"));
				}
			} else if("icbcGetPayResult".equals(service)) { // 接收支付结果查询	
				CommonRsp rsp = payResultService.getPayResult((PayResultReq) XmlUtil.XmlToObj(msg, PayResultReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(rsp, CommonRsp.class));
				if (rsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(rsp, CommonRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + rsp.getRespCode() + "|" + encoder.encode(rsp.getRespDesc().getBytes("utf-8"));
				}
			} else if("icbcPayRefundNotify".equals(service)) { // 接收退款请求
				CommonRsp rsp = refundService.pushRefundInfo((RefundReq) XmlUtil.XmlToObj(msg, RefundReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(rsp, CommonRsp.class));
				if (rsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(rsp, CommonRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + rsp.getRespCode() + "|" + encoder.encode(rsp.getRespDesc().getBytes("utf-8"));
				}
			} else if("icbcQueryPayh5Pre".equals(service)) { // 工行h5 支付订单结果查询
				CommonRsp rsp = payResultService.queryPrePayResult((PayResultReq) XmlUtil.XmlToObj(msg, PayResultReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(rsp, CommonRsp.class));
				rspXml = XmlUtil.ObjToXml(rsp, CommonRsp.class);
			} else if ("icbch5pre".equals(service)) {// 工行 h5 支付下单
				CommonRsp rsp = entranceExitNoticeService.prePay((ExitReq) XmlUtil.XmlToObj(msg, ExitReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(rsp, CommonRsp.class));
				rspXml =XmlUtil.ObjToXml(rsp, CommonRsp.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				return null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return rspXml;
	}

	@Override
	public String parkingIcbcPayCallCenterAsyn(String msg) throws Exception {
		return null;
	}
}
