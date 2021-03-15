package com.parkinghx.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class DecryptForClient {	

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static String encoding = "utf-8";

	
	/**
	 * @param xmlData
	 * @return
	 * @throws DocumentException
	 */
	public Map<String, String> getHead(String xmlData) throws DocumentException{
		Map<String, String> xmlTypeMap = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xmlData);
		Element bpcore = document.getRootElement();
		String service = bpcore.attributeValue("service");
		String channelId = bpcore.attributeValue("channelId");
		String termId = bpcore.attributeValue("termId");
		String osVersion = bpcore.attributeValue("osVersion");

		//赋值
		xmlTypeMap.put("service", service);
		xmlTypeMap.put("channelId", channelId);
		xmlTypeMap.put("termId", termId);
		xmlTypeMap.put("osVersion", osVersion);
		return xmlTypeMap;
	}
}
