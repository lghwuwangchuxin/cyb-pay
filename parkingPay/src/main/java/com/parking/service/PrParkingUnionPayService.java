package com.parking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "parkingunionsh")
public interface PrParkingUnionPayService {

    @ResponseBody
    @RequestMapping(value = "/parkingunionsh/gw", method = RequestMethod.GET)
    public String parkingUnionPayCallCenterSync(@RequestParam(value = "msg") String msg) throws Exception;
}
