package com.parking.service;

import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.ApplyOrderReq;
import com.parking.dto.QueryAtmResultReq;

/**
 *  公共多渠道下单网络请求 代理业务处理 接口类 
 */


public interface QrCodeChannelPayTradeProxyService {
	// 公共渠道处理业务类订单支付下单接口，商户扫用户二维码进行扣款
	public ApplyOrderChannelBaseDTO payTradeService(ApplyOrderReq mreq, ParkingQrcodeMchntConfig mchntConfig, ParkingTradeOrder parkingTradeOrder) throws Exception;
	// 公共渠道处理业务类查询订单交易状态接口
	public ApplyOrderChannelBaseDTO queryTradeService(QueryAtmResultReq mreq,ParkingQrcodeMchntConfig mchntConfig, ParkingTradeOrder parkingTradeOrder) throws Exception;

}

