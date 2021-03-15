package com.parkingynnx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 停车场 消费撤销 请求类
 * @author acer
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class CancelTradeReq extends BaseReq {
    
    @XmlElement
    private String orderNo;  //订单流水号
    @XmlElement
    private String approveId;  //商户code/认证id
    @XmlElement
    private String payNb;  //支付渠道
    @XmlElement
    private String temId;   //终端号
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
