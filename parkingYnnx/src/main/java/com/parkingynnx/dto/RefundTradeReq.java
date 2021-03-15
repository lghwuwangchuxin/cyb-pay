package com.parkingynnx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 停车场 退款申请 请求类
 * @author acer
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class RefundTradeReq extends BaseReq {
    @XmlElement
    private String approveId;  //商户号
    @XmlElement
    private String temId;  //终端号
    @XmlElement
    private String amt;  //退款金额(不能大于当前交易金额)
    @XmlElement
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
