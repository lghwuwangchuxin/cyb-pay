package com.parking.dto.icbc;

import com.parking.dto.BaseReq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class RefundReq extends BaseReq {
	
	@XmlElement
	private String parkingNum; // 停车记录编号
	@XmlElement
	private String refund; // 退款金额（单位:元） 
	@XmlElement
	private String remark; // 备注
	public String getParkingNum() {
		return parkingNum;
	}
	public void setParkingNum(String parkingNum) {
		this.parkingNum = parkingNum;
	}
	public String getRefund() {
		return refund;
	}
	public void setRefund(String refund) {
		this.refund = refund;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "RefundReq [parkingNum=" + parkingNum + ", refund=" + refund + ", remark=" + remark + "]";
	}
	
	
	
}
