package com.parkinghx.service;

import com.parkinghx.dto.HxbScanPayReq;
import com.parkinghx.dto.HxbScanPayRsp;

public interface HxbNewScanPayService extends HxbScanPayService {

    // 主扫支付  老文档
    HxbScanPayRsp scanCBPay(HxbScanPayReq req) throws Exception;

    //  h5 下单 支付  微信统一下单 新文档接入 统一条码
    HxbScanPayRsp preH5Pay(HxbScanPayReq req) throws Exception;
    // 被扫  新文档接入 统一条码
    HxbScanPayRsp scanBcQrCodePay(HxbScanPayReq req) throws Exception;

    // 主扫支付  新文档 统一条码
    HxbScanPayRsp scanCbQrCodePay(HxbScanPayReq req) throws Exception;


}
