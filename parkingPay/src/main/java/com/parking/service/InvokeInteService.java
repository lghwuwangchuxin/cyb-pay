package com.parking.service;

import java.util.Map;

public interface InvokeInteService {
	//解析返回
	public Map parseResp(String resp,byte[] dkey)throws Exception;	
	
	//解析XML
	public Map<String, String> parseXml(String xml) throws Exception;	
	
	//接口调用
	public String invokeInteService(Object obj,String mkey,String url)throws Exception;
	
	//接口调用通知
	public String invokeInteNotifyService(Object obj,String mkey,String url)throws Exception;
	
	//解析返回
	public Map<String, String> parseResp(String resp) throws Exception;

	// 解析返回JSON转换成map
	public Map<String, String> parseRespJson(String respJson) throws Exception;


}
