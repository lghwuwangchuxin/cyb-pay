package com.parkingunionpay.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "parkingPay")
public interface PrParkingService {

    @ResponseBody
    @RequestMapping(value = "/parking/parkingCallCenterSync", method = RequestMethod.GET)
    public String parkingCallCenterSync(@RequestParam(value = "msg") String msg);
}
