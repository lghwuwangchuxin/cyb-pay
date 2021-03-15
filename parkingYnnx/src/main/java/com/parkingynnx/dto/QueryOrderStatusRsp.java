package com.parkingynnx.dto;

import java.io.Serializable;

public class QueryOrderStatusRsp implements Serializable {
    
    private String code;  //应答码
    private QueryTradeRsp data;  //状态信息
    private String msg;  //应答码信息
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public QueryTradeRsp getData() {
        return data;
    }
    public void setData(QueryTradeRsp data) {
        this.data = data;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    

}
