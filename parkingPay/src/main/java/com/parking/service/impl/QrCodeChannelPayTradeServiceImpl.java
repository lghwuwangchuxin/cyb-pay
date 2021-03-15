package com.parking.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import com.parking.dao.ParkingQrcodeMchntConfigDao;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.dto.*;
import com.parking.service.*;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.dao.ParkingTradeOrderDao;
import com.parking.domain.ParkingTradeOrder;

/**

 *   多渠道公共下单业务 接口类
 */

@Service("qrCodeChannelPayTradeService")
public class QrCodeChannelPayTradeServiceImpl implements QrCodeChannelPayTradeService {
    
	private static final Logger logger = LoggerFactory.getLogger(QrCodeChannelPayTradeServiceImpl.class);
	@Inject
	private SeqService seqService;
	@Inject
	private ParkingQrcodeMchntConfigDao mchntConfigDao;
	@Inject
	private ParkingTradeOrderDao parkingTradeOrderDao;
	@Inject
	private QrCodePerminCheckService  qrCodePerminCheckService;
	@Inject
	private QrCodeChannelPayTradeProxyService qrCodeChannelPayTradeProxyService;
	@Inject
	private QrCodeQueryOrderService qrCodeQueryOrderService;

	/**
	 * (non-Javadoc)  //公共渠道被扫下单业务处理类
	 * @see com.parking.service.QrCodeTradePayService(ApplyOrderReq)
	 */
	@Override
	public ApplyOrderRsp payTradeService(ApplyOrderReq mreq) throws Exception {
		logger.info("进入支付前置商户被扫公共渠道下单业务B-C--------payTradeService");
		ApplyOrderRsp mrsp = new ApplyOrderRsp();
		setInitRspParams(mrsp,mreq);
		
		//终端权限验证
		String flag = mreq.getRecCode(); //二维码类型判断
		ApplyOrderRsp  applayOrderRsp = null;
		//终端权限商户配置（1） 支付开启
		applayOrderRsp = qrCodePerminCheckService.termIdPerminCheck(mreq, null);
		if (null == applayOrderRsp) return mrsp;
		if (!StringUtil.checkStringsEqual(RespUtil.successCode, applayOrderRsp.getRespCode())) {
			setRspParams(mrsp, applayOrderRsp.getRespCode(), applayOrderRsp.getRespDesc());
			return mrsp;
		}
		
		 mreq.setMchntId(applayOrderRsp.getMchntId()); //自定义虚拟商户号
		logger.info("打印公共渠道下单类：\n"+mreq.toString());
		
		// (2)
	    // 查询订单
	    logger.info("线下终端请求流水号是:" + mreq.getSerialNumber());
	    ParkingTradeOrder parkingTradeOrder = new ParkingTradeOrder();
	    parkingTradeOrder.setMchntSysNumber(mreq.getSerialNumber()); // 线下本次请求流水号
	    parkingTradeOrder = parkingTradeOrderDao.selectParkingOrderByMchnt(parkingTradeOrder);
	    if (null!= parkingTradeOrder) {
	    	//检查订单在此流程时间有效性  ,防止风险
	    	if (StringUtil.checkNullString(parkingTradeOrder.getCreatedTime()) || Utility.orderDateVaild(parkingTradeOrder.getCreatedTime()) > Long.valueOf(applayOrderRsp.getQrValidTime())) {
	    		setRspParams(mrsp, RespUtil.repeatPayRisk, "请重新发起支付订单，此订单交易时间有效期已过或不确定此订单");
	    		logger.info(mrsp.getRespDesc()+ "["+mreq.getSerialNumber()+"]");
	    	    return mrsp;
	    	}
	    	logger.info("已经生成过支付订单，支付订单号是：" + parkingTradeOrder.getOrderId());
	    	if("Paid".equals(parkingTradeOrder.getTradeStatus()) || "14".equals(parkingTradeOrder.getStates()) || "1".equals(parkingTradeOrder.getStates())){
				mrsp.setOrderId(parkingTradeOrder.getOrderId());
				mrsp.setTxnAmt(mreq.getTxnAmt());
				mrsp.setPayAmt(mreq.getPayAmt());
				mrsp.setQrCodeConTent(mreq.getQrCodeConTent());
				mrsp.setTradeStatus(parkingTradeOrder.getTradeStatus());
				mrsp.setPayType(mreq.getPayType());
				setRspParams(mrsp, RespUtil.successCode, "request trade succeed！");
				return mrsp;
	    	} else {
				setRspParams(mrsp, RespUtil.unknownError, "Failed order creation！");
				return mrsp;	
	    	}
	    }
	    
	    //(4.1)
	    // 效检商户号是否存在
		ParkingQrcodeMchntConfig mchntConfig = new ParkingQrcodeMchntConfig();
  		mchntConfig.setMchntNo(mreq.getMchntId()); // 自定义商户号
  		mchntConfig.setAppChannel(mreq.getChannelType()); //渠道类型
  		List<ParkingQrcodeMchntConfig> listMchntConfig = mchntConfigDao.selectMParkingQrcodeMchntConfigList(mchntConfig);
  		if (null == listMchntConfig || listMchntConfig.size() == 0) {
  			logger.info("在数据库未能找到分配的商户号，商户号是：" + mreq.getMchntId());
  			setRspParams(mrsp, RespUtil.noResult, "未配置商户");
  			return mrsp;
  		} else {
  			logger.info("获取商户信息");
  			for (int i = 0, length = listMchntConfig.size(); i < length; i++) {
  				if (mchntConfig.getAppChannel().equals(listMchntConfig.get(i).getAppChannel())) {
  					logger.info("获取商户配置信息是：" + listMchntConfig.get(i));
  					mchntConfig = listMchntConfig.get(i);
  					break;
  				}
  			}	
  		}
  		
  	    //(5)生成流水号 订单数据存储
  		String tradeId = seqService.getTradeSequenceId("TRADE_ID");
		logger.info("未生成过支付订单，生成的支付订单是：" + tradeId);
		Date date = new Date();
	    String acccepDate = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode()).format(date);
		parkingTradeOrder = new ParkingTradeOrder();
		mreq.setMchntName(mchntConfig.getMchntName());
		mreq.setOrderName(CommEnum.PARKING_ORDER_NAME.getRspMsg()); //订单名称
		parkingTradeOrder.setTradeId(tradeId); 	// 交易订单号
		parkingTradeOrder.setOrderId(tradeId); // 下单订单号
		parkingTradeOrder.setAcceptMonth(acccepDate.substring(5,7)); // 订单生成的月份，数据库表按月份分表，所以需要月份（查询的时候需要使用）
		parkingTradeOrder.setMchntId(mchntConfig.getMchntId()); // 渠道 商户号
		parkingTradeOrder.setMchntNo(mreq.getMchntId()); // 商户Id,我们自己分配的商户id
		parkingTradeOrder.setMchntName(mchntConfig.getMchntName()); // 填停车场物业名称
		parkingTradeOrder.setMchntSysNumber(mreq.getSerialNumber()); // 本次交易请求 停车场自己的请求流水号
		parkingTradeOrder.setParkMchntSysNumber(mreq.getParkMchntSysNumber()); // 进出场流水号
		parkingTradeOrder.setCarPlate(Utility.decodeUnicode(mreq.getCarPlate())); // 车牌
		parkingTradeOrder.setResName("停车被扫["+parkingTradeOrder.getCarPlate()+"]");
		parkingTradeOrder.setParkId(StringUtil.checkNullString(mreq.getParkId()) ? mreq.getParkId() : mreq.getParkId()); // 停车场ID
		parkingTradeOrder.setResType("parkingBc");
		parkingTradeOrder.setTermId(mreq.getTermId()); //终端号
		parkingTradeOrder.setParkName(Utility.decodeUnicode(mreq.getParkName())); // 停车场名称
		parkingTradeOrder.setTxnAmt(mreq.getTxnAmt()); // 订单金额
		parkingTradeOrder.setPayAmt(mreq.getPayAmt()); // 支付金额
		parkingTradeOrder.setInTime(mreq.getInTime());
		parkingTradeOrder.setOutTime(mreq.getOutTime());  // 出场时间
		parkingTradeOrder.setTimeLong(mreq.getStayTime());
		parkingTradeOrder.setNotifyUrl(mreq.getNotifySysUrl());
		parkingTradeOrder.setQrNo(mreq.getQrCodeConTent());//cb2码
		parkingTradeOrder.setEffectTimes(CommEnum.QR_EFFECT_TIMES.getRspCode()); //初始次数
		parkingTradeOrder.setPayType(mreq.getChannelType()); // 自定义渠道支付类型
		parkingTradeOrder.setSubPayType(QrCodeChannelUtil.getSubPayType(flag)); // 客户支付真正子类型
		parkingTradeOrder.setChannelNum(mchntConfig.getRsrvStr6()); // 收单渠道
		parkingTradeOrder.setCreatedTime(acccepDate); // 订单创建时间
		parkingTradeOrder.setOverTime(applayOrderRsp.getQrValidTime());//超时时间s
		String tradeStatus = CommEnum.PAY_ORDER_CREATED.getRspCode(); //Created
		parkingTradeOrder.setTradeStatus(tradeStatus); //初始状态
		parkingTradeOrder.setStates(CommEnum.TRADE_ORDER_STATE.getRspCode()); // 1
		if(ComQrCodeEnum.YSHENGPAY_CODE.getRspCode().equals(mreq.getChannelType())) {  //当被扫支付渠道为银盛时，手动拼接订单号
			parkingTradeOrder.setOutTradeNo(new SimpleDateFormat("yyyyMMdd").format(new Date())+tradeId);
		} else if (CommEnum.HXBPAY_CODE.getRspCode().equals(mreq.getChannelType())) {
			//tradeId = ConfigUtil.getValue("_PREFIX") +tradeId.substring(10); // 截取订单号
			//parkingTradeOrder.setTradeId(tradeId); 	// 交易订单号
			//parkingTradeOrder.setOrderId(tradeId); // 下单订单号
		}
		int iResult = parkingTradeOrderDao.insertParkingOrder(parkingTradeOrder);
		if (iResult <= 0) {
			logger.info("停车场被扫下单数据存储异常或者重复下单["+mreq.getSerialNumber()+"]");
			setRspParams(mrsp, RespUtil.dberror, "trade iFail");
			return mrsp;
		}
		
