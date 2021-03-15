package com.parkingunionpay.service.impl;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import com.parkingunionpay.dto.EntranceReq;
import com.parkingunionpay.dto.EntranceRsp;
import com.parkingunionpay.dto.ExitReq;
import com.parkingunionpay.dto.ExitRsp;
import com.parkingunionpay.dto.PayBillReq;
import com.parkingunionpay.dto.PayBillRsp;
import com.parkingunionpay.dto.PayStatusReq;
import com.parkingunionpay.dto.PayStatusRsp;
import com.parkingunionpay.dto.RecNotifyReq;
import com.parkingunionpay.dto.RecNotifyRsp;
import com.parkingunionpay.dto.RefundBillReq;
import com.parkingunionpay.dto.RefundBillRsp;
import com.parkingunionpay.dto.RefundStatusReq;
import com.parkingunionpay.dto.RefundStatusRsp;
import com.parkingunionpay.dto.SignStatusReq;
import com.parkingunionpay.dto.SignStatusRsp;
import com.parkingunionpay.service.EntranceExitNoticeService;
import com.parkingunionpay.service.PayBillStatusService;
import com.parkingunionpay.service.PrParkingUnionPayService;
import com.parkingunionpay.service.QuerySignStatusService;
import com.parkingunionpay.service.RefundBillStatusService;
import com.parkingunionpay.util.DecryptForClient;
import com.parkingunionpay.util.XmlUtil;

@Service("prParkingUnionPayService")
public class PrParkingUnionPayServiceImpl implements PrParkingUnionPayService {

	private static final Logger logger = LoggerFactory.getLogger(PrParkingUnionPayServiceImpl.class);
	@Inject
	private QuerySignStatusService querySignStatusService; // 签约状态接口
	@Inject
	private PayBillStatusService payBillStatusService; // 支付接口
	@Inject
	private RefundBillStatusService refundBillStatusService; // 退款接口
	@Inject
	private EntranceExitNoticeService noticeEntranceExitService; // 出入场通知接口

	@Override
	public String parkingUnionPayCallCenterSync(String msg) throws Exception {
		logger.info("进入金融云闪付系统入口：ParkingUnionPayCallCenterSync-----");
		logger.info("收到消息：" + msg);
		String rspXml = ""; // 响应报文
		Map<String, String> xmlTypeMap = new HashMap<String, String>();
		BASE64Encoder encoder = new BASE64Encoder();
		BASE64Decoder decoder = new BASE64Decoder();
		DecryptForClient decryptForClinet = new DecryptForClient();
		xmlTypeMap = decryptForClinet.getHead(msg);
		String service = (String) xmlTypeMap.get("service");
		logger.info("进入金融银联云闪付交易通知系统,调用服务[" + service + "]");
		try {
			// 各应用接收请求进行处理,并响应明文
			if (service.equals("notifyTradeResult")) {// 接收通知

			} else if (service.equals("shGetSignStatus")) { // 接收查询签约状态通知
				SignStatusRsp mrsp = querySignStatusService
						.getUnionPayStatus((SignStatusReq) XmlUtil.XmlToObj(msg, SignStatusReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(mrsp, SignStatusRsp.class));
				if (mrsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(mrsp, SignStatusRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + mrsp.getRespCode() + "|" + encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else if (service.equals("shPushPayBill")) { // 接收推送支付订单通知
				PayBillRsp mrsp = payBillStatusService
						.pushPayBill((PayBillReq) XmlUtil.XmlToObj(msg, PayBillReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(mrsp, PayBillRsp.class));
				if (mrsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(mrsp, PayBillRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + mrsp.getRespCode() + "|" + encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else if (service.equals("shGetPayStatus")) { // 接收查询支付订单状态通知
				PayStatusRsp mrsp = payBillStatusService
						.getPayStatus((PayStatusReq) XmlUtil.XmlToObj(msg, PayStatusReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(mrsp, PayStatusRsp.class));
				if (mrsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(mrsp, PayStatusRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + mrsp.getRespCode() + "|" + encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else if (service.equals("shPushRefundBill")) { // 接收申请订单退款通知
				RefundBillRsp mrsp = refundBillStatusService
						.pushRefundBill((RefundBillReq) XmlUtil.XmlToObj(msg, RefundBillReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(mrsp, RefundBillRsp.class));
				if (mrsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(mrsp, RefundBillRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + mrsp.getRespCode() + "|" + encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else if (service.equals("shGetRefundStatus")) { // 接收查询退款订单状态通知
				RefundStatusRsp mrsp = refundBillStatusService
						.getRefundStatus((RefundStatusReq) (XmlUtil.XmlToObj(msg, RefundStatusReq.class)));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(mrsp, RefundStatusRsp.class));
				if (mrsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(mrsp, RefundStatusRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + mrsp.getRespCode() + "|" + encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else if (service.equals("shPushNoticeEntrance")) { // 接收车辆入场通知
				EntranceRsp mrsp = noticeEntranceExitService
						.pushEntranceNotice((EntranceReq) XmlUtil.XmlToObj(msg, EntranceReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(mrsp, EntranceRsp.class));
				if (mrsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(mrsp, EntranceRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + mrsp.getRespCode() + "|" + encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else if (service.equals("shPushNoticeExit")) { // 接收车辆出场通知
				ExitRsp mrsp = noticeEntranceExitService.pushExitNotice((ExitReq) XmlUtil.XmlToObj(msg, ExitReq.class));
				logger.info("调用服务[" + service + "]返回：" + XmlUtil.ObjToXml(mrsp, ExitRsp.class));
				if (mrsp.getRespCode().equals("000000")) {
					rspXml = "1|" + encoder.encode(XmlUtil.ObjToXml(mrsp, ExitRsp.class).getBytes("utf-8"));
				} else {
					rspXml = "0|" + mrsp.getRespCode() + "|" + encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
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
	public String parkingUnionPayCallCenterAsyn(String msg) throws Exception {
		return null;
	}
}
