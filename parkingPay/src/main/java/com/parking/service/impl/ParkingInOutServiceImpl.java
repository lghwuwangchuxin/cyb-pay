package com.parking.service.impl;

import com.parking.dao.ParkingChannelIcbcCarDao;
import com.parking.dao.ParkingChannelShParamsConfigDao;
import com.parking.domain.ParkingChannelAccessRouteConfig;
import com.parking.domain.ParkingChannelCar;
import com.parking.domain.ParkingChannelShParamsConfig;
import com.parking.domain.ParkingWhiteTempCar;
import com.parking.dto.*;
import com.parking.dto.icbc.CommonRsp;
import com.parking.dtosh.NoticeEntranceReq;
import com.parking.dtosh.NoticeEntranceRsp;
import com.parking.dtosh.NoticeExitReq;
import com.parking.dtosh.NoticeExitRsp;
import com.parking.service.*;
import com.parking.unsens.channel.service.ParkingIcbcChannelAccessService;
import com.parking.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("parkingInOutService")
public class ParkingInOutServiceImpl implements ParkingInOutService {

    private static final Logger logger = LoggerFactory.getLogger(ParkingInOutServiceImpl.class);
    @Inject
    private ParkingChannelConfQueryService parkingChannelConfQueryService;
    @Inject
    private ParkingChannelShParamsConfigDao parkingChannelShParamsConfigDao;
    @Inject
    private InvokeInteService invokeInteService;
    @Inject
    private PrParkingUnionPayService prParkingUnionPayService;
    @Inject
    private ParkingWhiteTempCarService parkingWhiteTempCarService;
    @Inject
    private ParkingUnChannelQueryCarService parkingUnChannelQueryCarService;
    @Inject
    private ParkingIcbcChannelAccessService parkingIcbcChannelAccessService;
    @Inject
    private ParkingChannelIcbcCarDao parkingChannelIcbcCarDao;

    /**
     *
     * @param mreq
     * @return  多渠道入场通知服务
     * @throws Exception
     */
    @Override
    public EnterParkingRsp enterParkingNotify(EnterParkingReq mreq) throws Exception {
        logger.info("多渠道入场通知服务--------------enterParkingNotify");
        EnterParkingRsp mrsp = new EnterParkingRsp();
        setEntRspParams(mreq, mrsp);

        // 参数进行进行检查
        //(1)参数检查
        if (checkParamsIsNull(mreq) <0) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
            return mrsp;
        }

        // (2) 车牌、停车场为unicode编码，转换为中文
        setEnterParkingReq(mreq);

