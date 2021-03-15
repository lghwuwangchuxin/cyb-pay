package com.parkingynnx.dto;

import java.io.Serializable;
/**
 * 云信交易查询实体类 
 * @author acer
 *
 */
public class QueryOrderStatusReq implements Serializable{
    
    private String orderNo;  //订单号-post
    private String orderId;  //聚合平台订单流水号-get
    private String payNb;  //支付渠道:002支付宝-003微信-100银联
    private String approveId;  //商户号

    
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

}
