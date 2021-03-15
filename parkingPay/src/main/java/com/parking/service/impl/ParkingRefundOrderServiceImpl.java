package com.parking.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import com.parking.dao.ParkingRefundTradeOrderDao;
import com.parking.domain.ParkingRefundTrade;
import com.parking.dtosh.RefundBillReq;
import com.parking.dtosh.RefundBillRsp;
import com.parking.dtosh.RefundStatusReq;
import com.parking.dtosh.RefundStatusRsp;
import com.parking.service.SeqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.dao.ParkingChannelAccessRouteConfigDao;
import com.parking.dao.ParkingChannelShParamsConfigDao;
import com.parking.dao.ParkingTradeOrderDao;
import com.parking.domain.ParkingChannelAccessRouteConfig;
import com.parking.domain.ParkingChannelShParamsConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.OrderRefundReq;
import com.parking.dto.OrderRefundRsp;
import com.parking.service.ParkingRefundOrderService;
import com.parking.unsens.channel.service.ParkingShOrderRefundService;
import com.parking.util.CommBeanCopyUtil;
import com.parking.util.CommEnum;
import com.parking.util.ConfigUtil;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;
/**

 * 公共退款统一服务
 */

@Service("parkingRefundOrderService")
public class ParkingRefundOrderServiceImpl implements ParkingRefundOrderService {
	private static final Logger logger = LoggerFactory.getLogger(ParkingRefundOrderServiceImpl.class);
	@Inject
	private ParkingChannelShParamsConfigDao parkingChannelShParamsConfigDao;
	@Inject
	private ParkingChannelAccessRouteConfigDao parkingChannelAccessRouteConfigDao;
	@Inject
	private ParkingTradeOrderDao parkingTradeOrderDao;
	@Inject
	private ParkingRefundTradeOrderDao refundTradeDao;
	@Inject
	private SeqService seqService;
	@Inject
	private ParkingShOrderRefundService parkingShOrderRefundService;

