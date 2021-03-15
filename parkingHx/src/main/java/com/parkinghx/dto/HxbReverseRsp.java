package com.parkinghx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "msg")
public class HxbReverseRsp extends BaseRsp {

	public HxbReverseRsp() {
	}
	
	public HxbReverseRsp(String respCode,String respDesc) {
		super.respCode = respCode;
		super.respDesc = respDesc;
	}
	
	@Override
	public String toString() {
		return "HxbReverseRsp [respMsg=" + respMsg + ", randomStr=" + randomStr + ", signType=" + signType
				+ ", respCode=" + respCode + ", respDesc=" + respDesc + "]";
	}

}
