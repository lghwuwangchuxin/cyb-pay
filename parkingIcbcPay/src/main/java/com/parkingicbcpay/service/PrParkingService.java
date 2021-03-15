package com.parkingicbcpay.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "parkingPay")
public interface PrParkingService {
	// feign对外暴露服务统一入口，同步方式
	@ResponseBody
	@RequestMapping("/parking/parkingCallCenterSync")
	public String parkingCallCenterSync(@RequestParam(value = "msg") String msg) throws Exception;

	// fegin对外暴露服务统一入口，异步方式
	@ResponseBody
	@RequestMapping("/parking/parkingCallCenterAsyn")
	public String parkingCallCenterAsyn(@RequestParam(value = "msg") String msg) throws Exception;
}
