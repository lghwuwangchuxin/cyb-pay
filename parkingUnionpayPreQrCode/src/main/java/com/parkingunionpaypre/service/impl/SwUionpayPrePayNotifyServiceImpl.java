package com.parkingunionpaypre.service.impl;

import com.parkingunionpaypre.dto.PayNotifyReq;
import com.parkingunionpaypre.dto.SwUnionOrderPayRsp;
import com.parkingunionpaypre.service.PrParkingService;
import com.parkingunionpaypre.service.SwUionpayPrePayNotifyService;
import com.parkingunionpaypre.util.SortUtil;
import com.parkingunionpaypre.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.Map;

@Service("swUionpayPrePayNotifyService")
public class SwUionpayPrePayNotifyServiceImpl implements SwUionpayPrePayNotifyService {

    private static final Logger logger = LoggerFactory.getLogger(SwUionpayPrePayNotifyServiceImpl.class);

    @Autowired
    private PrParkingService prParkingService;
    @Override
    public String payNotify(String result) throws Exception {
        logger.info("进入接收条码前置支付结果通知---------payNotify");
        SwUnionOrderPayRsp rsp = null;
        try {
            Map<String,Object> resultMap = SortUtil.xmlStrToMap(result);
            if("0".equals(resultMap.get("status")) && "0".equals(resultMap.get("result_code"))) {
                if (resultMap.containsKey("sign")) {
                    //boolean i = SortUtil.checkParam(resultMap, mreq.getKey());
                    //logger.info("验签结果为--i:"+i);
                    rsp = (SwUnionOrderPayRsp) XmlUtil.XmlToObj(result, SwUnionOrderPayRsp.class);
                    prParkingService.parkingCallCenterSync(genePayNotify(rsp));
                }
            }

        } catch (Exception e) {
            logger.error("处理接收支付结果异常：" +e.getMessage());
        }
        return "success";
    }

    // 支付 通知
    private String genePayNotify(SwUnionOrderPayRsp rsp) throws JAXBException {
        PayNotifyReq payNotifyReq = new PayNotifyReq();
        payNotifyReq.setPayTime(rsp.getTime_end()); // 支付时间
        payNotifyReq.setPayId(rsp.getTransaction_id()); // 系统交易流水号
        payNotifyReq.setPayType(getPayTypes(rsp.getTrade_type())); // 支付渠道

        payNotifyReq.setOrderId(rsp.getOut_trade_no()); // 商户订单号
        payNotifyReq.setTxnAmt(rsp.getTotal_fee()); // 交易金额
        payNotifyReq.setTradeStatus(getBillTradeStatus(rsp.getPay_result())); // 订单状态
        payNotifyReq.setService("payNotify");// 支付服务名称
        String reqXml = XmlUtil.ObjToXml(payNotifyReq,PayNotifyReq.class);

        return reqXml;
    }

    private String getBillTradeStatus(String status) {
        status = status.equals("0") ? "Paid" : "PaidFail";
        return  status;
    }

    /**
     * 支付宝：pay.alipay.jspay
     *
     * 微信：pay.weixin.jspay
     *
     * 银联：pay.unionpay.native
     * @param payMethod
     * @return
     */
    private String getPayTypes(String payMethod) {
        if ("pay.alipay.jspay".equals(payMethod)) {
            return "12";
        } else if ("pay.weixin.jspay".equals(payMethod)) {
            return "13";
        } else if ("pay.unionpay.native".equals(payMethod)){
            return "14";
        }

        return  "0";
    }
}
