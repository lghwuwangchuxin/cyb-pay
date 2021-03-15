package com.parkinghx.service.impl;

import com.parkinghx.dto.HxFiles;
import com.parkinghx.dto.PayNotifyReq;
import com.parkinghx.service.HxPayNotifyService;
import com.parkinghx.service.PrParkingService;
import com.parkinghx.util.ConfigUtil;
import com.parkinghx.util.HxUtil;
import com.parkinghx.util.HxbConstants;
import com.parkinghx.util.XmlUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.text.MessageFormat;

@Service("hxPayNotifyService")
public class HxPayNotifyServiceImpl implements HxPayNotifyService {

    private static final Logger logger = LoggerFactory.getLogger(HxPayNotifyServiceImpl.class);
    @Autowired
    private PrParkingService prParkingService;

    /**
     * 支付通知
     * @param msg
     * @return
     * @throws Exception
     */
    @Override
    public String payNotifyResult(String msg) throws Exception {
        logger.info("接收华夏银行支付通知-----payNotifyResult");
        JSONObject rspJson = JSONObject.fromObject(msg);
        HxFiles hxFiles = null;
        try {
            // 解密操作
            // 证书路径扫描
            hxFiles = ConfigUtil.scanFilesWithRecursion(MessageFormat.format(ConfigUtil.getValue("mchntPaht"), rspJson.getString("appid")));
            logger.info("证书扫描路径："+hxFiles);
            String body = HxUtil.openEnvelope(rspJson.getString(HxbConstants.RSP_BODY), rspJson.getString("appid"), hxFiles);
            logger.info("支付通知解密body数据；" +body);
            // 解析数据
            JSONObject rspJsons = JSONObject.fromObject(body);
            prParkingService.parkingCallCenterSync(genePayNotify(rspJsons));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("处理支付通知异常:" +e.getMessage());
        }
        JSONObject reqJsonBody = reqJsonBody();
        String signatureStr = HxUtil.signStr(reqJsonBody.toString(), rspJson.getString("appid"), hxFiles);
        logger.info("支付通知签名数据：" +signatureStr);
        String encStr = HxUtil.makeEnvelope(reqJsonBody.toString(), rspJson.getString("appid"), hxFiles);
        logger.info("支付通知加密数据：" +encStr);
        JSONObject reqJson = new JSONObject();
        reqJson.put("version", "1.0"); // 版本号
        reqJson.put("appid", rspJson.getString("appid"));// appid
        reqJson.put("signature", signatureStr);
        reqJson.put(HxbConstants.RSP_BODY, encStr);

        return reqJson.toString();
    }

    private JSONObject reqJsonBody() {
        JSONObject reqJsonBody = new JSONObject();
        reqJsonBody.put("status", "success");
        return reqJsonBody;
    }

    // 支付 通知
    private String genePayNotify(JSONObject jsonObject) throws JAXBException {
        PayNotifyReq payNotifyReq = new PayNotifyReq();
        payNotifyReq.setPayTime(jsonObject.getString("successTime")); // 支付时间
        payNotifyReq.setPayId(jsonObject.getString("channelNo")); // 系统交易流水号
        payNotifyReq.setPayType(getPayTypes(jsonObject.getString("payMethod"))); // 支付渠道

        payNotifyReq.setOrderId(jsonObject.getString("orderNo")); // 商户订单号
        payNotifyReq.setTxnAmt(jsonObject.getString("amount")); // 交易金额
        payNotifyReq.setTradeStatus(getBillTradeStatus((String) jsonObject.getString("status"))); // 订单状态
        payNotifyReq.setService("payNotify");// 支付服务名称
        String reqXml = XmlUtil.ObjToXml(payNotifyReq,PayNotifyReq.class);

        return reqXml;
    }

    private String getBillTradeStatus(String status) {
        if ("100".equals(status)) {
            return  "Paid";
        } else if ("101".equals(status)) {
            return  "UnPaid";
        } else if ("102".equals(status)) {
            return  "PayFail";
        } else {
            return "Unknown";
        }
    }

    private String getPayTypes(String payMethod) {
        if ("ALIPAY".equals(payMethod)) {
             return "12";
        } else if ("WXPAY".equals(payMethod)) {
            return "13";
        } else {
            return "14";
        }
    }
}
