package com.parking.qrcode.service.impl;

import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.*;
import com.parking.qrcode.service.QrCodeHxbPayTradeService;
import com.parking.service.InvokeInteService;
import com.parking.service.PrHxbQrCodeService;
import com.parking.util.CommEnum;
import com.parking.util.CommonHxbPayQrCodePayEnum;
import com.parking.util.RespUtil;
import com.parking.util.XmlUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;


// 华夏银行老接口资料
@Service("qrCodeHxbPayTradeService")
public class QrCodeHxbPayTradeServiceImpl implements QrCodeHxbPayTradeService {

	private static final Logger logger = LoggerFactory.getLogger(QrCodeHxbPayTradeServiceImpl.class);
	
	@Autowired
	private PrHxbQrCodeService prHxbQrCodeService;
	@Autowired
	private InvokeInteService invokeInteService;
	
	@Override
	public Object tradePay(Object... obj) throws Exception {
		logger.info("进入华夏银行支付被扫下单：--------------------");
		ApplyOrderChannelBaseDTO applyOrderChannel = newApplyOrderChannel();
		
		HxbScanPayReq spReq = new HxbScanPayReq();
		spReq.setService(CommonHxbPayQrCodePayEnum.HXB_SCANPAY_SERVICE.getRspCode());
		spReq.setRandomStr(RandomStringUtils.randomNumeric(6)); // 随机字符串 
		spReq.setSignType(MD5);
		
		if(obj[0] instanceof ApplyOrderReq) {
			ApplyOrderReq areq = (ApplyOrderReq) obj[0];
			// 订单金额 转化
			String amount = areq.getPayAmt();
			spReq.setAmount(getBigDic(amount));
			// 授权码 
			String authCode = areq.getQrCodeConTent();
			spReq.setAuthCode(authCode);
			// 支付方式 
			String payMethod = getQrCodeConTent(authCode);
			if (YLPAY.equals(payMethod)) {
				spReq.setTermId(areq.getTermId());
			}
			spReq.setPayMethod(payMethod);
		} 
		if(obj[1] instanceof ParkingQrcodeMchntConfig) {
			ParkingQrcodeMchntConfig mconf = (ParkingQrcodeMchntConfig) obj[1];
			spReq.setAppKey(mconf.getMchntPrivateKey());  // 密钥
			spReq.setMerchantNo(mconf.getMchntId()); // 商户号 
			spReq.setSubject(mconf.getRsrvStr1()); // 订单标题
		}
		if(obj[2] instanceof ParkingTradeOrder) {
			ParkingTradeOrder pto = (ParkingTradeOrder) obj[2];
			spReq.setOrderNo(pto.getTradeId()); // 商户订单号
		}

		try {
			String scReqXml = XmlUtil.ObjToXml(spReq, HxbScanPayReq.class);
			logger.info("组装华夏银行支付被扫下单请求报文：" + scReqXml);
			String cellRspXml = prHxbQrCodeService.hxbQrCodeCallCenterSync(scReqXml);
			logger.info("接收华夏银行支付被扫下单响应报文："+cellRspXml);
			Map<String, String> map = invokeInteService.parseResp(cellRspXml);
			if("1".equals(map.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				String scRspXml = map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
				logger.info("华夏银行支付--被扫渠道订单支付返回为：" + scRspXml);
				HxbScanPayRsp scanPayRsp = (HxbScanPayRsp) XmlUtil.XmlToObj(scRspXml, HxbScanPayRsp.class);
				if(RespUtil.successCode.equals(scanPayRsp.getRespCode())) {
					applyOrderChannel.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
					applyOrderChannel.setRespCode(CommEnum.SUCCESS_COID.getRspCode());  // 000000
					applyOrderChannel.setTradeCode(CommEnum.SUCCESS_COID.getRspCode());  // 000000
					applyOrderChannel.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());  // paid
					applyOrderChannel.setTradeDesc(scanPayRsp.getRespDesc());  // 成功
					
					applyOrderChannel.setPayId(scanPayRsp.getTradeNo()); // 华夏银行支付系统订单号
					applyOrderChannel.setPayTime(scanPayRsp.getSuccessTime()); // 支付成功时间
				}
			} else {
				applyOrderChannel.setTradeCode(CommEnum.RESP_CODE.getRspCode());  
				applyOrderChannel.setTradeDesc(map.get(CommEnum.RESP_DESC.getRspCode()));  
			}
		} catch (Exception e) {
			logger.error("发起扣款出现异常",e);
		}
		
		return applyOrderChannel;
	}

