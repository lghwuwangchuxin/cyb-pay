package com.parking.service.impl;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import com.parking.dto.PayNotifyReq;
import com.parking.dto.PayNotifyRsp;
import com.parking.dto.UnPayNotifyReq;
import com.parking.dto.UnPayNotifyRsp;
import com.parking.dto.icbc.OrderInfoReq;
import com.parking.dto.icbc.OrderInfoRsp;
import com.parking.dtosh.*;
import com.parking.service.*;
import com.parking.unsens.channel.service.ParkingIcbcChannelAccessService;
import com.parking.unsens.channel.service.ParkingShOrderPayService;
import com.parking.unsens.channel.service.ParkingShOrderRefundService;
import com.parking.unsens.channel.service.ParkingShQueryLotService;
import com.parking.util.CommIcbcEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.util.DecryptForClient;
import com.parking.util.XmlUtil;


@Service("prParkingService")
public class PrParkingServiceImpl implements PrParkingService {

	private static final Logger logger = LoggerFactory.getLogger(PrParkingServiceImpl.class);
	@Inject
    private ParkingShOrderPayService parkingShOrderPayService;
	@Inject
	private ParkingChannelShCarService parkingChannelShCarService;
	@Inject
	private ParkingShQueryLotService parkingShQueryLotService;
	@Inject
	private ParkingShOrderRefundService parkingShOrderRefundService;
	@Inject
	private ParkingChannelShQuerySignKeyService parkingChannelShQuerySignKeyService;
	@Inject
	private  ParkingOrderService parkingOrderService;
	@Inject
	private UnionQrCodeNotifyService unionQrCodeNotifyService;
	@Inject
	private ParkingIcbcChannelAccessService parkingIcbcChannelAccessService;

	
	@Override
	public String parkingCallCenterSync(String msg) throws Exception {
		logger.info("进入支付前置系统入口：ParkingCallCenterSync-----");
		String service="";
		String rspXml = "";  //响应报
		logger.info("收到报文：" + msg);
		Map xmlTypeMap = new HashMap(10);//头属性
		DecryptForClient decryptForClient = new DecryptForClient();

		xmlTypeMap = decryptForClient.getHead(msg);
		service = (String)xmlTypeMap.get("service");
		logger.info("进入支付前置系统,调用服务["+service+"]");

		try{
			//各应用接收请求进行处理,并响应明文 
			if(service.equals("payNotifyResult")){ //
				PayNotifyRsp mrsp = parkingOrderService.notifyPayOrderAResult((PayNotifyReq) XmlUtil.XmlToObj(msg, PayNotifyReq.class));
				logger.info("调用支付前置服务[" +service +"]返回：" +XmlUtil.ObjToXml(mrsp,PayNotifyRsp.class));
				rspXml = XmlUtil.ObjToXml(mrsp, PayNotifyRsp.class);
			} else if (service.equals("payNotify")) {
				UnPayNotifyRsp mrsp = unionQrCodeNotifyService.notifyQrCodeTradeOrderStatus((UnPayNotifyReq) XmlUtil.XmlToObj(msg, UnPayNotifyReq.class));
				logger.info("调用支付前置服务[" +service +"]返回：" +XmlUtil.ObjToXml(mrsp,UnPayNotifyRsp.class));
				rspXml = XmlUtil.ObjToXml(mrsp, UnPayNotifyRsp.class);
			}else if (service.equals("shPayNotify")) { //上海金融事业部智慧平台支付通知结果
				PayResultRsp  mrsp = parkingShOrderPayService.notifyPayAResult((PayResultReq) XmlUtil.XmlToObj(msg, PayResultReq.class));
				logger.info("调用支付前置服务[" +service +"]返回：" +XmlUtil.ObjToXml(mrsp,PayResultRsp.class));
				rspXml = XmlUtil.ObjToXml(mrsp, PayResultRsp.class);
			} else if (service.equals("shStateSyncNotify")) {//智慧平台无感支付状态通知
				StateSyncSignRsp mrsp = parkingChannelShCarService.noStateSyncNotify((StateSyncSignReq) XmlUtil.XmlToObj(msg, StateSyncSignReq.class));
				logger.info("调用支付前置服务[" +service +"]返回：" +XmlUtil.ObjToXml(mrsp,StateSyncSignRsp.class));
				rspXml = XmlUtil.ObjToXml(mrsp, StateSyncSignRsp.class);
			} else if (service.equals("shRefundNotify")) { //智慧平台退款通知结果
				RefundResultRsp mrsp = parkingShOrderRefundService.notifyRefundResult((RefundResultReq) XmlUtil.XmlToObj(msg, RefundResultReq.class));
				logger.info("调用支付前置服务[" +service +"]返回：" +XmlUtil.ObjToXml(mrsp,RefundResultRsp.class));
				rspXml = XmlUtil.ObjToXml(mrsp, RefundResultRsp.class);
			} else if (service.equals("shGetPreOrder")) { //智慧平台查询临时缴费订单 ，未出场的情况场内缴费
				OrderUnpayRsp mrsp = parkingShOrderPayService.getPrePayBill((OrderUnpayReq) XmlUtil.XmlToObj(msg, OrderUnpayReq.class));
				logger.info("调用支付前置服务[" +service +"]返回：" +XmlUtil.ObjToXml(mrsp,OrderUnpayRsp.class));
				rspXml = XmlUtil.ObjToXml(mrsp, OrderUnpayRsp.class);
			} else if (service.equals("shGetParking")) { //智慧平台查询停车场实时状态
				ParkingStateRsp mrsp = parkingShQueryLotService.getParkingLotStateInfo((ParkingStateReq) XmlUtil.XmlToObj(msg, ParkingStateReq.class));
				logger.info("调用支付前置服务[" +service +"]返回：" +XmlUtil.ObjToXml(mrsp, ParkingStateRsp.class));
				rspXml = XmlUtil.ObjToXml(mrsp, ParkingStateRsp.class);
			} else if (service.equals("shGetAppIdSignKkey")) { //智慧平台查询密钥进行验签
				QueryShSignKeyRsp mrsp = parkingChannelShQuerySignKeyService.queryAppIdSignKey((QueryShSignKeyReq) XmlUtil.XmlToObj(msg, QueryShSignKeyReq.class));
				logger.info("调用支付前置服务[" +service +"]返回：" +XmlUtil.ObjToXml(mrsp, QueryShSignKeyRsp.class));
				rspXml = XmlUtil.ObjToXml(mrsp, QueryShSignKeyRsp.class);
			} else if (service.equals(CommIcbcEnum.ICBC_QUERY_ORDER_NOTIFY_SERVICE.getRespCode())) { // 工行渠道 查询 进出场 记录
				OrderInfoRsp mrsp = (OrderInfoRsp) parkingIcbcChannelAccessService.queryParkingInOutRecorId(XmlUtil.XmlToObj(msg, OrderInfoReq.class));
				rspXml = XmlUtil.ObjToXml(mrsp, OrderInfoRsp.class);
				logger.info("工行渠道调用停车费用和停车记录返回：---"+service+"]返回："+rspXml);
			}

		}catch (Exception e) {
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
	public String parkingCallCenterAsyn(String msg) throws Exception {
		logger.info("进入支付前置系统入口：parkingCallCenterAsyn-----");
		logger.info("收到报文："+msg);
		String rspXml = "";  //响应报文

		return rspXml;
	}
}
