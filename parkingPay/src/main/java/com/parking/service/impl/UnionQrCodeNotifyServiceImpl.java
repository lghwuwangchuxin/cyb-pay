package com.parking.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;

import com.parking.dto.PayNotifyReq;
import com.parking.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parking.dao.ParkingTradeOrderDao;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.UnPayNotifyReq;
import com.parking.dto.UnPayNotifyRsp;
import com.parking.service.UnionQrCodeNotifyService;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;

@Service("unionQrCodeNotifyService")
public class UnionQrCodeNotifyServiceImpl implements UnionQrCodeNotifyService{
	
	@Inject
	private ParkingTradeOrderDao parkingTradeOrderDao;
	@Autowired
	private ParkingOrderService parkingOrderService;

	@Override
	public UnPayNotifyRsp notifyQrCodeTradeOrderStatus(UnPayNotifyReq mreq)throws Exception {

		UnPayNotifyRsp mrsp = new UnPayNotifyRsp();
		setRspParams(mrsp, RespUtil.codeError,CommEnum.RESP_DESC_ERROR.getRspMsg());

		// (1) 校验订单合法性

		if(!StringUtil.checkNullString(mreq.getOrderId())) {
			ParkingTradeOrder parkingTradeOrder = new ParkingTradeOrder();
			parkingTradeOrder.setOrderId(mreq.getOrderId());
			parkingTradeOrder = parkingTradeOrderDao.selectParkingOrderById(parkingTradeOrder);
			if (null == parkingTradeOrder) {
				setRspParams(mrsp,RespUtil.noResult, "查无此订单信息");
				return mrsp;
			} else {

				if(!CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode().equals(parkingTradeOrder.getStates())) {
					SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					parkingTradeOrder = new ParkingTradeOrder();
					parkingTradeOrder.setUpdateTime(fmt.format(new Date()));
					parkingTradeOrder.setStates(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
					parkingTradeOrder.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
					parkingTradeOrder.setTradeId(mreq.getOrderId());
					parkingTradeOrder.setOutTradeNo(mreq.getPayId());
					parkingTradeOrder.setSubPayType(mreq.getPayType()); // 支付子类型
					int i = parkingTradeOrderDao.updateParkingOrder(parkingTradeOrder);
					if(i > 0 ) {
						mrsp.setRespCode(RespUtil.successCode);
						mrsp.setRespDesc("通知成功");
						PayNotifyReq payReq = new PayNotifyReq();
						payReq.setOrderId(mreq.getOrderId());
						payReq.setPayType(mreq.getPayType());
						payReq.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
						parkingOrderService.notifyPayOrderAResult(payReq);
					}
				} else {
					setRspParams(mrsp, RespUtil.successCode, "订单已支付或结束支付");
					return mrsp;
				}
			}
		}else {
			setRspParams(mrsp, RespUtil.REQ_XML_LESS, "请求订单号不能为空");
		}		
		return mrsp;
	}

	private void setRspParams(UnPayNotifyRsp mrsp, String codeError, String rspMsg) {
	    mrsp.setRespCode(codeError);
	    mrsp.setRespDesc(rspMsg);
	}

}
