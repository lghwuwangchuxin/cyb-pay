package com.parking.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import com.parking.dao.ParkingQrcodeMchntConfigDao;
import com.parking.domain.ParkingQrcodeMchntConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.dao.ParkingTradeOrderDao;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.QueryAtmResultReq;
import com.parking.dto.QueryAtmResultRsp;
import com.parking.service.QrCodeChannelPayTradeProxyService;
import com.parking.service.QrCodeQueryOrderService;
import com.parking.util.CommEnum;
import com.parking.util.QrCodeChannelUtil;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;
import com.parking.util.Utility;

@Service("qrCodeQueryOrderService")
public class QrCodeQueryOrderServiceImpl implements QrCodeQueryOrderService {

	private static final Logger logger = LoggerFactory.getLogger(QrCodeQueryOrderServiceImpl.class);

	@Inject
	private ParkingQrcodeMchntConfigDao mchntConfigDao;
	@Inject
	private ParkingTradeOrderDao parkingTradeOrderDao;
	@Inject
	private QrCodeChannelPayTradeProxyService qrCodeChannelPayTradeProxyService;


	/**
	 * (non-Javadoc)提供给B-C端 查询当前交易状态
	 * 
	 * @see
	 */
	@Override
	public QueryAtmResultRsp queryAtmResultService(QueryAtmResultReq mreq)throws Exception {
		logger.info("进入终端查询当前交易状态服务-------------------------queryAtmResultService");
		QueryAtmResultRsp mrsp = new QueryAtmResultRsp();
		setInitQueryAtmResultRsp(mrsp, mreq);

		// (1) 校验订单合法性
		ParkingTradeOrder parkingTradeOrder = new ParkingTradeOrder();
		if (StringUtil.checkNullString(mreq.getOrderId())) {
			//进行容错 ，下单异常出现时容错
			parkingTradeOrder.setMchntSysNumber(mreq.getSerialNumber()); // 本次 请求流水号
			parkingTradeOrder = parkingTradeOrderDao.selectParkingOrderByMchnt(parkingTradeOrder);
		} else {
			parkingTradeOrder.setOrderId(mreq.getOrderId());
			parkingTradeOrder = parkingTradeOrderDao.selectParkingOrderById(parkingTradeOrder);
		}
		
		if (null == parkingTradeOrder) {
			mrsp.setRespCode(RespUtil.noResult);
			mrsp.setRespDesc("not found orderId");
			return mrsp;
		} else {
			
			if (Utility.orderDateVaild(parkingTradeOrder.getCreatedTime()) >80 ) {
				mrsp.setRespCode(RespUtil.repeatPayRisk);
	    		mrsp.setRespDesc("此笔查询订单交易时间有效期已过或者订单有异常");
	    		logger.info(mrsp.getRespDesc() + "["+mreq.getSerialNumber()+"]");
	    	    return mrsp;
			}

			String tradeTatus = "";
			String appChannel = "";

			//置返回值
			tradeTatus = parkingTradeOrder.getTradeStatus();
			mreq.setOrderId(parkingTradeOrder.getOrderId());
			logger.info("订单状态是:" + parkingTradeOrder.getStates());
			if (null != parkingTradeOrder.getStates() && ("14".equals(parkingTradeOrder.getStates().trim()) || "1".equals(parkingTradeOrder.getStates().trim()))) { // tradeOrder.getState()如果是null的话，这个时候如果给null去空格，则会报java.lang.NullPointerException
																		// 异常。
				logger.info("需要查询订单渠道状态");
				//查询商户渠道支付类型
				appChannel = QrCodeChannelUtil.getMatchChannelMchntName(parkingTradeOrder.getPayType());
				ParkingQrcodeMchntConfig mchntConfig = new ParkingQrcodeMchntConfig();
				mchntConfig.setMchntNo(parkingTradeOrder.getMchntNo()); // 自定义虚拟商户号
				mchntConfig.setAppChannel(appChannel);
				List<ParkingQrcodeMchntConfig> mchntInfo = mchntConfigDao.selectMParkingQrcodeMchntConfigList(mchntConfig);
				if (null == mchntInfo || mchntInfo.size() == 0) {
					mrsp.setRespCode(RespUtil.noResult);
					mrsp.setRespDesc("not found mchntId");
					return mrsp;
				}
			    // 获取到唯一商户对象
				for (int i = 0, length = mchntInfo.size(); i < length; i++) {
					if (appChannel.equals(mchntInfo.get(i).getAppChannel())) {
						mchntConfig = mchntInfo.get(i);
						break;
					}
				}
				
				String code = "";
				String desc = "";
				String outTradeNo = "";
				String payTime = "";
				String state = "";

                // 公共多渠道处理  查询订单状态
				mreq.setChannelType(appChannel);//渠道类型
				String qRYTIME = querySumQyTime(parkingTradeOrder.getEffectTimes());//渠道查询次数
					ApplyOrderChannelBaseDTO dto = qrCodeChannelPayTradeProxyService.queryTradeService(mreq, mchntConfig, parkingTradeOrder);
					if(null != dto) {
						if(StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), dto.getRespCode())) {
							if(!StringUtil.checkNullString(dto.getState())) {
								state = QrCodeChannelUtil.getState(dto.getState());
								if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode(), state)) {
									tradeTatus = CommEnum.PAY_PAID_CODE.getRspCode(); //支付状态
								} else if(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_5.getRspCode(), state) || StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_12.getRspCode(), state)) {
									tradeTatus = CommEnum.PAY_CLOSED_CODE.getRspCode(); //订单关闭
								} 
							} else {
								state = CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode();
							}
						} else {
							state = CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode();
						}
						payTime = dto.getPayTime();//渠道方支付时间
						outTradeNo = dto.getPayId();//渠道方支付订单号
						code = dto.getTradeCode(); //交易返回码
						desc = dto.getTradeDesc(); //返回描述
						logger.info("渠道调用[" +appChannel+"]返回描述："+code+CommEnum.LONG_STRING.getRspCode()+desc);
					} else {
						desc = CommEnum.CALL_CHANNEL_ERROR.getRspMsg();//调用渠道异常
						code = CommEnum.CALL_CHANNEL_ERROR.getRspCode();
						state = CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode();
						logger.info("多渠道查询调用异常：" +mreq.getSerialNumber());
					}

				
				//本地受理时间
				Date date = new Date();
				String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				// 订单表
				parkingTradeOrder = new ParkingTradeOrder();
				parkingTradeOrder.setPayTime(payTime);
				parkingTradeOrder.setOutTradeNo(outTradeNo); //渠道方订单号
				parkingTradeOrder.setTradeId(mreq.getOrderId());
				parkingTradeOrder.setTradeCode(code); //渠道返回码
				parkingTradeOrder.setTradeDesc(desc); //渠道返回描述
				parkingTradeOrder.setStates(StringUtil.checkStringsEqual(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode(), state) ? state : null); //只有等于3的时候才去更新反之不更新 ，这样可以少去查询一次,提高用户体验
				parkingTradeOrder.setUpdateTime(nowTime);
				parkingTradeOrder.setEffectTimes(qRYTIME); // 查询次数
				parkingTradeOrder.setTradeStatus(queryTradeStatus(tradeTatus)); // 支付成功 或者 关闭才更新
				int stateUpdate = parkingTradeOrderDao.updateParkingOrder(parkingTradeOrder);
				if (stateUpdate > 0) {
					logger.info("更新数据成功，（查询）");
					mrsp.setRespCode(RespUtil.successCode);
					mrsp.setRespDesc("query success！");
				} else {
					logger.info("更新数据失败，（查询）");
					mrsp.setRespCode(RespUtil.unknownError); //
					mrsp.setRespDesc("失败！");
					return mrsp;
				}
			} else if (null != parkingTradeOrder.getStates() && CommEnum.GZBD_ORDER_CURR_STATE_5.getRspCode().equals(parkingTradeOrder.getStates().trim())) { // 第一次贴卡支付的时候就支付失败了
				tradeTatus = CommEnum.PAY_CLOSED_CODE.getRspCode();
			}
			// 置返回值数据
			mrsp.setOrderId(mreq.getOrderId()); // 订单号
			mrsp.setTradeId(parkingTradeOrder.getTradeId());
			mrsp.setRespCode(RespUtil.successCode);
			mrsp.setRespDesc("查询成功！");
			mrsp.setPayType(appChannel);
			mrsp.setTradeStatus(tradeTatus); // 成功状态 如果=paid 出场

		}
		return mrsp;
	}
	
	/**
	 * setInitQueryAtmResultRsp: 查询 置 初始 化
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
	
	private void setInitQueryAtmResultRsp(QueryAtmResultRsp mrsp,
			QueryAtmResultReq mreq) {
		mrsp.setRespCode(RespUtil.codeError);
		mrsp.setRespDesc("other error");
		mrsp.setSerialNumber(mreq.getSerialNumber());
	}

	/**
	 * queryTradeStatus: 只有支付成功时 或者 订单 CLOSED 更新
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
	 * @param  @param tradeTatus
	 * @param  @return    设定文件
	 * @return String    DOM对象
	 * @throws 
	 * @since  CodingExample　Ver 1.1
	*/
	
	private String queryTradeStatus(String tradeTatus) {
		if (StringUtil.checkStringsEqual(CommEnum.PAY_PAID_CODE.getRspCode(), tradeTatus) ||
				StringUtil.checkStringsEqual(CommEnum.PAY_CLOSED_CODE.getRspCode(), tradeTatus)) {
			return tradeTatus;
		}
		return "";
	}
	//计算轮询次数
	private String querySumQyTime(String qyTime) {
		qyTime = String.valueOf(Integer.parseInt(qyTime) +1); //查询次数
		return qyTime;
	}


}
