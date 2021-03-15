package com.parking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "parkingyshang")
public interface PrPosTongService {

    @ResponseBody
    @RequestMapping(value = "/parkingyshang/gw", method = RequestMethod.GET)
    public String yshangPosBcCallCenterSync(@RequestParam(value = "msg") String msg) throws Exception;

    @ResponseBody
    @RequestMapping(value ="/parkingyshang/preH5PayOrder", method = RequestMethod.GET)
    public String yshangH5PayTrade(@RequestParam(value = "msg") String msg) throws Exception;

    @ResponseBody
    @RequestMapping(value = "/parkingyshang/payCbTrade", method = RequestMethod.GET)
    public String yshangPayCbTrade(@RequestParam(value = "msg") String msg) throws Exception;

     // 公众 号 和 二维码订单支付结果查询
    @ResponseBody
    @RequestMapping(value = "/parkingyshang/queryOrder", method = RequestMethod.GET)
    public String yshangQuerOrder(@RequestParam(value = "msg") String msg) throws Exception;


}
