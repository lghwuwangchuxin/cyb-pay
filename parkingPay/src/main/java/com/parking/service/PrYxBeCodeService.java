package com.parking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@FeignClient(value = "parkingynnx")
public interface PrYxBeCodeService {

    @ResponseBody
    @RequestMapping(value = "/parkingynnx/gw")
    public String prYnnxContent(@RequestParam(value = "msg") String msg) throws Exception;
}
