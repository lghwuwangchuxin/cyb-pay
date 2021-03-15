package com.parking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "parkingunionpayprerqrcode")
public interface PrSwiftUnionPayService {

    @ResponseBody
    @RequestMapping(value = "/parkingunionpayprerqrcode/gw")
    public String prUnionpayPreContent(@RequestParam(value = "msg") String msg) throws Exception;
}


