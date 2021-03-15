package com.parking.controller;


import com.parking.service.PrParkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/parking")
public class PrParkingController {

    private static final Logger logger = LoggerFactory.getLogger(PrParkingController.class);
    @Autowired
    private PrParkingService prParkingService;

    // feign对外暴露服务统一入口，同步方
    @ResponseBody
    @RequestMapping(value = "/parkingCallCenterSync",method = RequestMethod.GET)
    public void parkingCallCenterSync(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Exception {
        String reqXml = "";
        String rspXml = "";
        reqXml = httpRequest.getParameter("msg");
        logger.info("接收到数据:\n" + reqXml);
        try {
            rspXml =  prParkingService.parkingCallCenterSync(reqXml);
            httpResponse.getWriter().print(rspXml);
        } catch (Exception e) {
            e.printStackTrace();
            try {

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
