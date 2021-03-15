package com.parkingynnx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *停车场 被扫 响应实体类
 * @author acer
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class BeCodeUnionPayRsp extends BaseRsp {
    
    @XmlElement
    private String code;    //应答码
    @XmlElement
    private String orderNo; //订单编号
    @XmlElement
    private String orderTime;   //交易时间
    @XmlElement
    private String mgs; //应答详细信息
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public String getMgs() {
        return mgs;
    }
    public void setMgs(String mgs) {
        this.mgs = mgs;
    }   
    
}
