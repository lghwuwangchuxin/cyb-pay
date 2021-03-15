package com.parkingunionpaypre.util;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;


public class XmlUtil{

	public static Object XmlToObj(Reader reader , Class<?> ObjectClazz) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(ObjectClazz);
		Unmarshaller um = context.createUnmarshaller();
		return um.unmarshal(reader);
	}
	
	public static Object XmlToObj(InputStream in ,Class<?> ObjectClazz) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(ObjectClazz);
		Unmarshaller um = context.createUnmarshaller();
		return um.unmarshal(in);
	} 
	
	public static Object XmlToObj(File f , Class<?> ObjectClazz) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(ObjectClazz);
		Unmarshaller um = context.createUnmarshaller();
		return um.unmarshal(f);
	} 
	
	public static Object XmlToObj(org.w3c.dom.Node node , Class<?> ObjectClazz) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(ObjectClazz);
		Unmarshaller um = context.createUnmarshaller();
		return um.unmarshal(node);
	} 
	
	public static Object XmlToObj(String xml , Class<?> ObjectClazz) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(ObjectClazz);
		Unmarshaller um = context.createUnmarshaller();
		StringReader reader = new StringReader(xml);
		
		return um.unmarshal(reader);
	} 
	
	public static  String ObjToXml(Object object , Class... classesToBeBound) throws JAXBException{
		
		JAXBContext context = JAXBContext.newInstance(classesToBeBound);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setSchema(null);
		StringWriter sw = new StringWriter();
		m.marshal(object, sw);
		return sw.toString();
	}
	
	public static  String ObjToXml(Object object  , boolean XmlFormat ,  Class... classesToBeBound) throws JAXBException{
		
		JAXBContext context = JAXBContext.newInstance(classesToBeBound);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, XmlFormat);
		m.setSchema(null);
		StringWriter sw = new StringWriter();
		m.marshal(object, sw);
		return sw.toString();
	}
	/** 
	* 判断是否是xml结构 
	*/ 
	public static boolean isXML(String value) {
		try {
			DocumentHelper.parseText(value);
		} catch (DocumentException e) {
			return false;
		}
		return true;
	}

}
