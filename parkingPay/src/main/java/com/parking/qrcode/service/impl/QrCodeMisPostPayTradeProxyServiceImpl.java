package com.parking.qrcode.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.inject.Inject;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.dto.*;
import com.parking.service.PrPosTongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parking.domain.ParkingTradeOrder;
import com.parking.qrcode.service.QrCodeMisPostPayTradeService;
import com.parking.service.InvokeInteService;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;
import com.parking.util.XmlUtil;

@Service("qrCodeMisPostPayTradeService")
public class QrCodeMisPostPayTradeProxyServiceImpl implements QrCodeMisPostPayTradeService  {
	
	private static final Logger logger = LoggerFactory.getLogger(QrCodeMisPostPayTradeProxyServiceImpl.class);
	
	 @Autowired
	 private InvokeInteService invokeInteService;
	 @Autowired
	 private PrPosTongService prPosTongService;
	
	/**
	 * (non-Javadoc)  pos 通下单
	 * @see com.parking.qrcode.service.QrCodePayTradeChannelAccessService#tradePay(Object[])
	 */
	@Override
	public Object tradePay(Object... obj) throws Exception {
		logger.info("进入银商pos通下单业务-------------------------------");
		ApplyOrderChannelBaseDTO applyOrderChannelBaseDTO=new ApplyOrderChannelBaseDTO();
		applyOrderChannelBaseDTO.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
		applyOrderChannelBaseDTO.setRespCode(RespUtil.codeError);
		applyOrderChannelBaseDTO.setTradeCode(RespUtil.codeError);
		applyOrderChannelBaseDTO.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		
		String  callResp="";
		BeCodeOrderPaysReq beCodeOrderPaysReq=new BeCodeOrderPaysReq();
		beCodeOrderPaysReq.setService(CommEnum.PARKING_PosTong_YShang_ORDER_PAYS_SERVICE.getRspCode());
		beCodeOrderPaysReq.setTransactionCurrencyCode(CommEnum.PosTong_YShang_CurrencyCode.getRspCode());  // 交易货币
		beCodeOrderPaysReq.setPayMode(CommEnum.PosTong_YShang_PayMode.getRspCode());  //支付方式
		if(obj[0] instanceof ApplyOrderReq) {
			ApplyOrderReq req = (ApplyOrderReq)obj[0];
			logger.info(req.getChannelType());
			beCodeOrderPaysReq.setTransactionAmount(req.getPayAmt());  //支付金额
			beCodeOrderPaysReq.setPayCode(req.getQrCodeConTent());  //支付条码
			beCodeOrderPaysReq.setSerialNumber(req.getSerialNumber());  //终端流水号
			beCodeOrderPaysReq.setMerchantRemark(req.getOrderName());  //商户备注
			applyOrderChannelBaseDTO.setSerialNumber(req.getSerialNumber());
		} 
		if(obj[1] instanceof ParkingQrcodeMchntConfig) {
			ParkingQrcodeMchntConfig mchntConfig=(ParkingQrcodeMchntConfig) obj[1];
			beCodeOrderPaysReq.setTerminalCode(mchntConfig.getRsrvStr1());  //终端号
			beCodeOrderPaysReq.setMerchantCode(mchntConfig.getMchntId());  //商户号	
			beCodeOrderPaysReq.setAppId(mchntConfig.getMchntAppId());  //appid
			beCodeOrderPaysReq.setAppKey(mchntConfig.getMchntPrivateKey());  //签名密钥
		}
		if(obj[2] instanceof ParkingTradeOrder) {
			ParkingTradeOrder parkingTradeOrder=(ParkingTradeOrder) obj[2];
			beCodeOrderPaysReq.setMerchantOrderId(parkingTradeOrder.getTradeId());  //商户订单号
		}
		try {
			String xmlReq = XmlUtil.ObjToXml(beCodeOrderPaysReq, BeCodeOrderPaysReq.class);
			logger.info("组装银商pos通被扫渠道接入下单请求报文:"+xmlReq);
			callResp = prPosTongService.yshangPosBcCallCenterSync(xmlReq);
			logger.info("组装银商pos通被扫渠道接入下单响应报文:"+callResp);
			Map<String ,String> resultMap = invokeInteService.parseResp(callResp);
			if(resultMap.get(CommEnum.GET_MSG_TAG.getRspCode()).equals("1")) {
				String postongrspxml = resultMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
				logger.info("银商pos通被扫渠道订单支付返回为："+postongrspxml);
				BeCodeOrderPaysRsp beCodeOrderPaysRsp = (BeCodeOrderPaysRsp)XmlUtil.XmlToObj(postongrspxml, BeCodeOrderPaysRsp.class);
				if(null != beCodeOrderPaysRsp && StringUtil.checkStringsEqual(RespUtil.successCode, beCodeOrderPaysRsp.getRespCode())) {
					if(!StringUtil.checkNullString(beCodeOrderPaysRsp.getErrCode())&&StringUtil.checkStringsEqual(CommEnum.PosTong_YShang_BECODE_PAY_RESUTLT.getRspCode(), beCodeOrderPaysRsp.getErrCode())) {
						SimpleDateFormat fmt = new SimpleDateFormat("yyyy");
						applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
						applyOrderChannelBaseDTO.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
						applyOrderChannelBaseDTO.setPayId(beCodeOrderPaysRsp.getOrderId());  //第三方支付订单号
						applyOrderChannelBaseDTO.setPayTime(beCodeOrderPaysRsp.getTransactionDate()+beCodeOrderPaysRsp.getTransactionTime());  //支付时间
						applyOrderChannelBaseDTO.setWaitTime(StringUtil.checkNullString(beCodeOrderPaysRsp.getSettlementDate())?"":fmt.format(new Date())+beCodeOrderPaysRsp.getSettlementDate());  //清算日期
						applyOrderChannelBaseDTO.setRespCode(CommEnum.SUCCESS_COID.getRspCode());
						applyOrderChannelBaseDTO.setTradeCode(beCodeOrderPaysRsp.getErrCode());
						applyOrderChannelBaseDTO.setTradeDesc(beCodeOrderPaysRsp.getErrInfo());
					}
				} else {
					applyOrderChannelBaseDTO.setTradeCode(beCodeOrderPaysRsp.getRespCode());
					applyOrderChannelBaseDTO.setTradeDesc(beCodeOrderPaysRsp.getRespDesc());
				}	
			} else {
				applyOrderChannelBaseDTO.setTradeCode(resultMap.get(CommEnum.RESP_CODE.getRspCode()));
				applyOrderChannelBaseDTO.setTradeDesc(resultMap.get(CommEnum.RESP_DESC.getRspCode()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("发起扣款出现异常");
		}
		return applyOrderChannelBaseDTO;
		
	}

	/**
	 * (non-Javadoc) 银商pos通查询交易
	 * @see com.parking.qrcode.service.QrCodePayTradeChannelAccessService(Object[])
	 */
	@Override
	public Object queryTrade(Object... obj) throws Exception {
		logger.info("进入银商pos通支付状态查询-------------------------------");
		ApplyOrderChannelBaseDTO applyOrderChannelBaseDTO=new ApplyOrderChannelBaseDTO();
		applyOrderChannelBaseDTO.setRespCode(RespUtil.codeError);
		applyOrderChannelBaseDTO.setTradeCode(RespUtil.codeError);
		applyOrderChannelBaseDTO.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		
		String  callResp="";
		QueryOrderPaysStatusReq queryOrderPaysStatusReq=new QueryOrderPaysStatusReq();
		queryOrderPaysStatusReq.setService(CommEnum.PARKING_PosTong_YShang_QUERY_ORDERS_SERVICE.getRspCode());  //service
		if(obj[0] instanceof QueryAtmResultReq) {
			QueryAtmResultReq queryAtmResultReq=(QueryAtmResultReq)obj[0];
			queryOrderPaysStatusReq.setMerchantOrderId(queryAtmResultReq.getOrderId());  //订单号
			queryOrderPaysStatusReq.setSerialNumber(queryAtmResultReq.getSerialNumber());  //
		}
		if(obj[1] instanceof ParkingQrcodeMchntConfig) {
			ParkingQrcodeMchntConfig mchntConfig=(ParkingQrcodeMchntConfig)obj[1];
			queryOrderPaysStatusReq.setMerchantCode(mchntConfig.getMchntId());  //商户号
			queryOrderPaysStatusReq.setTerminalCode(mchntConfig.getRsrvStr1());  //终端号
			queryOrderPaysStatusReq.setAppId(mchntConfig.getMchntAppId());  //appid
			queryOrderPaysStatusReq.setAppKey(mchntConfig.getMchntPrivateKey());  //签名密钥
		}
		try {
			String xmlReq = XmlUtil.ObjToXml(queryOrderPaysStatusReq, QueryOrderPaysStatusReq.class);
			logger.info("组装银商pos通被扫渠道接入订单支付状态查询请求报文:"+xmlReq);
			callResp = prPosTongService.yshangPosBcCallCenterSync(xmlReq);
			logger.info("组装银商pos通被扫渠道接入订单支付状态查询响应报文:"+callResp);
			Map<String ,String> resultMap = invokeInteService.parseResp(callResp);
			if(resultMap.get(CommEnum.GET_MSG_TAG.getRspCode()).equals("1")) {
				String postongrspxml=resultMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
				logger.info("银商pos通被扫渠道订单状态查询返回为："+postongrspxml);
				QueryOrderPaysStatusRsp  queryOrderPaysStatusRsp=(QueryOrderPaysStatusRsp)XmlUtil.XmlToObj(postongrspxml, QueryOrderPaysStatusRsp.class);
				if(null != queryOrderPaysStatusRsp && StringUtil.checkStringsEqual(RespUtil.successCode, queryOrderPaysStatusRsp.getRespCode())) {
					if(!StringUtil.checkNullString(queryOrderPaysStatusRsp.getQueryResCode())&&StringUtil.checkStringsEqual("0", queryOrderPaysStatusRsp.getQueryResCode())) {
						applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
						applyOrderChannelBaseDTO.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
						applyOrderChannelBaseDTO.setPayId(queryOrderPaysStatusRsp.getOrderId());
						applyOrderChannelBaseDTO.setRespCode(CommEnum.SUCCESS_COID.getRspCode());
						applyOrderChannelBaseDTO.setTradeCode(queryOrderPaysStatusRsp.getErrCode());
						applyOrderChannelBaseDTO.setTradeDesc(queryOrderPaysStatusRsp.getErrInfo());
					}
				} else {
					applyOrderChannelBaseDTO.setTradeCode(queryOrderPaysStatusRsp.getRespCode());
					applyOrderChannelBaseDTO.setTradeDesc(queryOrderPaysStatusRsp.getRespDesc());
				}	
			} else {
				applyOrderChannelBaseDTO.setTradeCode(resultMap.get(CommEnum.RESP_CODE.getRspCode()));
				applyOrderChannelBaseDTO.setTradeDesc(resultMap.get(CommEnum.RESP_DESC.getRspCode()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询订单支付状态出现异常");
		}
		return applyOrderChannelBaseDTO;
		
	}

}

