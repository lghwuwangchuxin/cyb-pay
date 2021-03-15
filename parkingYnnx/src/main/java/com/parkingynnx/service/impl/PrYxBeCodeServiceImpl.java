package com.parkingynnx.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.parkingynnx.dto.*;
import com.parkingynnx.service.*;
import com.parkingynnx.util.DecryptForClient;
import com.parkingynnx.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;


@Service("prYxBeCodeService")
public class PrYxBeCodeServiceImpl implements PrYxBeCodeService {

    private static final Logger logger = LoggerFactory.getLogger(PrYxBeCodeServiceImpl.class);
    @Inject
    private YnNxBeCodeTradeUnionPayService ynNxBeCodeTradeUnionPayService;
    @Inject
    private QueryTradeService queryTradeService;
    @Inject
    private CancelTradeService cancelTradeService;
    @Inject
    private RefundTradeService refundTradeService;

    @Override
    public String YxBeCodeCallCenterSync(String msg) throws Exception {
        logger.info("进入云南农信接入系统入口：YxBeCodeCallCenterSync-----");
        logger.info("收到消息：" + msg);
        String rspXml = ""; // 响应报文
        String rspObj = "";
        Map<String, String> xmlTypeMap = new HashMap<String, String>();
        BASE64Encoder encoder = new BASE64Encoder();
        DecryptForClient decryptForClinet = new DecryptForClient();
        /*
         * String privateKey=""; //私钥 String desKey = "";// 客户端随机生成的3Deskey desKey =
         * decryptForClinet.decodeDesKey(msg,privateKey);
         */
        xmlTypeMap = decryptForClinet.getHead(msg);
        String service = (String) xmlTypeMap.get("service");
        logger.info("进入云南农信接入系统,调用服务[" + service + "]");
        try {
            // 各应用接收请求进行处理,并响应明文
            if (service.equals("notifyTradeResult")) {// 接收通知信息

            } else if (service.equals("queryOrderByPOST")) { // 被扫支付 交易查询-post
                // 交易查询-POST方式
                QueryTradeReq mreq = (QueryTradeReq) XmlUtil.XmlToObj(msg, QueryTradeReq.class);
                QueryTradeRsp mrsp = queryTradeService.queryTrade(mreq);
                rspObj = XmlUtil.ObjToXml(mrsp, QueryTradeRsp.class);
                logger.info("调用[" + service + "]返回：" + rspObj.toString());
                if (mrsp.getRespCode().equals("000000")) {
                    rspXml = "1|" + encoder.encode(rspObj.getBytes("utf-8"));
                } else {
                    rspXml = "0|" + mrsp.getRespCode() + "|" + encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
                }

            } else if (service.equals("BeCodeUnionPay")) { // 银联被扫支付
                BeCodeUnionPayReq mreq = (BeCodeUnionPayReq) XmlUtil.XmlToObj(msg, BeCodeUnionPayReq.class);
                BeCodeUnionPayRsp mrsp = ynNxBeCodeTradeUnionPayService.getYunBeCodeUnionPay(mreq);
                rspObj = XmlUtil.ObjToXml(mrsp, BeCodeUnionPayRsp.class);
                logger.info("调用[" + service + "] 返回的xml为：：" + rspObj.toString());
                if (mrsp.getRespCode().equals("000000")) {
                    rspXml = "1|" + encoder.encode(rspObj.getBytes("utf-8"));
                } else {
                    rspXml = "0|" + mrsp.getRespCode() + "|" + encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
                }
            } else if (service.equals("CancelOrderPost")) {  //交易撤销
                CancelTradeReq mreq = (CancelTradeReq) XmlUtil.XmlToObj(msg, CancelTradeReq.class);
                CancelTradeRsp mrsp = cancelTradeService.cancelTrade(mreq);
                rspObj = XmlUtil.ObjToXml(mrsp, CancelTradeRsp.class);
                logger.info("调用[" + service + "]返回：" + rspObj.toString());
                if (mrsp.getRespCode().equals("000000")) {
                    rspXml = "1|" + encoder.encode(rspObj.getBytes("utf-8"));
                } else {
                    rspXml = "0|" + mrsp.getRespCode() + "|" + encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
                }
            } else if (service.equals("RefundOrderPost")) {  //申请退款
                RefundTradeReq mreq=(RefundTradeReq) XmlUtil.XmlToObj(msg, RefundTradeReq.class);
                RefundTradeRsp mrsp=refundTradeService.refundTrade(mreq);
                rspObj =XmlUtil.ObjToXml(mrsp, RefundTradeRsp.class);
                logger.info("调用["+service+"]返回："+rspObj.toString());
                if(mrsp.getRespCode().equals("000000")) {
                    rspXml="1|"+encoder.encode(rspObj.getBytes("utf-8"));                   
                }else {
                    rspXml="0|"+mrsp.getRespCode()+"|"+encoder.encode(mrsp.getRespDesc().getBytes("utf-8"));
                }
            } else {
                rspXml = "0|999903|" + encoder.encode("查无此应用接口".getBytes("utf-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return rspXml;
    }

    @Override
    public String YxBeCodeCallCenterAsyn(String msg) throws Exception {
        return null;
    }
}
