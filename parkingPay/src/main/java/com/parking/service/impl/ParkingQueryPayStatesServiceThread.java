package com.parking.service.impl;

import com.parking.dto.ParkingTradeOrderQueryReq;
import com.parking.dto.ParkingTradeOrderQueryRsp;
import com.parking.dto.PayNotifyReq;
import com.parking.service.ParkingQueryOrderService;
import com.parking.util.CommEnum;
import com.parking.util.PostUtil;
import com.parking.util.StringUtil;
import com.parking.util.XmlUtil;
import javax.xml.bind.JAXBException;


/**
 *   无感支付 统一查询
 */
public class ParkingQueryPayStatesServiceThread implements Runnable {

    private ParkingQueryOrderService parkingQueryOrderService;

    private ParkingTradeOrderQueryReq parkingTradeOrderQueryReq;

    private PayNotifyReq payNotifyReq;

    private String delayTime;

    public ParkingQueryOrderService getParkingQueryOrderService() {
        return parkingQueryOrderService;
    }

    public void setParkingQueryOrderService(ParkingQueryOrderService parkingQueryOrderService) {
        this.parkingQueryOrderService = parkingQueryOrderService;
    }

    public PayNotifyReq getPayNotifyReq() {
        return payNotifyReq;
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

    public ParkingTradeOrderQueryReq getParkingTradeOrderQueryReq() {
        return parkingTradeOrderQueryReq;
    }

    public void setParkingTradeOrderQueryReq(ParkingTradeOrderQueryReq parkingTradeOrderQueryReq) {
        this.parkingTradeOrderQueryReq = parkingTradeOrderQueryReq;
    }

    @Override
    public void run() {

        // 如果首次支付成功了 直接通知 无需循环查询
        if (StringUtil.checkStringsEqual(CommEnum.PAY_PAID_CODE.getRspCode(),this.getPayNotifyReq().getTradeStatus())) {
            // 通知第三方 易泊云端系统
            String reqxml = null;
            try {
                reqxml = XmlUtil.ObjToXml(this.getPayNotifyReq(), PayNotifyReq.class);
                System.out.println("下单确认支付结果直接通知报文"+reqxml);
                System.out.println("云端通知报文："+reqxml);
                String rspXml = PostUtil.transferData(reqxml, "UTF-8", this.getPayNotifyReq().getNotifySysUrl());
                System.out.println("云端通知报文：" +rspXml);
            } catch (JAXBException e) {
                e.printStackTrace();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        long newtime = System.currentTimeMillis();

        while (true) {
            try {
                System.out.println("开始循无感订单循环查询订单任务");
                ParkingTradeOrderQueryRsp rsp = this.getParkingQueryOrderService().queryParkingChannelTradeOrder(this.getParkingTradeOrderQueryReq());
                if (null != rsp && StringUtil.checkStringsEqual(CommEnum.SUCCESS_COID.getRspCode(), rsp.getRespCode())) {

                    if (StringUtil.checkStringsEqual(CommEnum.PAY_PAID_CODE.getRspCode(), rsp.getTradeStatus())) {
                        // 通知第三方 云端系统
                        this.getPayNotifyReq().setTradeStatus(CommEnum.PAY_PAID_CODE.getRspCode());
                        String reqxml = XmlUtil.ObjToXml(this.getPayNotifyReq(),PayNotifyReq.class);
                        System.out.println("云端无感支付结果异步通知报文："+reqxml);
                        String rspXml = PostUtil.transferData(reqxml, "UTF-8", this.getPayNotifyReq().getNotifySysUrl());
                        System.out.println("云端通知报文：" +rspXml);
                        break;
                    }

                }
                System.out.println("延时时间：" +this.getDelayTime());
                Thread.sleep(Long.valueOf(this.getDelayTime())); //每次查询 延时800毫秒
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
