package com.parking.controller;

import com.parking.dto.*;
import com.parking.service.*;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payparking")
public class InterParkingController {

    private static final Logger logger = LoggerFactory.getLogger(InterParkingController.class);

    byte [] DESKEY = new BASE64Decoder().decodeBuffer(ConfigUtil.getValue("3DESKEY"));
    @Autowired
    private ParkingInOutService parkingInOutService;
    @Autowired
    private ParkingOrderService parkingOrderService;
    @Autowired
    private UnQrCodeTradePayService unQrCodeTradePayService;
    @Autowired
    private QrCodeQueryOrderService qrCodeQueryOrderService;
    @Autowired
    private ParkingPrePayTradeOrderService parkingPrePayTradeOrderService;
    @Autowired
    private ParkingQrCodeCbPayTradeOrderService parkingQrCodeCbPayTradeOrderService;
    @Autowired
    private ParkingChannelQueryOrderService parkingChannelQueryOrderService;

    public InterParkingController() throws IOException {
    }

    @ResponseBody
    @RequestMapping("/gw")
    public void gateWays(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("进入支付前置服务--------");
        BaseXml rsp = new BaseXml();
        setBaseRsp(rsp ,RespUtil.CLIENT_INFO_ERR,"客户端异常");
        String reqXml = "";
        String rspXml = "";
        String service = "";
        String decryptXml= "";
        String osVersion = "";

        Map<String, String> xmlTypeMap = new HashMap<String, String>(12);// 头属性
        BASE64Encoder  encoder = new BASE64Encoder();
        // 接收请求数据
        try {
            reqXml = getReqXML(request);
        } catch (Exception e) {
            // 接收请求报文异常
            e.printStackTrace();
        }
        logger.info("接收到数据:\n" + reqXml);
        try {
            if (StringUtil.checkNullString(reqXml)) {
                setBaseRsp(rsp,RespUtil.noResult, "接收数据异常");
                rspXml = XmlUtil.ObjToXml(rsp,BaseXml.class);
                response.getWriter().print(encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY)));
                return;
            }
             // 解密数据
            DecryptForClient decryptForClient = new DecryptForClient();
            decryptXml = new String(DesUtil.decrypt(new BASE64Decoder().decodeBuffer(reqXml),DESKEY));
            logger.info("解密原文:" +decryptXml);
            xmlTypeMap = decryptForClient.getHead(decryptXml);
            service = (String) xmlTypeMap.get("service");
            osVersion = (String) xmlTypeMap.get("osVersion");

