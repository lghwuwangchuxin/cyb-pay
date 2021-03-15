package com.parkingunionpay.controller;

import com.parkingunionpay.service.PrParkingUnionPayService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/parkingunionsh")
public class PrParkingUnionPayController {

    private static final Logger logger = LoggerFactory.getLogger(PrParkingUnionPayController.class);
    @Autowired
    PrParkingUnionPayService prParkingUnionPayService;

    @ResponseBody
    @RequestMapping(value = "/gw", method = RequestMethod.GET)
    public String  parkingUnionPayCallCenterSync(HttpServletRequest httpRequest) throws Exception {
       String reqXml = "";
        // 接收请求数据
        reqXml = httpRequest.getParameter("msg");
        logger.info("接收到数据:\n" + reqXml);
        try {
            String rspXml = prParkingUnionPayService.parkingUnionPayCallCenterSync(reqXml);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return reqXml;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return reqXml;
    }
}
