package com.parking.qrcode.service.prejs.impl;

import com.parking.domain.ParkingPreChannelPayRouteConfig;
import com.parking.domain.ParkingQrcodeMchntConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.dto.*;
import com.parking.qrcode.service.prejs.QrCodeH5HxPayTradeService;
import com.parking.service.InvokeInteService;
import com.parking.service.PrHxbQrCodeService;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


@Service("qrCodeH5HxPayTradeService")
public class QrCodeH5HxPayTradeServiceImpl implements QrCodeH5HxPayTradeService {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeH5HxPayTradeServiceImpl.class);

    @Autowired
    private InvokeInteService invokeInteService;
    @Autowired
    private PrHxbQrCodeService prHxbQrCodeService;

    @Override
    public Object tradePay(Object... obj) throws Exception {
        logger.info("进入组装华夏银联微信公众号h5统一下单报文开始---tradePay");
        ApplyOrderChannelBaseDTO dto = new ApplyOrderChannelBaseDTO();
        dto.setRespCode(RespUtil.codeError);
        dto.setTradeCode(RespUtil.codeError);
        dto.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());

        HxbScanPayReq spReq = new HxbScanPayReq();
        spReq.setService(CommonHxbPayQrCodePayEnum.HXB_H5_SERVICE.getRspCode());
        if(obj[0] instanceof ParkingPreChannelPayRouteConfig) {
            ParkingPreChannelPayRouteConfig payRouteConfig = (ParkingPreChannelPayRouteConfig) obj[0];
            spReq.setNotifyUrl(payRouteConfig.getNotifyUrl());
            spReq.setPayOrderUrl(payRouteConfig.getPayOrderUrl());
        }

        if(obj[1] instanceof ParkingPrePayOrderReq) {
            ParkingPrePayOrderReq prePayOrderReq = (ParkingPrePayOrderReq) obj[1];
            spReq.setAmount(prePayOrderReq.getTxnAmt()); // 分
            spReq.setSerialNumber(prePayOrderReq.getSerialNumber());
            spReq.setSubAppid(prePayOrderReq.getSubAppId());// 微信商户id
            spReq.setSubOpenId(prePayOrderReq.getUserId());
            spReq.setOrderNo(prePayOrderReq.getTradeId());
        }

        if(obj[2] instanceof ParkingQrcodeMchntConfig) {
            ParkingQrcodeMchntConfig mconf = (ParkingQrcodeMchntConfig) obj[2];
            spReq.setAppKey(mconf.getMchntAppId());  // 密钥 appid
            spReq.setMerchantNo(mconf.getMchntId()); // 商户号
            spReq.setSubject(mconf.getRsrvStr1()); // 订单标题
            spReq.setTradeType(mconf.getRsrvStr2()); // 交易码
        }
        try {
            String scReqXml = XmlUtil.ObjToXml(spReq, HxbScanPayReq.class);
            logger.info("组装华夏银行公众号下单请求报文：" + scReqXml);
            String cellRspXml = prHxbQrCodeService.hxbNewQrCodeCallCenterSync(scReqXml);
            logger.info("接收华夏银行公众号下单响应报文："+cellRspXml);
            Map<String, String> map = invokeInteService.parseResp(cellRspXml);
            if("1".equals(map.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
                String scRspXml = map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
                logger.info("华夏银行支付--公众号订单返回为：" + scRspXml);
                HxbScanPayRsp rsp = (HxbScanPayRsp) XmlUtil.XmlToObj(scRspXml, HxbScanPayRsp.class);
                if(RespUtil.successCode.equals(rsp.getRespCode())) {
                    dto.setRespCode(CommEnum.SUCCESS_COID.getRspCode());  // 000000
                    // 读取本地 替换参数
                    WxH5PayInfo wxH5PayInfo = (WxH5PayInfo) FastJSONUtil.parseObject(rsp.getWxPayInfo(), WxH5PayInfo.class);
                    String wxJs = getWxjsRep(wxH5PayInfo);
                    logger.info("华夏银行支付--公众号组装支付wxjs文件：" + wxJs);
                    dto.setH5PayParams(wxJs); // 微信支付信息 js 文件串
                    dto.setFrowdToRediToInput(CommEnum.H5_PAY_METHO_2.getRspCode()); // 输出js 文件 操作
                    dto.setPayId(rsp.getTradeNo()); // 华夏银行支付系统订单号
                }
            } else {
                dto.setTradeCode(CommEnum.RESP_CODE.getRspCode());
                dto.setTradeDesc(map.get(CommEnum.RESP_DESC.getRspCode()));
            }
        } catch (Exception e) {
            logger.error("华夏银行公众号下单出现异常",e);
        }

        return dto;
    }

    /**
     *  微信  js 文件字符 替换
     * @param wxH5PayInfo
     * @return
     * @throws IOException
     */
    private String getWxjsRep(WxH5PayInfo wxH5PayInfo) throws IOException {
        String wxJs = FileUtil.readResourceFile("wxpayOrder")
                .replace("${appId}", wxH5PayInfo.getAppId())
                .replace("${timeStamp}", wxH5PayInfo.getTimeStamp())
                .replace("${nonceStr}", wxH5PayInfo.getNonceStr())
                .replace("${packageWeChat}", wxH5PayInfo.getPackages())
                .replace("${paySign}", wxH5PayInfo.getPaySign())
                .replace("${signType}", wxH5PayInfo.getSignType());
        return wxJs;
    }

    @Override
    public Object queryTrade(Object... obj) throws Exception {
        logger.info("进入组装华夏银联微信公众号h5支付结果查询报文开始---queryTrade");
        ApplyOrderChannelBaseDTO applyOrderChannel = new ApplyOrderChannelBaseDTO();
        applyOrderChannel.setRespCode(RespUtil.codeError);
        applyOrderChannel.setTradeCode(RespUtil.codeError);
        applyOrderChannel.setTradeDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());

        HxbOrderQueryReq oqReq = new HxbOrderQueryReq();
        oqReq.setService(CommonHxbPayQrCodePayEnum.HXB_QUERY_PAY_TRADE_SERVICE.getRspCode());

        if(obj[0] instanceof ParkingPreChannelPayRouteConfig) {
            ParkingPreChannelPayRouteConfig payRouteConfig = (ParkingPreChannelPayRouteConfig) obj[0];
        }

        if(obj[1] instanceof ParkingQrcodeMchntConfig) {
            ParkingQrcodeMchntConfig mconf = (ParkingQrcodeMchntConfig) obj[1];
            oqReq.setAppKey(mconf.getMchntAppId());  // APPid
            oqReq.setMerchantNo(mconf.getMchntId()); // 商户号
            oqReq.setTradeType(mconf.getRsrvStr3()); // 交易码
        }
        if(obj[2] instanceof ParkingTradeOrder) {
            ParkingTradeOrder pto = (ParkingTradeOrder) obj[2];
            oqReq.setOrderNo(pto.getTradeId()); // 交易订单号
            oqReq.setOrgPcsDate(Utility.getDateYYYYMMDD(pto.getCreatedTime()));
        }
        // 请求 子系统（）进行远程调用
        try {
            String oqReqXml = XmlUtil.ObjToXml(oqReq, HxbOrderQueryReq.class);
            logger.info("组装华夏银行支付订单查询请求报文："+ oqReqXml);
            String cellRspXml = prHxbQrCodeService.hxbNewQrCodeCallCenterSync(oqReqXml);
            logger.info("接收华夏银行支付订单查询响应报文："+cellRspXml);
            Map<String, String> map = invokeInteService.parseResp(cellRspXml);
            if("1".equals(map.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
                String oqRspXml = map.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
                logger.info("华夏银行--渠道公众号订单查询返回为："+ oqRspXml);
                HxbOrderQueryRsp orderQueryRsp = (HxbOrderQueryRsp) XmlUtil.XmlToObj(oqRspXml, HxbOrderQueryRsp.class);
                if(RespUtil.successCode.equals(orderQueryRsp.getRespCode())) {
                    applyOrderChannel.setState(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());
                    applyOrderChannel.setRespCode(CommEnum.SUCCESS_COID.getRspCode());  // 000000
                    applyOrderChannel.setTradeCode(CommEnum.SUCCESS_COID.getRspCode());  // 000000
                    applyOrderChannel.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());  // paid
                    applyOrderChannel.setTradeDesc(orderQueryRsp.getRespDesc());  // 成功

                    applyOrderChannel.setPayId(orderQueryRsp.getTradeNo()); // 华夏银行支付系统订单号
                    applyOrderChannel.setPayTime(orderQueryRsp.getSuccessTime()); // 支付成功时间
                } else {
                    applyOrderChannel.setTradeCode(orderQueryRsp.getRespCode());
                    applyOrderChannel.setTradeDesc(orderQueryRsp.getRespDesc());
                    applyOrderChannel.setState(CommEnum.GZBD_ORDER_CURR_STAT_14.getRspCode());
                }
            } else {
                applyOrderChannel.setTradeCode(CommEnum.RESP_CODE.getRspCode());
                applyOrderChannel.setTradeDesc(map.get(CommEnum.RESP_DESC.getRspCode()));
            }

        } catch (Exception e) {
            logger.error("订单查询出现异常",e);
        }

        return applyOrderChannel;
    }
}
