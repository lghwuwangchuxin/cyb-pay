package com.parking.qrcode.service.impl;

import com.alibaba.fastjson.JSON;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.ApplyOrderReq;
import com.parking.dto.QueryAtmResultReq;
import com.parking.dto.icbc.*;
import com.parking.qrcode.service.QrCodeIcbcPayTradeService;
import com.parking.service.InvokeInteService;
import com.parking.service.PrIcBcBeCodeService;
import com.parking.util.CommEnum;
import com.parking.util.CommIcbcEnum;
import com.parking.util.RespUtil;
import com.parking.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service("qrCodeIcbcPayTradeService")
public class QrCodeIcbcPayTradeServiceImpl implements QrCodeIcbcPayTradeService {
	
	private static final Logger logger = LoggerFactory.getLogger(QrCodeIcbcPayTradeServiceImpl.class);
	
	@Inject
	private PrIcBcBeCodeService prIcBcBeCodeService;
	
	@Inject
	private InvokeInteService invokeInteService;

	@Override
	public Object tradePay(Object... obj) throws Exception {
		logger.info("接入 中国工商银行 商户主扫聚合 下单支付 渠道：");
		
		ApplyOrderChannelBaseDTO applyOrderChannelBaseDTO = new ApplyOrderChannelBaseDTO();
		applyOrderChannelBaseDTO.setRespCode(RespUtil.codeError);
		applyOrderChannelBaseDTO.setTradeCode(RespUtil.noResult);
		applyOrderChannelBaseDTO.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		
		IcBcBaseReq mreq = new IcBcBaseReq();
		OrderPay or = new OrderPay();
		mreq.setService(CommIcbcEnum.INTERFACE_NAME01.getRespCode());		//下单支付
		if(obj[0] instanceof ApplyOrderReq) {
			ApplyOrderReq applyOrderReq = (ApplyOrderReq) obj[0];
			or.setOrderAmt(applyOrderReq.getPayAmt()); 		//订单金额 
			or.setQrCode(applyOrderReq.getQrCodeConTent()); 	//支付条码
		}
		if(obj[1] instanceof ParkingQrcodeMchntConfig) {
			ParkingQrcodeMchntConfig mchntConfig = (ParkingQrcodeMchntConfig) obj[1];
			or.setMerId(mchntConfig.getMchntId()); 		//商户号 
			mreq.setAppgwPublicKey(mchntConfig.getMchntPublicOtherKey());		//验签公钥
			mreq.setMyPrivateKey(mchntConfig.getMchntPrivateKey()); 		//加签私钥
			mreq.setApp_id(mchntConfig.getMchntAppId()); 		//APP的编号,应用在API开放平台注册时生成
		}
		if(obj[2] instanceof ParkingTradeOrder) {
			ParkingTradeOrder parkingTradeOrder = (ParkingTradeOrder) obj[2];
			or.setOutTradeNo(parkingTradeOrder.getTradeId()); 		//订单号 		
		}
		SimpleDateFormat fmt = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode());
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat fmt3 = new SimpleDateFormat("HHmmss");
		or.setTradeDate(fmt2.format(new Date()));
		or.setTradeTime(fmt3.format(new Date()));
		mreq.setTimestamp(fmt.format(new Date()));
		mreq.setBiz_content(JSON.toJSONString(or));		//请求参数集合
		
