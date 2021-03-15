package com.parkinghx.dto;


import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * DTO 基类
 * @author htz
 *
 */
public abstract class BaseDTO implements Serializable{

	private static final long serialVersionUID=1L;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString( this, ToStringStyle.MULTI_LINE_STYLE );
	}
	
	
}
