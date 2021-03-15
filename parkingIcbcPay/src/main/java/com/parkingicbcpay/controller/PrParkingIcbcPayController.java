package com.parkingicbcpay.controller;


import com.alibaba.fastjson.JSONObject;
import com.parkingicbcpay.dto.OrderInfoRsp;
import com.parkingicbcpay.service.PayResultService;
import com.parkingicbcpay.service.PrParkingIcbcPayService;
import com.parkingicbcpay.service.QueryOrderInfoService;
import com.parkingicbcpay.util.EJTRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("parkingicbcpay")
public class PrParkingIcbcPayController {

    private static final Logger logger = LoggerFactory.getLogger(PrParkingIcbcPayController.class);
    @Autowired
    private PrParkingIcbcPayService prParkingIcbcPayService;
    @Autowired
    private QueryOrderInfoService queryOrderInfoService;
    @Autowired
    private PayResultService payResultService;

    @ResponseBody
    @RequestMapping(value = "/gw", method = RequestMethod.GET)
    public String prparkingIcbc(HttpServletRequest request) throws Exception {
        logger.info("进入接收工行无感渠道----prparkingIcbc");
        String msg = request.getParameter("msg");
        logger.info("接收到的报文：" +msg);
        String rspXml = prParkingIcbcPayService.parkingIcbcPayCallCenterSync(msg);
        return rspXml;
    }

    // 提供给工行 渠道查询订单 url
    @ResponseBody
    @RequestMapping(value = "/getPreOrder", method = RequestMethod.GET)
    public String getOrder(HttpServletRequest request) throws Exception {
        logger.info("---------接收到工行平台查询订单接口请求----------");
        OrderInfoRsp rsp = queryOrderInfoService.getOrderInfo(request);
        String rspJson = JSONObject.toJSONString(rsp);
        logger.info("查询订单返回报文：" +rspJson);
        return  rspJson;
    }


    // 提供给工行 渠道支付通知 url
    @ResponseBody
    @RequestMapping(value = "/payNotifyResult", method = RequestMethod.GET)
    public String payNotifyResult(HttpServletRequest request) throws Exception {
        logger.info("---------接收到工行平台支付通知订单接口请求----------");
        String data = EJTRequest.getRequestMessage(request);
        logger.info("订单支付结果接口接收数据："+data);
        String rsp = payResultService.recPrePayNotifyResult(data);
        logger.info("支付通知返回报文：" +rsp);
        return  rsp;
    }




}
