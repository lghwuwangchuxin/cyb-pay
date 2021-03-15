package com.parking.service.impl;

import com.parking.domain.ParkingPreChannelPayRouteConfig;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.ApplyOrderChannelBaseDTO;
import com.parking.dto.ParkingPrePayOrderReq;
import com.parking.qrcode.service.prejs.QrCodeH5HxPayTradeService;
import com.parking.qrcode.service.prejs.QrCodeH5SwUnionPayTradeService;
import com.parking.service.ParkingH5ChannelPayTradeProxyService;
import com.parking.util.CommEnum;
import com.parking.util.RespUtil;
import com.parking.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.inject.Inject;

@Service("parkingH5ChannelPayTradeProxyService")
public class ParkingH5ChannelPayTradeProxyServiceImpl implements ParkingH5ChannelPayTradeProxyService {

    private static final Logger logger = LoggerFactory.getLogger(ParkingH5ChannelPayTradeProxyServiceImpl.class);

    @Inject
    private QrCodeH5SwUnionPayTradeService qrCodeH5SwUnionPayTradeService;
    @Inject
    private QrCodeH5HxPayTradeService qrCodeH5HxPayTradeService;

    @Override
    public ApplyOrderChannelBaseDTO payTradeService(ParkingPrePayOrderReq mreq, ParkingPreChannelPayRouteConfig payRouteConfig, ParkingQrcodeMchntConfig mchntConfig) throws Exception {
        logger.info("进入公共渠道h5下单代理业务类-------------------payTradeService");
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
        if (obj[0] instanceof ParkingPreChannelPayRouteConfig) {
            ParkingPreChannelPayRouteConfig payRouteConfig = (ParkingPreChannelPayRouteConfig) obj[0];
            channelType = payRouteConfig.getChannelSelect();
            service = payRouteConfig.getService();
        }
        try {
            if(StringUtil.checkStringsEqual(CommEnum.SW_UNION_PAY_CODE.getRspCode(), channelType)) { // 银联条码前置
                return (ApplyOrderChannelBaseDTO) ProxyFactory.payTradeToQueryInterface(qrCodeH5SwUnionPayTradeService, service, obj);
            } else if (StringUtil.checkStringsEqual(CommEnum.HXBPAY_CODE.getRspCode(),channelType)) { // 华夏银行
                return (ApplyOrderChannelBaseDTO) ProxyFactory.payTradeToQueryInterface(qrCodeH5HxPayTradeService, service, obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ApplyOrderChannelBaseDTO queryTradeService(ParkingPreChannelPayRouteConfig payRouteConfig, ParkingQrcodeMchntConfig mchntConfig, ParkingTradeOrder parkingTradeOrder) throws Exception {
        logger.info("进入公共渠道查询h5订单查询---------queryTradeService");
        ApplyOrderChannelBaseDTO dto = new ApplyOrderChannelBaseDTO();
        dto.setRespCode(RespUtil.codeError);
        dto.setRespDesc(CommEnum.CALL_PROXY_ERROR.getRspMsg());
        ApplyOrderChannelBaseDTO  payTrade = payTradeToQueryTrade(payRouteConfig, mchntConfig, parkingTradeOrder);
        if(null !=payTrade) dto = payTrade;
        return dto;
    }
}
