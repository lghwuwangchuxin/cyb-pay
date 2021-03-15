package com.parkinghx.controller;

import com.parkinghx.dto.HxbOrderQueryReq;
import com.parkinghx.dto.HxbOrderQueryRsp;
import com.parkinghx.dto.HxbScanPayReq;
import com.parkinghx.dto.HxbScanPayRsp;
import com.parkinghx.service.HxPayNotifyService;
import com.parkinghx.service.HxbNewScanPayService;
import com.parkinghx.service.HxbOrderNewQueryService;
import com.parkinghx.service.PrHxbQrCodeService;
import com.parkinghx.util.XmlUtil;
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
@RequestMapping("/parkinghx")
public class InterHxController {

    private static final Logger logger= LoggerFactory.getLogger(InterHxController.class);
    @Autowired
    private PrHxbQrCodeService prHxbQrCodeService;
    @Autowired
    private HxbNewScanPayService hxbNewScanPayService;
    @Autowired
    private HxbOrderNewQueryService hxbOrderNewQueryService;
    @Autowired
    private HxPayNotifyService hxPayNotifyService;

    @ResponseBody
    @RequestMapping(value = "/gwold", method = RequestMethod.GET)
    public String hxBecodeCallCenterSync(HttpServletRequest request) throws Exception {
        logger.info("接收到华夏银行渠道支付被扫入口---------hxBecodeCallCenterSync");
        String msg = request.getParameter("msg");
        String rspXml = prHxbQrCodeService.hxbQrCodeCallCenterSync(msg);
        return rspXml;
    }

    @ResponseBody
    @RequestMapping(value = "/gwnew", method = RequestMethod.GET)
    public String hxNewBecodeCallCenterSync(HttpServletRequest request) throws Exception {
        logger.info("接收到华夏银行渠道支付入口---------hxNewBecodeCallCenterSync");
        String msg = request.getParameter("msg");
        String rspXml = prHxbQrCodeService.hxbQrCodeCallCenterSync(msg);
        logger.info("响应华夏银行返回报文:" +rspXml);
        return rspXml;
    }


    @ResponseBody
    @RequestMapping(value = "/hxcbpaytrade", method = RequestMethod.GET)
    public String cbPayTrade(HttpServletRequest request) throws Exception {
        logger.info("接收到华夏银行主扫下单入口----cbPayTrade");
        String msg = request.getParameter("msg");
        logger.info("接收到主扫华夏下单报文:" +msg);
        HxbScanPayRsp rsp = hxbNewScanPayService.scanCbQrCodePay((HxbScanPayReq) XmlUtil.XmlToObj(msg, HxbScanPayReq.class));
        String rspXml = XmlUtil.ObjToXml(rsp, HxbScanPayRsp.class);
        logger.info("华夏银行主扫下单返回报文：" +rspXml);
        return rspXml;
    }


    @ResponseBody
    @RequestMapping(value = "/queryPayTrade", method = RequestMethod.GET)
    public String queryPayTrade(HttpServletRequest request) throws Exception {
        logger.info("接收到华夏银行订单查询入口----queryPayTrade");
        String msg = request.getParameter("msg");
        logger.info("接收到华夏订单查询报文:" +msg);
        HxbOrderQueryRsp rsp = hxbOrderNewQueryService.queryPayTrade((HxbOrderQueryReq) XmlUtil.XmlToObj(msg, HxbOrderQueryReq.class));
        String rspXml = XmlUtil.ObjToXml(rsp, HxbOrderQueryRsp.class);
        logger.info("华夏银行支付结果查询返回报文：" +rspXml);
        return rspXml;
    }

    @RequestMapping(value = "/payNotifyResult", method = RequestMethod.POST)
    public void payNotifyResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("接收华夏银行渠道支付结果通知--------payNotifyResult");
        String reqStr = getReqXML(request);
        logger.info("接收到支付数据：" +reqStr);
        String rspStr = hxPayNotifyService.payNotifyResult(reqStr);
        logger.info("响应华夏银行支付数据：" +rspStr);
        response.getWriter().println(rspStr);
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