	@Override
	public Object queryTrade(Object... obj) throws Exception {
		logger.info("进入华夏银行支付订单查询：----------------");
		ApplyOrderChannelBaseDTO applyOrderChannel = newApplyOrderChannel();
		
		HxbOrderQueryReq oqReq = new HxbOrderQueryReq();
		oqReq.setService(CommonHxbPayQrCodePayEnum.HXB_ORDERQUERY_SERVICE.getRspCode());
		oqReq.setRandomStr(RandomStringUtils.randomNumeric(6)); // 随机字符串 
		oqReq.setSignType(MD5);
		
		if(obj[1] instanceof ParkingQrcodeMchntConfig) {
			ParkingQrcodeMchntConfig mconf = (ParkingQrcodeMchntConfig) obj[1];
			oqReq.setAppKey(mconf.getMchntPrivateKey());  // 密钥
			oqReq.setMerchantNo(mconf.getMchntId()); // 商户号
		}
		if(obj[2] instanceof ParkingTradeOrder) {
			ParkingTradeOrder pto = (ParkingTradeOrder) obj[2];
			oqReq.setOrderNo(pto.getTradeId()); // 交易订单号
		}
		// 请求 子系统（HxbQrCode）进行远程调用
		try {
			String oqReqXml = XmlUtil.ObjToXml(oqReq, HxbOrderQueryReq.class);
			logger.info("组装华夏银行支付订单查询请求报文："+ oqReqXml);
			String cellRspXml = prHxbQrCodeService.hxbQrCodeCallCenterSync(oqReqXml);
			logger.info("接收华夏银行支付订单查询响应报文："+cellRspXml);
			Map<String, String> map = invokeInteService.parseResp(cellRspXml);
			if("1".equals(map.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				String oqRspXml = map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
				logger.info("华夏银行--被扫渠道订单查询返回为："+ oqRspXml);
				HxbOrderQueryRsp orderQueryRsp = (HxbOrderQueryRsp) XmlUtil.XmlToObj(oqRspXml, HxbOrderQueryRsp.class);
				if(RespUtil.successCode.equals(orderQueryRsp.getRespCode()) && SUCCESS.equals(orderQueryRsp.getTradeStatus())) {
					applyOrderChannel.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
					applyOrderChannel.setRespCode(CommEnum.SUCCESS_COID.getRspCode());  // 000000
					applyOrderChannel.setTradeCode(CommEnum.SUCCESS_COID.getRspCode());  // 000000
					applyOrderChannel.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());  // paid
					applyOrderChannel.setTradeDesc(orderQueryRsp.getRespDesc());  // 成功
					
					applyOrderChannel.setPayId(orderQueryRsp.getTradeNo()); // 华夏银行支付系统订单号
					applyOrderChannel.setPayTime(orderQueryRsp.getSuccessTime()); // 支付成功时间
				} else {
					applyOrderChannel.setTradeCode(orderQueryRsp.getRespCode());  
					applyOrderChannel.setTradeDesc(orderQueryRsp.getRespDesc());  
					applyOrderChannel.setState(CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode());	
				}
			} else {
				applyOrderChannel.setTradeCode(CommEnum.RESP_CODE.getRspCode());  
				applyOrderChannel.setTradeDesc(map.get(CommEnum.RESP_DESC.getRspCode()));  
			}	
			
		} catch (Exception e) {
			logger.error("订单查询出现异常",e);
		}
		
		return applyOrderChannel;
	}
	
	private ApplyOrderChannelBaseDTO newApplyOrderChannel() {
		ApplyOrderChannelBaseDTO instance = new ApplyOrderChannelBaseDTO();
		instance.setRespCode(RespUtil.codeError);
		instance.setTradeCode(RespUtil.codeError);
		instance.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		return instance;
	}
	
	/**
	 * getBigDic: 分转元操作
	 * @param  @param string
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	private String getBigDic(String string) {
		BigDecimal  bigDecimal = new BigDecimal(string);
		return bigDecimal.movePointLeft(2).toString();
	}
	
	//	二维码类型判断
	private String getQrCodeConTent(String authCode) throws NumberFormatException{
			// (2.1)
			String flag = "";
			// 效检授权码是否正确和判读是哪种授权码
			int number = Integer.parseInt(authCode.trim().substring(0,2));//截取头2位
			if (number > 9 && number < 16 ) { // 微信贴卡授权码
				/* 
				 *扫码支付授权码，设备读取用户微信中的条码或者二维码信息
				 *（注：用户刷卡条形码规则：18位纯数字，以10、11、12、13、14、15开头）
				*/
				if (18 == authCode.length()) {	
					flag = WXPAY;
				}
			} else if (number > 24 && number < 31) { // 支付宝条码授权码
				/*
				 * 支付授权码，25~30开头的长度为16~24位的数字，实际字符串长度以开发者获取的付款码长度为准
				 * */
				flag = ALIPAY;
			} else if (62 == number) { // 银联被扫授权码
				/*
				 * 银联授权码全部都是以62开头*/
				flag = YLPAY;
			} else {
				logger.info ("被扫授权码不明，请查阅官方文档进行验证");
				flag = "";
			}			
			return flag;
	}

}
