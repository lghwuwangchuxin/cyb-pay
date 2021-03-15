package com.parking.service.impl;

import com.parking.dao.ParkingChannelShParamsConfigDao;
import com.parking.dao.ParkingTradeOrderDao;
import com.parking.domain.ParkingChannelAccessRouteConfig;
import com.parking.domain.ParkingChannelShParamsConfig;
import com.parking.domain.ParkingTradeOrder;
import com.parking.domain.ParkingWhiteTempCar;
import com.parking.dto.*;
import com.parking.dtosh.PayBillReq;
import com.parking.dtosh.PayBillRsp;
import com.parking.service.*;
import com.parking.unsens.channel.service.ParkingIcbcOrderService;
import com.parking.unsens.channel.service.ParkingShOrderPayService;
import com.parking.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("parkingOrderService")
public class ParkingOrderServiceImpl implements ParkingOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ParkingOrderServiceImpl.class);
    @Inject
    private ParkingTradeOrderDao tradeOrderDao;
    @Inject
    private ParkingChannelConfQueryService parkingChannelConfQueryService;
    @Inject
    private ParkingUnChannelQueryCarService parkingUnChannelQueryCarService;
    @Inject
    private SeqService seqService;
    @Inject
    private ParkingChannelShParamsConfigDao parkingChannelShParamsConfigDao;
    @Inject
    private ParkingShOrderPayService parkingShOrderPayService;
    @Inject
    private EParkingQueryService eParkingQueryService;
    @Inject
    private ParkingWhiteTempCarService parkingWhiteTempCarService;
    @Inject
    private ParkingIcbcOrderService parkingIcbcOrderService;
    @Inject
    private ParkingAsynNotifyService parkingAsynNotifyService;
    @Inject
    private ParkingQueryOrderService parkingQueryOrderService;


    private void setRspParams(PayNotifyRsp mrsp, String respCode, String respDesc) {
        mrsp.setRespDesc(respDesc);
        mrsp.setRespCode(respCode);
    }

    // 通知报文
    private PayNotifyReq getPayNotifyReq(PayNotifyReq mreq, String tradeId, String payType, String channelNum) {
        PayNotifyReq  payNotifyReq = new PayNotifyReq();
        payNotifyReq.setService("payNotifyResult");
        payNotifyReq.setCarPlate(mreq.getCarPlate());
        payNotifyReq.setParkId(mreq.getParkId());
        payNotifyReq.setParkName(mreq.getParkName());
        payNotifyReq.setOrderId(tradeId);
        payNotifyReq.setResType(mreq.getResType());
        payNotifyReq.setPayType(String.valueOf(ParkingChannelPayType.getDecsitPayTypeMap().get(payNotifyReq.getResType())));
        payNotifyReq.setPayChannel(String.valueOf(ParkingChannelPayType.getDecsitChannelPayTypeMap().get(payType)));
        payNotifyReq.setChannelNum(channelNum);
        payNotifyReq.setTxnAmt(mreq.getTxnAmt());
        payNotifyReq.setPayAmt(mreq.getPayAmt());
        payNotifyReq.setSerialNumber(mreq.getSerialNumber());
        payNotifyReq.setParkMchntSysNumber(mreq.getParkMchntSysNumber());
        payNotifyReq.setTradeStatus(mreq.getTradeStatus());
        payNotifyReq.setStayTime(mreq.getStayTime());
        return payNotifyReq;
    }


    private ParkingTradeOrder updateParkingTradePayNotify(
            ParkingTradeOrder tradeOrder, PayNotifyReq mreq, String acceptDate,
            String status, String orderStauts) {
        tradeOrder = new ParkingTradeOrder();
        tradeOrder.setTradeId(mreq.getOrderId());
        tradeOrder.setTradeStatus(orderStauts);
        tradeOrder.setUpdateTime(acceptDate);
        tradeOrder.setPayAmt(mreq.getPayAmt());
        tradeOrder.setResType(mreq.getResType()) ;//子业务编码
        tradeOrder.setTradeCode(mreq.getTradeCode());//交易码
        tradeOrder.setTradeDesc(mreq.getTradeDesc());//交易描述
        //tradeOrder.setPayType(mreq.getPayType());// 支付类型
        tradeOrder.setOutTradeNo(mreq.getPayId()); //第三方支付流水号
        tradeOrder.setPayTime(acceptDate);
        tradeOrder.setModifyTag(CommEnum.PARKING_UPDATE_TAG.getRspCode());
        tradeOrder.setStates(status);
        return tradeOrder;
    }

    private ParkingTradeOrder updatePayParkingTradeOrderFail(
            ParkingTradeOrder tradeOrder, PayNotifyReq mreq, String tradeStatus) {
        tradeOrder = new ParkingTradeOrder();
        tradeOrder.setTradeId(mreq.getOrderId());
        tradeOrder.setTradeStatus(tradeStatus);
        //tradeOrder.setPayType(mreq.getPayType());//支付类型
        tradeOrder.setOutTradeNo(!StringUtil.checkNullString(mreq.getPayId()) ? mreq.getPayId() : null);
        tradeOrder.setTradeCode(!StringUtil.checkNullString(mreq.getTradeCode()) ? mreq.getTradeCode() : null);
        tradeOrder.setTradeDesc(!StringUtil.checkNullString(mreq.getTradeDesc()) ? mreq.getTradeDesc() : null);
        tradeOrder.setModifyTag(CommEnum.PARKING_UPDATE_TAG.getRspCode());
        return tradeOrder;
    }
    /**
     *   无感支付通知业务类
     * @param mreq
     * @return
     * @throws Exception
     */
    @Override
    public PayNotifyRsp notifyPayOrderAResult(PayNotifyReq mreq) throws Exception {
        logger.info("进入统一支付通知业务类---------------------notifyPayOrderAResult");
        PayNotifyRsp mrsp = new PayNotifyRsp();
        setInitRspParams(mrsp,mreq);


        // (1) 查询订单
        ParkingTradeOrder tradeOrder = new ParkingTradeOrder();
        tradeOrder.setOrderId(mreq.getOrderId());
        tradeOrder = tradeOrderDao.selectParkingOrderById(tradeOrder);
        if (null == tradeOrder) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.ORDER_NO_DESC.getRspMsg());
            return mrsp;
        }
        // 判断是否 重复 销账
        if (StringUtil.checkStringsEqual(CommEnum.PAY_Finished_CODE.getRspCode(), tradeOrder.getTradeStatus())) {
            setRspParams(mrsp, RespUtil.successCode, CommEnum.PAY_Finished_CODE.getRspMsg());
            return mrsp;
        }
        mreq.setNotifySysUrl(tradeOrder.getNotifyUrl());
        mreq.setParkMchntSysNumber(tradeOrder.getParkMchntSysNumber());
        mreq.setStayTime(tradeOrder.getTimeLong());
        mreq.setChannelNum(tradeOrder.getChannelNum());
        mreq.setParkName(tradeOrder.getParkName());
        mreq.setParkId(tradeOrder.getParkId());
        mreq.setCarPlate(tradeOrder.getCarPlate());
        mreq.setResType(tradeOrder.getResType());
        mreq.setTxnAmt(tradeOrder.getTxnAmt());
        mreq.setPayAmt(tradeOrder.getPayAmt());
        mreq.setTradeStatus(mreq.getTradeStatus());
        // 本地受理时间
        Date date = new Date();
        String acceptDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);

        if (CommEnum.PAY_PAID_CODE.getRspCode().equals(mreq.getTradeStatus())) {
                // 订单表
                tradeOrder = updateParkingTradePayNotify(tradeOrder, mreq, acceptDate,  CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode(), CommEnum.PAY_Finished_CODE.getRspCode());
                uniDealData(tradeOrder);
                setRspParams(mrsp, RespUtil.successCode, "[支付前置]自助停车处理完成");
                // 支付通知 车场业务系统
                PayNotifyReq payNotifyReq = getPayNotifyReq(mreq, mreq.getOrderId(), mreq.getPayType(), mreq.getChannelNum());
                String reqXml = XmlUtil.ObjToXml(payNotifyReq, PayNotifyReq.class);
                logger.info("云端系统支付通知请求报文："+reqXml);
                 try {
                     String rspXml = PostUtil.transferData(reqXml, "UTF-8", mreq.getNotifySysUrl());
                     logger.info("云端系统支付通知响应报文：" +rspXml);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
        } else {
            // 订单表
            tradeOrder = updatePayParkingTradeOrderFail(tradeOrder, mreq,mreq.getTradeStatus());
            uniDealData(tradeOrder);
            setRspParams(mrsp, RespUtil.payFail, "支付失败，请重新操作");
        }

        return mrsp;
    }

    private void setInitRspParams(PayNotifyRsp mrsp, PayNotifyReq mreq) {
        mrsp.setRespCode(RespUtil.codeError);
        mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
        mrsp.setSign(mreq.getSign());
        mrsp.setSerialNumber(mreq.getSerialNumber());
    }

    //自动扣费检查检查
    private int checkParamsIsNnull(ParkingAutoPayReq mreq) {
        if (StringUtil.checkNullString(mreq.getSerialNumber()) ||
                StringUtil.checkNullString(mreq.getParkMchntSysNumber()) ||
                StringUtil.checkNullString(mreq.getParkId()) ||
                StringUtil.checkNullString(mreq.getPayAmt()) ||
                StringUtil.checkNullString(mreq.getTxnAmt()) ||
                StringUtil.checkNullString(mreq.getCarPlate()) ||
                StringUtil.checkNullString(mreq.getParkName()) ||
                StringUtil.checkNullString(mreq.getStayTime()) ||
                StringUtil.checkNullString(mreq.getNotifySysUrl()) ||
                StringUtil.checkNullString(mreq.getInTime()) ||
                StringUtil.checkNullString(mreq.getOutTime()) ||
                StringUtil.checkNullString(mreq.getCarPlateColor())) {
            return -1;
        }
        return 1;
    }
    //判断交易金额  支付金额
    private int checkTxnAmtToPayAmt(String txnAmt,String payAmt) {
        int falg = 0;
        if (!StringUtil.checkNumber(txnAmt) || !StringUtil.checkNumber(payAmt) || Integer.valueOf(txnAmt) <= 0 || Integer.valueOf(payAmt) <= 0 ) falg = -1;
        return falg;
    }

    // 多渠道无感查询
    private UserDTO queryMulitCarStates(String carPlate, String parkId, String parkName, String carColour) {
        UserDTO userDTO = new UserDTO();
        userDTO.setChannelId(-1);
        ParkingChannelCarReq  mreq = new ParkingChannelCarReq();
        mreq.setCarPlate(carPlate); //车牌
        mreq.setParkingNo(parkId); //停车id
        mreq.setParkingName(parkName);//车场名称
        mreq.setCarColor(carColour);
        mreq.setInnerFalg("0"); // 本地 查询使用 有些渠道使用
        try {
            ParkingChannelCarRsp mrsp = parkingUnChannelQueryCarService.unParkingChannelQuery(mreq);
            if (null != mrsp && StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), mrsp.getRespCode())) {
                if (!StringUtil.checkNullString(mrsp.getPermitState()) && StringUtil.checkStringsEqual(CommEnum.UN_PERMIT_STATUS_00.getRspCode(), mrsp.getPermitState())) {
                    userDTO.setChannelId(Integer.valueOf(mrsp.getChannelId()).intValue());
                    //userDTO.setCustAgrNo(mrsp.getCustAgrNo()); // 客户协议号  不是所有渠道 都有协议号
                    //userDTO.setUserId(mrsp.getUserId()); // 客户id
                    return userDTO;
                }
            } else return userDTO;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询白名单异常：" + e.getMessage());
            userDTO.setChannelId(-1);
            return userDTO;
        }
        return userDTO;
    }

    private void setChannelPayInfo(ParkingAutoPayReq mreq, String payType,
                                   String userId) {
        mreq.setPayType(payType); //设置渠道
        mreq.setUserId(null == userId ? null : userId);
    }

    // 中文名称解码
    private void setDecodeDnicode(ParkingAutoPayReq mreq) {
        mreq.setCarPlate(Utility.decodeUnicode(mreq.getCarPlate()));
        mreq.setParkName(Utility.decodeUnicode(mreq.getParkName()));
        mreq.setStayTime(Utility.decodeUnicode(mreq.getStayTime()));
    }
    // 格式化时间
    private String getDate(String dateFormat, Date date) {
        String acceptDate = new SimpleDateFormat(dateFormat).format(date);
        return acceptDate;
    }
    // 无感订单插入
    private ParkingTradeOrder inserParkingTradeOrder(ParkingAutoPayReq mreq, String tradeId, String acceptDate, String partParkId) {
        logger.info("支付渠道："+mreq.getPayType());
        ParkingTradeOrder tradeOrder = new ParkingTradeOrder();
        tradeOrder.setTradeId(tradeId);
        tradeOrder.setOrderId(tradeId);
        tradeOrder.setAcceptMonth(acceptDate.substring(5, 7));
        tradeOrder.setUserId(mreq.getUserId());
        tradeOrder.setTxnAmt(mreq.getTxnAmt());
        tradeOrder.setPayAmt(mreq.getPayAmt());
        tradeOrder.setMchntSysNumber(mreq.getSerialNumber());
        tradeOrder.setParkMchntSysNumber(mreq.getParkMchntSysNumber());

        tradeOrder.setPayType(mreq.getPayType());
        tradeOrder.setCarPlate(mreq.getCarPlate()); //车牌
        tradeOrder.setParkId(mreq.getParkId()); // 停车编号
        tradeOrder.setPartParkId(partParkId);// 第三停车停车 标示
        tradeOrder.setResName(CommEnum.SELF_STOP_CAR.getRspMsg() + mreq.getCarPlate() + CommEnum.BRACKET_RIGHT.getRspCode());
        tradeOrder.setParkName(mreq.getParkName()); // 停车名称
        tradeOrder.setTimeLong(mreq.getStayTime());//时长
        tradeOrder.setInTime(mreq.getInTime());
        tradeOrder.setOutTime(mreq.getOutTime());

        tradeOrder.setEffectTimes(CommEnum.EFFECT_TIMES.getRspCode()); //次数
        tradeOrder.setOverTime(CommEnum.OVER_TIME.getRspCode()); //超时时间
        tradeOrder.setNotifyUrl(mreq.getNotifySysUrl());
        tradeOrder.setTradeStatus(CommEnum.PAY_ORDER_CREATED.getRspCode()); //订单初始状态 编码
        tradeOrder.setStates(CommEnum.GZBD_ORDER_CURR_STATE_1.getRspCode()); //订单初始状态
        tradeOrder.setResType(CommEnum.RES_TYPE_AUTO_PAY.getRspCode()); //业务子类型
        tradeOrder.setCreatedTime(acceptDate);
        tradeOrder.setSubPayType(mreq.getPayType()); //
        tradeOrder.setModifyTag(CommEnum.PARKING_INSERT_TAG.getRspCode()); //插入
        return tradeOrder;
    }

    private ParkingChannelShParamsConfig findParkingChannelShParamsConfig(
            String parkId) throws Exception {
        ParkingChannelShParamsConfig shParamsConfig = new ParkingChannelShParamsConfig();
        shParamsConfig.setParkId(parkId);
        shParamsConfig.setStates(CommEnum.GZBD_DB_STATUS.getRspCode());
        shParamsConfig = parkingChannelShParamsConfigDao.selectParkingChannelShParamsConfigById(shParamsConfig);
        return shParamsConfig;
    }

    private void setOrderPayDto(OrderPayDTO orderPayDTO, String respCode,
                                String respDesc, String channelNum) {
        orderPayDTO.setRespCode(StringUtil.checkNullString(respCode) ? RespUtil.payFail : respCode);
        orderPayDTO.setRespDesc(StringUtil.checkNullString(respDesc) ? CommEnum.PAY_PAYFILE_CODE.getRspMsg() : respDesc);
        orderPayDTO.setChannelNum(StringUtil.checkNullString(channelNum) ? "0" : channelNum);
    }

    // 23 渠道上海金融事业部智慧平台进行下单
    private PayBillRsp tradePayBill(ParkingAutoPayReq mreq,ParkingTradeOrder tradeOrder,ParkingChannelShParamsConfig shParamsConfig) throws SQLException, ParseException {
        PayBillReq payBillReq = new PayBillReq();
        payBillReq.setAppId(shParamsConfig.getAppId()); //应用代码
        payBillReq.setIndustryCode(shParamsConfig.getMchntNo()); //发起扣款的商户
        payBillReq.setPrivateKey(shParamsConfig.getSignKey()); //扣款密钥
        payBillReq.setSyncId(tradeOrder.getTradeId());//唯一序列号
        payBillReq.setOrderId(tradeOrder.getOrderId());//订单号
        payBillReq.setPlateNumber(mreq.getCarPlate());//车牌号
        payBillReq.setPayAmount(mreq.getPayAmt()); //订单支付金额
        payBillReq.setServiceAmount(mreq.getTxnAmt());// 订单总金额
        payBillReq.setOrderDate(Utility.getDataToUtc8Del(tradeOrder.getCreatedTime()));//订单生成时间
        payBillReq.setStartTime(Utility.getDataToUtc8Del(mreq.getInTime())); //入场时间
        payBillReq.setEndTime(Utility.getDataToUtc8Del(mreq.getOutTime())); // 出场时间
        payBillReq.setParkId(mreq.getParkId()); //停车场id
        payBillReq.setParkName(mreq.getParkName()); //停车场名称
        payBillReq.setPayCallback(ConfigUtil.getValue("UNIONPAY_SH_BACK_NOTIFY_URL")); //支付后台通知地址
        JSONObject jsonAccSplitData = new JSONObject();
        jsonAccSplitData.put(CommEnum.ACC_SPLIT_TYPE.getRspCode(), shParamsConfig.getAccSplitType());//分账类型
        jsonAccSplitData.put(CommEnum.ACC_SPLIT_RULE_ID.getRspCode(), shParamsConfig.getAccSplitRuleId()); //分账类型1时出现 分账规则id
        payBillReq.setAccSplitData(jsonAccSplitData.toString());
        PayBillRsp payBillRsp = null;
        try {
            payBillRsp = parkingShOrderPayService.tradePayBill(payBillReq);
        } catch (Exception e) {
            e.printStackTrace();
            payBillRsp.setRespCode(RespUtil.timeOut);
            payBillRsp.setRespDesc("调用超时");
        }
        return payBillRsp;
    }

    /**
     * icbcBillPay:  组装  工商银行 支付订单 报文
     * TODO(这里描述这个方法适用条件 – 可选)
     * TODO(这里描述这个方法的执行流程 – 可选)
     * TODO(这里描述这个方法的使用方法 – 可选)
     * TODO(这里描述这个方法的注意事项 – 可选)
     *
     * @param  @param mreq
     * @param  @return    设定文件
     * @return EntranceExitRsp    DOM对象
     * @throws Exception
     * @throws
     * @since  CodingExample　Ver 1.1
     */

    private com.parking.dto.icbc.CommonRsp icbcBillPay(ParkingAutoPayReq mreq) throws Exception {
        Object[] obj = {mreq.getParkId(), mreq.getParkMchntSysNumber(), mreq.getCarPlate(), mreq.getPayAmt(), mreq.getInTime(), mreq.getOutTime(),mreq.getCarPlateColor()};
        com.parking.dto.icbc.CommonRsp commonRsp = (com.parking.dto.icbc.CommonRsp) parkingIcbcOrderService.billPay(obj);
        return commonRsp;
    }

    /**
     * updateParkingTradeOrderPaid: 组装 更新 订单 表 更改状态   支付成功 更新
     *
     * @param  @param tradeOrder 交易主表
     * @param  @param orderPayDTO  实体 缓存I
     * @param  @param tradeId  交易流水号
     * @param  @param acceptDate  更新时间
     * @param  @param cardId  卡号
     * @param  @param payType  支付类型
     * @param  @return    设定文件
     * @return ParkingTradeOrder    DOM对象
     * @throws SQLException
     * @throws
     * @since  CodingExample　Ver 1.1
     */

    private ParkingTradeOrder updateParkingTradeOrderPaid(
            ParkingTradeOrder tradeOrder, OrderPayDTO orderPayDTO,
            String tradeId, String acceptDate, String payType) throws SQLException {
        tradeOrder = new ParkingTradeOrder();
        tradeOrder.setTradeId(tradeId);
        //String tradeStatus = getTradeStatus(tradeId);
        //tradeOrder.setTradeStatus(tradeStatus); // 支付成功
        tradeOrder.setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode()); //支付成功
        tradeOrder.setStates(CommEnum.GZBD_ORDER_CURR_STATE_3.getRspCode());//
        tradeOrder.setPayTime(acceptDate);
        tradeOrder.setUpdateTime(acceptDate);
        tradeOrder.setTradeCode(orderPayDTO.getRespCode()); //交易返回码
        tradeOrder.setTradeDesc(orderPayDTO.getRespDesc()); // 交易返回描述
        tradeOrder.setChannelNum(orderPayDTO.getChannelNum()); // 收单编码
        // 渠道方此笔交易流水号
        if (!StringUtil.checkNullString(orderPayDTO.getPayId())) tradeOrder.setOutTradeNo(orderPayDTO.getPayId());
        tradeOrder.setModifyTag(CommEnum.PARKING_UPDATE_TAG.getRspCode());
        //置返回记录
        tradeOrder.setPayType(payType);
        return tradeOrder;
    }

    /**
     * updateParkingTradeOrderFail: 订单 支付 表  更新 失败时更新
     *
     * @param  @param tradeOrder
     * @param  @param orderPayDTO  实体 缓存 类
     * @param  @param tradeId  交易流水号
     * @param  @param acceptDate  更新 时间
     * @param  @param payType  支付类型
     * @param  @param cardId  卡号
     * @param  @return    设定文件
     * @return ParkingTradeOrder    DOM对象
     * @throws
     * @since  CodingExample　Ver 1.1
     */

    private ParkingTradeOrder updateParkingTradeOrderFail(
            ParkingTradeOrder tradeOrder, OrderPayDTO orderPayDTO,
            String tradeId, String acceptDate, String payType, String cardId) {
        tradeOrder = new ParkingTradeOrder();
        tradeOrder.setTradeId(tradeId);
        tradeOrder.setCardId(StringUtil.checkNullString(cardId) ? null : cardId);
        tradeOrder.setTradeStatus(CommEnum.PAY_UN_PAID.getRspCode()); //待支付
        tradeOrder.setPayTime(acceptDate);
        tradeOrder.setUpdateTime(acceptDate);
        tradeOrder.setTradeCode(orderPayDTO.getRespCode());
        tradeOrder.setTradeDesc(orderPayDTO.getRespDesc());
        tradeOrder.setChannelNum(orderPayDTO.getChannelNum()); // 收单编码
        // 渠道方此笔交易流水号
        if (!StringUtil.checkNullString(orderPayDTO.getPayId())) tradeOrder.setOutTradeNo(orderPayDTO.getPayId());
        tradeOrder.setModifyTag(CommEnum.PARKING_UPDATE_TAG.getRspCode()); //更新操作
        //置返回记录
        tradeOrder.setPayType(payType);
        return tradeOrder;
    }

    /**
     * getTradeStatus: 查询 一下 有个别情况 异步比 同步还快
     * TODO(这里描述这个方法适用条件 – 可选)
     * TODO(这里描述这个方法的执行流程 – 可选)
     * TODO(这里描述这个方法的使用方法 – 可选)
     * TODO(这里描述这个方法的注意事项 – 可选)
     *
     * @param  @param tradeId
     * @param  @return    设定文件
     * @return String    DOM对象
     * @throws SQLException
     * @throws
     * @since  CodingExample　Ver 1.1
     */

    private String getTradeStatus(String tradeId) throws SQLException {
        ParkingTradeOrder tradeOrder2 = findParkingOrderByTrade(tradeId);
        String tradeStatus = tradeOrder2.getTradeStatus();
        if(!"Finished".equals(tradeOrder2.getTradeStatus()) && !"Unpaid".equals(tradeOrder2.getTradeStatus())){
            tradeStatus = CommEnum.PAY_PAID_CODE.getRspCode();
        }
        return tradeStatus;
    }

    /**
     * findParkingOrderByTrade:  查询 交易流水
     *
     * @param  @param tradeId  交易流水号
     * @param  @return    设定文件
     * @return ParkingTradeOrder    DOM对象
     * @throws SQLException
     * @throws
     * @since  CodingExample　Ver 1.1
     */

    private ParkingTradeOrder findParkingOrderByTrade(String tradeId) throws SQLException {
        ParkingTradeOrder tradeOrder = new ParkingTradeOrder();
        tradeOrder.setTradeId(tradeId);
        tradeOrder = tradeOrderDao.selectParkingOrderByTrade(tradeOrder);
        return tradeOrder;
    }

    /**
     *   无感支付
     * @param mreq
     * @return
     * @throws Exception
     */
    @Override
    public ParkingAutoPayRsp geneParkTerminalAutoOrder(ParkingAutoPayReq mreq) throws Exception {
        ParkingAutoPayRsp mrsp = new ParkingAutoPayRsp();
        // (1)基本参数检查
        if (checkParamsIsNnull(mreq) <0) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.REQ_PARAMS_IS_NULL.getRspMsg());
            return mrsp;
        }

        // (2) 交易金额和支付金额效检  防止风险
        if (checkTxnAmtToPayAmt(mreq.getTxnAmt(), mreq.getPayAmt()) <0) {
            setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, CommEnum.TRADE_RISK_AMOUNT_DESC.getRspMsg());
            return mrsp;
        }

        // (3) 检查上送数据合法性，如该订单是否已提交过
        ParkingTradeOrder tradeOrder = new ParkingTradeOrder();
        tradeOrder.setMchntSysNumber(mreq.getSerialNumber()); // 请求流水号
        try {
            tradeOrder = tradeOrderDao.selectParkingOrderByMchnt(tradeOrder);
            if (null != tradeOrder) {
                setRspParams(mrsp, RespUtil.repeatSubmit, "订单数据重复");
                return mrsp;
            }
        } catch (Exception e) {
            logger.info("出现异常！！！" + e.getMessage());
            setRspParams(mrsp, RespUtil.repeatSubmit, "订单数据重复");
            return mrsp;
        }


        String partParkId = ""; //渠道方停车id
        int iResult = 0;
        String cardId = "";
        UserDTO userDTO;
        // (3) 停车场 多渠道路由配置检查
        List<ParkingChannelAccessRouteConfig> parkingChannelConfigList = parkingChannelConfQueryService.queryParkingChannelAccessRouteConfigList(mreq.getParkId());
        if (null == parkingChannelConfigList || parkingChannelConfigList.size() <= 0) {
            setRspParams(mrsp, RespUtil.noResult, CommEnum.PARKING_NO_CHANEL_ROUTE_REG_DESC.getRspMsg() + mreq.getParkId() + CommEnum.BRACKET_RIGHT.getRspCode());
            return mrsp;
        }
        // 多渠道查询 白名单  查询状态
        if (null != parkingChannelConfigList) {
            userDTO = queryMulitCarStates(mreq.getCarPlate(), mreq.getParkId(), mreq.getParkName(), mreq.getCarPlateColor());
            if (null == userDTO || userDTO.getChannelId() < 0) { //查询白名单失败 直接返回
                setRspParams(mrsp, RespUtil.payFail, CommEnum.TRADE_RISK_CAR_STATES_DESC.getRspMsg());
                logger.info(CommEnum.TRADE_RISK_CAR_STATES_DESC.getRspMsg() + CommEnum.BRACKET_LIFT.getRspCode() + mreq.getSerialNumber()+ CommEnum.BRACKET_RIGHT.getRspCode());
                return mrsp;
            }

            setChannelPayInfo(mreq, String.valueOf(userDTO.getChannelId()), userDTO.getUserId());
            ParkingChannelAccessRouteConfig routeConfig = null;
            Iterator<ParkingChannelAccessRouteConfig> iteraror = parkingChannelConfigList.iterator();
            while (iteraror.hasNext()) {
                routeConfig = iteraror.next();
                if (StringUtil.checkStringsEqual(userDTO.getChannelId(), routeConfig.getChannelSelect())) mreq.setDelayTime(routeConfig.getRsrvStr1());  break;
            }
        }


        // 车牌、停车场、时长为unicode编码，转换为中文
        setDecodeDnicode(mreq);
        // 流水号
        String tradeId = seqService.getTradeSequenceId("TRADE_ID");
        logger.info("正在存储订单数据----------------------");
        // 本地受理时间
        Date date = new Date();
        String acceptDate = getDate(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode(), date);
        // 交易发送时间
        String txnTime = getDate(CommEnum.DATE_YYYYMMDDHHMMSS.getRspCode() , date);

        // 台账主表 记录  && 台账订单表
        tradeOrder = inserParkingTradeOrder(mreq, tradeId, acceptDate, partParkId);
        iResult = uniDealData(tradeOrder);
        if (iResult <= 0 ) {
            setRspParams(mrsp, RespUtil.dberror, CommEnum.ORDER_GENE_EXCEPTION_DESC.getRspMsg() + mreq.getSerialNumber()+"]");
            logger.info(mrsp.getRespDesc());
            return mrsp;
        }

        logger.info("支付方式是："+mreq.getPayType());

        OrderPayDTO  orderPayDTO = null;
        boolean bPayCall = false;
        //23 渠道类型  -----上海金融事业部智慧平台支付下单
        if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), mreq.getPayType())) {
            logger.info("准备进入智慧通行平台无感支付下单------------------------");
            if (StringUtils.isNotBlank(mreq.getTxnAmt()) && Integer.valueOf(mreq.getPayAmt())> 0) {
                bPayCall = false;
                //根据停车场查询配置
                ParkingChannelShParamsConfig shParamsConfig = findParkingChannelShParamsConfig(mreq.getParkId());
                if (null == shParamsConfig) {
                    setRspParams(mrsp, RespUtil.noResult, "此停车场未配置渠道商户[" +mreq.getParkId()+"]");
                    return mrsp;
                }
                orderPayDTO = new OrderPayDTO();
                PayBillRsp payBillRsp = tradePayBill(mreq,tradeOrder,shParamsConfig);
                if (null != payBillRsp && StringUtil.checkStringsEqual(RespUtil.successCode, payBillRsp.getRespCode())) {
                    setOrderPayDto(orderPayDTO, payBillRsp.getRespCode(), payBillRsp.getRespDesc(), shParamsConfig.getRsrvStr1());
                    bPayCall = true;
                } else {
                    setOrderPayDto(orderPayDTO, payBillRsp.getRespCode(), payBillRsp.getRespDesc(), shParamsConfig.getRsrvStr1());
                    bPayCall = false;
                }
            } else {
                setRspParams(mrsp, RespUtil.successCode, "使用权益成功"); // 使用权益的情况
            }
            logger.info("准备进入智慧通行平台无感支付下单结束------------------------");
        }

        // 工商 银行渠道 支付
        if (StringUtil.checkStringsEqual(CommEnum.ICBC_PAY_CHANNEL.getRspCode(), mreq.getPayType())) {
            if (StringUtils.isNotBlank(mreq.getTxnAmt()) && Integer.valueOf(mreq.getPayAmt())> 0) {
                bPayCall = false;
                orderPayDTO = new OrderPayDTO();
                com.parking.dto.icbc.CommonRsp commonRsp = icbcBillPay(mreq);
                if (null == commonRsp) {
                    bPayCall = false;
                } else {
                    if (StringUtil.checkStringsEqual(RespUtil.successCode, commonRsp.getRespCode())) {
                        setOrderPayDto(orderPayDTO, commonRsp.getRespCode(), commonRsp.getRespDesc(), commonRsp.getChannelNum());
                        bPayCall = true;
                    }  else {
                        setOrderPayDto(orderPayDTO, commonRsp.getRespCode(), commonRsp.getRespDesc(),commonRsp.getChannelNum());
                    }
                }
            } else {
                // 暂不使用权益的情况
                setRspParams(mrsp, RespUtil.successCode, "使用权益成功");
            }
        }

        // 上海金融事业部智慧平台无感支付 /  工商银行
        if (StringUtil.checkStringsEqual(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), mreq.getPayType())||
        StringUtil.checkStringsEqual(CommEnum.ICBC_PAY_CHANNEL.getRspCode(), mreq.getPayType())){
            logger.info("bPayCall的值为："+bPayCall);
            if (bPayCall) {
                tradeOrder = updateParkingTradeOrderPaid(tradeOrder, orderPayDTO, tradeId, acceptDate, mreq.getPayType());
                setRspParams(mrsp, RespUtil.successCode, "交易成功");
            } else {
                tradeOrder = updateParkingTradeOrderFail(tradeOrder, orderPayDTO, tradeId, acceptDate, mreq.getPayType(), cardId);
                setRspParams(mrsp, RespUtil.payFail, CommEnum.TRADE_FAIL_DESC.getRspMsg());
            }
            uniDealData(tradeOrder);
            if (StringUtil.checkStringsEqual(CommEnum.PAY_PAID_CODE.getRspCode(), tradeOrder.getTradeStatus())) {
                if (getMapContr().containsKey(mreq.getPayType())) {
                    // 开启线程查询无感订单
                    threadPkingQueryPayState(mreq,tradeOrder, orderPayDTO.getChannelNum());
                } else {
                    threadNotifyCloudPayResult(tradeOrder,CommEnum.SYS_CLOUD.getRspCode(), orderPayDTO.getChannelNum());
                }
            } else {
                // 开启线程查询无感订单
                threadPkingQueryPayState(mreq,tradeOrder, orderPayDTO.getChannelNum());
            }
        }
        mrsp.setPayType(mreq.getPayType()); //只默认值值给线下停车场服务 ，固定值无感
        mrsp.setOrderId(tradeId);
        mrsp.setTradeStatus(tradeOrder.getTradeStatus());
        return mrsp;
    }

    // 判断是否 需要进出轮询判断 查询无感状态
    private Map<String, Boolean>  getMapContr() {
        Map<String, Boolean> map = new HashMap<String, Boolean>(4);
        map.put(CommEnum.UNIONPAY_SH_CHANNEL.getRspCode(), true);
        return map;
    }
     // 开启 线程池  需要 进行 查询的无感 进行查询
    private void threadPkingQueryPayState(ParkingAutoPayReq mreq, ParkingTradeOrder tradeOrder, String channelNum) throws Exception {
        ParkingQueryPayStatesServiceThread  parkingQueryPayStatesServiceThread  = new ParkingQueryPayStatesServiceThread();
        parkingQueryPayStatesServiceThread.setParkingQueryOrderService(parkingQueryOrderService);
        parkingQueryPayStatesServiceThread.setPayNotifyReq(copyParkingAutoPayReqToPayNotify(mreq, tradeOrder, channelNum));
        // 延时时间
        parkingQueryPayStatesServiceThread.setDelayTime(null == mreq.getDelayTime() ? "6000" : mreq.getDelayTime());
        parkingQueryPayStatesServiceThread.setParkingTradeOrderQueryReq(getQueryOrder(tradeOrder));
        ChannelNoQueryPayStatesServiceThreadPool.executeThread(parkingQueryPayStatesServiceThread);
    }

    private ParkingTradeOrderQueryReq getQueryOrder(ParkingTradeOrder tradeOrder) {
        ParkingTradeOrderQueryReq   queryReq = new ParkingTradeOrderQueryReq();
        queryReq.setOrderId(tradeOrder.getTradeId());
        return queryReq;
    }

    // 对象 值进行拷贝
    private PayNotifyReq copyParkingAutoPayReqToPayNotify(ParkingAutoPayReq mreq, ParkingTradeOrder tradeOrder, String channelNum) throws Exception {
        PayNotifyReq payNotifyReq = new PayNotifyReq();
        payNotifyReq = (PayNotifyReq) BeanCopyUtil.CopyBeanToBean(mreq,payNotifyReq);
        payNotifyReq.setTradeStatus(tradeOrder.getTradeStatus());
        payNotifyReq.setPayType(String.valueOf(ParkingChannelPayType.getDecsitPayTypeMap().get(payNotifyReq.getResType())));
        payNotifyReq.setChannelNum(channelNum);
        payNotifyReq.setPayChannel(String.valueOf(ParkingChannelPayType.getDecsitChannelPayTypeMap().get(mreq.getPayType())));
        return payNotifyReq;
    }

    //开启线程 异步支付通知
    private  void threadNotifyCloudPayResult(ParkingTradeOrder tradeOrder,String sysName, String channelNum) {
        ParkingServiceThread   parkingServiceRunnable = new ParkingServiceThread();
        parkingServiceRunnable.setTradeOrder(tradeOrder);
        tradeOrder.setChannelNum(channelNum);
        parkingServiceRunnable.setSysName(sysName);
        parkingServiceRunnable.setParkingAsynNotifyService(parkingAsynNotifyService);
        Thread thread = new Thread(parkingServiceRunnable);
        thread.start();
    }

    // 统一处理台账数据
    private int uniDealData(Object... obj) throws Exception {
        int result = 0;
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] instanceof ParkingTradeOrder) {
                ParkingTradeOrder tradeOrder = (ParkingTradeOrder) obj[i];
                if (tradeOrder.getModifyTag().equals("I")) {
                    result = tradeOrderDao.insertParkingOrder(tradeOrder);
                } else if (tradeOrder.getModifyTag().equals("U")) {
                    result = tradeOrderDao.updateParkingOrder(tradeOrder);
                }
            }
        }
        return result;
    }

    private void setRspParams(ParkingAutoPayRsp mrsp, String noResult, String rspMsg) {
        mrsp.setRespCode(noResult);
        mrsp.setRespDesc(rspMsg);
    }


    //车牌长度检查 大于6
    private int checkCarPlateLength(String carPlate) {
        if(carPlate.replaceAll("\\s*", "").length()<=6) return -1;
        return 1;
    }

    private void setRspParams(ParkingPreOrderQueryRsp mrsp, String noResult, String rspMsg) {
        mrsp.setRespCode(noResult);
        mrsp.setRespDesc(rspMsg);
    }

    /**
     * ；临时预缴业务类
     * @param mreq
     * @return
     * @throws Exception
     */
    @Override
    public ParkingPreOrderQueryRsp queryParkingPrePayOrder(ParkingPreOrderQueryReq mreq) throws Exception {
        logger.info("进入临停车缴费订单查询服务--------------------------------queryParkingPrePayOrder");
        ParkingPreOrderQueryRsp  mrsp = new ParkingPreOrderQueryRsp();
        setInitRspParams(mrsp, mreq);
        //参数检查
        int paramsFlag = 1;
        if(StringUtil.checkNullString(mreq.getCarPlate()) ||StringUtil.checkNullString(mreq.getSerialNumber())){
            paramsFlag = -1;
            if (!StringUtil.checkNullString(mreq.getParkId()) && !StringUtil.checkNullString(mreq.getOutChannelId())) {
                paramsFlag = 2;
            }
        }
        if (paramsFlag<0) {
            setRspParams(mrsp, RespUtil.noResult, "业务参数缺失");
            return mrsp;
        }
        //车牌长度检查  大于6
        if(paramsFlag != 2 &&  checkCarPlateLength(mreq.getCarPlate()) < 0 ) {
            setRspParams(mrsp, RespUtil.CLIENT_INFO_ERR, "此业务车牌异常，请核实");
            return mrsp;
        }

        ParkingWhiteTempCar tempCar = parkingWhiteTempCarService.findParkingWhiteTempCarById(mreq.getCarPlate());
        if (null == tempCar) {
            setRspParams(mrsp, RespUtil.noResult, "无入场记录");
            return mrsp;
        }
        //请求头商户流水检查重复
        ParkingTradeOrder parkingTradeOrder = new ParkingTradeOrder();
        parkingTradeOrder.setMchntSysNumber(mreq.getSerialNumber());
        parkingTradeOrder = tradeOrderDao.selectParkingOrderByMchnt(parkingTradeOrder);
        if (null !=parkingTradeOrder) {
            setRspParams(mrsp, RespUtil.repeatSubmit, "请求流水号重复");
            return mrsp;
        }

        //定义初始化参数
        int iResult = 0;
        boolean bCheck = false;
        Date date = new Date();
        OrderPreDTO dto = new OrderPreDTO();

        // 流水号
        String tradeId = "";


        //(4)  停车预缴账单查询-----
        OrderPreDTO orderPreDTO = eParkingQueryService.getTempCarFee(mreq);
        if (null == orderPreDTO) {
            setRspParams(mrsp, RespUtil.timeOut, CommEPConstant.QUERY_OVER_TIME_DESC);
            return mrsp;
        }
        // 其他 异常 情况
        if (!StringUtil.checkStringsEqual(RespUtil.successCode, orderPreDTO.getRespCode())) {
            setRspParams(mrsp, orderPreDTO.getRespCode(), orderPreDTO.getRespDesc());
            return mrsp;
        }
        dto = orderPreDTO;
        logger.info("获取线下车易泊MQTT获取临时车费用DTO----->"+dto);


        // 金额判断
        if(StringUtil.checkStringsEqual(CommEnum.FREE0_CODE.getRspCode(), dto.getFree())||StringUtil.checkStringsEqual(CommEnum.FREE000_CODE.getRspCode(), dto.getFree())||StringUtil.checkStringsEqual(CommEnum.FREE00_CODE.getRspCode(), dto.getFree())){
            setRspParams(mrsp, RespUtil.noResult, CommEPConstant.NO_AMOUNT_DESC);
            return mrsp;
        }

        //交易流水号生产
        tradeId = seqService.getTradeSequenceId("TRADE_ID");
        // 本地受理时
        String acceptDate = new SimpleDateFormat(CommEnum.DATE_YYYY_MM_DD_HH_MM_SS.getRspCode()).format(date);

        //交易订单表
        ParkingTradeOrder tradeOrder = insertParkingTradeOrder(mreq, tradeId, acceptDate, dto);
        iResult = uniDealData(tradeOrder);
        if(iResult>0){
            setRspParams(mrsp, mreq, tradeId, tradeOrder, dto);
        }else{
            setRspParams(mrsp, RespUtil.dberror, "数据异常");
        }

        return mrsp;
    }

    private ParkingTradeOrder insertParkingTradeOrder(
            ParkingPreOrderQueryReq mreq, String tradeId, String acceptDate, OrderPreDTO dto
            ) {
        ParkingTradeOrder  tradeOrder = new ParkingTradeOrder();
        tradeOrder.setTradeId(tradeId); //交易单号
        tradeOrder.setOrderId(tradeId); //交易订单号
        tradeOrder.setUserId(mreq.getUserId()) ;//用户ID
        tradeOrder.setTxnAmt(dto.getFree()); //交易金额  车易泊返回
        tradeOrder.setPayAmt(dto.getPayAmt()); //支付金额
        tradeOrder.setMchntSysNumber(mreq.getSerialNumber());
        tradeOrder.setAcceptMonth(acceptDate.substring(5, 7)); //月份
        tradeOrder.setMchntId(mreq.getMchntId()); //商户id
        tradeOrder.setParkId(mreq.getParkId());  //停车场id
        tradeOrder.setPartParkId(!StringUtil.checkNullString(mreq.getPartParkId()) ? mreq.getPartParkId() : null); //合作渠道方停车id /接入渠道方停车id
        tradeOrder.setParkName(mreq.getParkName()); // 停车场名称
        tradeOrder.setCarPlate(StringUtil.checkNullString(mreq.getCarPlate()) ? dto.getCarPlate() : mreq.getCarPlate()); //车牌
        tradeOrder.setResName("预缴停车["+tradeOrder.getCarPlate()+"]"); //描述
        tradeOrder.setTimeLong(dto.getTimeLong());//时长
        tradeOrder.setEffectTimes("3");
        tradeOrder.setOverTime("3m");
        tradeOrder.setResType("parkingPre"); //业务子类型
        tradeOrder.setStates("1");
        tradeOrder.setCreatedTime(acceptDate); //创建时间
        tradeOrder.setNotifyUrl(ConfigUtil.getValue("ePARKING_URL"));  //有用户内部地址
        tradeOrder.setPayType("00"); //银行卡 自身渠道 预定义，有时第三方支付类型
        tradeOrder.setTradeStatus("Created");
        tradeOrder.setModifyTag("I");
        return tradeOrder;
    }

    /**
     * setRspParams: 置 查询预缴返回
     * TODO(这里描述这个方法适用条件 – 可选)
     * TODO(这里描述这个方法的执行流程 – 可选)
     * TODO(这里描述这个方法的使用方法 – 可选)
     * TODO(这里描述这个方法的注意事项 – 可选)
     *
     * @param  @param mrsp
     * @param  @param mreq
     * @param  @param tradeId
     * @param  @param tradeOrder
     * @param  @param dto    设定文件
     * @return void    DOM对象
     * @throws
     * @since  CodingExample　Ver 1.1
     */

    private void setRspParams(ParkingPreOrderQueryRsp mrsp,
                              ParkingPreOrderQueryReq mreq, String tradeId,
                              ParkingTradeOrder tradeOrder, OrderPreDTO dto) {
        mrsp.setTradeId(tradeId);
        mrsp.setOrderId(tradeId);  //订单号
        mrsp.setResType("parkingPre"); //子编码类型
        mrsp.setResId(tradeOrder.getCarPlate()); // 车牌号
        mrsp.setResName(tradeOrder.getResName());
        mrsp.setMchntId(tradeOrder.getMchntId()); //商户号
        mrsp.setParkName(mreq.getParkName()); //停车名称
        mrsp.setParkId(mreq.getParkId());
        mrsp.setPayAmt(tradeOrder.getPayAmt()); //交易金额
        mrsp.setPayType(dto.getPayType()); //出现 全额优惠时出现 类型 ，有金额时不出现
        mrsp.setTxnAmt(tradeOrder.getTxnAmt()); //交易金额
        mrsp.setPrePaid(dto.getPrePaid()); //已支付金额 预缴费金额
        mrsp.setNotifySysName("parking"); //系统名称
        mrsp.setNotifySysUrl(tradeOrder.getNotifyUrl()); //通知地址
        mrsp.setOrderSummary("临时车缴费");
        mrsp.setTimeLong(dto.getTimeLong());//时长
        mrsp.setSecondSums(dto.getSeconds()); //停车时长 秒
        mrsp.setTradeStatus(tradeOrder.getTradeStatus()); //交易状态
        mrsp.setCarColor(dto.getCarColor());// 车牌颜色
        mrsp.setOutChannelId(dto.getOutChannelId());// 出场 通道id
        mrsp.setRespCode(RespUtil.successCode);
        mrsp.setRespDesc("查询成功!");
    }

    /**
     * setInitRspParams: 置 初始化返回
     * TODO(这里描述这个方法适用条件 – 可选)
     * TODO(这里描述这个方法的执行流程 – 可选)
     * TODO(这里描述这个方法的使用方法 – 可选)
     * TODO(这里描述这个方法的注意事项 – 可选)
     *
     * @param  @param mrsp
     * @param  @param mreq    设定文件
     * @return void    DOM对象
     * @throws
     * @since  CodingExample　Ver 1.1
     */

    private void setInitRspParams(ParkingPreOrderQueryRsp mrsp,
                                  ParkingPreOrderQueryReq mreq) {
        mrsp.setRespCode(RespUtil.codeError);
        mrsp.setRespDesc(CommEnum.RESP_DESC_ERROR.getRspMsg());
        mrsp.setSerialNumber(mreq.getSerialNumber());
    }
}