            System.out.println("+--------------------------------------------------------+");
            System.out.println("|                                                        |");
            System.out.println("|                  parkingPay【" + service+ "】                                          ");
            System.out.println("|                                                        |");
            System.out.println("+--------------------------------------------------------+");
            if (service.equals("enterPakring")) {  //入场通知服务
                EnterParkingRsp mrsp = parkingInOutService.enterParkingNotify((EnterParkingReq) XmlUtil.XmlToObj(decryptXml, EnterParkingReq.class));
                 rspXml = XmlUtil.ObjToXml(mrsp,EnterParkingRsp.class);
                 logger.info("调用[" +service +"]返回:" +rspXml);
                 rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            } else if (service.equals("outParking")) {// 出场通知服务
                OutParkingRsp mrsp = parkingInOutService.outParkingNotify((OutParkingReq) XmlUtil.XmlToObj(decryptXml, OutParkingReq.class));
                rspXml = XmlUtil.ObjToXml(mrsp,OutParkingRsp.class);
                logger.info("调用[" +service +"]返回:" +rspXml);
                rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            } else if (service.equals("payNotifyResult")) { // 支付通知
                PayNotifyRsp mrsp = parkingOrderService.notifyPayOrderAResult((PayNotifyReq) XmlUtil.XmlToObj(decryptXml, PayNotifyReq.class));
                rspXml = XmlUtil.ObjToXml(mrsp,PayNotifyRsp.class);
                logger.info("调用[" +service +"]返回:" +rspXml);
                rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            } else if (service.equals("geneAutoPayOrder")) { // 无感支付
                ParkingAutoPayRsp mrsp = parkingOrderService.geneParkTerminalAutoOrder((ParkingAutoPayReq) XmlUtil.XmlToObj(decryptXml, ParkingAutoPayReq.class));
                rspXml = XmlUtil.ObjToXml(mrsp,ParkingAutoPayRsp.class);
                logger.info("调用[" +service +"]返回:" +rspXml);
                rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            } else if (service.equals("getPreOrder")) { // 预缴服务
                ParkingPreOrderQueryRsp mrsp = parkingOrderService.queryParkingPrePayOrder((ParkingPreOrderQueryReq) XmlUtil.XmlToObj(decryptXml, ParkingPreOrderQueryReq.class));
                rspXml = XmlUtil.ObjToXml(mrsp,ParkingPreOrderQueryRsp.class);
                logger.info("调用[" +service +"]返回:" +rspXml);
                rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            } else if (service.equals("qrcodeTradePay")) { //   被扫 二维码下单支付
                ApplyOrderRsp mrsp = unQrCodeTradePayService.unPayTradeService((ApplyOrderReq) XmlUtil.XmlToObj(decryptXml, ApplyOrderReq.class));
                rspXml = XmlUtil.ObjToXml(mrsp,ApplyOrderRsp.class);
                logger.info("调用[" +service +"]返回:" +rspXml);
                rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            } else if (service.equals("queryAtmResult")) { //   被扫二维码 多渠道 订单查询
                QueryAtmResultRsp mrsp = qrCodeQueryOrderService.queryAtmResultService((QueryAtmResultReq) XmlUtil.XmlToObj(decryptXml, QueryAtmResultReq.class));
                rspXml = XmlUtil.ObjToXml(mrsp,QueryAtmResultRsp.class);
                logger.info("调用[" +service +"]返回:" +rspXml);
                rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            } else if (service.equals("qrcodeCbPayTrade")) { //   主扫二维码下单
                ParkingAutoPayRsp mrsp = parkingQrCodeCbPayTradeOrderService.payCbTrade((ParkingAutoPayReq) XmlUtil.XmlToObj(decryptXml, ParkingAutoPayReq.class));
                rspXml = XmlUtil.ObjToXml(mrsp,ParkingAutoPayRsp.class);
                logger.info("调用[" +service +"]返回:" +rspXml);
                rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            } else if(service.equals("queryH5OrCbOrder")) { // 多渠道 主扫订单 或者公众号 h5订单查询
                ParkingTradeOrderQueryRsp mrsp = parkingChannelQueryOrderService.queryOrder((ParkingTradeOrderQueryReq) XmlUtil.XmlToObj(decryptXml, ParkingTradeOrderQueryReq.class));
                rspXml = XmlUtil.ObjToXml(mrsp,ParkingTradeOrderQueryRsp.class);
                logger.info("调用[" +service +"]返回:" +rspXml);
                rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            } else {
                setBaseRsp(rsp, RespUtil.noResult, CommEnum.PARKING_NO_APP_SERVICE_DESC.getRspMsg());
                rspXml = XmlUtil.ObjToXml(rsp,BaseXml.class);
                rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            }
            response.getWriter().print(rspXml);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                rspXml = XmlUtil.ObjToXml(rsp,BaseXml.class);
                response.getWriter().print(encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void setBaseRsp(BaseXml rsp, String clientInfoErr, String desc) {
        rsp.setRespCode(clientInfoErr);
        rsp.setRespDesc(desc);
    }

    @RequestMapping("/webtrade")
    public void h5Trade(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //String reqXml = getReqXML(request);
        String reqXml = request.getParameter("msg");
        response.setCharacterEncoding("UTF-8");
        logger.info("接收到原密文数据：" +reqXml);
        //reqXml = new String(DesUtil.decrypt(new BASE64Decoder().decodeBuffer(reqXml),DESKEY));
        logger.info("接收到数据:\n" + reqXml);
        ParkingPrePayOrderRsp mrsp = parkingPrePayTradeOrderService.prePayTradeOrder((ParkingPrePayOrderReq) XmlUtil.XmlToObj(reqXml,ParkingPrePayOrderReq.class));
        if (null== mrsp || !CommEnum.SUCCESS_COID.getRspCode().equals(mrsp.getRespCode())) {
            logger.info("h5下单响应：" +mrsp.getRespDesc());
            //request.setAttribute("error", "异常");
            request.getRequestDispatcher("/error.html").forward(request, response);

        } else {
            response.setCharacterEncoding("UTF-8");
            if (CommEnum.H5_PAY_METHO_1.getRspCode().equals(mrsp.getFrowdToRediToInput())) {
                response.setHeader("Authorization",mrsp.getAuthorization());
                response.sendRedirect(mrsp.getH5PayParams());
            } else if (CommEnum.H5_PAY_METHO_2.getRspCode().equals(mrsp.getFrowdToRediToInput())){
                response.setContentType("text/html");
                response.getWriter().println(mrsp.getH5PayParams());
            }
        }
    }

    @ResponseBody
    @RequestMapping("/h5PayaTrade")
    public void h5PayTrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String reqXml = getReqXML(request);
        response.setCharacterEncoding("UTF-8");
        BASE64Encoder  encoder = new BASE64Encoder();
        String rspXml = "";
        logger.info("接收到原密文数据：" +reqXml);
        reqXml = new String(DesUtil.decrypt(new BASE64Decoder().decodeBuffer(reqXml),DESKEY));
        logger.info("接收到数据:\n" + reqXml);
        ParkingPrePayOrderRsp mrsp = parkingPrePayTradeOrderService.prePayTradeOrder((ParkingPrePayOrderReq) XmlUtil.XmlToObj(reqXml,ParkingPrePayOrderReq.class));
        logger.info("接收到数据响应数据:\n" + mrsp.toString());
        if (null== mrsp || !CommEnum.SUCCESS_COID.getRspCode().equals(mrsp.getRespCode())) {
            logger.info("h5下单响应：" +mrsp.getRespDesc());
            BaseXml rsp = new BaseXml();
            setBaseRsp(rsp, RespUtil.orderh5Fail, "h5下单异常了,请检查");
            rspXml = XmlUtil.ObjToXml(rsp,BaseXml.class);
        } else {
            rspXml = XmlUtil.ObjToXml(mrsp,ParkingPrePayOrderRsp.class);
        }
        logger.info("h5下单返回数据：" +rspXml);
        rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
        response.getWriter().print(rspXml);
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
