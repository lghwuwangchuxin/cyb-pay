package com.parkingunionpay.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parkingunionpay.service.OrderUnpayService;
import com.parkingunionpay.service.ParkingStateService;
import com.parkingunionpay.service.PayRefundResultService;
import com.parkingunionpay.service.StateSyncSignService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parkingunionpay")
public class ReceiveNotifyController {

	private static final Logger logger = LoggerFactory.getLogger(ReceiveNotifyController.class);
	
	@Inject
	private StateSyncSignService stateSyncSignService;
	@Inject
	private OrderUnpayService orderUnpayService;
	@Inject
	private PayRefundResultService payRefundResultService;
	@Inject
	private ParkingStateService parkingStateService;

	/**
	 * 无感支付结果通知地址url
	 * 
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/payNotify", method = RequestMethod.POST)
	public String payNotify(HttpServletRequest getRequest, HttpServletResponse getResponse) throws IOException {

		logger.info("---------接收到智慧通行平台无感支付结果通知请求----------");
		String result = payRefundResultService.payResultNotify(getRequest);
		getResponse.getWriter().print(result);
		return null;
	}

	/**
	 * 无感支付退款通知地址url
	 * 
	 * @returnHttpServletRequest getRequest, HttpServletResponse getResponse
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/refundNotify")
	public String refundNotify(HttpServletRequest getRequest, HttpServletResponse getResponse) throws IOException {

		logger.info("---------接收到智慧通行平台无感支付退款结果通知请求----------");
		String result = payRefundResultService.refundResultNotify(getRequest);
		getResponse.getWriter().print(result);
		return null;
	}

	/**
	 * 无感支付状态同步地址url stateSyncNotify
	 * 
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/stateSyncNotify")
	public String stateSyncNotify(HttpServletRequest getRequest, HttpServletResponse getResponse) throws IOException {

		logger.info("---------接收到智慧通行平台无感支付状态同步请求----------");
		String result = stateSyncSignService.stateSyncSign(getRequest);
		getResponse.getWriter().print(result);
		return null;
	}

	/**
	 * 查询临时车预缴停车费地址url
	 * 
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getPreOrder",method = RequestMethod.GET)
	public String getPreOrder(HttpServletRequest getRequest, HttpServletResponse getResponse) throws IOException {

		logger.info("---------接收到智慧通行平台查询临时车预缴停车费请求----------");
		String result = orderUnpayService.getOrderUnpay(getRequest);
		getResponse.getWriter().print(result);
		return null;
	}

	/**
	 * 查询停车场实时状态
	 * 
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getParking")
	public String getParking(HttpServletRequest getRequest, HttpServletResponse getResponse) throws IOException {

		logger.info("---------接收到智慧通行平台查询停车场实时状态请求----------");
		String result = parkingStateService.getParkingState(getRequest);
		getResponse.getWriter().print(result);
		return null;
	}

}
