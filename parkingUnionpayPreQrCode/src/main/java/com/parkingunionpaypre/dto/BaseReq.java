package com.parkingunionpaypre.dto;



import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class BaseReq implements Serializable{
	
	private static final long serialVersionUID=-6771846817624421765L;
	
	@XmlAttribute
	private String service;
	@XmlAttribute
	private String sysName;
	@XmlAttribute
	private String serialNumber;
	@XmlAttribute
	private String userId;
	@XmlAttribute
	private String termId;	
	@XmlAttribute
	private String termAdd;
	@XmlAttribute
	private String termBrand;
	@XmlAttribute
	private String osVersion;
	@XmlAttribute
	private String appVersion;
	@XmlAttribute
	private String tokenValue;
	@XmlAttribute
	private String tradeId;
	@XmlElement
	private String parkid;
	public String getParkid() {
		return parkid;
	}
	public void setParkid(String parkid) {
		this.parkid = parkid;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getTermAdd() {
		return termAdd;
	}
	public void setTermAdd(String termAdd) {
		this.termAdd = termAdd;
	}
	public String getTermBrand() {
		return termBrand;
	}
	public void setTermBrand(String termBrand) {
		this.termBrand = termBrand;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getTokenValue() {
		return tokenValue;
	}
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	
	
}
