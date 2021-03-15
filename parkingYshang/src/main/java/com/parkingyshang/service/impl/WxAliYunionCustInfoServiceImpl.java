package com.parkingyshang.service.impl;

import com.parkingyshang.dto.WxAliYuionpayOrderPayReq;
import com.parkingyshang.dto.WxAliYuionpayOrderPayRsp;
import com.parkingyshang.service.WxAliYunionCustInfoService;
import com.parkingyshang.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service("wxAliYunionCustInfoService")
public class WxAliYunionCustInfoServiceImpl implements WxAliYunionCustInfoService {

    private static final Logger logger= LoggerFactory.getLogger(WxAliYunionCustInfoServiceImpl.class);

    @Override
    public String getwxAliYunionCustInfo(WxAliYuionpayOrderPayReq mreq) throws Exception {

        if ("00".equals(mreq.getChannelId())) {
            try {
                // 银商户渠道获取openid 方式组装json报文
                String jsonStr = geneWxOpenidJson(mreq);
                String A = MerKeyUtil.getSHA256StrJava(jsonStr);  //sha256 加密后str
                SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmss");
                String Timestamp = sim.format(new Date());
                String Nonce = StringUtil.getRomNum();
                String longstr = mreq.getAppId() + Timestamp + Nonce + A;

                String merKeyUtils = MerKeyUtil._64sha256_HMAC(longstr, mreq.getAppKey());     //hmac_sha_256
                String signatures = "";

                signatures = StringUtil.replaceBlank(merKeyUtils);  //去除 \t\n
                logger.info("Signatures签名为:" + signatures);
                StringBuffer sbtr = new StringBuffer("");
                sbtr.append("OPEN-BODY-SIG ").append("AppId=\"").append(mreq.getAppId()).append("\",Timestamp=\"").append(Timestamp)
                        .append("\",Nonce=\"").append(Nonce).append("\",Signature=\"").append(signatures).append("\"");
                logger.info("Authorization认证内容为:" + sbtr.toString());
                logger.info("开始请求pos通支付----------" + mreq.getSerialNumber() + "请求参数为：" + jsonStr.toString());

                HttpSSLClient sslclient = new HttpSSLClient();
                HashMap<String, String> headers = new HashMap<String, String>(12);
                headers.put("Authorization", sbtr.toString());
                String rsp = sslclient.doHttpsPost(mreq.getPayOrderUrl(), jsonStr, "utf-8", headers);
                logger.info("结束请求银商渠道支付----------" + mreq.getSerialNumber() + "响应参数为：" + rsp);
                if (StringUtil.checkNullString(rsp)) {
                    return null;
                }
                // 解析返回 json 数据
                JSONObject jsonRsp = JSONObject.fromObject(rsp);
                WxAliYuionpayOrderPayRsp payRsp = (WxAliYuionpayOrderPayRsp) FastJSONUtil.parseObject(rsp, WxAliYuionpayOrderPayRsp.class);
                if (null == payRsp) {
                    return null;
                }
                // 判断是否 下单成功 SUCCESS
                if (!StringUtil.checkStringsEqual(ComEnum.ERROR_SUCCESS.getResName(), payRsp.getErrCode())) {
                    return null;
                }
                return payRsp.getBuyerId();

            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        } else if ("13" .equals(mreq.getChannelId())) {

        }

        return null;
    }

    // 微信openid 获取的
    private String geneWxOpenidJson(WxAliYuionpayOrderPayReq mreq) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String requestTimestamp = sim.format(new Date());
        JSONObject jsonStr = new JSONObject();
        jsonStr.put("requestTimestamp",requestTimestamp);
        jsonStr.put("mid",mreq.getMid()); // 商户号
        jsonStr.put("tid",mreq.getTid());// 终端号
        jsonStr.put("instMid","YUEDANDEFAULT");// 业务类型
        jsonStr.put("barCode",""); //条码
        return jsonStr.toString();
    }
}
