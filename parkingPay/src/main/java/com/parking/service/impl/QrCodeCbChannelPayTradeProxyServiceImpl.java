package com.parking.service.impl;

import com.parking.domain.ParkingQrCodeCbChannelPayRouteConfig;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.ParkingAutoPayReq;
import com.parking.qrcode.service.qrcodecb.QrCodeCbSwUnionPayTradeService;
import com.parking.service.QrCodeCbChannelPayTradeProxyService;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("qrCodeCbChannelPayTradeProxyService")
public class QrCodeCbChannelPayTradeProxyServiceImpl implements QrCodeCbChannelPayTradeProxyService {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeCbChannelPayTradeProxyServiceImpl.class);

    @Autowired
    private QrCodeCbSwUnionPayTradeService qrCodeCbSwUnionPayTradeService;
    @Override
    public ApplyOrderChannelBaseDTO payTradeService(ParkingAutoPayReq mreq, ParkingQrCodeCbChannelPayRouteConfig payRouteConfig, ParkingQrcodeMchntConfig mchntConfig) throws Exception {
        logger.info("进入公共渠道主扫下单代理业务类cb-------------------payTradeService");
        ApplyOrderChannelBaseDTO dto = new ApplyOrderChannelBaseDTO();
        dto.setRespCode(RespUtil.codeError);
        dto.setRespDesc(CommEnum.CALL_PROXY_ERROR.getRspMsg());
        dto.setSerialNumber(mreq.getSerialNumber());
        ApplyOrderChannelBaseDTO  payTrade = payTradeToQueryTrade(payRouteConfig,mreq, mchntConfig);
        if(null !=payTrade) dto = payTrade;
        return dto;
    }

    private ApplyOrderChannelBaseDTO payTradeToQueryTrade(Object ... obj) {
        //多渠道通道
        String channelType = ""; //渠道类型
        String service = "";
        if (obj[0] instanceof ParkingQrCodeCbChannelPayRouteConfig) {
            ParkingQrCodeCbChannelPayRouteConfig payRouteConfig = (ParkingQrCodeCbChannelPayRouteConfig) obj[0];
            channelType = payRouteConfig.getChannelSelect();
            service = payRouteConfig.getService();
        }
        try {
            if(StringUtil.checkStringsEqual(CommEnum.SW_UNION_PAY_CODE.getRspCode(), channelType)) { // 银联条码前置
                return (ApplyOrderChannelBaseDTO) ProxyFactory.payTradeToQueryInterface(qrCodeCbSwUnionPayTradeService, service, obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ApplyOrderChannelBaseDTO queryTradeService(ParkingQrCodeCbChannelPayRouteConfig payRouteConfig, ParkingQrcodeMchntConfig mchntConfig, ParkingTradeOrder parkingTradeOrder) throws Exception {
        logger.info("进入公共渠道查询订单主扫cb---------queryTradeService");
        ApplyOrderChannelBaseDTO dto = new ApplyOrderChannelBaseDTO();
        dto.setRespCode(RespUtil.codeError);
        dto.setRespDesc(CommEnum.CALL_PROXY_ERROR.getRspMsg());
        ApplyOrderChannelBaseDTO  payTrade = payTradeToQueryTrade(payRouteConfig, mchntConfig, parkingTradeOrder);
        if(null !=payTrade) dto = payTrade;
        return dto;
    }
}
