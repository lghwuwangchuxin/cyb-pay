package com.parkinghx.service.impl;

import com.parkinghx.dto.*;
import com.parkinghx.service.*;
import com.parkinghx.util.DecryptForClient;
import com.parkinghx.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;
import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service("prBocQrCodeService")
public class PrHxbQrCodeServiceImpl implements PrHxbQrCodeService {

	private static final Logger logger = LoggerFactory.getLogger(PrHxbQrCodeServiceImpl.class);

	@Inject
	private HxbScanPayService hxbScanPayService;
	@Inject
	private HxbOrderQueryService hxbOrderQueryService;
	@Inject
	private HxbApplyRefundService hxbApplyRefundService;
	@Inject
	private HxbReverseService hxbReverseService;
	@Inject
	private HxbNewScanPayService hxbNewScanPayService;
	@Inject
	private HxbOrderNewQueryService hxbOrderNewQueryService;
	
	@Override
	public String hxbQrCodeCallCenterSync(String msg) throws Exception {
		logger.info("进入华夏银行支付接入系统入口：hxbQrCodeCallCenterSync-----");
		logger.info("接受到的消息为：" + msg);
		String rspXml = ""; // 响应消息
		Map<String, String> map = new HashMap<String, String>(10);
		BASE64Encoder encoder = new BASE64Encoder();
		DecryptForClient decryptForClient = new DecryptForClient();
		map = decryptForClient.getHead(msg);
		String service = map.get("service");
		logger.info("进入华夏银行被扫接入系统,调用服务【" + service + "】");
		try {
			if ("hxbScanPay".equals(service)) { // 被扫下单
				HxbScanPayRsp rsp = hxbScanPayService.scanPay((HxbScanPayReq) XmlUtil.XmlToObj(msg, HxbScanPayReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbScanPayRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);
				
			} else if("hxbOrderQuery".equals(service)) { // 订单查询
				HxbOrderQueryRsp rsp = hxbOrderQueryService.queryTrade((HxbOrderQueryReq) XmlUtil.XmlToObj(msg, HxbOrderQueryReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbOrderQueryRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);
				
			} else if("hxbToRefund".equals(service)) { // 申请退款
				HxbRefundRsp rsp = hxbApplyRefundService.applyRefund((HxbRefundReq) XmlUtil.XmlToObj(msg, HxbRefundReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbRefundRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);
				
			} else if("hxbToReverse".equals(service)) { // 交易撤销
				HxbReverseRsp rsp = hxbReverseService.toReverse((HxbReverseReq) XmlUtil.XmlToObj(msg, HxbReverseReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbReverseRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);
			} else if ("hxbNewScanPay".equals(service)) { // 被扫下单
				HxbScanPayRsp rsp = hxbNewScanPayService.scanPay((HxbScanPayReq) XmlUtil.XmlToObj(msg, HxbScanPayReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbScanPayRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);

			} else if("hxbNewOrderQuery".equals(service)) { // 订单查询
				HxbOrderQueryRsp rsp = hxbOrderNewQueryService.queryTrade((HxbOrderQueryReq) XmlUtil.XmlToObj(msg, HxbOrderQueryReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbOrderQueryRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);
			} else if ("hxbNewCbScanPay".equals(service)) { // 主扫下单
				HxbScanPayRsp rsp = hxbNewScanPayService.scanCBPay((HxbScanPayReq) XmlUtil.XmlToObj(msg, HxbScanPayReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbScanPayRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);

			} else if ("scanBcQrCodePay".equals(service)) { // 被扫下单  统一条码
				HxbScanPayRsp rsp = hxbNewScanPayService.scanBcQrCodePay((HxbScanPayReq) XmlUtil.XmlToObj(msg, HxbScanPayReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbScanPayRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);

			} else if("queryPayTrade".equals(service)) { // 订单查询  统一条码
				HxbOrderQueryRsp rsp = hxbOrderNewQueryService.queryPayTrade((HxbOrderQueryReq) XmlUtil.XmlToObj(msg, HxbOrderQueryReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbOrderQueryRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);
			} else if ("scanCbQrCodePay".equals(service)) { // 主扫下单  统一条码
				HxbScanPayRsp rsp = hxbNewScanPayService.scanCbQrCodePay((HxbScanPayReq) XmlUtil.XmlToObj(msg, HxbScanPayReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbScanPayRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);

			} else if ("preH5Pay".equals(service)) { // 微信 h5 下单  统一条码
				HxbScanPayRsp rsp = hxbNewScanPayService.preH5Pay((HxbScanPayReq) XmlUtil.XmlToObj(msg, HxbScanPayReq.class));
				rspXml = XmlUtil.ObjToXml(rsp, HxbScanPayRsp.class);
				rspXml = commonRspXmlHandler(service, rspXml, encoder, rsp);
			}
			else {
				rspXml = "0|999910|" + encoder.encode("查无此应用接口".getBytes("utf-8"));
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				return null;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rspXml;
	}

	
	private String commonRspXmlHandler(String service, String rspXml, BASE64Encoder encoder, BaseRsp rsp) throws UnsupportedEncodingException {
		logger.info("调用服务[" + service + "]返回：" + rspXml);
		if (rsp.getRespCode().equals("000000")) {
			rspXml = "1|" + encoder.encode(rspXml.getBytes("utf-8"));
		} else {
			rspXml = "0|" + rsp.getRespCode() + "|" + encoder.encode(rsp.getRespDesc().getBytes("utf-8"));
		}
		return rspXml;
	}

}
