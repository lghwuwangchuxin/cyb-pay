package com.parking.service.impl;

import com.parking.dao.ParkingChannelShParamsConfigDao;
import com.parking.dao.ParkingTradeOrderDao;
import com.parking.domain.*;
import com.parking.dto.ParkingTradeOrderQueryReq;
import com.parking.dto.ParkingTradeOrderQueryRsp;
import com.parking.dto.icbc.CommonRsp;
import com.parking.dtosh.PayStatusReq;
import com.parking.dtosh.PayStatusRsp;
import com.parking.service.ParkingChannelConfQueryService;
import com.parking.service.ParkingQueryOrderService;
import com.parking.unsens.channel.service.*;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.sql.SQLException;
/**
 *  渠道公共 订单查询
 *  
 */

@Service("parkingQueryOrderService")
public class ParkingQueryOrderServiceImpl implements ParkingQueryOrderService {
	private static final Logger logger = LoggerFactory.getLogger(ParkingQueryOrderServiceImpl.class);
	@Inject
	private ParkingTradeOrderDao tradeOrderDao;
	@Inject
	private ParkingShOrderPayService parkingShOrderPayService;
	@Inject
	private ParkingChannelShParamsConfigDao parkingChannelShParamsConfigDao;
	@Inject
	private ParkingChannelConfQueryService parkingChannelConfQueryService;
	@Inject
	private ParkingIcbcOrderService parkingIcbcOrderService;

