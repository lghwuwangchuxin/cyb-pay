package com.parking.qrcode.service;
/**

 *   公共业务类渠道接入下单类
 */


public interface QrCodePayTradeChannelAccessService {
	//被扫或者主扫下单 或 公众号
    public Object tradePay(Object ... obj) throws Exception;
    //交易查询
    public Object queryTrade(Object ... obj) throws Exception;
}

