package com.icbcbecode.service.impl;

import com.icbcbecode.util.ConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.icbc.api.DefaultIcbcClient;
import com.icbc.api.IcbcApiException;
import com.icbc.api.IcbcConstants;
import com.icbc.api.request.QrcodeQueryRequestV2;
import com.icbc.api.request.QrcodeQueryRequestV2.QrcodeQueryRequestV2Biz;
import com.icbc.api.response.QrcodeQueryResponseV2;
import com.icbcbecode.dto.IcBcBaseReq;
import com.icbcbecode.dto.IcBcBaseRsp;
import com.icbcbecode.dto.IcBcOrderPayRsp;
import com.icbcbecode.service.IcBcOrderQueryService;
import com.icbcbecode.util.JsonUtils;
import com.icbcbecode.util.StrUtils;

@Service
public class IcBcOrderQueryServiceImpl implements IcBcOrderQueryService {

	private static final Logger logger = LoggerFactory.getLogger(IcBcOrderPayServiceImpl.class);
	
	@Override
	public IcBcBaseRsp getOrderStatus(IcBcBaseReq mreq) {
		logger.info("进入 工商银行聚合扫码订单支付状态查询 业务处理类：...");
		IcBcBaseRsp mrsp = new IcBcBaseRsp();
		 mrsp.setRespCode("999910");
		    mrsp.setRespDesc("unexpected error");
		    
		    if(checkResultParams(mreq) > 0) {
		    	JSONObject jsonObject = JSONObject.parseObject(mreq.getBiz_content());
		    	String mer_id = jsonObject.getString("mer_id");
		    	String cust_id = jsonObject.getString("cust_id");
		    	String out_trade_no = jsonObject.getString("out_trade_no");
		    	String order_id = jsonObject.getString("order_id");
		    	String msgId = "icbc"+StrUtils.getYearMonthDayString()+StrUtils.getRandomString(4);
		    	
		    	DefaultIcbcClient client = new DefaultIcbcClient(mreq.getApp_id(), IcbcConstants.SIGN_TYPE_RSA2, mreq.getMyPrivateKey(), mreq.getAppgwPublicKey());
				
				QrcodeQueryRequestV2 request = new QrcodeQueryRequestV2();
				request.setServiceUrl(ConfigUtil.getValue("orderQuery"));
				QrcodeQueryRequestV2Biz bizContent = new QrcodeQueryRequestV2Biz();
				bizContent.setMerId(mer_id);
				bizContent.setCustId(cust_id);//该字段非必输项
				bizContent.setOutTradeNo(out_trade_no);//该字段非必输项,out_trade_no和order_id选一项上送即可
				bizContent.setOrderId(order_id);//该字段非必输项,out_trade_no和order_id选一项上送即可
				request.setBizContent(bizContent);
				logger.info("调用工行聚合扫码订单支付状态查询,请求参数为："+JSON.toJSONString(bizContent));
				
				QrcodeQueryResponseV2 response;
				try {
					response = client.execute(request, msgId);//msgId消息通讯唯一编号，要求每次调用独立生成，APP级唯一
					if (response.isSuccess()) {
						logger.info("调用 工行聚合扫码订单支付状态查询接口成功,响应参数为："+JSON.toJSONString(response));
						IcBcOrderPayRsp irsp = new IcBcOrderPayRsp();
						irsp.setReturnCode(response.getReturnCode());
						irsp.setReturnMsg(response.getReturnMsg());
						irsp.setMsgId(response.getMsgId());
						irsp.setPayStatus(response.getPayStatus());
						irsp.setCustId(response.getCustId());
						irsp.setCardNo(response.getCardNo());
						irsp.setTotalAmt(response.getTotalAmt());
						irsp.setPointAmt(response.getPointAmt());
						irsp.setEcouponAmt(response.getEcouponAmt());
						irsp.setMerDiscAmt(response.getMerDiscAmt());
						irsp.setCouponAmt(response.getCouponAmt());
						irsp.setBankDiscAmt(response.getBankDiscAmt());
						irsp.setPaymentAmt(response.getPaymentAmt());
						irsp.setOutTradeNo(response.getOutTradeNo());
						irsp.setOrderId(response.getOrderId());
						irsp.setPayTime(response.getPayTime());
						irsp.setTotalDiscAmt(response.getTotalDiscAmt());
						
						mrsp.setRespCode("000000");
						mrsp.setRespDesc(response.getReturnMsg());
						mrsp.setResponse_biz_content(JSON.toJSONString(irsp));
					} else {
						//失败
						logger.info("调用 工行聚合扫码订单支付状态查询接口失败,响应参数为："+JSON.toJSONString(response));
						mrsp.setRespDesc(response.getReturnMsg());
					}		
				} catch (IcbcApiException e) {
					e.printStackTrace();
				}		    	
		    } else {
		    	mrsp.setRespDesc("聚合下单请求参数为空或格式有误");
		    }
		    logger.info("工行聚合扫码订单支付状态查询 响应结果为："+JSON.toJSONString(mrsp));
		return mrsp;
	}
	
	private int checkResultParams(IcBcBaseReq mreq) {
		if(StringUtils.isNotBlank(mreq.getApp_id()) && StringUtils.isNotBlank(mreq.getAppgwPublicKey()) && StringUtils.isNotBlank(mreq.getMyPrivateKey()) && 
				StringUtils.isNotBlank(mreq.getBiz_content()) && JsonUtils.isOrNotJson(mreq.getBiz_content())) {
			return 1;
		}
		return -1;
	}

}
