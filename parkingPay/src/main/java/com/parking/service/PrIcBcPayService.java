package com.parking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "parkingicbcpay")
public interface PrIcBcPayService {

    //fein对外暴露服务统一入口，同步方式
    @ResponseBody
    @RequestMapping(value = "/parkingicbcpay/gw", method = RequestMethod.GET)
    public String uniCallProvider(@RequestParam(value = "msg")String msg) throws Exception;
}
