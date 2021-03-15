package com.parkingynnx.dto;

import java.io.Serializable;

/**
 * 云信 退款申请 请求类
 * @author acer
 *
 */
public class RefundOrderStatusReq implements Serializable {
    
    private String approveId;  //商户号
    private String temId;  //终端号
    private String amt;  //退款金额(不能大于当前交易金额)
    private String orderId;  //原订单 流水号
    public String getApproveId() {
        return approveId;
    }
    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }
    public String getTemId() {
        return temId;
    }
    public void setTemId(String temId) {
        this.temId = temId;
    }
    public String getAmt() {
        return amt;
    }
    public void setAmt(String amt) {
        this.amt = amt;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