		// (6)
		// 发起扣款
		String tradeCode = "";
		String tradeDesc = "";
		String payTime = "";
		String state = "";
		String overTime = "";
		String settleDate = "";
		//公共渠道业务下单处理
		ApplyOrderChannelBaseDTO  dto = null;
		try {
			if(StringUtil.checkStringsEqual(CommEnum.WECHAT_CODE.getRspCode(), flag) || StringUtil.checkStringsEqual(CommEnum.ALIPAY_CODE.getRspCode(), flag) || StringUtil.checkStringsEqual(CommEnum.UNIONAPY_CODE.getRspCode(), flag)) {
				 //公共渠道代理业务类通用报文组装
				 dto = qrCodeChannelPayTradeProxyService.payTradeService(mreq, mchntConfig, parkingTradeOrder);
				 if(null != dto) {
					if(StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), dto.getRespCode())) {
						if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode(), dto.getState())) {
							tradeStatus = CommEnum.PAY_PAID_CODE.getRspCode();
							state = CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode();
						} else {
							//不确定需查询
							state = CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode();
						}
					} else {
						state = CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode();
					}
					tradeCode = dto.getTradeCode(); //渠道返回码
					tradeDesc = dto.getTradeDesc(); //渠道返回描述
					payTime = !StringUtil.checkNullString(dto.getPayTime()) ? dto.getPayTime() : "";
					settleDate = !StringUtil.checkNullString(dto.getWaitTime()) ? dto.getWaitTime() : "";
					logger.info("公共渠道代理业务返回码：" +tradeCode+CommEnum.LONG_STRING.getRspCode()+tradeDesc);
				 } else {
					 state = CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode();
					 logger.info("公共渠道调用代理业务处理业务类异常:-------"+mreq.getSerialNumber());
				 }
			} else {
				logger.info("公共渠道被扫授权码无法进行渠道区分，授权码是：" + mreq.getQrCodeConTent());
				setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, "qrCodeConTent error！");
				return mrsp;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("公共被扫渠道下单异常-----------");
		}
		Date newdate = new Date();
	    String nowTime = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode()).format(newdate);	
		parkingTradeOrder = new ParkingTradeOrder();
		parkingTradeOrder.setTradeCode(null != dto ? dto.getTradeCode() : null); //返回码
		parkingTradeOrder.setTradeDesc(null != dto ? dto.getTradeDesc() : null); //返回描述
		parkingTradeOrder.setStates(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode(), state) ? state : null);//成功3   失败5,待知14
		parkingTradeOrder.setTradeStatus(StringUtil.checkStringsEqual(CommEnum.PAY_PAID_CODE.getRspCode(), tradeStatus) ?  tradeStatus : null); //当前状态
		parkingTradeOrder.setPayAmt(mreq.getPayAmt()); // 支付金额
		parkingTradeOrder.setOverTime(overTime);
		parkingTradeOrder.setPayTime(payTime); // 支付时间
		parkingTradeOrder.setSettleDate(settleDate); //清算日期
		parkingTradeOrder.setTradeId(tradeId); // 交易流水号
		parkingTradeOrder.setOutTradeNo(null != dto ? dto.getPayId() : null); // //第三支付渠道交易流水号
		int updateResult = parkingTradeOrderDao.updateParkingOrder(parkingTradeOrder);
		if (updateResult > 0) {
		   logger.info("调用渠道方数据更新成功");
		   setTradePayRsp(tradeId, mrsp, mreq, tradeStatus);
		   setRspParams(mrsp, RespUtil.successCode, "下单成功");
		} else {
			setRspParams(mrsp, RespUtil.dberror, "下单失败");
		    logger.info("调用渠道方支付，数据更新失败");	
		}
		// 如果失败启动线程池 查询结果

		QrCodeQueryPayStatesServiceThread  qrCodeQueryPayStatesServiceThread = new QrCodeQueryPayStatesServiceThread();
		qrCodeQueryPayStatesServiceThread.setQrCodeQueryOrderService(qrCodeQueryOrderService);
		qrCodeQueryPayStatesServiceThread.setQueryAtmResultReq(getQueryResultReq(tradeId));
		qrCodeQueryPayStatesServiceThread.setPayNotifyReq(getPayNotifyReq(mreq, tradeId, tradeStatus,mchntConfig.getRsrvStr6()));
		qrCodeQueryPayStatesServiceThread.setDelayTime(applayOrderRsp.getDelayTime());
		QrCodeQueryPayStatesServiceThreadPool.executeThread(qrCodeQueryPayStatesServiceThread);
		return mrsp;
	}



	private PayNotifyReq getPayNotifyReq(ApplyOrderReq mreq, String tradeId, String tradeStuats, String channelNum) {
		PayNotifyReq  payNotifyReq = new PayNotifyReq();
		payNotifyReq.setService("payNotifyResult");
		payNotifyReq.setResType("parkingBc");
		payNotifyReq.setCarPlate(mreq.getCarPlate());
		payNotifyReq.setParkId(mreq.getParkId());
		payNotifyReq.setParkName(mreq.getParkName());
		payNotifyReq.setOrderId(tradeId);
		payNotifyReq.setPayType(String.valueOf(ParkingChannelPayType.getDecsitPayTypeMap().get(payNotifyReq.getResType())));
		payNotifyReq.setPayChannel(String.valueOf(ParkingChannelPayType.getDecsitChannelPayTypeMap().get(QrCodeChannelUtil.getSubPayType(mreq.getRecCode()))));
		payNotifyReq.setChannelNum(channelNum);
		payNotifyReq.setTxnAmt(mreq.getTxnAmt());
		payNotifyReq.setPayAmt(mreq.getPayAmt());

		payNotifyReq.setSerialNumber(mreq.getSerialNumber());
		payNotifyReq.setParkMchntSysNumber(mreq.getParkMchntSysNumber());
		payNotifyReq.setNotifySysUrl(mreq.getNotifySysUrl());
		payNotifyReq.setTradeStatus(tradeStuats);
		payNotifyReq.setStayTime(mreq.getStayTime());
		return payNotifyReq;
	}

	private QueryAtmResultReq getQueryResultReq(String tradeId) {
		QueryAtmResultReq  queryAtmResultReq = new QueryAtmResultReq();
		queryAtmResultReq.setOrderId(tradeId);
		queryAtmResultReq.setService("queryAtmResult");
		return queryAtmResultReq;
	}

	/**
	 * setInitRspParams:置公共返回值
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param mreq    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setInitRspParams(ApplyOrderRsp mrsp, ApplyOrderReq mreq) {
		mrsp.setSerialNumber(mreq.getSerialNumber());
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
	}
	/**
	 * setTradePayRsp: 置下单返回
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param tradeId
	 * @param  @param mrsp
	 * @param  @param mreq    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setTradePayRsp(String tradeId, ApplyOrderRsp mrsp,
			ApplyOrderReq mreq, String tradeStatus) {
		   mrsp.setOrderId(tradeId);
		   mrsp.setTxnAmt(mreq.getTxnAmt());
		   mrsp.setPayAmt(mreq.getPayAmt());
		   mrsp.setQrCodeConTent(mreq.getQrCodeConTent());
		   mrsp.setPayType(mreq.getChannelType());// 渠道 类型
		   mrsp.setTradeStatus(tradeStatus);
	}
	/**
	 * setRspParams: 公共置返回
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param mrsp
	 * @param  @param successcode
	 * @param  @param string    设定文件
	 * @return void    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private void setRspParams(ApplyOrderRsp mrsp, String respCode,
			String respDesc) {
		mrsp.setRespCode(respCode);
		mrsp.setRespDesc(respDesc);
	}

}

