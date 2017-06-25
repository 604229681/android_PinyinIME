package com.android.inputmethod.pinyin.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * <h3>JsonUtil工具类</h3>
 *
 * @author HuangBing
 * @mail huangb@Ivollo.com
 * @date 2015-8-27
 * @Version: 1.0
 */
public class JsonUtil {

    private static Gson gson = null;

    private static Gson gsonField;

    /**
     * json字符串中取key对应的值<br>
     *
     * @param jsonstring json字符串
     * @param key        键值
     * @return String 某节点的值
     */
    public static String parserJsonString(String jsonstring, String key) {
        try {
            JSONObject json = new JSONObject(jsonstring);
            return json.getString(key);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串中取多层级key对应的值<br>
     *
     * @param jsonstring json字符串
     * @param key        键值
     * @return String 某节点的值
     */
    public static String parserJsonString(String jsonstring, String key, String... parentKey) {
        try {
            if (parentKey != null && parentKey.length > 0) {
                String jsonStr = jsonstring;
                for (int i = 0; i < parentKey.length; i++) {
                    JSONObject json = new JSONObject(jsonStr);
                    jsonStr = json.getString(parentKey[i]);
                }
                JSONObject json = new JSONObject(jsonStr);
                return json.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据jsonString 以及 class 获取结构体返回<br>
     *
     * @param jsonstring json字符串
     * @param clazz      类
     * @return T 结构体
     */
    @SuppressWarnings("unchecked")
    public static <T> Object getClazzByGson(String jsonstring, Class<T> clazz) {
        if (jsonstring == null || "".equals(jsonstring))
            return null;
        if (jsonstring.startsWith("[")) {
            List<T> jsonList = new ArrayList<T>();
            try {
                JSONArray jsonArray = new JSONArray(jsonstring);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String json = jsonArray.get(i).toString();
                    jsonList.add((T) getClazzByGson(json, clazz));
                }
                return jsonList;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        } else {
            return getGsonInstance().fromJson(jsonstring, clazz);
        }
    }

    /**
     * 根据dataName 生成对应json
     * @param jsonString
     * @param dataName
     * @return
     */
    public static String getStringGson(String jsonString ,String dataName){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonObject.put(dataName,jsonObject);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 根据jsonString 以及 class 获取结构体返回<br>
     *
     * @param jsonstring json字符串
     * @param type       类型
     * @return T 结构体
     */
    public static <T> T getClazzByGson(String jsonstring, Type type) {
        if (jsonstring == null || "".equals(jsonstring))
            return null;
        return getGsonInstance().fromJson(jsonstring, type);
    }

    /**
     * 根据Object生成字符串<br>
     *
     * @param clazz 类
     * @return String 结构体的json字符串
     */
    public static String getJsonStringByGson(Object clazz) {
        return getGsonInstance().toJson(clazz);
    }

    /**
     * 根据Object生成字符串<br>
     *
     * @param clazz 类
     * @return String 结构体的json字符串
     */
    public static String getJsonExcludeFieldStringByGson(Object clazz) {
        return getGsonExcludeFieldInstance().toJson(clazz);
    }


//    /**
//     * 根据键值对生成json字符串<br>
//     *
//     * @param Object 类
//     * @return String 结构体的json字符串
//     */
//    public static String getJsonStringByList(List<NameValuePair> pairs) {
//        StringBuilder stringBuilder = new StringBuilder("");
//        if (pairs.size() > 0) {
//            stringBuilder.append("{");
//            for (NameValuePair pair : pairs) {
//                if (pair != null) {
//                    stringBuilder.append("\"");
//                    stringBuilder.append(pair.getKey());
//                    stringBuilder.append("\"");
//                    stringBuilder.append(":");
//                    stringBuilder.append("\"");
//                    stringBuilder.append(pair.getValue().toString());
//                    stringBuilder.append("\"");
//                    stringBuilder.append(",");
//                }
//            }
//            String str = stringBuilder.toString();
//            if (str.endsWith(",") && str.length() > 2) {
//                stringBuilder = new StringBuilder(str.substring(0, str.length() - 1));
//            }
//            stringBuilder.append("}");
//        }
//        return stringBuilder.toString();
//    }

    /**
     * 得到Gson实例<br>
     *
     * @return Gson 实例
     */
    private static Gson getGsonInstance() {
        if (gson == null) {
            synchronized (JsonUtil.class) {
                if (gson == null) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
                    gson = gsonBuilder.create();
                }
            }
        }
        return gson;
    }

    /**
     * 得到Gson实例<br>
     * 过来不需要的GSON字段
     * @return Gson 实例
     */
    private static Gson getGsonExcludeFieldInstance() {

        if (gsonField == null) {
            synchronized (JsonUtil.class) {
                if (gsonField == null) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.excludeFieldsWithoutExposeAnnotation();
                    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
                    gsonField = gsonBuilder.create();
                }
            }
        }
        return gsonField;
    }
}
