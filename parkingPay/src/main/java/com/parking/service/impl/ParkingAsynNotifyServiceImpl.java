package com.parking.service.impl;

import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.PayNotifyReq;
import com.parking.dto.PayNotifyRsp;
import com.parking.service.ParkingAsynNotifyService;
import com.parking.service.ParkingOrderService;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.inject.Inject;

/**

 *  异步通知
 */

@Service("parkingAsynNotifyService")
public class ParkingAsynNotifyServiceImpl implements ParkingAsynNotifyService {
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingAsynNotifyServiceImpl.class);
	@Inject
	private ParkingOrderService parkingOrderService;
	/**
	 * (non-Javadoc) 异步通知平台处理
	 * @see ParkingAsynNotifyService#(ParkingTradeOrder)
	 */
	@Override
	public int notifyCloudPayResult(ParkingTradeOrder tradeOrder) {
		logger.info("通知------------notifyCloudPayResult");

        try{
			PayNotifyReq  req = new PayNotifyReq();
			req = (PayNotifyReq) BeanCopyUtil.CopyBeanToBean(tradeOrder,req);
			req.setOrderId(tradeOrder.getTradeId());
			PayNotifyRsp rsp = parkingOrderService.notifyPayOrderAResult(req);
			logger.info("通知返回码：" +rsp.getRespCode());
		} catch (Exception e) {
        	e.printStackTrace();
		}
		  return -1;
	}
}

