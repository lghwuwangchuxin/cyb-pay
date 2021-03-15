package com.parkingyshang.service;

import com.parkingyshang.dto.WxAliYuionpayOrderPayReq;

public interface WxAliYunionCustInfoService {

    //获取微信 支付宝 云闪付 openid 或者userId
    public String getwxAliYunionCustInfo(WxAliYuionpayOrderPayReq mreq) throws Exception;
}