	/**
	 * (non-Javadoc)  公共渠道订单订单查询
	 * @see com.parking.service.ParkingQueryOrderService#queryParkingChannelTradeOrder(ParkingTradeOrderQueryReq)
	 */
	@Override
	public ParkingTradeOrderQueryRsp queryParkingChannelTradeOrder(ParkingTradeOrderQueryReq mreq) throws Exception{
		logger.info("进入公共渠道订单查询服务---------------queryParkingChannelTradeOrder");
		ParkingTradeOrderQueryRsp mrsp = new ParkingTradeOrderQueryRsp();
		mrsp = (ParkingTradeOrderQueryRsp) CommBeanCopyUtil.beanCopy(mrsp);
		
		//参数检查
		if (checkParamsIsNull(mreq) <0) {
			setRspParams(mrsp, RespUtil.noResult, CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
			return mrsp;
		}
		
		//检查订单是否存在
		ParkingTradeOrder tradeOrder = new ParkingTradeOrder();
		tradeOrder.setOrderId(mreq.getOrderId());
		tradeOrder = tradeOrderDao.selectParkingOrderById(tradeOrder);
		if (null == tradeOrder) {
			setRspParams(mrsp, RespUtil.noResult, "查无此交易，请重新确认此笔订单["+mreq.getOrderId()+"]");
			return mrsp;
		}
		
		//渠道路由配置选择
		if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), tradeOrder.getPayType())) {
			ParkingChannelShParamsConfig shParamsConfig = findParkingChannelShParamsConfig(tradeOrder.getParkId());
			if (null == shParamsConfig) {
				setRspParams(mrsp, RespUtil.noResult, "未配置渠道路由，请确认配置");
				return mrsp;
			}
			
			PayStatusRsp payStatusRsp = queryShPayStatusBill(mreq, shParamsConfig);
			if (null != payStatusRsp) {
				if (StringUtil.checkStringsEqual(RespUtil.successCode, payStatusRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), payStatusRsp.getResultCode())) {
					setShPayStatusRsp(mrsp, payStatusRsp);
					setRspParams(mrsp, RespUtil.successCode, payStatusRsp.getDescription()); //渠道返回描述
				} else {
					setRspParams(mrsp, payStatusRsp.getRespCode(), payStatusRsp.getDescription()); //渠道返回描述
				}
			} else {
				setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, "查询调用异常");
			}
		}  else if (StringUtil.checkStringsEqual(CommEnum.ICBC_PAY_CHANNEL.getRspCode(), tradeOrder.getPayType())) {
			CommonRsp commonRsp  = queryIcbcOrder(mreq, tradeOrder);
			setIcbcQueryOrder(mrsp, commonRsp, mreq.getOrderId(), tradeOrder.getTxnAmt());
		} else {
			setRspParams(mrsp, RespUtil.noResult, "此笔订单暂找不到渠道路由,请核实");
		}
		return mrsp;
	}
	
	/**
	 * setIcbcQueryOrder: 设置 工行 查询 订单 返回
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param commonRsp
	 * @param  @param orderId    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setIcbcQueryOrder(ParkingTradeOrderQueryRsp mrsp,
			CommonRsp commonRsp, String orderId, String txnAmt) {
		if (null == commonRsp) {
			setRspParams(mrsp, RespUtil.timeOut, CommEnum.QUERY_FAIL_RESP_DESC.getRspMsg());
			return;
		}
		mrsp.setOrderId(orderId);
		mrsp.setTradeStatus(getIcbcTradeStatus(commonRsp.getMessagecode()));
		mrsp.setTxnAmt(txnAmt);
		setRspParams(mrsp, RespUtil.successCode, CommEnum.QUERY_SUCCESS_RESP_DESC.getRspMsg());
	}

	/**
	 * getIcbcTradeStatus: 置工行 交易 状态 转化返回
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param commonRsp
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getIcbcTradeStatus(String messageCode) {
		if (null == messageCode) {
			return CommEnum.PAY_UN_PAID.getRspCode();
		}
		if (StringUtil.checkStringsEqual(CommIcbcEnum.ICBC_PAY_STATUS_20000.getRespCode(), messageCode)) {
			return CommEnum.PAY_PAID_CODE.getRspCode();
		}
		return CommEnum.PAY_UN_PAID.getRspCode();
	}

	/**
	 * queryIcbcOrder: 工行渠道 查询 订单 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mreq
	 * @param  @param tradeOrder
	 * @param  @return    设定文件
	 * @return CommonRsp    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private CommonRsp queryIcbcOrder(ParkingTradeOrderQueryReq mreq,
			ParkingTradeOrder tradeOrder) throws Exception {
		CommonRsp commonRsp = null;
		try {
			Object[] obj = {tradeOrder.getParkId(), tradeOrder.getMchntSysNumber(), tradeOrder.getCarPlate(), tradeOrder.getOutTime(), tradeOrder.getTimeLong(), tradeOrder.getTxnAmt(), tradeOrder.getInTime()};
			commonRsp = (CommonRsp) parkingIcbcOrderService.queryPayOrder(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return commonRsp;
		}
		return commonRsp;
	}


	
    /**
	 * setShPayStatusRsp: 上海 银联无感 订单 查询 返回 置值
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param payStatusRsp    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setShPayStatusRsp(ParkingTradeOrderQueryRsp mrsp,
			PayStatusRsp payStatusRsp) {
		mrsp.setOrderId(payStatusRsp.getOrderId()); //订单号
		mrsp.setPayAmt(payStatusRsp.getDiscountAmount());//
		mrsp.setTradeStatus(getShTradeStatus(payStatusRsp.getPayStatus())); 
	}
	/**
	 * queryShPayStatusBill: 组装 上海银联 金融事业部 无感订单 查询 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mreq
	 * @param  @param shParamsConfig
	 * @param  @return    设定文件
	 * @return PayStatusReq    DOM对象
     * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private PayStatusRsp queryShPayStatusBill(ParkingTradeOrderQueryReq mreq,
			ParkingChannelShParamsConfig shParamsConfig) throws Exception {
		PayStatusReq payStatusReq = new PayStatusReq();
		payStatusReq.setOrderId(mreq.getOrderId());//订单号
		payStatusReq.setAppId(shParamsConfig.getAppId());//商户应用代码
		payStatusReq.setPrivateKey(shParamsConfig.getSignKey()); //密钥
		PayStatusRsp payStatusRsp = parkingShOrderPayService.queryPayStatusBill(payStatusReq);
		return payStatusRsp;
	}
	/**
	 * findParkingChannelShParamsConfig 上海银联金融事业部 渠道 接入 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param parkId
	 * @param  @return    设定文件
	 * @return ParkingChannelShParamsConfig    DOM对象
     * @throws SQLException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private ParkingChannelShParamsConfig findParkingChannelShParamsConfig(
			String parkId) throws SQLException {
		ParkingChannelShParamsConfig shParamsConfig = new ParkingChannelShParamsConfig();
		shParamsConfig.setParkId(parkId);//停车场id
		shParamsConfig.setStates("1");
		shParamsConfig = parkingChannelShParamsConfigDao.selectParkingChannelShParamsConfigById(shParamsConfig);
		return shParamsConfig;
	}


	/**
	 * getShTradeStatus: 上海 金融 无感 查询 订单 置换订单 状态
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param payStatus
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getShTradeStatus(String payStatus) {
		String tradeStatus = "";
		if (StringUtil.checkStringsEqual("0", payStatus)) {
			tradeStatus = (CommEnum.PAY_PAID_CODE.getRspCode()); //支付成功
		} else if (StringUtil.checkStringsEqual("1", payStatus)) {
			tradeStatus = (CommEnum.PAY_PAYING_CODE.getRspCode());//支付中
		} else if (StringUtil.checkStringsEqual("2", payStatus)) {
			tradeStatus = (CommEnum.PAY_PAYFILE_CODE.getRspCode());//支付失败
		} else if (StringUtil.checkStringsEqual("3", payStatus)) {
			tradeStatus = (CommEnum.REFUNDING.getRspCode());//退款中
		} else if (StringUtil.checkStringsEqual("4", payStatus)) {
			tradeStatus = (CommEnum.REFUNDED.getRspCode());//退款成功
		} else if (StringUtil.checkStringsEqual("5", payStatus)) {
			tradeStatus = (CommEnum.REFUNDED_FAIL.getRspCode());//退款失败
		}
		return tradeStatus;
	}



	/**
	 * setRspParams 置 返回值 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param respCode
	 * @param  @param respDesc    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(ParkingTradeOrderQueryRsp mrsp, String respCode, String respDesc) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
	}

	private int checkParamsIsNull(ParkingTradeOrderQueryReq mreq) {
    	if (StringUtil.checkNullString(mreq.getOrderId())) return -1; 
		return 1;
    }
}

