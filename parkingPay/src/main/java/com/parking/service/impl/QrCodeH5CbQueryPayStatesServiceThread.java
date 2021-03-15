package com.parking.service.impl;

import com.parking.dto.ParkingTradeOrderQueryReq;
import com.parking.dto.ParkingTradeOrderQueryRsp;
import com.parking.dto.PayNotifyReq;
import com.parking.service.ParkingChannelQueryOrderService;
import com.parking.util.CommEnum;
import com.parking.util.PostUtil;
import com.parking.util.StringUtil;
import com.parking.util.XmlUtil;
import javax.xml.bind.JAXBException;

/**
 *  公众 号 支付查询 和 主扫订单 查询 服务
 */
public class QrCodeH5CbQueryPayStatesServiceThread implements Runnable {

    private ParkingChannelQueryOrderService parkingChannelQueryOrderService;
    private PayNotifyReq payNotifyReq;
    private ParkingTradeOrderQueryReq queryReq;
    private String delayTime;



    public ParkingChannelQueryOrderService getParkingChannelQueryOrderService() {
        return parkingChannelQueryOrderService;
    }

    public void setParkingChannelQueryOrderService(ParkingChannelQueryOrderService parkingChannelQueryOrderService) {
        this.parkingChannelQueryOrderService = parkingChannelQueryOrderService;
    }

    public PayNotifyReq getPayNotifyReq() {
        return payNotifyReq;
    }

    public ParkingTradeOrderQueryReq getQueryReq() {
        return queryReq;
    }

    public void setQueryReq(ParkingTradeOrderQueryReq queryReq) {
        this.queryReq = queryReq;
    }

    public void setPayNotifyReq(PayNotifyReq payNotifyReq) {
        this.payNotifyReq = payNotifyReq;
    }

    public String getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(String delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public void run() {

        long newtime = System.currentTimeMillis();

        while (true) {
            try {
                System.out.println("开始循环公众号或者主扫 查询订单任务");
                ParkingTradeOrderQueryRsp rsp = this.getParkingChannelQueryOrderService().queryOrder(this.getQueryReq());
                if (null != rsp && StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), rsp.getRespCode())) {

                    if (StringUtil.checkStringsEqual(CommEnum.PAY_PAID_CODE.getRspCode(), rsp.getTradeStatus())) {
                        // 通知第三方 易泊云端系统
                        if (!StringUtil.checkNullString(rsp.getSubPayType())) this.getPayNotifyReq().setPayChannel(rsp.getSubPayType());
                        this.getPayNotifyReq().setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
                        String reqxml = XmlUtil.ObjToXml(this.getPayNotifyReq(),PayNotifyReq.class);
                        System.out.println("云端通知报文："+reqxml);
                        String rspXml = PostUtil.transferData(reqxml, "UTF-8", this.getPayNotifyReq().getNotifySysUrl());
                        System.out.println("云端通知报文：" +rspXml);
                        break;
                    }

                }
                Thread.sleep(Long.valueOf(this.getDelayTime())); //每次查询 延时1000毫秒
                long lastTime = System.currentTimeMillis();
                long cTime = (lastTime - newtime)/1000;  // 得到秒置
                if (cTime > 90) break;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("查询订单处理异常"+e.getMessage());
                break;
            }
        }

    }
}
