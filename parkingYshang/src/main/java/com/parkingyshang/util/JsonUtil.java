package com.parkingyshang.util;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class JsonUtil {

	private static Gson gson = null;

	static{
		if(gson == null){
			gson = new Gson();
		}
	}

    /**
     * 灏咼SON鏁版嵁瑙ｆ瀽鎴愮浉搴旂殑鏄犲皠瀵硅薄
     * @param jsonData
     * @param type
     * @return bean
     */
	public static <T> T jsonToBean(String jsonData,Class<T> type){
		T result = gson.fromJson(jsonData, type);
		return result;
	}

    /**
     * 灏唈ava瀵硅薄杞崲鎴恓son瀛楃涓�
     * @param obj
     * @return str
     */
	public static String beanToJson(Object obj){
		return gson.toJson(obj);
	}

    /**
     * json瀛楃涓茶浆map
     * @param json
     * @return
     */
	public static <T> Map<String, T> json2Map(String json) {
		Map<String,T> map = null;
		if(json != null){
			map = gson.fromJson(json,new TypeToken<Map<String,T>>(){}
			.getType());
		}
	     return map;
	}

    /**
     * 灏咼son鏍煎紡鐨勫瓧绗︿覆杞崲鎴愭寚瀹氬璞＄粍鎴愮殑List杩斿洖
     *
     * @param jsonString
     *            Json鏍煎紡鐨勫瓧绗︿覆
     * @param pojoClass
     *            杞崲鍚庣殑List涓璞＄被鍨�
     * @return 杞崲鍚庣殑List瀵硅薄
     */
    @SuppressWarnings("unchecked")
    public static List json2List(String jsonString, Class pojoClass) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        Object pojoValue;
        List list = new ArrayList();
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            pojoValue = JSONObject.toBean(jsonObject, pojoClass);
            list.add(pojoValue);
        }
        return list;
    }

//    /**
//     * Map杞琂SON
//     * @param map
//     * @return
//     */
   /* public static String map2Param(Map<String,Object> map){

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for(Map.Entry<String, Object> entry : map.entrySet()){
            params.add(new BasicNameValuePair(entry.getKey(),entry.getValue().toString()));
        }

        String param = "";
        try {
            param = EntityUtils.toString(new UrlEncodedFormEntity(params,Consts.UTF_8));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return param;
    }*/

    /**
     * 鏍规嵁鑺傜偣鍚嶇О鑾峰彇JSON
     * @param requestValue
     * @param verifyName
     * @return
     */
    /*public static Map<String, String> getBaseMap(String requestValue,String verifyName) {
        Map<String, String> map = new HashMap<String, String>();
        if (!StringUtils.isNullOrEmpty(requestValue)) {
            JSONObject json = JSONObject.fromObject(requestValue);
            Map maps = verify(verifyName,json);
            if (maps == null) {
                Iterator it = json.keys();
                while (it.hasNext()) {
                    String key = String.valueOf(it.next());
                    String value = (String) json.get(key);
                    map.put(key, value);
                }
            } else {
                return maps;
            }
        } else if(verifyName != null){
            map.put("error", "鍙傛暟涓嶈兘涓虹┖");
        }
        return map;
    }*/

    /**
     * 楠岃瘉鍙傛暟
     * @param requestValue
     * @param incServiceName
     * @return
     */
    /*public static Map<String, String> verify(String verifyName, JSONObject json) {
        Map<String, String> map =new HashMap<String, String>();
        if (verifyName != null) {
            String verifyValue = PropertiesUtil.get(verifyName);
            JSONObject verifyJson = JSONObject.fromObject(verifyValue);
            for (Object key : verifyJson.keySet()) {
                boolean flag = verifyJson.getBoolean(String.valueOf(key));
                if (flag) {
                    if(json.get(key) == null || json.get(key).equals("")){
                        map.put("error", String.valueOf(key)+"涓嶈兘涓虹┖");
                        return map;
                    }
                }
            }
        }
        return null;
    }*/
    /**
     * 将一个List对象转换成Json数据格式返回
     *
     * @param list
     *            需要进行转换的List对象
     * @return 转换后的Json数据格式字符串
     */
    public static String listToJson(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(beanToJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }
}