		try {
			String msg = XmlUtil.ObjToXml(mreq, IcBcBaseReq.class);
			logger.info("组装---中国工商银行 被扫 渠道下单支付,请求参数为："+msg);
			String result = prIcBcBeCodeService.uniCallProvider(msg);
			logger.info("组装---中国工商银行 被扫 渠道下单支付,响应参数为："+result);
			Map<String, String> map = invokeInteService.parseResp(result);
			if("1".equals(map.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				logger.info("组装---中国工商银行 被扫 渠道下单支付,响应Object--XML为："+map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode()));
				IcBcBaseRsp irsp = (IcBcBaseRsp) XmlUtil.XmlToObj(map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode()), IcBcBaseRsp.class);
				if( irsp!=null ) {
					OrderStatus os = JSON.parseObject(irsp.getResponse_biz_content(), OrderStatus.class);
					if("1".equals(os.getPayStatus())) {
						applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
						applyOrderChannelBaseDTO.setRespCode(CommEnum.SUCCESS_COID.getRspCode());
						applyOrderChannelBaseDTO.setPayId(os.getOrderId()); 		//第三方订单号
						applyOrderChannelBaseDTO.setPayTime(os.getPayTime()); 		//支付时间
						applyOrderChannelBaseDTO.setTradeCode(String.valueOf(os.getReturnCode()));
						applyOrderChannelBaseDTO.setTradeDesc(os.getReturnMsg()); 						
					}/* else {
						applyOrderChannelBaseDTO.setRespCode(irsp.getRespCode());
						applyOrderChannelBaseDTO.setTradeDesc(irsp.getRespDesc());
					}*/
				}
			} else {
				applyOrderChannelBaseDTO.setRespCode(map.get(CommEnum.RESP_CODE.getRspCode()));
				applyOrderChannelBaseDTO.setTradeDesc(map.get(CommEnum.RESP_DESC.getRspCode()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return applyOrderChannelBaseDTO;
	}

	@Override
	public Object queryTrade(Object... obj) throws Exception {
		logger.info("接入 中国工商银行 商户被扫聚合 订单查询 渠道：");
		
		ApplyOrderChannelBaseDTO applyOrderChannelBaseDTO = new ApplyOrderChannelBaseDTO();
		applyOrderChannelBaseDTO.setRespCode(RespUtil.codeError);
		applyOrderChannelBaseDTO.setTradeCode(RespUtil.noResult);
		applyOrderChannelBaseDTO.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		
		IcBcBaseReq mreq = new IcBcBaseReq();
		OrderQuery oq = new OrderQuery();
		mreq.setService(CommIcbcEnum.INTERFACE_NAME02.getRespCode()); 		//订单查询
		if(obj[0] instanceof QueryAtmResultReq) {
			QueryAtmResultReq queryAtmResultReq = (QueryAtmResultReq) obj[0];
			oq.setOutTradeNo(queryAtmResultReq.getOrderId());		//订单号
		}
		if(obj[1] instanceof ParkingQrcodeMchntConfig) {
			ParkingQrcodeMchntConfig mchntConfig = (ParkingQrcodeMchntConfig) obj[1];
			oq.setMerId(mchntConfig.getMchntId()); 		//商户编号
			mreq.setAppgwPublicKey(mchntConfig.getMchntPublicOtherKey());
			mreq.setMyPrivateKey(mchntConfig.getMchntPrivateKey()); 		//加签私钥
			mreq.setApp_id(mchntConfig.getMchntAppId()); 		//APP的编号,应用在API开放平台注册时生成
		}
		if(obj[2] instanceof ParkingTradeOrder) {
			ParkingTradeOrder parkingTradeOrder = (ParkingTradeOrder) obj[2];
			oq.setOrderId(parkingTradeOrder.getOutTradeNo());			
		}
		SimpleDateFormat fmt = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode());
		mreq.setTimestamp(fmt.format(new Date()));
		mreq.setBiz_content(JSON.toJSONString(oq)); 		//请求参数集合
		
		try {
			String msg = XmlUtil.ObjToXml(mreq, IcBcBaseReq.class);
			logger.info("组装---中国工商银行  渠道被扫订单查询,请求参数为："+msg);
			String result = prIcBcBeCodeService.uniCallProvider(msg);
			logger.info("组装---中国工商银行  渠道 被扫订单查询,响应参数为："+result);
			Map<String, String> map = invokeInteService.parseResp(result);
			if("1".equals(map.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				logger.info("组装---中国工商银行 渠道下单支付,响应Object--XML为："+map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode()));
				IcBcBaseRsp irsp = (IcBcBaseRsp) XmlUtil.XmlToObj(map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode()), IcBcBaseRsp.class);
				if( irsp!=null ) {
					OrderStatus os = JSON.parseObject(irsp.getResponse_biz_content(), OrderStatus.class);
					if("1".equals(os.getPayStatus())) {
						applyOrderChannelBaseDTO.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
						applyOrderChannelBaseDTO.setRespCode(CommEnum.SUCCESS_COID.getRspCode());
						applyOrderChannelBaseDTO.setPayId(os.getOrderId()); 		//第三方订单号
						applyOrderChannelBaseDTO.setPayTime(os.getPayTime()); 		//支付时间
						applyOrderChannelBaseDTO.setTradeCode(String.valueOf(os.getReturnCode()));
						applyOrderChannelBaseDTO.setTradeDesc(os.getReturnMsg()); 						
					}/* else {
						applyOrderChannelBaseDTO.setRespCode(irsp.getRespCode());
						applyOrderChannelBaseDTO.setTradeDesc(irsp.getRespDesc());
					}*/
				}
			} else {
				applyOrderChannelBaseDTO.setRespCode(map.get(CommEnum.RESP_CODE.getRspCode()));
				applyOrderChannelBaseDTO.setTradeDesc(map.get(CommEnum.RESP_DESC.getRspCode()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applyOrderChannelBaseDTO;
	}

}
