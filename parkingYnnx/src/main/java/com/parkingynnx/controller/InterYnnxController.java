package com.parkingynnx.controller;

import com.parkingynnx.service.PrYxBeCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/parkingynnx")
public class InterYnnxController {

    private static final Logger logger= LoggerFactory.getLogger(InterYnnxController.class);
    @Autowired
    private PrYxBeCodeService prYxBeCodeService;
    @ResponseBody
    @RequestMapping(value = "/gw", method = RequestMethod.GET)
    public String ynnxPay(HttpServletRequest request) throws Exception {
        logger.info("进入云南农信渠道-----ynnxPay");
        String msg = request.getParameter("msg");
        String rspXml = prYxBeCodeService.YxBeCodeCallCenterSync(msg);
        return rspXml;
    }

}