	// 统一处理台账数据
	private int uniDealData(Object... obj) throws Exception {
		int result = 0;
		for (int i = 0; i < obj.length; i++) {
			 if (obj[i] instanceof ParkingRefundTrade) {
				ParkingRefundTrade refundTrade = (ParkingRefundTrade) obj[i];
				if (refundTrade.getModifyTag().equals("I")) {
					result = refundTradeDao.insertRefundInfo(refundTrade);
				} else if (refundTrade.getModifyTag().equals("U")) {
					result = refundTradeDao.updateParkingRefund(refundTrade);
				}
			}
		}
		return result;
	}
	/** 
	 * (non-Javadoc)  退款服务
	 * @see com.parking.service.impl)
	 */
	@Override
	public OrderRefundRsp refundOrder(OrderRefundReq mreq) throws Exception {
		logger.info("进入退款服务-----------refundOrder");
		OrderRefundRsp  mrsp = new OrderRefundRsp();
		setInitRspParams(mrsp, mreq.getSerialNumber());
		boolean bResult = false;
		//检查参数
		if (chekcParamsIsNull(mreq) < 0) {
			setRspParams(mrsp, RespUtil.noResult, CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
			return mrsp;
		}
		
		//检查订单是否存在
		ParkingTradeOrder tradeOrder = findParkingOrderById(mreq.getOrderId());
		if (null == tradeOrder) {
			setRspParams(mrsp, RespUtil.noResult, "查无此订单号,请确认此订单["+mreq.getOrderId()+"]");
		    return mrsp;
		}
		// 检查退款条件
		if (checkTradeStatus(tradeOrder.getTradeStatus()) <0) {
			setRspParams(mrsp, RespUtil.noResult, "此订单,不容许退款["+mreq.getOrderId()+"]");
		    return mrsp;
		}
		
		List<ParkingRefundTrade> listRefundTrade = findParkingRefundTradeList(mreq.getOrderId());
		//查询退款订单表 渠道检查 多条数据存在 
		if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), tradeOrder.getPayType())) {
			//上海智慧平台值取一条数据
			if (null != listRefundTrade && listRefundTrade.size()>0 && StringUtil.checkStringsEqual(CommEnum.REFUNDED.getRspCode(), listRefundTrade.get(0).getRefundStatus())) {
				setRspParams(mrsp, RespUtil.noResult, "此笔订单已经退过款,订单不可重复退款["+mreq.getOrderId()+"]");
				return mrsp;
			}
		}  else {} // 其他渠道 情况不同 ，要针对分类处理,此处可以进行扩展其他
		
		//检查配置路由渠道
		List<ParkingChannelAccessRouteConfig> routeConfigList = findParkingChannelAccessRouteConfigByParkIdList(tradeOrder.getParkId());
		if (null == routeConfigList || routeConfigList.size() <= 0) {
			setRspParams(mrsp, RespUtil.noResult, "未配置停车场渠道路由配置");
			return mrsp;
		}
		
		//渠道路由分发上海智慧平台渠道
		ParkingChannelShParamsConfig shParamsConfig = null;
		if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), tradeOrder.getPayType())) {
			//参数检查配置
			shParamsConfig = findParkingChannelShParamsConfigById(tradeOrder.getParkId());
			if (null == shParamsConfig) {
				setRspParams(mrsp, RespUtil.noResult, "未配置渠道商户参数，请检查");
				return mrsp;
			} else 
				bResult = true;  
		}  else {
			setRspParams(mrsp, RespUtil.dberror, "此笔订订单不可退款,暂不明确退款渠道路由");
			return mrsp;
		}
		
		if (bResult) {
			//流水号
			String tradeId = seqService.getTradeSequenceId("TRADE_ID");
			//本地受理时间
			String acceptDate = getDate(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode());
			// 退款表
			ParkingRefundTrade refundTrade = insertParkingRefundTrade(mreq, tradeId, acceptDate, tradeOrder, shParamsConfig);
			
			int iResult = uniDealData(refundTrade);
			if (iResult <=0) {
				setRspParams(mrsp, RespUtil.dberror, CommEnum.ORDER_DB_EXCEPITON_DESC.getRspMsg());
				return mrsp;
			}
			
			if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), tradeOrder.getPayType())) {
				RefundBillRsp refundBillRsp = shRefundBill(shParamsConfig, mreq, tradeId, tradeOrder);
				if (null != refundBillRsp) {
					setShUpdateParkingRefundTrade(mrsp, tradeId, acceptDate, refundBillRsp);
				} else {
					setRspParams(mrsp, RespUtil.timeOut, "调用系统异常");
					return mrsp;
				}
			} else {
				setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, "退款渠道不明确，需核实此笔订单情况");
			}
		}
		
		return mrsp;
	}

	/**
	 * getDate:获取系统时间
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param machDate  格式化
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String getDate(String machDate) {
		Date date = new Date();
		String acceptDate = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode()).format(date);		
		return acceptDate;
	}

	/**
	 * setShUpdateParkingRefundTrade: 上海 金融事业部 退款 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param tradeId
	 * @param  @param acceptDate
	 * @param  @param refundBillRsp    设定文件
	 * @return void    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setShUpdateParkingRefundTrade(OrderRefundRsp mrsp,
			String tradeId, String acceptDate, RefundBillRsp refundBillRsp) throws Exception {
		if (StringUtil.checkStringsEqual(RespUtil.successCode, refundBillRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), refundBillRsp.getResultCode())) {
			ParkingRefundTrade refundTrade = updateParkingRefundTrade(tradeId, acceptDate, refundBillRsp.getUpRefundId(), null); //这里通过渠道退款通知 来决定 更改本地 状态 
			int uResult = uniDealData(refundTrade);
			if (uResult>0) {
				setRspParams(mrsp, RespUtil.successCode, "退款申请提交成功");
			} else {
				setRspParams(mrsp, RespUtil.dberror, "退款申请成功，操作数据异常");
			}
		} else {
			ParkingRefundTrade refundTrade = updateParkingRefundTrade(tradeId, acceptDate);
			int uResult = uniDealData(refundTrade);
			if (uResult>0) {
				setRspParams(mrsp, refundBillRsp.getRespCode(), "退款申请失败");
			} else {
				setRspParams(mrsp, RespUtil.dberror, "退款失败，操作数据异常");
			}
		}
	}
	
	/**
	 * findParkingChannelShParamsConfigById:上海金融事业部
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
	
	private ParkingChannelShParamsConfig findParkingChannelShParamsConfigById(
			String parkId) throws SQLException {
		ParkingChannelShParamsConfig shParamsConfig = new ParkingChannelShParamsConfig();
		shParamsConfig.setParkId(parkId);
		shParamsConfig.setStates("1");
		shParamsConfig = parkingChannelShParamsConfigDao.selectParkingChannelShParamsConfigById(shParamsConfig); 
		return shParamsConfig;
	}
	/**
	 * findParkingChannelAccessRouteConfigByParkIdList: 查询 渠道配置路由
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param parkId
	 * @param  @return    设定文件
	 * @return List<ParkingChannelAccessRouteConfig>    DOM对象
	 * @throws SQLException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private List<ParkingChannelAccessRouteConfig> findParkingChannelAccessRouteConfigByParkIdList(
			String parkId) throws SQLException {
		ParkingChannelAccessRouteConfig routeConfig = new ParkingChannelAccessRouteConfig();
		routeConfig.setParkId(parkId);
		routeConfig.setStates("1");
		List<ParkingChannelAccessRouteConfig> routeConfigList = parkingChannelAccessRouteConfigDao.selectParkingChannelAccessRouteConfigByParkIdList(routeConfig);
		return routeConfigList;
	}
	/**
	 * findParkingRefundTradeList: 退款订单
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param orderId
	 * @param  @return    设定文件
	 * @return List<ParkingRefundTrade>    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private List<ParkingRefundTrade> findParkingRefundTradeList(String orderId) throws Exception {
		ParkingRefundTrade  refundTrade = new ParkingRefundTrade();
		refundTrade.setOrderId(orderId);
		List<ParkingRefundTrade> listRefundTrade = refundTradeDao.selectRefundIdList(refundTrade);
		return listRefundTrade;
	}
	/**
	 * findParkingOrderById: 根据订单号查询订单
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param orderId
	 * @param  @return    设定文件
	 * @return ParkingTradeOrder    DOM对象
	 * @throws SQLException 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private ParkingTradeOrder findParkingOrderById(String orderId) throws SQLException {
		ParkingTradeOrder tradeOrder = new ParkingTradeOrder();
		tradeOrder.setOrderId(orderId);//订单号
		tradeOrder = parkingTradeOrderDao.selectParkingOrderById(tradeOrder);
		return tradeOrder;
	}
	/**
	 * checkTradeStatus: 可以退款条件
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param tradeStatus
	 * @param  @return    设定文件
	 * @return int    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private int checkTradeStatus(String tradeStatus) {
		if (StringUtil.checkStringsEqual(CommEnum.PAY_Finished_CODE.getRspCode(), tradeStatus)) {
		    return 1;
		}
		
		if (StringUtil.checkStringsEqual(CommEnum.PAY_PAID_CODE.getRspCode(), tradeStatus)) {
		    return 1;
		}
		return -1;
	}
	/**
	 * setRefundParkOrderRsp: 设置 更新 数据库 返回 
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param tradeId
	 * @param  @param acceptDate
	 * @param  @param mrsp
	 * @param  @param refundParkOrderRsp    设定文件
	 * @return void    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRefundParkOrderRsp(String tradeId, String orderId, String acceptDate,
			OrderRefundRsp mrsp, String successCode) throws Exception {
		if (StringUtil.checkStringsEqual(RespUtil.successCode, successCode)) {
			ParkingRefundTrade refundTrade = updateParkingRefundTrade(tradeId, acceptDate, orderId, CommEnum.REFUNDED.getRspCode());  
			int uResult = uniDealData(refundTrade);
			if (uResult >0) {
				setRspParams(mrsp, RespUtil.successCode, "退款提交成功");
				return;
			} else {
				setRspParams(mrsp, RespUtil.dberror, "退款操作数据失败，请核实");
			}
		} else {
			updateParkingRefundTrade(tradeId, acceptDate);
			setRspParams(mrsp, RespUtil.dberror, "退款申请失败，请核实");
		}
	}

	/**
	 * shRefundBill: 上海 银联金融事业部 的 退款 报文组装
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param shParamsConfig
	 * @param  @param mreq
	 * @param  @param tradeId  退款订单号
	 * @param  @param tradeOrder
	 * @param  @return    设定文件
	 * @return RefundBillRsp    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private RefundBillRsp shRefundBill(
			ParkingChannelShParamsConfig shParamsConfig, OrderRefundReq mreq,
			String tradeId, ParkingTradeOrder tradeOrder) throws Exception {
		RefundBillReq refundBillReq = new RefundBillReq();
		refundBillReq.setAppId(shParamsConfig.getAppId());//商户应用代码
		refundBillReq.setPrivateKey(shParamsConfig.getSignKey());//商户密钥
		refundBillReq.setOrderId(mreq.getOrderId()); //支付时订单号
		refundBillReq.setRefundId(tradeId);//退款时生成退款订单号
		refundBillReq.setPlateNumber(tradeOrder.getCarPlate());//车牌
		refundBillReq.setRefundAmount(tradeOrder.getPayAmt());//退款
		refundBillReq.setRefundCallback(ConfigUtil.getValue("UNIONPAY_SH_BACK_RERUND_NOTIFY_URL"));// 退款通知地址
		RefundBillRsp refundBillRsp = parkingShOrderRefundService.refundBill(refundBillReq);
		return refundBillRsp;
	}
	/**
	 * insertParkingRefundTrade: 预保存退款订单 流水
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mreq
	 * @param  @param tradeId
	 * @param  @param acceptDate
	 * @param  @param tradeOrder
	 * @param  @param mchntNo
	 * @param  @return    设定文件
	 * @return ParkingRefundTrade    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private ParkingRefundTrade insertParkingRefundTrade(OrderRefundReq mreq,
			String tradeId, String acceptDate, ParkingTradeOrder tradeOrder,
			ParkingChannelShParamsConfig shParamsConfig) {
		ParkingRefundTrade refundTrade = new ParkingRefundTrade();
		refundTrade.setRefundId(tradeId);//退款交易流水号
		refundTrade.setOrderId(mreq.getOrderId());//支付是流水号
		refundTrade.setRefundTime(acceptDate);//退款时间
		refundTrade.setTradeFee(tradeOrder.getPayAmt());//退款时交易金额
		refundTrade.setApplyForRefundFee(tradeOrder.getPayAmt()); //申请退款金额
		refundTrade.setRefundStatus(CommEnum.REFUNDING.getRspCode()); //申请中 、预存数据
		refundTrade.setRefundChannel(tradeOrder.getPayType()); //根据支付类型即可区分
		refundTrade.setModifyTag(CommEnum.PARKING_INSERT_TAG.getRspCode());
		if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), tradeOrder.getPayType())) {
			refundTrade.setRefundMchntId(shParamsConfig.getMchntNo()); //商户号
		}
		return refundTrade;
	}
	/**
	 * setInitRspParams 设置  初始化 值 
	 *
	 * @param  @param mrsp
	 * @param  @param serialNumber    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setInitRspParams(OrderRefundRsp mrsp, String serialNumber) {
		mrsp.setIsSuccess(CommEnum.IS_SUCCESS.getRspCode());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
		mrsp.setSerialNumber(serialNumber);
	}

	/**
	 * updateParkingRefundTrade 更新  异常 失败 ，处理 系统 调用 直接置失败 
	 *
	 * @param  @param tradeId
	 * @param  @param acceptDate
	 * @param  @return    设定文件
	 * @return ParkingRefundTrade    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private ParkingRefundTrade updateParkingRefundTrade(String tradeId, String acceptDate) {
		ParkingRefundTrade refundTrade = new ParkingRefundTrade();
		refundTrade.setRefundId(tradeId);
		refundTrade.setRefundStatus(CommEnum.REFUNDED_FAIL.getRspCode());
		refundTrade.setUpdateTime(acceptDate);
		refundTrade.setModifyTag(CommEnum.PARKING_UPDATE_TAG.getRspCode());
		return refundTrade;
	}
	/**
	 * updateParkingRefundTrade 更新 退款  订单号  状态
	 *
	 * @param  @param tradeId
	 * @param  @param acceptDate
	 * @param  @param tradeId2
	 * @param  @return    设定文件
	 * @return ParkingRefundTrade    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private ParkingRefundTrade updateParkingRefundTrade(String tradeId, String acceptDate, String upRefundId, String refundStatus) {
		ParkingRefundTrade refundTrade = new ParkingRefundTrade();
		refundTrade.setRefundId(tradeId); //商户生成退款流水号 此笔 会多笔产生
		refundTrade.setUpdateTime(acceptDate);
		refundTrade.setOtherRefundId(upRefundId);//第三方退款流水号
		refundTrade.setRefundStatus(null == refundStatus ? null : refundStatus);
		refundTrade.setModifyTag(CommEnum.PARKING_UPDATE_TAG.getRspCode());
		return refundTrade;
	}
	/**
	 * setRspParams: 退款 返回 
	 *
	 * @param  @param mrsp
	 * @param  @param dberror
	 * @param  @param string    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(OrderRefundRsp mrsp, String respCode, String respDesc) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
	}
	
	private int chekcParamsIsNull(OrderRefundReq mreq) {
		if (StringUtil.checkNullString(mreq.getOrderId())) return -1;
		return 1;
	}
	/**
	 * (non-Javadoc)  退款查询
	 * @see com.parking.service.impl)
	 */
	@Override
	public OrderRefundRsp queryRefundOrdr(OrderRefundReq mreq) throws Exception {
		logger.info("进入退款查询服务  ---------queryRefundOrdr");
		OrderRefundRsp  mrsp = new OrderRefundRsp();
		mrsp = (OrderRefundRsp) CommBeanCopyUtil.beanCopy(mrsp);
		mrsp.setSerialNumber(mreq.getSerialNumber());
		//参检查
		if (StringUtil.checkNullString(mreq.getOrderId())) {
			setRspParams(mrsp, RespUtil.noResult, CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
			return mrsp; 
		}
		//检查订单是否存在
		ParkingTradeOrder tradeOrder = new ParkingTradeOrder();
		tradeOrder.setOrderId(mreq.getOrderId());//订单号
		tradeOrder = parkingTradeOrderDao.selectParkingOrderById(tradeOrder);
		if (null == tradeOrder) {
			setRspParams(mrsp, RespUtil.noResult, "查无此订单号,请确认此订单["+mreq.getOrderId()+"]");
			return mrsp;
		}		
		
		//查询退款订单表
		ParkingRefundTrade  refundTrade = new ParkingRefundTrade();
		refundTrade.setOrderId(mreq.getOrderId());
		List<ParkingRefundTrade> listRefundTrade = refundTradeDao.selectRefundIdList(refundTrade);
		if (null == listRefundTrade || listRefundTrade.size()<=0) {
			setRspParams(mrsp, RespUtil.noResult, "此笔交易未查询到退款订单号,请核实此订单["+mreq.getOrderId()+"]");
			return mrsp;
		} 
		
		//根据渠道判断路由 上海智慧平台   可以继续扩展的
		if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), tradeOrder.getPayType())) {
			//检查配置参数配置表 /上海智慧平台
			ParkingChannelShParamsConfig shParamsConfig = findParkingChannelShParamsConfigById(tradeOrder.getParkId());
			if (null == shParamsConfig) {
				setRspParams(mrsp, RespUtil.noResult, "未配置渠道商户参数，请检查");
				return mrsp;
			}
			
			RefundStatusRsp refundStatusRsp  = queryShRefundBillStatus(mreq, shParamsConfig, listRefundTrade.get(0).getOtherRefundId() );
			if (null != refundStatusRsp) {
				if (StringUtil.checkStringsEqual(RespUtil.successCode, refundStatusRsp.getRespCode()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), refundStatusRsp.getResultCode())) {
				    setShRefundStatusRsp(mreq, mrsp, refundStatusRsp);
				} else {
					setRspParams(mrsp, refundStatusRsp.getRespCode(), refundStatusRsp.getRespDesc());
				}
			} else {
				setRspParams(mrsp, RespUtil.timeOut, "查询退款订单状态异常");
			}
		} else {
			setRspParams(mrsp, RespUtil.noResult, "未匹配到查询渠道路由");
		}
		return mrsp;
	}
    
	/**
	 * setShRefundStatusRsp:(这里用一句话描述这个方法的作用)
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mreq
	 * @param  @param mrsp
	 * @param  @param refundStatusRsp    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setShRefundStatusRsp(OrderRefundReq mreq, OrderRefundRsp mrsp,
			RefundStatusRsp refundStatusRsp) {
		mrsp.setOrderId(mreq.getOrderId());
		mrsp.setRefundAmount(refundStatusRsp.getRefundAmount());// 退款金额
		mrsp.setPayStatus(refundStatusRsp.getPayStatus());//当前状态
		mrsp.setRespCode(RespUtil.successCode);
		mrsp.setRespDesc(refundStatusRsp.getRespDesc());
	}
	/**
	 * queryShRefundBillStatus: 上海银联无感渠道 订单 渠道退款结果查询
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mreq
	 * @param  @param shParamsConfig
	 * @param  @param otherRefundId
	 * @param  @return    设定文件
	 * @return RefundStatusRsp    DOM对象
	 * @throws Exception 
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private RefundStatusRsp queryShRefundBillStatus(OrderRefundReq mreq,
			ParkingChannelShParamsConfig shParamsConfig, String otherRefundId) throws Exception {
		RefundStatusReq refundStatusReq = new RefundStatusReq();
		refundStatusReq.setAppId(shParamsConfig.getAppId());//商户应用代码
		refundStatusReq.setOrderId(mreq.getOrderId()); //订单号
		refundStatusReq.setUpRefundId(otherRefundId); //渠道方退款流水号
		refundStatusReq.setSerialNumber(mreq.getSerialNumber());//
		refundStatusReq.setPrivateKey(shParamsConfig.getSignKey());//密钥
		RefundStatusRsp refundStatusRsp = parkingShOrderRefundService.queryRefundBillStatus(refundStatusReq);
		return refundStatusRsp;
	}
	/**
	 * (non-Javadoc) 进入退款通知服务
	 * @see com.parking.service.impl(OrderRefundReq)
	 */
	@Override
	public OrderRefundRsp notifyRefundAResult(OrderRefundReq mreq)throws Exception {
		logger.info("进入退款通知服务---------notifyRefundAResult");
		OrderRefundRsp mrsp = new OrderRefundRsp();
		mrsp = (OrderRefundRsp) CommBeanCopyUtil.beanCopy(mrsp);
		//查询订单主表是否存在
		ParkingTradeOrder tradeOrder = new ParkingTradeOrder();
		tradeOrder.setOrderId(mreq.getOrderId());//订单号
		tradeOrder = parkingTradeOrderDao.selectParkingOrderById(tradeOrder);
		if (null == tradeOrder) {
		    setRspParams(mrsp, RespUtil.noResult, "通知退款交易，查无此订单号,请确认此订单["+mreq.getOrderId()+"]");
			return mrsp;
		}
		
		//查询退款订单表
		ParkingRefundTrade  refundTrade = new ParkingRefundTrade();
		refundTrade.setOrderId(mreq.getOrderId());
		List<ParkingRefundTrade> listRefundTrade = refundTradeDao.selectRefundIdList(refundTrade);
		if (null == listRefundTrade || listRefundTrade.size()<0) {
			setRspParams(mrsp, RespUtil.noResult, "查无此退款订单号,请确认此订单["+mreq.getOrderId()+"]");
			return mrsp;
		} 
		//流水号
		String tradeId = seqService.getTradeSequenceId("TRADE_ID");
		//本地受理时间
		Date date = new Date();
		String acceptDate = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode()).format(date);		
	
		//此处可继续扩展
		if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), tradeOrder.getPayType())) {
			refundTrade = geneParkingRefundTrade(refundTrade, mreq, listRefundTrade.get(0).getRefundId(), acceptDate);
			refundTrade.setRefundStatus(getRefundStatus(mreq.getPayStatus()));
			//setRspParams(mrsp, CommEnum.UNIONPAY_SH_RESULT_CODE_1002.getRspCode(), "通知参数错误，支付状态不明确");
			//退款失败或退款成功都进行通知
			int iResult = uniDealData(refundTrade);
			if (iResult > 0) {
				setRspParams(mrsp, RespUtil.successCode, "退款通知成功");
			} else {
				setRspParams(mrsp, CommEnum.UNIONPAY_SH_RESULT_CODE_1003.getRspCode(), "退款通知失败");
			}
		} else {
			setRspParams(mrsp, CommEnum.UNIONPAY_SH_RESULT_CODE_1003.getRspCode(), "渠道未明确,通知异常");
		}
		return mrsp;
	}
	/**
	 * getRefundStatus:(这里用一句话描述这个方法的作用)
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
	
	private String getRefundStatus(String payStatus) {
		//理论值 0 1 2  不会出现  支付时出现 暂不处理
		String refundStatus = "";
		if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_0.getRspCode(), payStatus)) { //支付成功	
		} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_1.getRspCode(), payStatus)) { //支付中
		} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_2.getRspCode(), payStatus)) { //支付失败
		} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_3.getRspCode(), payStatus)) { //退款中
			refundStatus = CommEnum.REFUNDING.getRspCode();
		} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_4.getRspCode(), payStatus)) { //退款成功
			refundStatus = CommEnum.REFUNDED.getRspCode();
		} else if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_PAY_STATUS_5.getRspCode(), payStatus)) { //退款失败
			refundStatus = CommEnum.REFUNDED_FAIL.getRspCode();
		} 
		return refundStatus;
	}
	/**
	 * geneParkingRefundTrade: 退款 交易订单表
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param refundTrade
	 * @param  @param mreq
	 * @param  @param refundId
	 * @param  @param acceptDate
	 * @param  @return    设定文件
	 * @return ParkingRefundTrade    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private ParkingRefundTrade geneParkingRefundTrade(
			ParkingRefundTrade refundTrade, OrderRefundReq mreq,
			String refundId, String acceptDate) {
		refundTrade = new ParkingRefundTrade();
		refundTrade.setOrderId(mreq.getOrderId());
		refundTrade.setOtherRefundId(mreq.getUpRefundId());// 第三年方渠道生成的 退款订单号
		refundTrade.setRefundId(refundId);
		refundTrade.setUpdateTime(acceptDate);
		refundTrade.setModifyTag("U");
		return refundTrade;
	}

}

