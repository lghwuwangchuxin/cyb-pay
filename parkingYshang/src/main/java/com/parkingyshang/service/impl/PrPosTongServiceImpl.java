package com.parkingyshang.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.parkingyshang.dto.*;
import com.parkingyshang.service.*;
import com.parkingyshang.util.DecryptForClient;
import com.parkingyshang.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

@Service("prPosTongService")
public class PrPosTongServiceImpl implements PrPosTongService {
	
	private static final Logger logger=LoggerFactory.getLogger(PrPosTongServiceImpl.class);
	@Inject
	private BeCodeOrderPaysService beCodeOrderPaysService;
	@Inject
	private QueryOrderPaysStatusService queryOrderPaysStatusService;
	@Inject
	private RevokeOrderPaysService revokeOrderPaysService;
	@Inject
	private RefundOrderPaysService refundOrderPaysService;
	@Inject
	private RepayOrderPaysService repayOrderPaysService;

	@Override
	public String yshangPosBecodeCallCenterSync(String msg) throws Exception {
		logger.info("进入银商pos通接入系统入口：YShangBeCodeCallCenterSync-----");
		logger.info("接受到的消息为："+msg);
		String rspXml="";	//响应消息
		Map<String ,String> map = new HashMap<String ,String>();
		BASE64Encoder encoder=new BASE64Encoder();
		DecryptForClient decryptForClient=new DecryptForClient();
		map = decryptForClient.getHead(msg);
		String service = map.get("service");
		logger.info("进入银商pos通接入系统,调用服务【"+service+"】");
		try {
			if(service.equals("")) {
				
			}else if(service.equals("postongorderpays")) {  // 被扫订单支付
				BeCodeOrderPaysReq mreq=(BeCodeOrderPaysReq) XmlUtil.XmlToObj(msg, BeCodeOrderPaysReq.class);
				BeCodeOrderPaysRsp mrsp=beCodeOrderPaysService.getbeCodeTradePasyRsp(mreq);
				logger.info("调用【"+service+"】返回："+XmlUtil.ObjToXml(mrsp, BeCodeOrderPaysRsp.class));
				if(mrsp.getRespCode().equals("000000")) {					
					rspXml="1|"+encoder.encode(XmlUtil.ObjToXml(mrsp, BeCodeOrderPaysRsp.class).getBytes("utf-8"));
				}else {
					rspXml="0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			}else if(service.equals("postongqueryorderstatus")) {  //订单状态查询
				QueryOrderPaysStatusReq mreq=(QueryOrderPaysStatusReq) XmlUtil.XmlToObj(msg, QueryOrderPaysStatusReq.class);
				QueryOrderPaysStatusRsp mrsp=queryOrderPaysStatusService.getQueryOrderPaysStatusRsp(mreq);
				logger.info("调用【"+service+"】返回："+XmlUtil.ObjToXml(mrsp, QueryOrderPaysStatusRsp.class));
				if(mrsp.getRespCode().equals("000000")) {					
					rspXml="1|"+encoder.encode(XmlUtil.ObjToXml(mrsp, QueryOrderPaysStatusRsp.class).getBytes("utf-8"));
				}else {
					rspXml="0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			}else if(service.equals("postongrevokeorderpays")) {  //订单撤销
				RevokeOrderPaysReq mreq=(RevokeOrderPaysReq) XmlUtil.XmlToObj(msg, RevokeOrderPaysReq.class);
				RevokeOrderPaysRsp mrsp=revokeOrderPaysService.getRevokeBeCodeOrderRsp(mreq);
				logger.info("调用【"+service+"】返回："+XmlUtil.ObjToXml(mrsp, RevokeOrderPaysRsp.class));
				if(mrsp.getRespCode().equals("000000")) {					
					rspXml="1|"+encoder.encode(XmlUtil.ObjToXml(mrsp, RevokeOrderPaysRsp.class).getBytes("utf-8"));
				}else {
					rspXml="0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			}else if(service.equals("postongrefundorders")) {  //申请退款
				RefundOrderPaysReq mreq=(RefundOrderPaysReq) XmlUtil.XmlToObj(msg, RefundOrderPaysReq.class);
				RefundOrderPaysRsp mrsp=refundOrderPaysService.getRefundOrderPaysRsp(mreq);
				logger.info("调用【"+service+"】返回："+XmlUtil.ObjToXml(mrsp, RefundOrderPaysRsp.class));
				if(mrsp.getRespCode().equals("000000")) {					
					rspXml="1|"+encoder.encode(XmlUtil.ObjToXml(mrsp, RefundOrderPaysRsp.class).getBytes("utf-8"));
				}else {
					rspXml="0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			}else if(service.equals("postongreverseorders")) {  //支付冲正
				RepayOrderPaysReq mreq=(RepayOrderPaysReq) XmlUtil.XmlToObj(msg, RepayOrderPaysReq.class);
				RepayOrderPaysRsp mrsp=repayOrderPaysService.getRepayOrderPaysRsp(mreq);
				logger.info("调用【"+service+"】返回："+XmlUtil.ObjToXml(mrsp, RepayOrderPaysRsp.class));
				if(mrsp.getRespCode().equals("000000")) {					
					rspXml="1|"+encoder.encode(XmlUtil.ObjToXml(mrsp, RepayOrderPaysRsp.class).getBytes("utf-8"));
				}else {
					rspXml="0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else {
				rspXml="0|999903|"+encoder.encode("查无此应用接口".getBytes("utf-8"));
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


}
