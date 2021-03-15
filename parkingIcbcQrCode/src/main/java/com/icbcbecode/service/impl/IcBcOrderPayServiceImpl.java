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
import com.icbc.api.request.QrcodePayRequestV2;
import com.icbc.api.request.QrcodePayRequestV2.QrcodePayRequestV2Biz;
import com.icbc.api.response.QrcodePayResponseV2;
import com.icbcbecode.dto.IcBcBaseReq;
import com.icbcbecode.dto.IcBcBaseRsp;
import com.icbcbecode.dto.IcBcOrderPayRsp;
import com.icbcbecode.service.IcBcOrderPayService;
import com.icbcbecode.util.JsonUtils;
import com.icbcbecode.util.StrUtils;

@Service
public class IcBcOrderPayServiceImpl implements IcBcOrderPayService {

	private static final Logger logger = LoggerFactory.getLogger(IcBcOrderPayServiceImpl.class);

	@Override
	public IcBcBaseRsp getOrderPay(IcBcBaseReq mreq) {
		logger.info("进入 工商银行聚合扫码下单支付业务处理类：...");
		IcBcBaseRsp mrsp = new IcBcBaseRsp();
		mrsp.setRespCode("999910");
		mrsp.setRespDesc("unexpected error");
		
		if(checkPayParams(mreq) > 0) {
			JSONObject jsonObject = JSONObject.parseObject(mreq.getBiz_content());
			String qr_code = jsonObject.getString("qr_code");
			String mer_id = jsonObject.getString("mer_id");
			String out_trade_no = jsonObject.getString("out_trade_no");
			String order_amt = jsonObject.getString("order_amt");
			String trade_date = jsonObject.getString("trade_date");
			String trade_time = jsonObject.getString("trade_time");
			String msgId = "icbc"+StrUtils.getYearMonthDayString()+StrUtils.getRandomString(4);
			
			DefaultIcbcClient client = new DefaultIcbcClient(mreq.getApp_id(), IcbcConstants.SIGN_TYPE_RSA2, mreq.getMyPrivateKey(), mreq.getAppgwPublicKey());
			
			QrcodePayRequestV2 request = new QrcodePayRequestV2();
			request.setServiceUrl(ConfigUtil.getValue("orderPay"));
			QrcodePayRequestV2Biz bizContent = new QrcodePayRequestV2Biz();
			bizContent.setQrCode(qr_code);
			bizContent.setMerId(mer_id);
			bizContent.setOutTradeNo(out_trade_no);
			bizContent.setOrderAmt(order_amt);
			bizContent.setTradeDate(trade_date);
			bizContent.setTradeTime(trade_time);
			request.setBizContent(bizContent);
			logger.info("调用工行聚合扫码下单支付,请求参数为："+JSON.toJSONString(bizContent));
			
			QrcodePayResponseV2 response;
			try {
				response = client.execute(request, msgId);//msgId消息通讯唯一编号，要求每次调用独立生成，APP级唯一
				if (response.isSuccess()) {
					logger.info("调用 工行聚合扫码支付接口成功,响应参数为："+JSON.toJSONString(response));
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
					logger.info("调用 工行聚合扫码下单支付接口失败,响应参数为："+JSON.toJSONString(response));
					mrsp.setRespDesc(response.getReturnMsg());
				}		
			} catch (IcbcApiException e) {
				e.printStackTrace();
			}
		} else {			
			mrsp.setRespDesc("聚合下单请求参数为空或格式有误");
		}
		logger.info("工行("+mreq.getApp_id()+")聚合扫码下单响应结果为："+JSON.toJSONString(mrsp));
		return mrsp;
	}

	/**
	 * 下单参数校验
	 * @param mreq
	 * @return
	 */
	private int checkPayParams(IcBcBaseReq mreq) {
		if(StringUtils.isNotBlank(mreq.getApp_id()) && StringUtils.isNotBlank(mreq.getAppgwPublicKey()) && StringUtils.isNotBlank(mreq.getMyPrivateKey()) && 
				StringUtils.isNotBlank(mreq.getBiz_content()) && JsonUtils.isOrNotJson(mreq.getBiz_content())) {
			return 1;
		}
		return -1;
	}
}
