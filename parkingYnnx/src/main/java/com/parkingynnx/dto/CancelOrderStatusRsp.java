package com.parkingynnx.dto;

import java.io.Serializable;
/**
 * 云信 交易撤销响应类
 * @author acer
 *
 */
public class CancelOrderStatusRsp implements Serializable {
    
    private String code;  //响应码
    private String msg;  //响应消息
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
