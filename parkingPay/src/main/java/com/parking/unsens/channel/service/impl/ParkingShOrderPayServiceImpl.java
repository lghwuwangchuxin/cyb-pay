package com.parking.unsens.channel.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.inject.Inject;
import com.parking.dtosh.*;
import com.parking.service.PrParkingUnionPayService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parking.dao.ParkingChannelShParamsConfigDao;
import com.parking.domain.ParkingChannelShParamsConfig;
import com.parking.dto.ParkingPreOrderQueryReq;
import com.parking.dto.ParkingPreOrderQueryRsp;
import com.parking.dto.PayNotifyReq;
import com.parking.dto.PayNotifyRsp;
import com.parking.service.InvokeInteService;
import com.parking.service.ParkingOrderService;
import com.parking.unsens.channel.service.ParkingShOrderPayService;
import com.parking.util.CommEnum;
import com.parking.util.ConfigUtil;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;
import com.parking.util.Utility;
import com.parking.util.XmlUtil;

/**
 * 上海智慧平台账单支付 相关API 
 */

@Service("parkingShOrderPayService")
public class ParkingShOrderPayServiceImpl implements ParkingShOrderPayService {
	private static final Logger logger = LoggerFactory.getLogger(ParkingShOrderPayServiceImpl.class);
	@Autowired
	private PrParkingUnionPayService prParkingUnionPayService;
	@Inject
	private InvokeInteService invokeInteService;
	@Inject
	private ParkingOrderService parkingOrderService;
	@Inject
	private ParkingChannelShParamsConfigDao parkingChannelShParamsConfigDao;

	
	//订单支付参数检查
	private int chekcReqParamsIsNull(PayBillReq mreq){
		if (StringUtil.checkNullString(mreq.getOrderId()) || 
				StringUtil.checkNullString(mreq.getSyncId()) || 
				StringUtil.checkNullString(mreq.getAppId()) || 
				StringUtil.checkNullString(mreq.getParkId()) || 
				StringUtil.checkNullString(mreq.getEndTime()) || 
				StringUtil.checkNullString(mreq.getStartTime()) || 
				StringUtil.checkNullString(mreq.getIndustryCode()) || 
				StringUtil.checkNullString(mreq.getPlateNumber()) || 
				StringUtil.checkNullString(mreq.getPayAmount()) || 
				StringUtil.checkNullString(mreq.getServiceAmount()) || 
				StringUtil.checkNullString(mreq.getParkName()) || 
				StringUtil.checkNullString(mreq.getPayCallback()) || 
				StringUtil.checkNullString(mreq.getAccSplitData()) || 
				StringUtil.checkNullString(mreq.getOrderDate())) {
			 return -1;
		}
		return 1;
	}
	/**
	 * (non-Javadoc)  账单支付
	 * @see ParkingShOrderPayService#()
	 */
	@Override
	public PayBillRsp tradePayBill(PayBillReq mreq) throws Exception {
		logger.info("进入上海智慧平台账单支付下单服务------tradePayBill");
		PayBillRsp  mrsp = new PayBillRsp();
		mrsp.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
		mrsp.setSerialNumber(mreq.getSerialNumber());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		mrsp.setTradeStat(CommEnum.TRADE_STAT_OK.getRspCode());
		
		//参数检查
		if (chekcReqParamsIsNull(mreq) < 0) {
			mrsp.setRespCode(RespUtil.noResult);
			mrsp.setRespDesc(CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
			return mrsp;
		}
		
	    //组装xml报文
		mreq.setService(CommEnum.UNIPNPAY_SH_PUSH_PAY_BILL.getRspCode()); //订单下单
		String xmlPayReq = XmlUtil.ObjToXml(mreq, PayBillReq.class);
		logger.info("组装智慧平台账单下单报文请求:" +xmlPayReq);
		String callResp = prParkingUnionPayService.parkingUnionPayCallCenterSync(xmlPayReq);
		Map respMap = invokeInteService.parseResp(callResp);
		if (respMap.size() >0) {
			if (StringUtil.checkStringsEqual(CommEnum.TAG_SUECCESS.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				String xmlPayRespMsg = (String) respMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
				logger.info("智慧平台下单响应xmlMsg:" +xmlPayRespMsg);
				PayBillRsp payBillRsp = (PayBillRsp) XmlUtil.XmlToObj(xmlPayRespMsg, PayBillRsp.class);
				if (StringUtil.checkStringsEqual(RespUtil.successCode, payBillRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), payBillRsp.getResultCode())) {
					mrsp.setRespCode(RespUtil.successCode); //下单成功
					mrsp.setRespDesc(payBillRsp.getDescription() +"[" +payBillRsp.getResultCode() +"]");
					mrsp.setDescription(payBillRsp.getDescription());
				} else {
					mrsp.setRespCode(RespUtil.payFail); //置失败  ，还是要通知和查询为准 不排除 超时情况
					mrsp.setRespDesc(payBillRsp.getDescription() + "["+payBillRsp.getResultCode()+"]"); //渠道返回描述
					mrsp.setDescription(payBillRsp.getResultCode()); //渠道返回码
				}
			} else if (StringUtil.checkStringsEqual(CommEnum.TAG_FAIL.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				String respCode = (String) respMap.get(CommEnum.RESP_CODE.getRspCode());
				String respDesc = (String) respMap.get(CommEnum.RESP_DESC.getRspCode());
				logger.info("上海金融账单下单返回码:"  + respCode +"|" +respDesc);
				mrsp.setRespCode(RespUtil.payFail); //置失败  ，还是要通知和查询为准 不排除 超时情况
				mrsp.setRespDesc(respDesc);
			}
		}
		
		//无论成功与否都发起查询一次 ,不排除意外
		PayStatusReq payStatusReq = new PayStatusReq();
		payStatusReq.setOrderId(mreq.getOrderId());//订单号
		payStatusReq.setAppId(mreq.getAppId()); //商户应用代码
		payStatusReq.setPrivateKey(mreq.getPrivateKey());//密钥
		PayStatusRsp payStatusRsp = queryPayStatusBill(payStatusReq);
		if (null != payStatusRsp) { //
			if (StringUtil.checkStringsEqual(RespUtil.successCode, payStatusRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), payStatusRsp.getResultCode())) {
				if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_0.getRspCode(), payStatusRsp.getPayStatus())) {
					mrsp.setRespDesc(RespUtil.successCode); //支付成功
					mrsp.setRespDesc(payStatusRsp.getDescription() +"[" +payStatusRsp.getResultCode() +"]");
					mrsp.setResultCode(payStatusRsp.getResultCode());
				} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_1.getRspCode(), payStatusRsp.getPayStatus())) {
					//支付中 先待支付
					mrsp.setRespDesc(RespUtil.successCode);
					mrsp.setRespDesc(payStatusRsp.getDescription() +"[" +payStatusRsp.getResultCode() +"]");
					mrsp.setResultCode(payStatusRsp.getPayStatus());
				} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_2.getRspCode(), payStatusRsp.getPayStatus())) {
					mrsp.setRespCode(RespUtil.payFail); //支付失败
					mrsp.setRespDesc(payStatusRsp.getRespDesc());
					mrsp.setResultCode(payStatusRsp.getPayStatus());
				}
			} else {
				mrsp.setRespCode(payStatusRsp.getRespCode());
				mrsp.setRespDesc(payStatusRsp.getRespDesc());
				mrsp.setResultCode(CommEnum.UNIONPAY_SH_PAY_STATUS_2.getRspCode());
			}
		} else {
			mrsp.setRespCode(RespUtil.CLIENT_INFO_ERR);
			mrsp.setRespDesc("调用查询下单结果异常");
			mrsp.setResultCode(CommEnum.UNIONPAY_SH_PAY_STATUS_2.getRspCode());
		}
		return mrsp;
	}
    
	/**
	 * (non-Javadoc) 账单结果查询
	 * @see ParkingShOrderPayService#()
	 */
	@Override
	public PayStatusRsp queryPayStatusBill(PayStatusReq mreq) throws Exception {
		logger.info("进入渠道智慧平台账单结果查询服务----queryPayStatusBill");
		PayStatusRsp  mrsp = new PayStatusRsp();
		mrsp.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
		mrsp.setSerialNumber(mreq.getSerialNumber());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		mrsp.setTradeStat(CommEnum.TRADE_STAT_OK.getRspCode());
		
		//参数检查
		if (StringUtil.checkNullString(mreq.getOrderId())) {
			mrsp.setRespCode(RespUtil.noResult);
			mrsp.setRespDesc(CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
			return mrsp;
		}
		
		//组装账单查询报文
		mreq.setService(CommEnum.UNIONPAY_SH_GET_PAY_STATUS.getRspCode()); //接口查询支付订单状态
		String xmlQueryOrder = XmlUtil.ObjToXml(mreq, PayStatusReq.class);
		logger.info("组装智慧平台账单查询报文请求:" +xmlQueryOrder);
		String callResp = prParkingUnionPayService.parkingUnionPayCallCenterSync(xmlQueryOrder);
		Map respMap = invokeInteService.parseResp(callResp);
		PayStatusRsp payStatusRsp = null;
		if (respMap.size()>0) {
			if (StringUtil.checkStringsEqual(CommEnum.TAG_SUECCESS.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				String xmlRspMsg = (String) respMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
				logger.info("订单查询返回xmlMsg" +xmlRspMsg);
				payStatusRsp = (PayStatusRsp) XmlUtil.XmlToObj(xmlRspMsg, PayStatusRsp.class);
			} else if (StringUtil.checkStringsEqual(CommEnum.TAG_FAIL.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				String respCode = (String)respMap.get(CommEnum.RESP_CODE.getRspCode());
				String respDesc = (String)respMap.get(CommEnum.RESP_DESC.getRspCode());
				logger.info("订单查询返回码:" +respCode +"|" +respDesc);
				mrsp.setRespCode(respCode);
				mrsp.setRespDesc(respDesc);
				return mrsp;
			}
		}
	    if (null != payStatusRsp) {
	    	if (StringUtil.checkStringsEqual(RespUtil.successCode, payStatusRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), payStatusRsp.getResultCode())) {
				mrsp.setOrderId(payStatusRsp.getOrderId()); //订单号
				mrsp.setUpOrderId(payStatusRsp.getUpOrderId());//全渠道订单号
				mrsp.setPayAmount(payStatusRsp.getPayAmount()); //支付金额 分为单位
				mrsp.setOrderDate(payStatusRsp.getOrderDate());//订单支付时间utc时间
				mrsp.setPayStatus(payStatusRsp.getPayStatus());// 0: 支付成功;1: 支付中;2: 支付失败;3: 退款中;4: 退款成功;5: 退款失败
				mrsp.setDescription(payStatusRsp.getDescription());//返回描述
				mrsp.setDiscountAmount(payStatusRsp.getDiscountAmount());//优惠金额 分为单位
				mrsp.setRespCode(RespUtil.successCode);
				mrsp.setRespDesc(payStatusRsp.getDescription()+"[" +payStatusRsp.getResultCode()+"]");
				mrsp.setResultCode(payStatusRsp.getResultCode());
			} else {
				mrsp.setRespCode(RespUtil.CLIENT_INFO_ERR);
				mrsp.setRespDesc(payStatusRsp.getDescription());
			}
	    } else {
	    	mrsp.setRespCode(RespUtil.CLIENT_INFO_ERR);
			mrsp.setRespDesc("系统调用异常");
	    }
		return mrsp;
	}
    
	//通知参数检查
	private int checkNotifyParamsIsNull(PayResultReq mreq){
		if (StringUtil.checkNullString(mreq.getOrderId()) || 
				StringUtil.checkNullString(mreq.getSyncId()) || 
				StringUtil.checkNullString(mreq.getUpOrderId()) || 
				StringUtil.checkNullString(mreq.getPayAmount()) || 
				StringUtil.checkNullString(mreq.getOrderDate()) || 
				StringUtil.checkNullString(mreq.getPayStatus()) 
				) {
			return -1;
		}
		return 1;
	}
	/**
	 * (non-Javadoc)  通知支付账单 结果
	 * @see ParkingShOrderPayService#()
	 */
	@Override
	public PayResultRsp notifyPayAResult(PayResultReq mreq) throws Exception {
		logger.info("进入接收通知支付账单结果-------------------------notifyPayAResult");
		PayResultRsp  mrsp = new PayResultRsp();
		mrsp.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
		mrsp.setSerialNumber(mreq.getSerialNumber());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		mrsp.setTradeStat(CommEnum.TRADE_STAT_OK.getRspCode());
		
		//参数效检
		if (checkNotifyParamsIsNull(mreq) < 0) {
			mrsp.setRespCode(CommEnum.UNIONPAY_SH_RESULT_CODE_1001.getRspCode());
			mrsp.setRespDesc("必选参数为空");
			return mrsp;
		}
		//接收数据内部统一通知服务类
		PayNotifyReq  payNotifyReq = new PayNotifyReq();
		payNotifyReq.setOrderId(mreq.getOrderId()); //商户订单号
		payNotifyReq.setPayId(mreq.getUpOrderId());//全渠道订单号
		payNotifyReq.setTradeCode(mreq.getPayStatus()); //支付状态
		payNotifyReq.setPayAmt(mreq.getPayAmount());//订单金额  分为单位
		payNotifyReq.setResType(CommEnum.RES_TYPE_AUTO_PAY.getRspCode()); //自助停车 AutoPay
		payNotifyReq.setPayType(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode()); // 上海金融智慧平台订单支付类型 23
		if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_0.getRspCode(), mreq.getPayStatus())) {
			payNotifyReq.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
			payNotifyReq.setTradeDesc(CommEnum.PAY_PAID_CODE.getRspMsg());
		} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_1.getRspCode(), mreq.getPayStatus())) {
			payNotifyReq.setTradeStatus(CommEnum.PAY_UN_PAID.getRspCode()); //待支付
			payNotifyReq.setTradeDesc("支付中");
		} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_2.getRspCode(), mreq.getPayStatus())) {
			payNotifyReq.setTradeStatus(CommEnum.PAY_PAYFILE_CODE.getRspCode()); //支付失败
			payNotifyReq.setTradeDesc(CommEnum.PAY_PAYFILE_CODE.getRspMsg()); //描述
		} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_3.getRspCode(), mreq.getPayStatus())) {
			payNotifyReq.setTradeStatus(CommEnum.REFUNDING.getRspCode()); //退款中
		} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_4.getRspCode(), mreq.getPayStatus())) { //退款成功
			payNotifyReq.setTradeStatus(CommEnum.REFUNDED.getRspCode());
		} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_5.getRspCode(), mreq.getPayStatus())) { //退款失败
			payNotifyReq.setTradeStatus(CommEnum.REFUNDED_FAIL.getRspCode());
		}
		//无论支付成功还是失败  都进行通知
		PayNotifyRsp payNotifyRsp = parkingOrderService.notifyPayOrderAResult(payNotifyReq);
		if (StringUtil.checkStringsEqual(RespUtil.successCode, payNotifyRsp.getRespCode()) || StringUtil.checkStringsEqual(RespUtil.payFail,payNotifyRsp.getRespCode() )) {
			mrsp.setRespCode(RespUtil.successCode);
			mrsp.setRespDesc("通知成功");
		} else {
			logger.info("此交易内部通知失败订单号:-------[" +mreq.getOrderId()+"]");
			mrsp.setRespCode(CommEnum.UNIONPAY_SH_RESULT_CODE_1003.getRspCode());
			mrsp.setRespDesc(payNotifyRsp.getRespDesc());
		}
		return mrsp;
	}

	/**
	 * (non-Javadoc)  临时车预缴费订单查询
	 * @see ParkingShOrderPayService#()
	 */
	@Override
	public OrderUnpayRsp getPrePayBill(OrderUnpayReq mreq) throws Exception {
		logger.info("进入预缴费订单查询-----getPrePayBill");
		OrderUnpayRsp mrsp = new OrderUnpayRsp();
		mrsp.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		mrsp.setTradeStat(CommEnum.TRADE_STAT_OK.getRspCode());
		
		//参数检查
		if (checkParamsIsNull(mreq) < 0) {
			mrsp.setRespCode(RespUtil.noResult);
			mrsp.setRespDesc(CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
			return mrsp;
		}
		
		//本地受理时间
		Date date = new Date();//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String acceptDate = sdf.format(date);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String orderDate = sdf2.format(date);
		ParkingPreOrderQueryReq parkingPreOrderQueryReq = new ParkingPreOrderQueryReq();
		parkingPreOrderQueryReq.setCarPlate(mreq.getPlateNumber());//车牌
		parkingPreOrderQueryReq.setFlagToken(CommEnum.FLAG_TOKEN0.getRspCode());
		parkingPreOrderQueryReq.setAppCode(CommEnum.APP_CODE.getRspCode());
		parkingPreOrderQueryReq.setSerialNumber(acceptDate); //内部请求流水号
		parkingPreOrderQueryReq.setMchntId(CommEnum.UNIONPAY_GZBD_SH_MCHNT_ID.getRspCode());//内部传商户号
		ParkingPreOrderQueryRsp parkingPreOrderQueryRsp = parkingOrderService.queryParkingPrePayOrder(parkingPreOrderQueryReq);
		if (null != parkingPreOrderQueryRsp) {
			if (StringUtil.checkStringsEqual(RespUtil.successCode, parkingPreOrderQueryRsp.getRespCode())) {
				ParkingChannelShParamsConfig shParamsConfig = new ParkingChannelShParamsConfig();
				shParamsConfig.setParkId(parkingPreOrderQueryRsp.getParkId());
				shParamsConfig.setStates("1");
				shParamsConfig = parkingChannelShParamsConfigDao.selectParkingChannelShParamsConfigById(shParamsConfig);
				if (null == shParamsConfig) {
					mrsp.setRespCode(RespUtil.noResult);
					mrsp.setRespDesc("此停车场暂未配置");
					return mrsp;
				}
				//JSON 参数 解析
				JSONObject jsonDeill = JSONObject.fromObject("");
				mrsp.setSyncId(parkingPreOrderQueryRsp.getTradeId());//唯一序列号
				mrsp.setAppId(mreq.getAppId());//商户应用代码 智慧平台 渠道分配给商户
				mrsp.setIndustryCode(shParamsConfig.getMchntNo());//扣费商户号
				mrsp.setPlateNumber(parkingPreOrderQueryRsp.getResId());//车牌号
				mrsp.setOrderId(parkingPreOrderQueryRsp.getOrderId());//订单号
				mrsp.setPayAmount(parkingPreOrderQueryRsp.getPayAmt());//支付金额
				mrsp.setServiceAmount(parkingPreOrderQueryRsp.getTxnAmt());//订单金额
				mrsp.setOrderDate(Utility.getDataToUtc8Del(orderDate)); //订单时间 UTC 订单生成时间。UTC时间。例如：2018-03-16T16:06:05Z
				mrsp.setStartTime(Utility.getDataToUtc8Del(parkingPreOrderQueryRsp.getInTime())); //UTC入场时间
				mrsp.setEndTime(Utility.getDataToUtc8Del(orderDate));//  暂用订单生成时间，出场时间
				mrsp.setParkId(parkingPreOrderQueryRsp.getParkId());//停车场id
				mrsp.setParkName(parkingPreOrderQueryRsp.getParkName());//停车场名称
				mrsp.setPayCallback(ConfigUtil.getValue("UNIONPAY_SH_BACK_NOTIFY_URL")); //支付完成 后推送地址
				jsonDeill = new JSONObject();
				jsonDeill.put(CommEnum.ACC_SPLIT_TYPE.getRspCode(), shParamsConfig.getAccSplitType());
				jsonDeill.put(CommEnum.ACC_SPLIT_RULE_ID.getRspCode(), shParamsConfig.getAccSplitRuleId());
				mrsp.setAccSplitData(jsonDeill.toString());
				mrsp.setRespCode(RespUtil.successCode);
				mrsp.setRespDesc("交易成功");
			} else {
				mrsp.setRespCode(CommEnum.UNIONPAY_SH_RESULT_CODE_1003.getRspCode());
				mrsp.setRespDesc(parkingPreOrderQueryRsp.getRespDesc());
			}
		} else {
			mrsp.setRespCode(CommEnum.UNIONPAY_SH_RESULT_CODE_1003.getRspCode());
			mrsp.setRespDesc("系统调用异常");
		}
		return mrsp;
	}
	
	//参数检查
	private int checkParamsIsNull(OrderUnpayReq mreq) {
		if (StringUtil.checkNullString(mreq.getAppId()) || StringUtil.checkNullString(mreq.getPlateNumber())) return -1;
		return 1;
	}
    
}

