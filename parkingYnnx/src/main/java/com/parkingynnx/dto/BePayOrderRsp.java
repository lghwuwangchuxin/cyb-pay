package com.parkingynnx.dto;

import java.io.Serializable;
/**
 * 云信被扫响应实体类
 * @author acer
 *
 */
public class BePayOrderRsp implements Serializable {
    private String code;    //应答码
    private BeCodeUnionPayRsp data; //订单信息
    private String msg; //应答码信息
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public BeCodeUnionPayRsp getData() {
        return data;
    }
    public void setData(BeCodeUnionPayRsp data) {
        this.data = data;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
