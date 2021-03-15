package com.parkingicbcpay.util;

import java.util.ResourceBundle;

public class ConfigUtil { 
	public static ResourceBundle resourceBundle =null; 

	static{
		if(resourceBundle==null){
			resourceBundle=ResourceBundle.getBundle("config");
		}
	}

	public static String getValue(String refName){
		try{
			if(refName!=null){
				String result=resourceBundle.getString(refName);
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}	
}
