package com.parkingynnx.dto;

import java.io.Serializable;

/**
 * 云信 申请退款响应类
 * @author acer
 *
 */
public class RefundOrderStatusRsp implements Serializable {
    
    private String code;  //应答码
    private RefundTradeRsp data;  //应答码为000000，存在
    private String msg;  //应答信息
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public RefundTradeRsp getData() {
        return data;
    }
    public void setData(RefundTradeRsp data) {
        this.data = data;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
