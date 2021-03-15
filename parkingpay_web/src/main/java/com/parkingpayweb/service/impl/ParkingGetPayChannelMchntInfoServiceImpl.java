package com.parkingpayweb.service.impl;

import com.parkingpayweb.dao.*;
import com.parkingpayweb.domain.*;
import com.parkingpayweb.dto.ParkingGetChannelMchntReq;
import com.parkingpayweb.dto.ParkingGetChannelMchntRsp;
import com.parkingpayweb.dto.ParkingLot;
import com.parkingpayweb.service.ParkingGetPayChannelMchntInfoService;
import com.parkingpayweb.util.CommonEnun;
import com.parkingpayweb.util.FastJSONUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("parkingGetPayChannelMchntInfoService")
public class ParkingGetPayChannelMchntInfoServiceImpl implements ParkingGetPayChannelMchntInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ParkingGetPayChannelMchntInfoServiceImpl.class);

    @Autowired
    private ParkingPreChannelPayRouteConfigDao parkingPreChannelPayRouteConfigDao;
    @Autowired
    private ParkingPreMchntConfigDao parkingPreMchntConfigDao;
    @Autowired
    private ParkingQrCodeCbChannelPayRouteConfigDao parkingQrCodeCbChannelPayRouteConfigDao;
    @Autowired
    private ParkingQrCodeCbMchntConfigDao parkingQrCodeCbMchntConfigDao;
    @Autowired
    private ParkingChannelQrcodeTerminPayRouteConfigDao parkingChannelQrcodeTerminPayRouteConfigDao;
    @Autowired
    private ParkingQrcodeMchntConfigDao parkingQrcodeMchntConfigDao;
    @Autowired
    private ParkingQrcodeTerminConfigDao parkingQrcodeTerminConfigDao;
    @Autowired
    private ParkingChannelAccessRouteConfigDao parkingChannelAccessRouteConfigDao;

    @Override
    public ParkingGetChannelMchntRsp getParkingGetChannelMchntInfo(ParkingGetChannelMchntReq req) throws Exception {
        logger.info("进入停车场获取获取商户配置信息：");
        ParkingGetChannelMchntRsp rsp = new ParkingGetChannelMchntRsp();
        setRspParams(rsp, CommonEnun.noResult, "未知异常");

        if (StringUtils.isEmpty(req.getParkId())) {
            setRspParams(rsp, CommonEnun.noResult, "此停车场{"+req.getParkList()+"}"+"未配置商户信息");
            return rsp;
        }
        //List<ParkingLot> list = FastJSONUtil.parseObjectArray(req.getParkList(), ParkingLot.class);
        //ParkingLot pl = list.get(0);
        // h5 商户参数
        ParkingQrcodeMchntConfig qrcodeMchntConfig = null;
        ParkingPreChannelPayRouteConfig  preConfig = new ParkingPreChannelPayRouteConfig();
        preConfig.setParkId(req.getParkId());
        preConfig = parkingPreChannelPayRouteConfigDao.selectParkingPreChannelPayRouteConfig(preConfig);
        if (null != preConfig) {
            qrcodeMchntConfig = new ParkingQrcodeMchntConfig();
            qrcodeMchntConfig.setAppChannel(preConfig.getChannelSelect());
            qrcodeMchntConfig.setMchntNo(preConfig.getMchntNo());
            qrcodeMchntConfig = parkingPreMchntConfigDao.selectParkingPreMchntConfig(qrcodeMchntConfig);
            if (null != qrcodeMchntConfig) {
                rsp.setPreH5MchntInfo(FastJSONUtil.toJSONString(qrcodeMchntConfig));
            }
        }
        // 主扫 商户参数
        ParkingQrCodeCbChannelPayRouteConfig cbChannelPayRouteConfig = new ParkingQrCodeCbChannelPayRouteConfig();
        cbChannelPayRouteConfig.setParkId(req.getParkId());
        cbChannelPayRouteConfig = parkingQrCodeCbChannelPayRouteConfigDao.selectParkingQrCodeCbChannelPayRouteConfig(cbChannelPayRouteConfig);
        if (null != cbChannelPayRouteConfig) {
            qrcodeMchntConfig = new ParkingQrcodeMchntConfig();
            qrcodeMchntConfig.setAppChannel(cbChannelPayRouteConfig.getChannelSelect());
            qrcodeMchntConfig.setMchntNo(cbChannelPayRouteConfig.getMchntNo());
            qrcodeMchntConfig = parkingQrCodeCbMchntConfigDao.selectParkingCbMchntConfig(qrcodeMchntConfig);
            if (null != qrcodeMchntConfig) {
                rsp.setQrcodeCbMchntInfo(FastJSONUtil.toJSONString(qrcodeMchntConfig));
            }
        }
        // 被扫商户参数
        ParkingChannelQrcodeTerminPayRouteConfig payRouteConfig = new ParkingChannelQrcodeTerminPayRouteConfig();
        payRouteConfig.setParkId(req.getParkId());
        payRouteConfig = parkingChannelQrcodeTerminPayRouteConfigDao.selectParkingChannelQrcodeTerminPayRouteConfigById(payRouteConfig);
        ParkingQrcodeTerminConfig terminConfig  = new ParkingQrcodeTerminConfig();
        terminConfig.setParkId(req.getParkId());
        terminConfig = parkingQrcodeTerminConfigDao.selectParkingTerminConfigById(terminConfig);
        if (null != payRouteConfig && null != terminConfig) {
            qrcodeMchntConfig = new ParkingQrcodeMchntConfig();
            qrcodeMchntConfig.setAppChannel(payRouteConfig.getPayChannelSelect());
            qrcodeMchntConfig.setMchntNo(terminConfig.getMchntNo());
            qrcodeMchntConfig = parkingQrcodeMchntConfigDao.selectParkingQrcodeMchntConfig(qrcodeMchntConfig);
            if (null != qrcodeMchntConfig) {
                rsp.setQrcodeBcMchntInfo(FastJSONUtil.toJSONString(qrcodeMchntConfig));
            }
        }
        setRspParams(rsp, CommonEnun.SUCCESS, "查询成功");
        return rsp;
    }

    private void setRspParams(ParkingGetChannelMchntRsp rsp, String respCode, String respDesc) {
        rsp.setRespCode(respCode);
        rsp.setRespDesc(respDesc);
    }
}
