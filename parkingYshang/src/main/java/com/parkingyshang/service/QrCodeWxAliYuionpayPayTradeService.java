package com.parkingyshang.service;


import com.parkingyshang.dto.WxAliYuionpayOrderPayReq;
import com.parkingyshang.dto.WxAliYuionpayOrderPayRsp;
import com.parkingyshang.dto.WxAliYuionpayQueryOrderReq;
import com.parkingyshang.dto.WxAliYuionpayQueryOrderRsp;

import java.util.Map;

public interface QrCodeWxAliYuionpayPayTradeService {
    
    
    // 微信 支付宝 银联云闪付  后台 支付订单 下单
    public WxAliYuionpayOrderPayRsp payOrder(WxAliYuionpayOrderPayReq mreq) throws Exception;

    // h5前端 支付
    public  WxAliYuionpayOrderPayRsp frontPayOrder(WxAliYuionpayOrderPayReq mreq) throws Exception;

    // 公众号 h5 支付订单查询
    public WxAliYuionpayQueryOrderRsp queryOrder(WxAliYuionpayQueryOrderReq mreq) throws Exception;

    // 公众号支付通知
    public String payNotifyResult(Map<String, Object> map) throws Exception;

    // 主扫 支付通知
    public String payCbNotifyResult(Map<String, Object> map) throws Exception;

    // 微信 支付宝 银联云闪付  正扫 二维码 下单
    public WxAliYuionpayOrderPayRsp payCbOrder(WxAliYuionpayOrderPayReq mreq) throws Exception;
    // 主扫 二维码 支付 订单 查询
    public WxAliYuionpayQueryOrderRsp queryQrCodeCbOrder(WxAliYuionpayQueryOrderReq mreq) throws Exception;

}
