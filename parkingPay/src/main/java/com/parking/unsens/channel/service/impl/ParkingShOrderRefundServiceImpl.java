package com.parking.unsens.channel.service.impl;

import java.util.Map;

import javax.inject.Inject;

import com.parking.dtosh.*;
import com.parking.service.PrParkingUnionPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.dto.OrderRefundReq;
import com.parking.dto.OrderRefundRsp;
import com.parking.service.InvokeInteService;
import com.parking.service.ParkingRefundOrderService;
import com.parking.unsens.channel.service.ParkingShOrderRefundService;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;
import com.parking.util.XmlUtil;
/**

 *  上海银联金融事业部智慧停车平台 退款 API相关接口 实现
 */

@Service("parkingShOrderRefundService")
public class ParkingShOrderRefundServiceImpl implements ParkingShOrderRefundService {
	private static final Logger logger = LoggerFactory.getLogger(ParkingShOrderRefundServiceImpl.class);
	@Autowired
	private PrParkingUnionPayService prParkingUnionPayService;
	@Inject
	private InvokeInteService invokeInteService;
	@Inject
	private ParkingRefundOrderService parkingRefundOrderService;

		
	/**
	 * (non-Javadoc)  退款
	 * @see ParkingShOrderRefundService#()
	 */
	@Override
	public RefundBillRsp refundBill(RefundBillReq mreq) throws Exception {
		logger.info("进入退款服务--------refundBill");
		RefundBillRsp  mrsp = new RefundBillRsp();
		mrsp.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setSerialNumber(mreq.getSerialNumber());
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		
		//参数检查
		if (checkParamsIsNull(mreq) < 0) {
			mrsp.setRespCode(RespUtil.noResult);
			mrsp.setRespDesc(CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
			return mrsp;
		}
		
		mreq.setService(CommEnum.UNIONPAY_SH_REFUND_BILL.getRspCode()); //退款服务
		String xmlRefundReq = XmlUtil.ObjToXml(mreq, RefundBillReq.class);
	    logger.info("组装报文退款请求报文:" +xmlRefundReq);
		String callResp = prParkingUnionPayService.parkingUnionPayCallCenterSync(xmlRefundReq);
		Map respMap = invokeInteService.parseResp(callResp);
		if (respMap.size()>0) {
			if (StringUtil.checkStringsEqual(CommEnum.TAG_SUECCESS.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				String xmlRespMsg = (String) respMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
				logger.info("智慧平台退款返回响应报文:" +xmlRespMsg);
				RefundBillRsp refundBillRsp =  (RefundBillRsp) XmlUtil.XmlToObj(xmlRespMsg, RefundBillRsp.class);
				if (StringUtil.checkStringsEqual(RespUtil.successCode, refundBillRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), refundBillRsp.getResultCode())) {
					mrsp.setOrderId(refundBillRsp.getOrderId()); //商户订单
					mrsp.setUpRefundId(refundBillRsp.getUpRefundId());//渠道退款订单号
					mrsp.setRespCode(RespUtil.successCode);
					mrsp.setRespDesc("退款申请提交成功");
					mrsp.setResultCode(refundBillRsp.getResultCode());
				} else {
					mrsp.setRespCode(refundBillRsp.getRespCode());//返回码
					mrsp.setRespDesc(refundBillRsp.getRespDesc());//返回描述
					mrsp.setResultCode(refundBillRsp.getResultCode());
				}
			} else if (StringUtil.checkStringsEqual(CommEnum.TAG_FAIL.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				String respCode = (String) respMap.get(CommEnum.RESP_CODE.getRspCode());
				String respDesc = (String) respMap.get(CommEnum.RESP_DESC.getRspCode());
				logger.info("退款申请返回码:"+respCode +"|" + respDesc);
				mrsp.setRespCode(respCode);
				mrsp.setRespDesc(respDesc);
			}
		}
		
		//退款申请提交成功 才能去查询   查询退款需上送智慧平台退款订单号
		if (StringUtil.checkStringsEqual(RespUtil.successCode, mrsp.getRespCode())) {
			RefundStatusReq  refundStatusReq  = new RefundStatusReq();
			refundStatusReq.setAppId(mreq.getAppId()); //商户应用代码
			refundStatusReq.setOrderId(mreq.getOrderId()); //商户支付是退款订单号
			refundStatusReq.setUpRefundId(mrsp.getUpRefundId()); //智慧平台退款订单号
			refundStatusReq.setPrivateKey(mreq.getPrivateKey());
			RefundStatusRsp refundStatusRsp  = queryRefundBillStatus(refundStatusReq);
			if (null != refundStatusRsp) {
				if (StringUtil.checkStringsEqual(RespUtil.successCode, refundStatusRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_4.getRspCode(), refundStatusRsp.getPayStatus())) {
					mrsp.setOrderId(refundStatusRsp.getOrderId()); //商户订单
					mrsp.setUpRefundId(refundStatusRsp.getUpRefundId());//渠道退款订单号 智慧平台分配
					mrsp.setRespCode(RespUtil.successCode);
					mrsp.setRespDesc("退款成功");
					mrsp.setResultCode(refundStatusRsp.getResultCode());
				} else if (StringUtil.checkStringsEqual(RespUtil.successCode, refundStatusRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_3.getRspCode(), refundStatusRsp.getPayStatus())) {
					mrsp.setOrderId(refundStatusRsp.getOrderId()); //商户订单
					mrsp.setUpRefundId(refundStatusRsp.getUpRefundId());//渠道退款订单号 智慧平台分配
					mrsp.setRespCode(RespUtil.successCode);
					mrsp.setRespDesc("退款中");
					mrsp.setResultCode(refundStatusRsp.getResultCode());
				} else {
					mrsp.setRespCode(RespUtil.payFail); //失败
					mrsp.setRespDesc(refundStatusRsp.getRespDesc());
				}
			} else {
				mrsp.setRespCode(RespUtil.CLIENT_INFO_ERR);
				mrsp.setRespDesc("退款查询系统调用异常");
			}
		}
		
		return mrsp;
	}
    private int checkParamsIsNull(RefundBillReq mreq) {
    	if (StringUtil.checkNullString(mreq.getPlateNumber()) || 
    			StringUtil.checkNullString(mreq.getRefundAmount()) ||
    			StringUtil.checkNullString(mreq.getRefundId()) || 
    			StringUtil.checkNullString(mreq.getOrderId()) ) {
    		return -1;
    	}
		return 1;
    }
	/**
	 * (non-Javadoc)  查询退款状态
	 * @see ParkingShOrderRefundService#()
	 */
	@Override
	public RefundStatusRsp queryRefundBillStatus(RefundStatusReq mreq)throws Exception {
		logger.info("进入查询退款状态----queryRefundBillStatus");
		RefundStatusRsp  mrsp = new RefundStatusRsp();
		mrsp.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		mrsp.setSerialNumber(mreq.getSerialNumber());
		mrsp.setTradeStat(CommEnum.TRADE_STAT_OK.getRspCode());
		
		if (checkQueryParamsIsNull(mreq) < 0) {
			mrsp.setRespCode(RespUtil.noResult);
			mrsp.setRespDesc("查询退款参数缺失");
			return mrsp;
		}
		//组装报文数据 查询
		mreq.setService(CommEnum.UNIONPAY_SH_GET_REFUND_STATUS.getRspCode()); //查询退款状态
		String qeryRefundXml = XmlUtil.ObjToXml(mreq, RefundStatusReq.class);
		String callResp = prParkingUnionPayService.parkingUnionPayCallCenterSync(qeryRefundXml);
		Map respMap = invokeInteService.parseResp(callResp);
		if (respMap.size()>0) {
			if (StringUtil.checkStringsEqual(CommEnum.TAG_SUECCESS.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				String xmlQueryRefundStata = (String) respMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
				RefundStatusRsp refundStatusRsp = (RefundStatusRsp) XmlUtil.XmlToObj(xmlQueryRefundStata, RefundStatusRsp.class);
				if (StringUtil.checkStringsEqual(RespUtil.successCode, refundStatusRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), refundStatusRsp.getResultCode())) {
					mrsp.setRefundAmount(refundStatusRsp.getRefundAmount());
					mrsp.setOrderId(refundStatusRsp.getOrderId());//商户方订单支付时
					mrsp.setUpRefundId(refundStatusRsp.getUpRefundId());//由智慧通行平台生成的退款流水号
					mrsp.setOrderDate(refundStatusRsp.getOrderDate());//订单支付时间。UTC时间。例如：2018-03-16T16:06:05Z
					mrsp.setPayStatus(refundStatusRsp.getPayStatus());// 订单支付状态
					mrsp.setRespCode(RespUtil.successCode);
					mrsp.setRespDesc(refundStatusRsp.getDescription());
					mrsp.setResultCode(refundStatusRsp.getResultCode());
				} else {
					mrsp.setRespCode(refundStatusRsp.getRespCode());
					mrsp.setRespDesc(refundStatusRsp.getRespDesc());
					mrsp.setResultCode(refundStatusRsp.getResultCode());
				}
			} else if (StringUtil.checkStringsEqual(CommEnum.TAG_FAIL.getRspCode(),  respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
				String respCode = (String) respMap.get(CommEnum.RESP_CODE.getRspCode());
				String respDesc = (String) respMap.get(CommEnum.RESP_DESC.getRspCode());
				logger.info("返回码:"+respCode +"|" + respDesc);
				mrsp.setRespCode(respCode);
				mrsp.setRespDesc(respDesc);
			}
		}
		return mrsp;
	}
    
	//检查参数
	private int checkQueryParamsIsNull(RefundStatusReq mreq) {
		if (StringUtil.checkNullString(mreq.getOrderId()) ||
				StringUtil.checkNullString(mreq.getAppId()) || 
				StringUtil.checkNullString(mreq.getUpRefundId())) {
			return -1;
		}
		return 1;
	}
	/**
	 * (non-Javadoc) 通知退款结果
	 * @see ParkingShOrderRefundService#()
	 */
	@Override
	public RefundResultRsp notifyRefundResult(RefundResultReq mreq)throws Exception {
       logger.info("进入退款通知结果------notifyRefundResult");
       RefundResultRsp mrsp = new RefundResultRsp();
       mrsp.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
       mrsp.setRespCode(RespUtil.codeError);
       mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
       mrsp.setTradeStat(CommEnum.TRADE_STAT_OK.getRspCode());
       
       //参数检查
       if (checkParamsIsNull(mreq) <0) {
    	   mrsp.setRespCode(CommEnum.UNIONPAY_SH_RESULT_CODE_1001.getRspCode());
    	   mrsp.setRespDesc(CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
    	   return mrsp;
       }
       OrderRefundReq orderRefundReq = new OrderRefundReq();
       orderRefundReq.setOrderId(mreq.getOrderId());
       orderRefundReq.setUpRefundId(mreq.getUpRefundId());//渠道方退款订单号
       orderRefundReq.setPayStatus(mreq.getPayStatus());//渠道方当前交易状态
       orderRefundReq.setPayTime(mreq.getOrderDate());//订单支付时间
       OrderRefundRsp orderRefundRsp =  parkingRefundOrderService.notifyRefundAResult(orderRefundReq);
       if (null != orderRefundRsp && StringUtil.checkStringsEqual(RespUtil.successCode, orderRefundRsp.getRespCode())) {
    	   mrsp.setRespCode(RespUtil.successCode);
    	   mrsp.setRespDesc("退款通知成功");
       } else {
    	   mrsp.setRespCode(CommEnum.UNIONPAY_SH_RESULT_CODE_1003.getRspCode());
    	   mrsp.setRespDesc("退款通知系统异常");
       }
		return mrsp;
	}
    
	//参数检查
	private int checkParamsIsNull(RefundResultReq mreq) {
		if (StringUtil.checkNullString(mreq.getOrderId()) || 
				StringUtil.checkNullString(mreq.getSyncId()) || 
				StringUtil.checkNullString(mreq.getUpRefundId()) || 
				StringUtil.checkNullString(mreq.getRefundAmount()) || 
				StringUtil.checkNullString(mreq.getOrderDate()) || 
				StringUtil.checkNullString(mreq.getPayStatus()) || 
				StringUtil.checkNullString(mreq.getAppId())){
			 return -1;
		}
		return 1;
	}
}

