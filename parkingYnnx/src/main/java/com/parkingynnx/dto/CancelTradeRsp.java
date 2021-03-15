package com.parkingynnx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 停车场 交易撤销 响应类
 * @author acer
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg")
public class CancelTradeRsp extends BaseRsp {
    
    @XmlElement
    private String code;  //返回码
    @XmlElement
    private String mgs;  //返回信息
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMgs() {
        return mgs;
    }
    public void setMgs(String mgs) {
        this.mgs = mgs;
    }

}
