package com.parkingunionpaypre.service.impl;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import com.parkingunionpaypre.dto.SwUnionOrderPayReq;
import com.parkingunionpaypre.dto.SwUnionOrderPayRsp;
import com.parkingunionpaypre.dto.SwUnionOrderQueryReq;
import com.parkingunionpaypre.dto.SwUnionOrderQueryRsp;
import com.parkingunionpaypre.service.PrSwiftUnionPayService;
import com.parkingunionpaypre.service.SwUnionOrderPayService;
import com.parkingunionpaypre.service.SwUnionOrderQueryService;
import com.parkingunionpaypre.service.UnSwUnionH5OrderPayService;
import com.parkingunionpaypre.util.DecryptForClient;
import com.parkingunionpaypre.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

@Service("prSwiftUnionPayService")
public class PrSwiftUnionPayServiceImpl implements PrSwiftUnionPayService {
	
	private static final Logger logger = LoggerFactory.getLogger(PrSwiftUnionPayServiceImpl.class);
	
	@Inject
	private SwUnionOrderPayService swUnionOrderPayService;
	@Inject
	private SwUnionOrderQueryService swUnionOrderQueryService;
	@Inject
	private UnSwUnionH5OrderPayService unSwUnionH5OrderPayService;

	@Override
	public String swunionpayCallCenterSync(String msg) throws Exception {
		logger.info("进入 SW 中国银联 条码前置聚合扫码  业务处理：同步统一处理类~");
		logger.info("请求消息为：\n"+msg);
		String rspXml="";	//响应消息
		Map<String ,String> map=new HashMap<String ,String>(12);
		BASE64Encoder encoder=new BASE64Encoder();
		DecryptForClient decryptForClient=new DecryptForClient();
		map=decryptForClient.getHead(msg);
		String service=map.get("service");
		try {
			if("SwiftUnionOrderPay".equals(service)){     
				//被扫下单支付
				SwUnionOrderPayReq mreq = (SwUnionOrderPayReq) XmlUtil.XmlToObj(msg, SwUnionOrderPayReq.class);
				SwUnionOrderPayRsp mrsp = swUnionOrderPayService.getSwUionOrderPayRsp(mreq);
				logger.info("调用被扫下单【"+service+"】服务,响应参数为："+XmlUtil.ObjToXml(mrsp, SwUnionOrderPayRsp.class));
				if("000000".equals(mrsp.getRespCode())) {
					rspXml = "1|"+encoder.encode(XmlUtil.ObjToXml(mrsp, SwUnionOrderPayRsp.class).getBytes("utf-8"));
				}else {
					rspXml = "0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else if("SwiftUnionOrderQuery".equals(service)) { // 统一支付结果查询
				//订单支付状态查询
				SwUnionOrderQueryReq mreq = (SwUnionOrderQueryReq) XmlUtil.XmlToObj(msg, SwUnionOrderQueryReq.class);
				SwUnionOrderQueryRsp mrsp = swUnionOrderQueryService.getSwUnionOrderQueryRsp(mreq);
				logger.info("调用支付结果查询【"+service+"】服务,响应参数为："+XmlUtil.ObjToXml(mrsp, SwUnionOrderQueryRsp.class));
				if("000000".equals(mrsp.getRespCode())) {
					rspXml = "1|"+encoder.encode(XmlUtil.ObjToXml(mrsp, SwUnionOrderQueryRsp.class).getBytes("utf-8"));
				}else {
					rspXml = "0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else if ("SwiftUnionOrderPayCb".equals(service)) { // 主扫下单支付
				//主扫下单支付
				SwUnionOrderPayReq mreq = (SwUnionOrderPayReq) XmlUtil.XmlToObj(msg, SwUnionOrderPayReq.class);
				SwUnionOrderPayRsp mrsp = swUnionOrderPayService.getCbSwUionOrderPayRsp(mreq);
				logger.info("调用主扫下单【"+service+"】服务,响应参数为："+XmlUtil.ObjToXml(mrsp, SwUnionOrderPayRsp.class));
				if("000000".equals(mrsp.getRespCode())) {
					rspXml = "1|"+encoder.encode(XmlUtil.ObjToXml(mrsp, SwUnionOrderPayRsp.class).getBytes("utf-8"));
				}else {
					rspXml = "0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else if ("SwiftUnionOrderPayH5".equals(service)) { // 银联条码前置h5 统一下单
				//h5 统一下单服务
				SwUnionOrderPayReq mreq = (SwUnionOrderPayReq) XmlUtil.XmlToObj(msg, SwUnionOrderPayReq.class);
				SwUnionOrderPayRsp mrsp = unSwUnionH5OrderPayService.preH5SwUionOrderPayRsp(mreq);
				logger.info("调用h5下单【"+service+"】服务,响应参数为："+XmlUtil.ObjToXml(mrsp, SwUnionOrderPayRsp.class));
				if("000000".equals(mrsp.getRespCode())) {
					rspXml = "1|"+encoder.encode(XmlUtil.ObjToXml(mrsp, SwUnionOrderPayRsp.class).getBytes("utf-8"));
				}else {
					rspXml = "0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			} else {
				rspXml = "0|999903|" + encoder.encode("查无此应用接口".getBytes("utf-8"));
			}
			/*else if("SwiftUnionRefundOrder".equals(service)) {
				//订单退款
				SwUnionRefundOrderReq mreq = (SwUnionRefundOrderReq) XmlUtil.XmlToObj(msg, SwUnionRefundOrderReq.class);
				SwUnionRefundOrderRsp mrsp = swiftUnionOrderRefService.getSwUnionRefundOrderRsp(mreq);
				logger.info("调用【"+service+"】服务,响应参数为："+XmlUtil.ObjToXml(mrsp, SwUnionRefundOrderRsp.class));
				if("000000".equals(mrsp.getRespCode())) {
					rspXml = "1|"+encoder.encode(XmlUtil.ObjToXml(mrsp, SwUnionRefundOrderRsp.class).getBytes("utf-8"));
				}else {
					rspXml = "0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rspXml;
	}


}
