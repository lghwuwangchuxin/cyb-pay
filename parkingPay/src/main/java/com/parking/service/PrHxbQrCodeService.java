package com.parking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "parkinghx")
public interface PrHxbQrCodeService {

    //fein对外暴露服务统一入口，同步方式
    @ResponseBody
    @RequestMapping(value = "/parkinghx/gwold", method = RequestMethod.GET)
    public String hxbQrCodeCallCenterSync(@RequestParam(value = "msg")String msg) throws Exception;

    //fein对外暴露服务统一入口，同步方式 下单
    @ResponseBody
    @RequestMapping(value = "/parkinghx/gwnew", method = RequestMethod.GET)
    public String hxbNewQrCodeCallCenterSync(@RequestParam(value = "msg")String msg) throws Exception;

    //fein对外暴露服务统一入口，同步方式  主扫下单
    @ResponseBody
    @RequestMapping(value = "/parkinghx/hxcbpaytrade", method = RequestMethod.GET)
    public String cbPayTrade(@RequestParam(value = "msg")String msg) throws Exception;

    //fein对外暴露服务统一入口，同步方式  支付结果查询
    @ResponseBody
    @RequestMapping(value = "/parkinghx/queryPayTrade", method = RequestMethod.GET)
    public String queryCbPayTrade(@RequestParam(value = "msg")String msg) throws Exception;


}
