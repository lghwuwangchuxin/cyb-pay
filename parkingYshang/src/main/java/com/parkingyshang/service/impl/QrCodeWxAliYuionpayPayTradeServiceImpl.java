package com.parkingyshang.service.impl;

import com.parkingyshang.dto.*;
import com.parkingyshang.service.PrParkingService;
import com.parkingyshang.service.QrCodeWxAliYuionpayPayTradeService;
import com.parkingyshang.service.WxAliYunionCustInfoService;
import com.parkingyshang.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.bind.JAXBException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("qrCodeWxAliYuionpayPayTradeService")
public class QrCodeWxAliYuionpayPayTradeServiceImpl implements QrCodeWxAliYuionpayPayTradeService {

    private static final Logger logger= LoggerFactory.getLogger(QrCodeWxAliYuionpayPayTradeServiceImpl.class);
    @Autowired
    private WxAliYunionCustInfoService wxAliYunionCustInfoService;
    @Autowired
    private PrParkingService prParkingService;
    @Override
    public WxAliYuionpayOrderPayRsp payOrder(WxAliYuionpayOrderPayReq mreq) throws Exception {
        logger.info("进入银商渠道公众号渠道后台下单服务---------------payOrder");
        WxAliYuionpayOrderPayRsp mrsp = new WxAliYuionpayOrderPayRsp();
        setInitRspParams(mrsp,mreq);

        // 进行参数检查
        if(checkIsNull(mreq) <0) {
           setRspParams(mrsp, RespUtil.noResult, ComEnum.RESP_DESC_NO_RESULT.getResMsg());
        }

        // 获取 微信 支付宝  云闪付  openid userid
        try {
           
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // 组装json报文
            String jsonStr = geneOrderJson(mreq);
            String A = MerKeyUtil.getSHA256StrJava(jsonStr);  //sha256 加密后str
            SimpleDateFormat sim= new SimpleDateFormat("yyyyMMddHHmmss");
            String Timestamp=sim.format(new Date());
            String Nonce= StringUtil.getRomNum();
            String longstr=mreq.getAppId()+Timestamp+Nonce+A;

            String merKeyUtils = MerKeyUtil._64sha256_HMAC(longstr, mreq.getAppKey());     //hmac_sha_256
            String signatures = "";

            signatures=StringUtil.replaceBlank(merKeyUtils);  //去除 \t\n
            logger.info("Signatures签名为:"+signatures);
            StringBuffer sbtr=new StringBuffer("");
            sbtr.append("OPEN-BODY-SIG ").append("AppId=\"").append(mreq.getAppId()).append("\",Timestamp=\"").append(Timestamp)
                    .append("\",Nonce=\"").append(Nonce).append("\",Signature=\"").append(signatures).append("\"");
            logger.info("Authorization认证内容为:"+sbtr.toString());
            logger.info("开始请求pos通支付----------"+mreq.getSerialNumber()+"请求参数为："+jsonStr.toString());
            String rsp = null;
            if (ConfigUtil.getValue("FLAG").equals("0")) {
                rsp = HttpUtil.send1(mreq.getPayOrderUrl(), jsonStr, sbtr.toString(),HttpUtil.createHttpsClient());
            } else {
                HttpSSLClient sslclient = new HttpSSLClient();
                HashMap<String ,String> headers = new HashMap<String ,String>(12);
                headers.put("Authorization", sbtr.toString());
                rsp = sslclient.doHttpsPost(mreq.getPayOrderUrl(), jsonStr, "utf-8", headers);
            }

            logger.info("结束请求银商渠道支付----------"+mreq.getSerialNumber()+"响应参数为："+rsp);
            if(StringUtil.checkNullString(rsp)) {
                setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, ComEnum.TIME_OVER_TIME_DESC.getResMsg());
                return mrsp;
            }
             // 解析返回 json 数据
             JSONObject jsonRsp = JSONObject.fromObject(rsp);
            WxAliYuionpayOrderPayRsp payRsp = (WxAliYuionpayOrderPayRsp) FastJSONUtil.parseObject(rsp,WxAliYuionpayOrderPayRsp.class);
            if (null == payRsp) {
                setRspParams(mrsp, RespUtil.parseEx, ComEnum.ORDER_PAY_PARSE_EXCEPTION.getResMsg());
                return mrsp;
            }
            // 判断是否 下单成功 SUCCESS
            if (!StringUtil.checkStringsEqual(ComEnum.ERROR_SUCCESS.getResName(), payRsp.getErrCode())){
                mrsp.setRespCode(RespUtil.orderPayFail);
                mrsp.setRespDesc(payRsp.getErrMsg());
                return mrsp;
            }
            // 解析 jsPayRequest  待支付 元素
             if (ComEnum.TARGETSYS_WXPay.getResName().equals(payRsp.getTargetSys())) { // 微信
                 WxJsPay wxJsPay = (WxJsPay) FastJSONUtil.parseObject(payRsp.getJsPayRequest(),WxJsPay.class);
             }
        } catch (Exception e) {
            e.printStackTrace();
            setRspParams(mrsp, RespUtil.timeOut, ComEnum.TIME_OVER_TIME_DESC.getResMsg());
            return mrsp;
        }
        return mrsp;
    }

    // 订单支付组装Json报文
    private String geneOrderJson(WxAliYuionpayOrderPayReq mreq) {
        JSONObject jsonStr = new JSONObject();
        jsonStr.put("requestTimestamp", mreq.getRequestTimestamp());
        jsonStr.put("merOrderId",mreq.getMerOrderId());// 商户订单号
        jsonStr.put("mid", mreq.getMid()); // 商户号
        jsonStr.put("tid", mreq.getTid()); // 终端号
        jsonStr.put("orderDesc", mreq.getOrderDesc());// 订单描述
        jsonStr.put("originalAmount", mreq.getOriginalAmount()); // 订单原金额
        jsonStr.put("totalAmount", mreq.getTotalAmount()); // 支付金额
        jsonStr.put("notifyUrl", mreq.getNotifyUrl()); // 通知地址
        jsonStr.put("tradeType", mreq.getTradeType()); //
        if ("13".equals(mreq.getChannelId())) {// 微信
            jsonStr.put("subAppId", mreq.getSubAppId()); //appid
            jsonStr.put("subOpenId",mreq.getSubOpenId()); // 需获取openid
        } else {  // 支付宝 或者 云闪付
            jsonStr.put("userId", mreq.getUserId()); // 用户标识
        }
        return jsonStr.toString();
    }

    private void setRspParams(WxAliYuionpayOrderPayRsp mrsp, String noResult, String resMsg) {
        mrsp.setRespCode(noResult);
        mrsp.setRespDesc(resMsg);
    }

    // 参数检查
    private int checkIsNull(WxAliYuionpayOrderPayReq mreq) {
        if(StringUtil.checkNullString(mreq.getMerOrderId()) ||
                StringUtil.checkNullString(mreq.getMid()) ||
                StringUtil.checkNullString(mreq.getTid()) ||
                StringUtil.checkNullString(mreq.getInstMid())||
                StringUtil.checkNullString(mreq.getOriginalAmount()) ||
                StringUtil.checkNullString(mreq.getTotalAmount()) ||
                StringUtil.checkNullString(mreq.getTradeType()) ||
                StringUtil.checkNullString(mreq.getNotifyUrl()) ||
                StringUtil.checkNullString(mreq.getPayOrderUrl())) {
            return -1;
        }
        return 1;
    }

    // 初始化参数
    private void setInitRspParams(WxAliYuionpayOrderPayRsp mrsp, WxAliYuionpayOrderPayReq mreq) {
        mrsp.setRespCode(RespUtil.codeError);
        mrsp.setRespDesc(ComEnum.RESP_DESC.getResMsg());
        mrsp.setSerialNumber(mreq.getSerialNumber());
    }

    /**
     * 
     * @param mreq
     * @return  h5 前端支付  这里只需组装参数 前端发起支付 就可以了
     * @throws Exception
     */
    @Override
    public WxAliYuionpayOrderPayRsp frontPayOrder(WxAliYuionpayOrderPayReq mreq) throws Exception {
        logger.info("进入银商渠道公众号渠道H5下单服务---------------frontPayOrder");
        WxAliYuionpayOrderPayRsp mrsp = new WxAliYuionpayOrderPayRsp();
        setInitRspParams(mrsp,mreq);

        // 进行参数检查
        if(h5checkIsNull(mreq) <0) {
            setRspParams(mrsp, RespUtil.noResult, ComEnum.RESP_DESC_NO_RESULT.getResMsg());
            return mrsp;
        }

        try {
            //   是获取 微信渠道才进来获取  openid 调用接口获取参数
            if ("15".equals(mreq.getChannelId())){
                String openid = wxAliYunionCustInfoService.getwxAliYunionCustInfo(mreq);
                if (null == openid) {
                    setRspParams(mrsp, RespUtil.openId, ComEnum.GET_USER_ID_OPEN_ID.getResMsg());
                    return mrsp;
                }
           }
        }catch (Exception e) {
            e.printStackTrace();
            setRspParams(mrsp, RespUtil.openId, ComEnum.GET_USER_ID_OPEN_ID.getResMsg());
            return mrsp;
        }
        try {
            // 组装json报文
            String jsonStr = geneH5OrderJson(mreq);
            String A = MerKeyUtil.getSHA256StrJava(jsonStr);  //sha256 加密后str
            SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmss");
            String Timestamp = sim.format(new Date());
            String nonce = StringUtil.getRomNum();
            String longstr = mreq.getAppId()+Timestamp+ nonce +A;

            String merKeyUtils = MerKeyUtil._64sha256_HMAC(longstr, mreq.getAppKey());     //hmac_sha_256

            String signatures = StringUtil.replaceBlank(merKeyUtils);  //去除 \t\n
            logger.info("Signatures签名为:"+signatures);
            StringBuffer sbtr = new StringBuffer("");
            sbtr.append("OPEN-FORM-PARAM ").append("AppId=\"").append(mreq.getAppId()).append("\",Timestamp=\"").append(Timestamp)
                    .append("\",Nonce=\"").append(nonce).append("\",Signature=\"").append(signatures).append("\"");
            logger.info("Authorization认证内容为:"+sbtr.toString());
            logger.info("开始请求银商渠道支付----------"+mreq.getSerialNumber()+"请求参数为："+jsonStr.toString());

            /**
            Map<String,String> map = new HashMap<String, String>(10);
            map.put("appId", mreq.getAppId()); // 备注 这里appid不是微信公众号
            map.put("timestamp",Timestamp);// 请求时间
            map.put("nonce",nonce); // 随机数
            map.put("content",jsonStr); // 业务内容
            map.put("signature",signatures); // 签名参数
             */
            //String reqParams = StringUtil.mapChangeString(map, null);
            String reqParams = geneH5ReqStr(mreq, Timestamp, nonce, jsonStr,signatures);

            mrsp.setH5PayParams(mreq.getPayOrderUrl()+"?"+reqParams);// url +组装参数
            mrsp.setAuthorization(sbtr.toString());// 头部认证内容
            setRspParams(mrsp, RespUtil.successCode, ComEnum.GET_PAY_ORDER_SIGN_SUCCESS.getResMsg());

        } catch (Exception e) {
            e.printStackTrace();
            setRspParams(mrsp, RespUtil.timeOut, ComEnum.TIME_OVER_TIME_DESC.getResMsg());
            return mrsp;
        }
        return mrsp;
    }

    private String geneH5ReqStr(WxAliYuionpayOrderPayReq mreq, String timestamp, String nonce, String jsonStr, String signatures) throws Exception {
        StringBuffer sf = new StringBuffer("");
        sf.append("authorization="); sf.append("OPEN-FORM-PARAM");sf.append("&");
        sf.append("appId="); sf.append(mreq.getAppId());sf.append("&");
        sf.append("timestamp="); sf.append(timestamp);sf.append("&");
        sf.append("nonce="); sf.append(nonce);sf.append("&");
        sf.append("content="); sf.append(URLEncoder.encode(jsonStr,"UTF-8"));sf.append("&");
        sf.append("signature="); sf.append(URLEncoder.encode(signatures,"UTF-8"));
        return sf.toString();
    }

    // 参数检查
    private int h5checkIsNull(WxAliYuionpayOrderPayReq mreq) {
        if(StringUtil.checkNullString(mreq.getMerOrderId()) ||
                StringUtil.checkNullString(mreq.getMid()) ||
                StringUtil.checkNullString(mreq.getTid()) ||
                StringUtil.checkNullString(mreq.getInstMid())||
                StringUtil.checkNullString(mreq.getTotalAmount()) ||
                StringUtil.checkNullString(mreq.getNotifyUrl()) ||
                StringUtil.checkNullString(mreq.getPayOrderUrl())) {
            return -1;
        }
        return 1;
    }

    // 组装前端H5数据
    private String geneH5OrderJson(WxAliYuionpayOrderPayReq mreq) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requestTimestamp = sim.format(new Date());
        JSONObject jsonStr = new JSONObject();
        jsonStr.put("requestTimestamp", requestTimestamp);
        jsonStr.put("merOrderId",mreq.getMerOrderId());// 商户订单号
        jsonStr.put("mid", mreq.getMid()); // 商户号
        jsonStr.put("tid", mreq.getTid()); // 终端号
        jsonStr.put("instMid", mreq.getInstMid()); // 业务类型
        jsonStr.put("totalAmount", mreq.getTotalAmount()); // 支付金额 单位为分
        jsonStr.put("notifyUrl", mreq.getNotifyUrl()); // 通知地址
        if ("13".equals(mreq.getChannelId())) {// 微信
            //jsonStr.put("subAppId", mreq.getSubAppId()); //appid
            jsonStr.put("subOpenId",mreq.getSubOpenId()); // 需获取openid
        }
        return jsonStr.toString();
    }

    // h5 公众号 支付订单查询
    @Override
    public WxAliYuionpayQueryOrderRsp queryOrder(WxAliYuionpayQueryOrderReq mreq) throws Exception {
        logger.info("进入银商渠道公众号支付服务---------------queryOrder");
        WxAliYuionpayQueryOrderRsp mrsp = new WxAliYuionpayQueryOrderRsp();
        setInitRspParams(mrsp);

        try {
            // 组装 订单支付 查询 结果json 参数
            String jsonStr = queryOrdrJson(mreq);

            String A = MerKeyUtil.getSHA256StrJava(jsonStr);  //sha256 加密后str
            SimpleDateFormat sim= new SimpleDateFormat("yyyyMMddHHmmss");
            String Timestamp = sim.format(new Date());
            String Nonce = StringUtil.getRomNum();
            String longstr = mreq.getAppId()+Timestamp+Nonce+A;

            String MerKeyUtils=MerKeyUtil._64sha256_HMAC(longstr, mreq.getAppKey());     //hmac_sha_256

            String Signatures = StringUtil.replaceBlank(MerKeyUtils);  //去除 \t\n
            logger.info("Signatures签名为:"+Signatures);
            StringBuffer sbtr=new StringBuffer("");
            sbtr.append("OPEN-BODY-SIG ").append("AppId=\"").append(mreq.getAppId()).append("\",Timestamp=\"").append(Timestamp)
                    .append("\",Nonce=\"").append(Nonce).append("\",Signature=\"").append(Signatures).append("\"");
            logger.info("Authorization认证内容为:"+sbtr.toString());
            logger.info("开始请求公众号通支付结果查询----------"+mreq.getSerialNumber()+"请求参数为："+jsonStr.toString());
            String rsp = null;
            if (ConfigUtil.getValue("FLAG").equals("0")) {
                 rsp = HttpUtil.send1(mreq.getQueryOrderUrl(), jsonStr, sbtr.toString(),HttpUtil.createHttpsClient());
            } else {
                HttpSSLClient sslclient = new HttpSSLClient();
                HashMap<String ,String> headers = new HashMap<String ,String>(12);
                headers.put("Authorization", sbtr.toString());
                rsp = sslclient.doHttpsPost(mreq.getQueryOrderUrl(), jsonStr, "utf-8", headers);
            }
            logger.info("结束请求银商渠道支付结果查询----------"+mreq.getSerialNumber()+"响应参数为："+rsp);
            if(StringUtil.checkNullString(rsp)) {
                setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, ComEnum.TIME_OVER_TIME_DESC.getResMsg());
                return mrsp;
            }
            // 报文json 对象转换
            WxAliYuionpayQueryOrderRsp queryOrderRsp = (WxAliYuionpayQueryOrderRsp) FastJSONUtil.parseObject(rsp,WxAliYuionpayQueryOrderRsp.class);
            if (null == queryOrderRsp) {
                setRspParams(mrsp, RespUtil.parseEx, ComEnum.ORDER_PAY_PARSE_EXCEPTION.getResMsg());
                return mrsp;
            }
            // 判断是否 下单成功 SUCCESS
            if (!StringUtil.checkStringsEqual(ComEnum.ERROR_SUCCESS.getResName(), queryOrderRsp.getErrCode())){
                mrsp.setRespCode(RespUtil.orderPayFail);
                mrsp.setRespDesc("下单失败");
                return mrsp;
            }
            // 判断 交易状态
            if (!StringUtil.checkStringsEqual(ComEnum.TRADE_SUCCESS.getResName(), queryOrderRsp.getStatus())) {
                setRspParams(mrsp, RespUtil.orderPayFail, "未支付");
                return mrsp;
            }
            // 置成功置
            mrsp = queryOrderRsp;
            setRspParams(mrsp, RespUtil.successCode, ComEnum.ERROR_SUCCESS.getResMsg());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询 公众号支付结果账单异常-----------");
        }

        return mrsp;
    }

    // 支付结果查询参数 组装json 报文体
    private String queryOrdrJson(WxAliYuionpayQueryOrderReq mreq) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requestTimestamp = sim.format(new Date());
        JSONObject jsonQueryOrder  = new JSONObject();
        jsonQueryOrder.put("requestTimestamp", requestTimestamp);
        jsonQueryOrder.put("merOrderId",mreq.getMerOrderId());// 商户订单号
        jsonQueryOrder.put("mid", mreq.getMid()); // 商户号
        jsonQueryOrder.put("tid", mreq.getTid()); // 终端号
        jsonQueryOrder.put("instMid", mreq.getInstMid()); // 行业类型
        return jsonQueryOrder.toString();
    }

    private void setInitRspParams(WxAliYuionpayQueryOrderRsp mrsp) {
        mrsp.setRespCode(RespUtil.codeError);
        mrsp.setRespDesc(ComEnum.RESP_DESC.getResMsg());
    }

    // 置 公共值
    private void setRspParams(WxAliYuionpayQueryOrderRsp mrsp ,String respCode, String respDesc) {
        mrsp.setRespCode(respCode);
        mrsp.setRespDesc(respDesc);
    }

    // 支付结果通知
    @Override
    public String payNotifyResult(Map<String, Object> map) throws Exception {
        logger.info("进入支付结果通知服务---------payNotifyResult");

        // 组装支付结果 到支付前置系统
        String payNotifyXml = genePayNotify(map);
        try {
            prParkingService.parkingCallCenterSync(payNotifyXml);
        } catch (Exception e) {
            e.printStackTrace();
            return ComEnum.ERROR_SUCCESS.getResName();
        }
        return ComEnum.ERROR_SUCCESS.getResName();
    }

    // 主扫 支付 通知
    @Override
    public String payCbNotifyResult(Map<String, Object> map) throws Exception {
        logger.info("进入主扫支付结果通知服务---------payCbNotifyResult");

        // 组装支付结果 到支付前置系统
        String payNotifyXml = geneCbPayNotify(map);
        try {
            prParkingService.parkingCallCenterSync(payNotifyXml);
        } catch (Exception e) {
            e.printStackTrace();
            return ComEnum.ERROR_SUCCESS.getResName();
        }
        return ComEnum.ERROR_SUCCESS.getResName();
    }

    // 主扫 支付 通知
    private String geneCbPayNotify(Map<String, Object> map) throws JAXBException {
        PayNotifyReq payNotifyReq = new PayNotifyReq();
        if (map.containsKey("billPayment")) {
            WxAliYuionpayNotifyResultReq payResult = (WxAliYuionpayNotifyResultReq) FastJSONUtil.parseObject((String) map.get("billPayment"), WxAliYuionpayNotifyResultReq.class);
            payNotifyReq.setPayTime(payResult.getPayTime()); // 支付时间
            payNotifyReq.setPayId(payResult.getTargetOrderId()); // 系统交易流水号
            payNotifyReq.setPayType(getPayTypes(payResult.getTargetSys())); // 支付渠道
        }

        payNotifyReq.setOrderId((String) map.get("billNo")); // 商户订单号
        payNotifyReq.setTxnAmt((String) map.get("totalAmount")); // 交易金额
        payNotifyReq.setTradeStatus(getBillTradeStatus((String) map.get("billStatus"))); // 订单状态
        payNotifyReq.setTradeDesc((String) map.get("billDesc"));
        payNotifyReq.setService("payNotify");// 支付服务名称
        String reqXml = XmlUtil.ObjToXml(payNotifyReq,PayNotifyReq.class);

        return reqXml;
    }

    private String getBillTradeStatus(String billStatus) {
        if (StringUtil.checkStringsEqual(ComEnum.BILL_STATUES_PAID.getResName(),billStatus)) {
            return  "Paid";
        } else if (StringUtil.checkStringsEqual(ComEnum.BILL_STATUES_CLOSED.getResName(), billStatus)) {
            return  "Closed";
        } else if (StringUtil.checkStringsEqual(ComEnum.BILL_STATUES_UNPAID.getResName(),billStatus)) {
            return "Unpaid";
        }
        return "Unknown";
    }

    //  公众 号 支付 通知
    private String genePayNotify(Map<String, Object> map) throws JAXBException {
        PayNotifyReq payNotifyReq = new PayNotifyReq();
        payNotifyReq.setOrderId((String) map.get("merOrderId")); // 商户订单号
        payNotifyReq.setPayAmt((String) map.get("buyerPayAmount")); //  实付金额
        payNotifyReq.setTxnAmt((String) map.get("totalAmount")); // 交易金额
        payNotifyReq.setPayTime((String) map.get("payTime")); // 支付时间
        payNotifyReq.setPayId((String) map.get("seqId")); // 系统交易流水号
        payNotifyReq.setPayType(getPayTypes((String) map.get("targetSys"))); // 支付渠道
        payNotifyReq.setTradeStatus(getTradeStatus((String) map.get("status"))); // 订单状态
        payNotifyReq.setTradeDesc(getTradeDesc((String) map.get("status")));
        payNotifyReq.setTradeCode((String) map.get("status"));
        payNotifyReq.setService("payNotify");// 支付服务名称
        String reqXml = XmlUtil.ObjToXml(payNotifyReq,PayNotifyReq.class);
        return reqXml;
    }

    private String getTradeDesc(String status) {
        if (ComEnum.TRADE_SUCCESS.getResName().equals(status)) {
            return ComEnum.Error_00.getResMsg();
        }
        return ComEnum.TRADE_DESC_PAY_FAIL.getResMsg();
    }

    // 订单状态
    private String getTradeStatus(String status) {
        if (ComEnum.TRADE_SUCCESS.getResName().equals(status)) {
            return "Paid";
        }
        return "PayFaid";
    }

    // 支付渠道
    private String getPayTypes(String targetSys) {
        if (ComEnum.TARGETSYS_Alipay_1_0.getResName().equals(targetSys) ||ComEnum.TARGETSYS_Alipay_2_0.getResName().equals(targetSys)) {
            return ComEnum.TARGETSYS_Alipay_1_0.getResMsg();
        } else if(ComEnum.TARGETSYS_WXPay.getResName().equals(targetSys)){
            return ComEnum.TARGETSYS_WXPay.getResMsg();
        } else if (ComEnum.TARGETSYS_UnionPay.getResName().equals(targetSys)){
            return ComEnum.TARGETSYS_UnionPay.getResMsg();
        } else if (ComEnum.TARGETSYS_UnionPay_ACP.getResName().equals(targetSys)){
            return ComEnum.TARGETSYS_UnionPay_ACP.getResMsg();
        }
        return "00";
    }

    /**
     *   二维码 支付下单
     * @param mreq
     * @return
     * @throws Exception
     */
    @Override
    public WxAliYuionpayOrderPayRsp payCbOrder(WxAliYuionpayOrderPayReq mreq) throws Exception {
        logger.info("进入银商二维码渠道下单服务---------------payCbOrder");
        WxAliYuionpayOrderPayRsp mrsp = new WxAliYuionpayOrderPayRsp();
        setInitRspParams(mrsp,mreq);

        // 进行参数检查
        if(qrCodecheckIsNull(mreq) <0) {
            setRspParams(mrsp, RespUtil.noResult, ComEnum.RESP_DESC_NO_RESULT.getResMsg());
        }

        try {
            // 组装json报文
            String jsonStr = geneCbOrderJson(mreq);
            String A = MerKeyUtil.getSHA256StrJava(jsonStr);  //sha256 加密后str
            SimpleDateFormat sim= new SimpleDateFormat("yyyyMMddHHmmss");
            String Timestamp=sim.format(new Date());
            String Nonce= StringUtil.getRomNum();
            String longstr=mreq.getAppId()+Timestamp+Nonce+A;

            String merKeyUtils = MerKeyUtil._64sha256_HMAC(longstr, mreq.getAppKey());     //hmac_sha_256
            String signatures = "";

            signatures=StringUtil.replaceBlank(merKeyUtils);  //去除 \t\n
            logger.info("Signatures签名为:"+signatures);
            StringBuffer sbtr=new StringBuffer("");
            sbtr.append("OPEN-BODY-SIG ").append("AppId=\"").append(mreq.getAppId()).append("\",Timestamp=\"").append(Timestamp)
                    .append("\",Nonce=\"").append(Nonce).append("\",Signature=\"").append(signatures).append("\"");
            logger.info("Authorization认证内容为:"+sbtr.toString());
            logger.info("开始请求银商二维码下单支付----------"+mreq.getSerialNumber()+"请求参数为："+jsonStr.toString());
            String rsp = null;
            if (ConfigUtil.getValue("FLAG").equals("0")) {
                 rsp = HttpUtil.send1(mreq.getPayOrderUrl(), jsonStr, sbtr.toString(),HttpUtil.createHttpsClient());
            } else {
                HttpSSLClient sslclient = new HttpSSLClient();
                HashMap<String ,String> headers = new HashMap<String ,String>(12);
                headers.put("Authorization", sbtr.toString());
                rsp = sslclient.doHttpsPost(mreq.getPayOrderUrl(), jsonStr, "utf-8", headers);
            }
            logger.info("结束请求银商渠道二维码下单支付----------"+mreq.getSerialNumber()+"响应参数为："+rsp);
            if(StringUtil.checkNullString(rsp)) {
                setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, ComEnum.TIME_OVER_TIME_DESC.getResMsg());
                return mrsp;
            }
            // 解析返回 json 数据
            JSONObject jsonRsp = JSONObject.fromObject(rsp);
            WxAliYuionpayOrderPayRsp payRsp = (WxAliYuionpayOrderPayRsp) FastJSONUtil.parseObject(rsp,WxAliYuionpayOrderPayRsp.class);
            if (null == payRsp) {
                setRspParams(mrsp, RespUtil.parseEx, ComEnum.ORDER_PAY_PARSE_EXCEPTION.getResMsg());
                return mrsp;
            }
            // 判断是否 下单成功 SUCCESS
            if (!StringUtil.checkStringsEqual(ComEnum.ERROR_SUCCESS.getResName(), payRsp.getErrCode())){
                mrsp.setRespCode(RespUtil.orderPayFail);
                mrsp.setRespDesc(payRsp.getErrMsg());
                return mrsp;
            }
            mrsp.setBillQRCode(payRsp.getBillQRCode());
            mrsp.setMerOrderId(payRsp.getBillNo());
            setRspParams(mrsp, RespUtil.successCode, ComEnum.ORDER_PAY_QR_CODE_SUCCESS_DESC.getResMsg());

        } catch (Exception e) {
            e.printStackTrace();
            setRspParams(mrsp, RespUtil.timeOut, ComEnum.TIME_OVER_TIME_DESC.getResMsg());
            return mrsp;
        }
        return mrsp;
    }



    // 二维码 下单 服务
    private String geneCbOrderJson(WxAliYuionpayOrderPayReq mreq) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat billDate = new SimpleDateFormat("yyyy-MM-dd");
        String requestTimestamp = sim.format(new Date());
        JSONObject jsonStr = new JSONObject();
        jsonStr.put(ComYshEnum.requestTimestamp, requestTimestamp);
        jsonStr.put("billNo",mreq.getMerOrderId());// 商户订单号
        jsonStr.put("billDate",billDate.format(new Date()));// 账单日期
        jsonStr.put("mid", mreq.getMid()); // 商户号
        jsonStr.put("tid", mreq.getTid()); // 终端号
        jsonStr.put("instMid", mreq.getInstMid()); // 业务类型
        jsonStr.put("totalAmount", mreq.getTotalAmount()); // 支付金额 单位为分
        jsonStr.put("notifyUrl", mreq.getNotifyUrl()); // 通知地址
        return jsonStr.toString();

    }

    // 二维码支付 动态支付
    private int  qrCodecheckIsNull(WxAliYuionpayOrderPayReq mreq) {
        if(StringUtil.checkNullString(mreq.getMerOrderId()) ||
                StringUtil.checkNullString(mreq.getMid()) ||
                StringUtil.checkNullString(mreq.getTid()) ||
                StringUtil.checkNullString(mreq.getInstMid())||
                StringUtil.checkNullString(mreq.getTotalAmount()) ||
                StringUtil.checkNullString(mreq.getNotifyUrl()) ||
                StringUtil.checkNullString(mreq.getPayOrderUrl()) ||
                StringUtil.checkNullString(mreq.getAppId()) ||
                StringUtil.checkNullString(mreq.getAppKey())) {
            return -1;
        }
        return 1;
    }

    // 二维码 支付 订单 查询
    @Override
    public WxAliYuionpayQueryOrderRsp queryQrCodeCbOrder(WxAliYuionpayQueryOrderReq mreq) throws Exception {
        logger.info("进入银商渠道二维码 支付订单查询服务---------------queryQrCodeCbOrder");
        WxAliYuionpayQueryOrderRsp mrsp = new WxAliYuionpayQueryOrderRsp();
        setInitRspParams(mrsp);
        if (chekcIsNull(mreq) <0) {

        }
        try {
            // 组装 二维码订单支付 查询 结果json 参数
            String jsonStr = queryCbOrdrJson(mreq);
            logger.info("开始请求二维码订单查询----------"+mreq.getSerialNumber()+"请求参数为："+jsonStr.toString());
            String strSub = getStrSignatures(jsonStr, mreq.getAppId(), mreq.getAppKey());
            logger.info("Authorization认证内容为:"+strSub);
            String rsp = null;
            if (ConfigUtil.getValue("FLAG").equals("0")) {
                rsp = HttpUtil.send1(mreq.getQueryOrderUrl(), jsonStr, strSub,HttpUtil.createHttpsClient());
            } else {
                HttpSSLClient sslclient = new HttpSSLClient();
                HashMap<String ,String> headers = new HashMap<String ,String>(12);
                headers.put("Authorization", strSub);
                rsp = sslclient.doHttpsPost(mreq.getQueryOrderUrl(), jsonStr, "utf-8", headers);
            }
            logger.info("结束请求银商渠道二维码订单查询----------"+mreq.getSerialNumber()+"响应参数为："+rsp);
            if(StringUtil.checkNullString(rsp)) {
                setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, ComEnum.TIME_OVER_TIME_DESC.getResMsg());
                return mrsp;
            }
            // 报文json 对象转换
            WxAliYuionpayQueryOrderRsp queryOrderRsp = (WxAliYuionpayQueryOrderRsp) FastJSONUtil.parseObject(rsp,WxAliYuionpayQueryOrderRsp.class);
            if (null == queryOrderRsp) {
                setRspParams(mrsp, RespUtil.parseEx, ComEnum.ORDER_PAY_PARSE_EXCEPTION.getResMsg());
                return mrsp;
            }
            // 判断是否 下单成功 SUCCESS
            if (!StringUtil.checkStringsEqual(ComEnum.ERROR_SUCCESS.getResName(), queryOrderRsp.getErrCode())){
                setRspParams(mrsp, RespUtil.orderPayFail, "下单失败");
                return mrsp;
            }
            // 置成功置
            if (!StringUtil.checkStringsEqual(ComEnum.BILL_STATUES_PAID.getResName(), queryOrderRsp.getBillStatus())) {
                setRspParams(mrsp, RespUtil.orderPayFail, "未查询到支付结果");
                return mrsp;
            }

            mrsp = queryOrderRsp;
            if (ConfigUtil.getValue("FLAG").equals("1")) {
                WxAliYuionpayNotifyResultReq payResult = (WxAliYuionpayNotifyResultReq) FastJSONUtil.parseObject((String) queryOrderRsp.getBillPayment(), WxAliYuionpayNotifyResultReq.class);
                mrsp.setSubPayType(getPayTypes(payResult.getTargetSys()));
            }
            setRspParams(mrsp, RespUtil.successCode, ComEnum.ERROR_SUCCESS.getResMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mrsp;
    }

    private int chekcIsNull(WxAliYuionpayQueryOrderReq mreq) {
        if (StringUtil.checkNullString(mreq.getAppId()) ||
            StringUtil.checkNullString(mreq.getAppKey()) ||
            StringUtil.checkNullString(mreq.getMerOrderId()) ||
            StringUtil.checkNullString(mreq.getBillDate()) ||
            StringUtil.checkNullString(mreq.getMid()) ||
            StringUtil.checkNullString(mreq.getTid()) ||
            StringUtil.checkNullString(mreq.getInstMid()) ) {
            return -1;
        }
        return 1;
    }

    // 二维码 订单结果查询组装
    private String queryCbOrdrJson(WxAliYuionpayQueryOrderReq mreq) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requestTimestamp = sim.format(new Date());
        JSONObject jsonQueryOrder  = new JSONObject();
        jsonQueryOrder.put("requestTimestamp", requestTimestamp);
        jsonQueryOrder.put("billNo",mreq.getMerOrderId());// 商户订单号
        jsonQueryOrder.put("mid", mreq.getMid()); // 商户号
        jsonQueryOrder.put("tid", mreq.getTid()); // 终端号
        jsonQueryOrder.put("instMid", mreq.getInstMid()); // 行业类型
        jsonQueryOrder.put("billDate",mreq.getBillDate());// 账单日期
        return jsonQueryOrder.toString();
    }

    private String getStrSignatures(String jsonStr, String appId, String appKey) {
        String A = MerKeyUtil.getSHA256StrJava(jsonStr);  //sha256 加密后str
        SimpleDateFormat sim= new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sim.format(new Date());
        String Nonce= StringUtil.getRomNum();
        String longstr = appId +timestamp+Nonce+A;

        String MerKeyUtils=MerKeyUtil._64sha256_HMAC(longstr, appKey);     //hmac_sha_256
        String signatures = StringUtil.replaceBlank(MerKeyUtils);  //去除 \t\n
        logger.info("Signatures签名为:"+signatures);
        StringBuffer sbtr = new StringBuffer("");
        sbtr.append("OPEN-BODY-SIG ").
                append("AppId=\"").append(appId).
                append("\",Timestamp=\"").append(timestamp).
                append("\",Nonce=\"").append(Nonce).
                append("\",Signature=\"").append(signatures).
                append("\"");
        return sbtr.toString();
    }
}
