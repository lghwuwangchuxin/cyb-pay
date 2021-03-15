package com.parking.dto;

import java.io.Serializable;

public class BecodeOrderRsp implements Serializable {


    /**
     * @Auther RPC响应类
     * @Date 2020-08-19 20:39
     * @Description com.becode.javabean.vo
     */

        private String rspCode;

        private String rspMsg;

        private String out_trade_no;    //银盛支付合作商户网站唯一订单号 不可空

        private String trade_no;    //该交易在银盛支付系统中的交易流水号

        private String trade_status;    //交 易 目 前 所处 的 状态 不可空

        private String total_amount;    //该 笔 订 单 的资 金 总额，单位为 RMB-Yuan 不可空

        private String account_date;    //入 账 日 期 ， 格 式"yyyy-MM-dd"

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getAccount_date() {
        return account_date;
    }

    public void setAccount_date(String account_date) {
        this.account_date = account_date;
    }

    public BecodeOrderRsp() {
        }

        public BecodeOrderRsp(String rspCode, String rspMsg) {
            this.rspCode = rspCode;
            this.rspMsg = rspMsg;
        }

}
