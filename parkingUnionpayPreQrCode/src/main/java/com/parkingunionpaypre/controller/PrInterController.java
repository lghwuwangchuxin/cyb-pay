package com.parkingunionpaypre.controller;

import com.parkingunionpaypre.service.PrSwiftUnionPayService;
import com.parkingunionpaypre.service.SwUionpayPrePayNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/parkingunionpayprerqrcode")
public class PrInterController {

    private static final Logger logger = LoggerFactory.getLogger(PrInterController.class);

    @Autowired
    private PrSwiftUnionPayService prSwiftUnionPayService;
    @Autowired
    private SwUionpayPrePayNotifyService swUionpayPrePayNotifyService;

    @ResponseBody
    @RequestMapping(value = "/gw", method = RequestMethod.GET)
    public String swunionpayCallCenterSync(HttpServletRequest request) throws Exception {
        logger.info("进入到银联条码前置渠道支付入口---------swunionpayCallCenterSync");
        String msg = request.getParameter("msg");
        String rspXml = prSwiftUnionPayService.swunionpayCallCenterSync(msg);
        return rspXml;
    }

    @ResponseBody
    @RequestMapping(value = "/payNotifyResult", method = RequestMethod.POST)
    public String payNotifyResult(HttpServletRequest request) throws Exception {
        logger.info("进入到银联条码前置渠道支付支付通知结果接收---------payNotifyResult");
        String msg = getReqXML(request);
        logger.info("收到银联条码前置渠道支付支付通知数据：" +msg);
        String rspStr = swUionpayPrePayNotifyService.payNotify(msg);
        logger.info("收到银联条码前置渠道支付支付通知数据响应：" +rspStr);
        return "success";
    }

    // 统一接收流数据
    private String getReqXML(HttpServletRequest request) {
        // 接收请求数据
        String reqXml = "";
        try {
            InputStream inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String tempStr = "";
            while ((tempStr = reader.readLine()) != null) {
                reqXml += tempStr;
            }
            reader.close();
            inputStream.close();
        } catch (Exception e) {
            // 接收请求报文异常
            e.printStackTrace();
            return null;
        }
        return reqXml;
    }


}
