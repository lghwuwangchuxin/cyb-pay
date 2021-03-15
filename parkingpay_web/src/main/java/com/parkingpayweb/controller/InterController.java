package com.parkingpayweb.controller;

import com.parkingpayweb.dto.BaseXml;
import com.parkingpayweb.dto.ParkingGetChannelMchntReq;
import com.parkingpayweb.dto.ParkingGetChannelMchntRsp;
import com.parkingpayweb.service.ParkingGetPayChannelMchntInfoService;
import com.parkingpayweb.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/parkingpayweb")
public class InterController {

    private static final Logger logger = LoggerFactory.getLogger(InterController.class);

    byte [] DESKEY = new BASE64Decoder().decodeBuffer(ConfigUtil.getValue("3DESKEY"));

    public InterController() throws IOException {
    }

    @Autowired
    private ParkingGetPayChannelMchntInfoService parkingGetPayChannelMchntInfoService;

    @ResponseBody
    @RequestMapping("/gw")
    public void getMchntInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("进入获取商户参数服务--------");
        BaseXml rsp = new BaseXml();
        setBaseRsp(rsp ,CommonEnun.CLIENT_INFO_ERR,"客户端异常");
        String reqXml = "";
        String rspXml = "";
        String service = "";
        String decryptXml= "";
        String osVersion = "";

        Map<String, String> xmlTypeMap = new HashMap<String, String>(12);// 头属性
        BASE64Encoder encoder = new BASE64Encoder();
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
                setBaseRsp(rsp, CommonEnun.noResult, "接收数据异常");
                rspXml = XmlUtil.ObjToXml(rsp, BaseXml.class);
                response.getWriter().print(encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY)));
                return;
            }
            // 解密数据
            DecryptForClient decryptForClient = new DecryptForClient();
            decryptXml = new String(DesUtil.decrypt(new BASE64Decoder().decodeBuffer(reqXml), DESKEY));
            logger.info("解密原文:" + decryptXml);
            xmlTypeMap = decryptForClient.getHead(decryptXml);
            service = (String) xmlTypeMap.get("service");
            osVersion = (String) xmlTypeMap.get("osVersion");

            System.out.println("+--------------------------------------------------------+");
            System.out.println("|                                                        |");
            System.out.println("|                  parkingpayweb【" + service + "】                                          ");
            System.out.println("|                                                        |");
            System.out.println("+--------------------------------------------------------+");
            if (service.equals("getMchntInfo")) {  // 获取停车场商户信息
                ParkingGetChannelMchntRsp mrsp = parkingGetPayChannelMchntInfoService.getParkingGetChannelMchntInfo((ParkingGetChannelMchntReq) XmlUtil.XmlToObj(decryptXml, ParkingGetChannelMchntReq.class));
                rspXml = XmlUtil.ObjToXml(mrsp, ParkingGetChannelMchntRsp.class);
                logger.info("调用[" + service + "]返回:" + rspXml);
                rspXml = encoder.encode(DesUtil.encrypt(rspXml.getBytes("utf-8"), DESKEY));
            }
            response.getWriter().print(rspXml);
        }catch (Exception e) {
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
