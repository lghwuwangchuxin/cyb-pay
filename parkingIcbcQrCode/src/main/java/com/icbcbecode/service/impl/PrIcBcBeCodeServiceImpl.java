package com.icbcbecode.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icbcbecode.dto.IcBcBaseReq;
import com.icbcbecode.dto.IcBcBaseRsp;
import com.icbcbecode.service.IcBcOrderPayService;
import com.icbcbecode.service.IcBcOrderQueryService;
import com.icbcbecode.service.PrIcBcBeCodeService;
import com.icbcbecode.util.DecryptForClient;
import com.icbcbecode.util.XmlUtil;

@Service
public class PrIcBcBeCodeServiceImpl implements PrIcBcBeCodeService {

	private static final Logger logger = LoggerFactory.getLogger(PrIcBcBeCodeServiceImpl.class);

	@Autowired
	private IcBcOrderPayService icBcOrderPayService;
	@Autowired
	private IcBcOrderQueryService icBcOrderQueryService;

	@Override
	public String prIcBcBeCodeSyncMethod(String msg) {
		logger.info("进入工银二维码支付解决方案 业务处理类：");
		logger.info("收到消息：" + msg);
		String rspXml = "";
		Map<String, String> xmlTypeMap = new HashMap<String, String>();
		DecryptForClient decryptForClinet = new DecryptForClient();

		try {
			xmlTypeMap = decryptForClinet.getHead(msg);
			String service = (String) xmlTypeMap.get("service");
			logger.info("进入工银二维码支付 接入系统,调用服务[" + service + "]");
			if ("icbcQrCodeV2Pay".equals(service)) {
				IcBcBaseReq mreq = (IcBcBaseReq) XmlUtil.XmlToObj(msg, IcBcBaseReq.class);
				IcBcBaseRsp mrsp = this.icBcOrderPayService.getOrderPay(mreq);
				if (mrsp.getRespCode().equals("000000")) {
					rspXml = "1|" + Base64.getEncoder().encodeToString(XmlUtil.ObjToXml(mrsp, IcBcBaseRsp.class).getBytes("UTF-8"));
				} else {
					rspXml = "0|" + mrsp.getRespCode() + "|" + Base64.getEncoder().encodeToString(mrsp.getRespDesc().getBytes("UTF-8"));
				}
			} else if ("icbcQrCodeV2Query".equals(service)) {
				IcBcBaseReq mreq = (IcBcBaseReq) XmlUtil.XmlToObj(msg, IcBcBaseReq.class);
				IcBcBaseRsp mrsp = this.icBcOrderQueryService.getOrderStatus(mreq);
				if (mrsp.getRespCode().equals("000000")) {
					rspXml = "1|" + Base64.getEncoder().encodeToString(XmlUtil.ObjToXml(mrsp, IcBcBaseRsp.class).getBytes("UTF-8"));
				} else {
					rspXml = "0|" + mrsp.getRespCode() + "|" + Base64.getEncoder().encodeToString(mrsp.getRespDesc().getBytes("UTF-8"));
				}
			} else {
				rspXml = "0|999903|" + Base64.getEncoder().encodeToString("查无此应用接口".getBytes("UTF-8"));
			}

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rspXml;
	}

	@Override
	public String prIcBcBeCodeAsyncMethod(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

}
