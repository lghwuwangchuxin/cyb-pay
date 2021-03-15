package com.icbcbecode.controller;

import com.icbcbecode.service.PrIcBcBeCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("parkingicbccode")
public class PrIcBcBeCodeController {

    private static final Logger logger = LoggerFactory.getLogger(PrIcBcBeCodeController.class);

    @Autowired
    private PrIcBcBeCodeService prIcBcBeCodeService;

    @ResponseBody
    @RequestMapping(value = "/gw", method = RequestMethod.GET)
    public String prIcBcBeCodeSync(HttpServletRequest request) throws Exception {
        logger.info("进入工行被扫服务入口----prIcBcBeCodeSync");
        String msg = request.getParameter("msg");
        logger.info("接收到报文msg" +msg);
        String rspXml = prIcBcBeCodeService.prIcBcBeCodeSyncMethod(msg);
        logger.info("工行系统返回报文:" +rspXml);
        return rspXml;
    }

}
