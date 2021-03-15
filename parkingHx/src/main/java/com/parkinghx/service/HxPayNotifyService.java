package com.parkinghx.service;

public interface HxPayNotifyService {

    // 接收华夏银行支付通知
    public String payNotifyResult(String msg) throws Exception;
}
