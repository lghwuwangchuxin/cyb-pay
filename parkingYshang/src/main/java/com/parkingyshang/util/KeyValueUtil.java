package com.parkingyshang.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class KeyValueUtil {
	/**
	 * 对象转Map<String,Object>
	 * @param obj
	 * @return
	 * @throws Exception
	 */
    public static Map<String, Object> objectToMap2(Object obj) throws Exception {    
        if(obj == null)  
            return null;      
  
        Map<String, Object> map = new HashMap<String, Object>();   
  
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {    
            String key = property.getName();    
            if (key.compareToIgnoreCase("class") == 0) {   
                continue;  
            }  
            Method getter = property.getReadMethod();  
            Object value = getter!=null ? getter.invoke(obj) : null;  
            map.put(key, value);  
        }    
  
        return map;  
    }

    /**
     * Map转键值对String
     * @param data	待加密字段
     * @param secret  商户密钥
     * @return
     */
	public static String coverMap2Object(Map<String, Object> data,String secret) {
		StringBuffer str=new StringBuffer("");
		List<String> list=new ArrayList<String>(data.keySet());
		Collections.sort(list);
		for (String string : list) {
			str.append(string).append("=").append(data.get(string)).append("&");
		}
		str.substring(0, str.length()-1);
		return str.append(secret).toString();
	}
	/**
	 * 过滤sign字段
	 * @param data
	 * @return
	 */
	public static Map<String ,Object> getFilterSign(Map<String, Object> data){
		Map<String,Object> maps=new HashMap<String,Object>();
		if(data.size()<=0||data==null) {
			return maps;
		}
		for (String string : data.keySet()) {
			if(!"sign".equals(string)) {
				maps.put(string, data.get(string));
			}
		}
		return maps;
	}
    /**
     * LinkedHashMap转键值对String
     * @param data
     * @return
     */
	public static String coverMap2Object(LinkedHashMap<String, Object> data) {
		LinkedHashMap<String, Object> tree = new LinkedHashMap<String, Object>();
		Iterator<Entry<String, Object>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> en = it.next();
			tree.put(en.getKey(), en.getValue());
		}
		it = tree.entrySet().iterator();
		StringBuffer sf = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, Object> en = it.next();
			sf.append(en.getKey() + "=" + en.getValue()
					+ "&");
		}
		return sf.substring(0, sf.length() - 1);
	}


}
