package com.parking.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.parking.dto.OrderPayReq;
import com.parking.dto.PayNotifyReq;
import com.parking.util.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.parking.service.InvokeInteService;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Service("invokeInteService")
public class InvokeInteServiceImpl implements InvokeInteService{
	
	private static final Logger logger = LoggerFactory.getLogger(InvokeInteServiceImpl.class);	
	//统一解析返回
	@Override
	public Map<String,String> parseResp(String resp,byte[] dkey)throws Exception{
		BASE64Decoder decoder = new BASE64Decoder();
		Map<String,String> respMap = new HashMap<String,String>(6);
		//去掉空格
		if (StringUtil.checkNullString(resp) || resp.replaceAll("\\s*", "").length() == 0) {
			respMap.put("tag", "0");
			respMap.put("respCode", RespUtil.CLIENT_INFO_ERR);
			respMap.put("respDesc", "调用支付系统异常");
		    return respMap;
		}		
		String[] arrResp = resp.split("\\|");
		if(arrResp.length>0){
			if("1".equals(arrResp[0])){
				respMap.put("tag", arrResp[0]);
				byte[] waitDecResp= decoder.decodeBuffer(arrResp[1]);
				byte[] DecResult =DesUtil.decrypt(waitDecResp,dkey);
				respMap.put("msg", new String(DecResult,"utf-8"));
				//respMap.put("msg", new String(decoder.decodeBuffer(arrResp[1]),"utf-8"));
			}else if("0".equals(arrResp[0])){
				respMap.put("tag", arrResp[0]);
				respMap.put("respCode", arrResp[1]);
				respMap.put("respDesc", new String(decoder.decodeBuffer(arrResp[2]),"utf-8"));
			}
		}else{
			respMap.put("tag", "0");
			respMap.put("respCode", RespUtil.timeOut);
			respMap.put("respDesc", "系统调用异常");			
		}
		return respMap;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, String> parseXml(String xml) throws Exception{
		Map<String, String> returnDataMp = new HashMap<String, String>();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Iterator elementList = root.elementIterator();
			while (elementList.hasNext()) {
				Element element = (Element) elementList.next();
				System.out.println(element.getName() + ":"+ element.getTextTrim());
				returnDataMp.put(element.getName(), element.getTextTrim());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return returnDataMp;
	}	

	@Override
	public String invokeInteService(Object obj,String mkey,String url)throws Exception{
		PostUtil postUtil = new PostUtil();
		String xml = "";
		String channelId = "";
		if(obj instanceof OrderPayReq){
			OrderPayReq orderPayReq = (OrderPayReq)obj;
			xml =  XmlUtil.ObjToXml(orderPayReq, OrderPayReq.class);
		}

		//加密报文体格式：BASE64(商户号)| BASE64(RSA(报文加密密钥))| BASE64(3DES(报文原文))		
		BASE64Encoder encoder = new BASE64Encoder();
		BASE64Decoder decoder = new BASE64Decoder();
		//3DES密钥
		byte[] b3DesKey=decoder.decodeBuffer(mkey);
		byte[] publicKey = decoder.decodeBuffer(ConfigUtil.getValue("PUBLIC_KEY"));
		String strKey = encoder.encode(RSACoder.encryptByPublicKey(b3DesKey, publicKey));//第二段
		String strxml = encoder.encode(DesUtil.encrypt(xml.toString().getBytes("utf-8"),b3DesKey));//第三段
        String returnXml = encoder.encodeBuffer(channelId.getBytes()) + "|" + strKey+ "|" + strxml;//第一段
        logger.info("------请求地址------"+url);
		String resp = postUtil.transferData(returnXml, "utf-8",url);
        logger.info("------resp::--"+url);
		return resp;
	}

	/**
	 * (non-Javadoc)  接口通知调用
	 * @see InvokeInteService#invokeInteNotifyService(Object, String, String)
	 */
	@Override
	public String invokeInteNotifyService(Object obj, String mkey, String url)throws Exception {
		PostUtil postUtil = new PostUtil();
		String xml = "";
		String channelId = "";
		if(obj instanceof PayNotifyReq){
			PayNotifyReq payNotifyReq = (PayNotifyReq)obj;
			xml =  XmlUtil.ObjToXml(payNotifyReq, PayNotifyReq.class);
			logger.info("通知前明文报文:"+xml);
		}

		//加密报文体格式：BASE64(商户号)| BASE64(RSA(报文加密密钥))| BASE64(3DES(报文原文))		
		BASE64Encoder encoder = new BASE64Encoder();
		BASE64Decoder decoder = new BASE64Decoder();
		//3DES密钥
		byte[] b3DesKey=decoder.decodeBuffer(mkey);
		byte[] publicKey = decoder.decodeBuffer(ConfigUtil.getValue("PUBLIC_KEY"));
		String strKey = encoder.encode(RSACoder.encryptByPublicKey(b3DesKey, publicKey));//第二段
		String strxml = encoder.encode(DesUtil.encrypt(xml.toString().getBytes("utf-8"),b3DesKey));//第三段
        String returnXml = encoder.encodeBuffer(channelId.getBytes()) + "|" + strKey+ "|" + strxml;//第一段
        logger.info("------请求地址------"+url);
		String resp = postUtil.transferData(returnXml, "utf-8",url);
        logger.info("------resp::--"+resp);
		return resp;
	}

	/**
	 * (non-Javadoc)  内部调用xml解析业务类
	 * @see InvokeInteService#parseResp(String)
	 */
	@Override
	public Map<String, String> parseResp(String resp) throws Exception {
		Map<String, String> respMap = new HashMap<String, String>(6);
		//去掉空格
		if (StringUtil.checkNullString(resp) || resp.replaceAll("\\s*", "").length() == 0) {
			respMap.put(CommEnum.GET_MSG_TAG.getRspCode(), CommEnum.TAG_FAIL.getRspCode()); //tag/0
			respMap.put(CommEnum.RESP_CODE.getRspCode(), RespUtil.CLIENT_INFO_ERR); //respCode
			respMap.put(CommEnum.RESP_DESC.getRspCode(), "系统调用异常或超时或解析内容为空"); //respDesc
		    return respMap;
		}
		try {
			String[] arrResp = resp.split(CommEnum.STRING_SPLIT.getRspCode()); // |分割符
			if(arrResp.length>0){
				BASE64Decoder decoder = new BASE64Decoder();
				//标首1 成功时
				if(CommEnum.TAG_SUECCESS.getRspCode().equals(arrResp[0])){
					respMap.put(CommEnum.GET_MSG_TAG.getRspCode(), arrResp[0]);  //tag
					respMap.put(CommEnum.XML_RESP_MSG_KEY.getRspCode(), new String(decoder.decodeBuffer(arrResp[1]),"utf-8")); //msg
				}else if(CommEnum.TAG_FAIL.getRspCode().equals(arrResp[0])){ //标首0失败
					respMap.put(CommEnum.GET_MSG_TAG.getRspCode(), arrResp[0]); //tag
					respMap.put(CommEnum.RESP_CODE.getRspCode(), arrResp[1]); //respCode
					respMap.put(CommEnum.RESP_DESC.getRspCode(), new String(decoder.decodeBuffer(arrResp[2]),"utf-8")); //respDesc
				}
			}else{
				respMap.put(CommEnum.GET_MSG_TAG.getRspCode(), CommEnum.TAG_FAIL.getRspCode()); //tag/0
				respMap.put(CommEnum.RESP_CODE.getRspCode(), RespUtil.parseEx); //respCode
				respMap.put(CommEnum.RESP_DESC.getRspCode(), CommEnum.SYS_CALL_PAREEX_EXCEPTION.getRspMsg());//respDesc			
			}
		} catch (Exception e) {
			logger.info("调用解析出现异常----------------" +e.getMessage());
			respMap.put(CommEnum.GET_MSG_TAG.getRspCode(), CommEnum.TAG_FAIL.getRspCode()); // tag/0
			respMap.put(CommEnum.RESP_CODE.getRspCode(), RespUtil.timeOut); //respCode
			respMap.put(CommEnum.RESP_DESC.getRspCode(), CommEnum.SYS_CALL_ERROR_EXCEPTION.getRspMsg()); //respDesc
		}
		return respMap;
	}

	  // jsontext 转化成map
	@Override
	public Map<String, String> parseRespJson(String respJson) throws Exception {
		Map<String, String> map = (Map<String, String>) FastJSONUtil.parseObjectMap(respJson, Map.class);
		return map;
	}
}
