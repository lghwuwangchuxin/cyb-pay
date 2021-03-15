package com.parkingynnx.dto;

import java.io.Serializable;

/**
 * 云信 交易撤销 请求类
 * @author acer
 *
 */
public class CancelOrderStatusReq implements Serializable {
    
    private String orderNo;  //订单流水号
    private String approveId;  //商户号
    private String payNb;  //支付渠道
    private String temId;  //终端号
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getApproveId() {
        return approveId;
    }
    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }
    public String getPayNb() {
        return payNb;
    }
    public void setPayNb(String payNb) {
        this.payNb = payNb;
    }
    public String getTemId() {
        return temId;
    }
    public void setTemId(String temId) {
        this.temId = temId;
    }
    
}