        //(3)停车场渠道路由配置
        List<ParkingChannelAccessRouteConfig> parkingChannelAccessRouteConfigList = parkingChannelConfQueryService.queryParkingChannelAccessRouteConfigList(mreq.getParkId());
        if (null == parkingChannelAccessRouteConfigList || parkingChannelAccessRouteConfigList.size() == 0 ) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.PARKING_CHANNEL_CONF_DESC.getRspMsg());
            return mrsp;
        }
        ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfigOne = null;
        ParkingChannelShParamsConfig parkingChannelShParamsConfig = null;
        CommonRsp commonRsp = null;
        boolean icbcFlag = false;
        String pushFlag = "";
        // 多渠道、接入渠道路由选择
        Iterator iterator = parkingChannelAccessRouteConfigList.iterator();			//多渠道入场通知遍历发送
        while (iterator.hasNext()) {
            parkingChannelAccessRouteConfigOne = (ParkingChannelAccessRouteConfig) iterator.next();

            // 上海金融事业部智慧平台入场通知
            if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), parkingChannelAccessRouteConfigOne.getChannelSelect())) {
                parkingChannelShParamsConfig = findParkingChannelShParamsConfig(mreq.getParkId());
                if (null != parkingChannelShParamsConfig)
                    pushFlag += enterUnionpayParkingNotify(mreq,parkingChannelShParamsConfig);
                continue;
            }

            // 工商银行渠道
            if (StringUtil.checkStringsEqual(CommEnum.ICBC_PAY_CHANNEL.getRspCode(), parkingChannelAccessRouteConfigOne.getChannelSelect())) {
                icbcFlag = true;
                commonRsp  = enterIcbcNotify(mreq);
                pushFlag += (null == commonRsp ? CommIcbcEnum.ICBC_ENTER_INFAILL.getRespCode() : CommIcbcEnum.ICBC_ENTER_INSUCCESS.getRespCode());
                continue;
            }
        }

        // 工行渠道白名单更新
        if (icbcFlag) {
            setChannelIcbcCar(commonRsp, mreq);
        }

        // 统一查询 无感
        queryChannelCar(mreq);
        setRspParams(mrsp, RespUtil.successCode, CommEnum.PARKING_ENTER_SUCCESS_DESC.getRspMsg());
        return mrsp;
    }

    // 统一 查询 无感
    private void queryChannelCar(EnterParkingReq mreq) {
        ParkingChannelCarReq carReq = new ParkingChannelCarReq();
        carReq.setCarPlate(mreq.getCarPlate());
        carReq.setParkingNo(mreq.getParkId());
        carReq.setParkingName(mreq.getParkName());// 停车场名称
        carReq.setCarColor(mreq.getCarPlateColor());
        carReq.setTradeId(mreq.getParkMchntSysNumber());
        try {
            parkingUnChannelQueryCarService.unParkingChannelQuery(carReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /* setChannelIcbcCar: 设置 工商 入场返回 银行无感
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选)
	 * TODO(这里描述这个方法的注意事项 – 可选)
	 *
     * @param  @param entranceExitRsp    设定文件
	 * @return void    DOM对象
	 * @throws SQLException
	 * @throws
      * @since  CodingExample　Ver 1.1
     */

    private void setChannelIcbcCar(CommonRsp entranceExitRsp, EnterParkingReq mreq) throws SQLException {
        if (null == entranceExitRsp) {
            entranceExitRsp = new CommonRsp();
            entranceExitRsp.setRespCode(RespUtil.noResult);
            entranceExitRsp.setMessagecode(CommIcbcEnum.ICBC_QUERY_MESSAGE_CODE_2002.getRespCode());
        }
        if (!StringUtil.checkStringsEqual(RespUtil.successCode, entranceExitRsp.getRespCode())) {
        }
        // 置返回值
        String permitState = getPeritState(entranceExitRsp.getMessagecode());
        ParkingChannelCar parkingChannelCar = findParkingChannelCar(mreq.getCarPlate());
        if (null == parkingChannelCar) {
            parkingChannelCar = insertParkingChannelCar(mreq, getDate(), permitState);
        } else {
            parkingChannelCar = updateParkingChannelCar(parkingChannelCar, mreq, getDate(), permitState);
        }
        uniData(parkingChannelCar);
    }
    private ParkingChannelCar findParkingChannelCar(String carPlate) throws SQLException {
        Map<String, String>  map = new HashMap<String, String>(2);
        map.put(CommEnum.UNIONPAY_CAR_PLATE_PARAMS.getRspCode(), carPlate);
        ParkingChannelCar parkingChannelCar = parkingChannelIcbcCarDao.selecttParkingChannelCar(map);
        return parkingChannelCar;
    }
    private ParkingChannelCar insertParkingChannelCar(EnterParkingReq mreq, String acceptDate, String permitState) {
        ParkingChannelCar parkingChannelCar = new ParkingChannelCar();
        parkingChannelCar.setCarPlate(mreq.getCarPlate());//车牌
        parkingChannelCar.setPermitState(permitState);
        parkingChannelCar.setParkingNo(mreq.getParkId());
        parkingChannelCar.setParkingName(mreq.getParkName());//
        parkingChannelCar.setInsetTime(acceptDate);
        parkingChannelCar.setState(CommEnum.GZBD_DB_STATUS.getRspCode());
        parkingChannelCar.setModifyTag(CommEnum.PARKING_INSERT_TAG.getRspCode());
        return parkingChannelCar;
    }
    private ParkingChannelCar updateParkingChannelCar(ParkingChannelCar parkingChannelCar, EnterParkingReq mreq,
                                                      String date, String permitState) {
        parkingChannelCar = new ParkingChannelCar();
        parkingChannelCar.setUpdateTime(date);
        parkingChannelCar.setPermitState(permitState);
        parkingChannelCar.setBindTime(date);
        parkingChannelCar.setCarPlate(mreq.getCarPlate());
        parkingChannelCar.setParkingName(mreq.getParkName());
        parkingChannelCar.setParkingNo(mreq.getParkId());
        parkingChannelCar.setModifyTag(CommEnum.PARKING_UPDATE_TAG.getRspCode());
        return parkingChannelCar;
    }
    private void uniData(ParkingChannelCar parkingChannelCar) throws SQLException {
        if (CommEnum.PARKING_INSERT_TAG.getRspCode().equals(parkingChannelCar.getModifyTag())) {
            parkingChannelIcbcCarDao.insertParkingChannelCar(parkingChannelCar);
        } else {
            parkingChannelIcbcCarDao.updateParkingChannelCar(parkingChannelCar);
        }
    }
    /**
     * getDate: 获取时间
     * TODO(这里描述这个方法适用条件 – 可选)
     * TODO(这里描述这个方法的执行流程 – 可选)
     * TODO(这里描述这个方法的使用方法 – 可选)
     * TODO(这里描述这个方法的注意事项 – 可选)
     *
     * @param  @return    设定文件
     * @return String    DOM对象
     * @throws
     * @since  CodingExample　Ver 1.1
     */

    private String getDate() {
        // 本地受理时间
        Date date = new Date();
        String acceptDate = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode()).format(date);
        return acceptDate;
    }

    // 工商银行 白名单 转换
    private String getPeritState(String status) {
        if (StringUtil.checkStringsEqual(CommIcbcEnum.ICBC_QUERY_MESSAGE_CODE_10000.getRespCode(), status)) {
            status = CommEnum.UN_PERMIT_STATUS_00.getRspCode();
        } else if(StringUtil.checkStringsEqual(CommIcbcEnum.ICBC_QUERY_MESSAGE_CODE_2002.getRespCode(), status)) {
            status = CommEnum.UN_PERMIT_STATUS_01.getRspCode();
        } else {
            status = CommEnum.UN_PERMIT_STATUS_02.getRspCode();
        }
        return status;
    }

    /**
     *
     * enterNotify: 工商 银行入场 通知  也是 查询 白名单 返回
     * TODO(这里描述这个方法适用条件 – 可选)
     * TODO(这里描述这个方法的执行流程 – 可选)
     * TODO(这里描述这个方法的使用方法 – 可选)
     * TODO(这里描述这个方法的注意事项 – 可选)
     *
     * @param  @param mreq
     * @param  @param parkingInOut
     * @param  @return
     * @param  @throws Exception    设定文件
     * @return EntranceExitRsp    DOM对象
     * @throws
     * @since  CodingExample　Ver 1.1
     */
    private CommonRsp enterIcbcNotify(EnterParkingReq mreq) throws Exception {
        Object[] obj = {mreq.getParkMchntSysNumber(), mreq.getCarPlate(), mreq.getInTime(), mreq.getParkId(), mreq.getCarPlateColor()};
        CommonRsp commonRsp = (CommonRsp) parkingIcbcChannelAccessService.enterNotify(obj);
        return commonRsp;
    }

    //上海金融事业部智慧平台入场通知
    @SuppressWarnings("rawtypes")
    private String enterUnionpayParkingNotify(EnterParkingReq mreq,ParkingChannelShParamsConfig shParamsConfig) throws Exception{
        String pushFlag = "";
        NoticeEntranceReq noticeEntranceReq = new NoticeEntranceReq();
        noticeEntranceReq.setService(CommEnum.UNIONPAY_SH_PUSH_NOTICE_ETRANCE.getRspCode());//入场通知接口
        noticeEntranceReq.setSerialNumber(mreq.getSerialNumber());
        noticeEntranceReq.setAppId(shParamsConfig.getAppId()); //应用代码
        noticeEntranceReq.setSyncId(mreq.getParkMchntSysNumber());//停车记录唯一id
        noticeEntranceReq.setPrivateKey(shParamsConfig.getSignKey());//密钥
        noticeEntranceReq.setStartTime(Utility.getDataToUtc8Del(mreq.getInTime()));//入场时间
        noticeEntranceReq.setParkId(mreq.getParkId());//停车场id
        noticeEntranceReq.setParkName(mreq.getParkName());//停车名称
        noticeEntranceReq.setPlateNumber(mreq.getCarPlate());//车牌
        try {
            String xmlReq = XmlUtil.ObjToXml(noticeEntranceReq, NoticeEntranceReq.class);
            logger.info("上海金融事业部渠道入场通知报文请求:" +xmlReq);
            String callResp = prParkingUnionPayService.parkingUnionPayCallCenterSync(xmlReq);
            Map respMap = invokeInteService.parseResp(callResp);
            if (respMap.size() > 0) {
                if (StringUtil.checkStringsEqual(CommEnum.TAG_SUECCESS.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
                    String msg = (String) respMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
                    logger.info("返回Msg数据:" +msg);
                    NoticeEntranceRsp noticeEntranceRsp = (NoticeEntranceRsp) XmlUtil.XmlToObj(msg, NoticeEntranceRsp.class);
                    if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), noticeEntranceRsp.getResultCode()))
                        pushFlag = CommEnum.UNIONPAY_SH_ENTER_INSUCCESS.getRspCode(); //成功
                    else
                        pushFlag = CommEnum.UNIONPAY_SH_ENTER_INFAILL.getRspCode(); //失败
                } else if (StringUtil.checkStringsEqual(CommEnum.TAG_FAIL.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
                    pushFlag = CommEnum.UNIONPAY_SH_ENTER_INFAILL.getRspCode(); //失败
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pushFlag;
    }

    // 统一返回码
    private void setRspParams(EnterParkingRsp mrsp, String noResult, String rspMsg) {
        mrsp.setRespCode(noResult);
        mrsp.setRespDesc(rspMsg);
    }

    // 车牌解码
    private void setEnterParkingReq(EnterParkingReq mreq) {
        mreq.setCarPlate(Utility.decodeUnicode(mreq.getCarPlate()));
        mreq.setParkName(Utility.decodeUnicode(mreq.getParkName()));
    }

    // 入场通知参数检查
    private int checkParamsIsNull(EnterParkingReq mreq) {
        if (StringUtil.checkNullString(mreq.getCarPlate()) ||
                StringUtil.checkNullString(mreq.getParkName()) ||
                StringUtil.checkNullString(mreq.getInTime()) ||
                StringUtil.checkNullString(mreq.getParkMchntSysNumber()) ||
                StringUtil.checkNullString(mreq.getParkId()) ||
                StringUtil.checkNullString(mreq.getCarPlateColor())) {
            return -1;
        }
        return 1;
    }

    /**
     *
     * @param mreq
     * @param mrsp
     */
    private void setEntRspParams(EnterParkingReq mreq, EnterParkingRsp mrsp) {
        mrsp.setRespCode(RespUtil.codeError);
        mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
        mrsp.setSign(mreq.getSign());
        mrsp.setSerialNumber(mreq.getParkMchntSysNumber());
    }

    private ParkingChannelShParamsConfig findParkingChannelShParamsConfig(
            String parkId) throws SQLException {
        ParkingChannelShParamsConfig parkingChannelShParamsConfig = new ParkingChannelShParamsConfig();
        parkingChannelShParamsConfig.setParkId(parkId);
        parkingChannelShParamsConfig.setStates(CommEnum.GZBD_DB_STATUS.getRspCode());
        parkingChannelShParamsConfig = parkingChannelShParamsConfigDao.selectParkingChannelShParamsConfigById(parkingChannelShParamsConfig);
        return parkingChannelShParamsConfig;
    }

    /**
     *
     * @param mreq
     * @return    多渠道出场通知服务
     * @throws Exception
     */
    @Override
    public OutParkingRsp outParkingNotify(OutParkingReq mreq) throws Exception {
        logger.info("进入多渠道出场通知服务------------------outParkingNotify");
        OutParkingRsp mrsp = new OutParkingRsp();
        setOutRspParams(mreq, mrsp);

        if (checkParamsIsNull(mreq) <0) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
            return mrsp;
        }

        // (1) 车牌、停车场为unicode编码，转换为中文
        setOutParkingReq(mreq);
        // (2)停车场渠道路由配置
        List <ParkingChannelAccessRouteConfig> parkingChannelAccessRouteConfigList = parkingChannelConfQueryService.queryParkingChannelAccessRouteConfigList(mreq.getParkId());
        if (null == parkingChannelAccessRouteConfigList || parkingChannelAccessRouteConfigList.size() <=0) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.PARKING_CHANNEL_CONF_DESC.getRspMsg());
            return mrsp;
        }

        String pushFlag = "";
        //遍历多渠道 ，多渠道发出场通知
        ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfigOne = null;
        Iterator iterator = parkingChannelAccessRouteConfigList.iterator();
        while (iterator.hasNext()) {
            parkingChannelAccessRouteConfigOne = (ParkingChannelAccessRouteConfig) iterator.next();
            pushFlag += outMulitParkingNotify(mreq, parkingChannelAccessRouteConfigOne);
        }
        setRspParams(mrsp, RespUtil.successCode, "notify success");
        deleteParkingWhiteTempCar(mreq.getCarPlate());// 清除临时白名单数据
        return mrsp;
    }

    private void deleteParkingWhiteTempCar(String carPlate) throws Exception {
        ParkingWhiteTempCar tempCar = new ParkingWhiteTempCar();
        tempCar.setCarPlate(carPlate);
        parkingWhiteTempCarService.deleteParkingWhiteTempCar(tempCar);
    }

    // 出场通知参数检查
    private int checkParamsIsNull(OutParkingReq mreq) {
        if (StringUtil.checkNullString(mreq.getCarPlate()) ||
                StringUtil.checkNullString(mreq.getParkName()) ||
                StringUtil.checkNullString(mreq.getInTime()) ||
                StringUtil.checkNullString(mreq.getOutTime()) ||
                StringUtil.checkNullString(mreq.getParkMchntSysNumber()) ||
                StringUtil.checkNullString(mreq.getParkId()) ||
                StringUtil.checkNullString(mreq.getCarPlateColor())) {
            return -1;
        }
        return 1;
    }

    //进行解码
    private void setOutParkingReq(OutParkingReq mreq) {
        mreq.setCarPlate(Utility.decodeUnicode(mreq.getCarPlate()));
        mreq.setParkName(Utility.decodeUnicode(mreq.getParkName()));
    }

    // 出场通知初始化
    private void setOutRspParams(OutParkingReq mreq, OutParkingRsp mrsp) {
        mrsp.setRespCode(RespUtil.codeError);
        mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
        mrsp.setSign(mreq.getSign());
        mrsp.setSerialNumber(mreq.getParkMchntSysNumber());
    }
   // 出场通知置统一返回
    private void setRspParams(OutParkingRsp mrsp, String respCode, String respDesc) {
        mrsp.setRespCode(respCode);
        mrsp.setRespDesc(respDesc);
    }

    // 统一出场通知服务
    private String outMulitParkingNotify(OutParkingReq mreq,ParkingChannelAccessRouteConfig parkingChannelAccessRouteConfigOne) throws Exception {

        ParkingChannelShParamsConfig parkingChannelShParamsConfig = null;

        String pushFlag = "";
        boolean outFlag = false;

        // 上海银联金融事业部智慧平台出场通知上送
        if (!StringUtil.checkNullString(parkingChannelAccessRouteConfigOne.getChannelSelect()) && StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), parkingChannelAccessRouteConfigOne.getChannelSelect())) {
            // 上海银联金融事业部智慧平台出场通知上送  取入场时间
            if (checkParamsIsNull(mreq.getInTime(), mreq.getOutTime()) <0) {
                //setRspParams(mrsp, RespUtil.noResult, CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
                return pushFlag;
            }
            parkingChannelShParamsConfig = findParkingChannelShParamsConfig(mreq.getParkId());
            if (null != parkingChannelShParamsConfig)
                pushFlag = outParkingShNotify(mreq,parkingChannelShParamsConfig);
            return pushFlag;
        }

        return pushFlag;
    }
    //sh 出场时间检查
    private int checkParamsIsNull(String startTime,String endTime) {
        if (StringUtil.checkNullString(startTime) || StringUtil.checkNullString(endTime)) return -1;
        return 1;
    }
    // 组装上海金融事业部智慧平台出场通知
    private String outParkingShNotify(OutParkingReq mreq, ParkingChannelShParamsConfig shParamsConfig) throws Exception {
        logger.info("上海金融事业部智慧平台渠道出场通知---------");
        String pushFlag = "";
        NoticeExitReq noticeExitReq = new NoticeExitReq();
        noticeExitReq.setService(CommEnum.UNIONPAY_SH_PUSH_NOTICE_EXIT.getRspCode());//出场通知接口
        noticeExitReq.setSerialNumber(mreq.getSerialNumber()); //请求流水号
        noticeExitReq.setPrivateKey(shParamsConfig.getSignKey());//密钥
        noticeExitReq.setAppId(shParamsConfig.getAppId()); //应用代码
        noticeExitReq.setSyncId(mreq.getParkMchntSysNumber());//停车记录唯一id
        noticeExitReq.setStartTime( Utility.getDataToUtc8Del(mreq.getInTime()) );//入场时间
        noticeExitReq.setEndTime(Utility.getDataToUtc8Del(mreq.getOutTime()));//出场时间 UTC
        noticeExitReq.setParkId(mreq.getParkId());//停车场id
        noticeExitReq.setParkName(mreq.getParkName());//停车名称
        noticeExitReq.setPlateNumber(mreq.getCarPlate());//车牌
        try {
            String xmlReq = XmlUtil.ObjToXml(noticeExitReq, NoticeExitReq.class);
            logger.info("上海金融事业部渠道出场通知报文请求:" +xmlReq);
            String callResp = prParkingUnionPayService.parkingUnionPayCallCenterSync(xmlReq);
            Map respMap = invokeInteService.parseResp(callResp);
            if (respMap.size() > 0) {
                if (StringUtil.checkStringsEqual(CommEnum.TAG_SUECCESS.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
                    String msg = (String) respMap.get(CommEnum.XML_RESP_MSG_KEY.getRspCode());
                    logger.info("返回Msg数据:" +msg);
                    NoticeExitRsp noticeExitRsp = (NoticeExitRsp) XmlUtil.XmlToObj(msg, NoticeExitRsp.class);
                    if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_RESULT_CODE_0.getRspCode(), noticeExitRsp.getResultCode()))
                        pushFlag = CommEnum.UNIONPAY_SH_OUTSUCCESS.getRspCode(); //成功
                    else
                        pushFlag = CommEnum.UNIONPAY_SH_OUTFAIL.getRspCode(); //失败
                } else if (StringUtil.checkStringsEqual(CommEnum.TAG_FAIL.getRspCode(), respMap.get(CommEnum.GET_MSG_TAG.getRspCode()))) {
                    pushFlag = CommEnum.UNIONPAY_SH_OUTFAIL.getRspCode(); //失败
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pushFlag;
    }
}
