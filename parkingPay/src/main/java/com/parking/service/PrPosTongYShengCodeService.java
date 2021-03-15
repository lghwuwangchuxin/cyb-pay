package com.parking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(value = "parkingYsheng")
public interface PrPosTongYShengCodeService {

    @ResponseBody
    @RequestMapping(value = "/gw")
    public String prPosTongYShengContent(@RequestParam(value = "msg") String msg) throws Exception;
}
