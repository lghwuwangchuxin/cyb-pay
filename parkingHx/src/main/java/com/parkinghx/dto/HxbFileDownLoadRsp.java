package com.parkinghx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class HxbFileDownLoadRsp extends BaseRsp {

	public HxbFileDownLoadRsp() {
	}
	
	public HxbFileDownLoadRsp(String respCode,String respDesc) {
		super.respCode = respCode;
		super.respDesc = respDesc;
	}
}
