package com.mm.weclubs.util;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonHelper {
    public static Object toJSON(Object object) throws JSONException {
        if (object instanceof Map) {
            JSONObject json = new JSONObject();
            Map map = (Map) object;
            for (Object key : map.keySet()) {
                json.put(key.toString(), toJSON(map.get(key)));
            }
            return json;
        } else if (object instanceof Iterable) {
            JSONArray json = new JSONArray();
            for (Object value : ((Iterable)object)) {
                json.put(value);
            }
            return json;
        } else {
            return object;
        }
    }

    public static boolean isEmptyObject(JSONObject object) {
        return object.names() == null;
    }

    public static Map<String, Object> getMap(JSONObject object, String key) throws JSONException {
        return toMap(object.getJSONObject(key));
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keys = object.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            map.put(key, fromJson(object.get(key)));
        }
        return map;
    }

    public static List toList(JSONArray array) throws JSONException {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(fromJson(array.get(i)));
        }
        return list;
    }

    private static Object fromJson(Object json) throws JSONException {
        if (json == JSONObject.NULL) {
            return null;
        } else if (json instanceof JSONObject) {
            return toMap((JSONObject) json);
        } else if (json instanceof JSONArray) {
            return toList((JSONArray) json);
        } else {
            return json;
        }
    }

    /**
     * 将Gson封装,支持将result传入,然后转成实体对象
     *
     * @param obj result,一般是服务器返回的键值对
     * @param targetClass   目标实体对象
     * @param <T>   目标实体对象
     * @return  返回转换成功的对象
     */
    public static <T> T getBeanFromJson(Object obj, Class<T> targetClass) {
        Gson gson = new Gson();
        String json;
        json = gson.toJson(obj);
        return gson.fromJson(json, targetClass);
    }

    /**
     * 根据传进来的json字符串转成实体对象
     * @param jsonStr   jsonString
     * @param targetClass   目标实体对象
     * @param <T>   目标实体对象
     * @return  返回转换成功的对象
     */
    public static <T>T getBeanFromJsonString(String jsonStr, Class<T> targetClass) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, targetClass);
    }

    /**
     * 将一个对象转换成json字符串
     *
     * @param obj   需要转成json字符串的对象
     * @return  返回json字符串
     */
    public static String getJsonStrFromObj(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * SDK < 19的时候，使用该方法代替 JSONArray.remove(index)方法
     *
     * @param jsonArray 需要移除的数组
     * @param index 下标
     * @return  移除后的数组
     */
    public static JSONArray removeJsonArrayAtIndex(JSONArray jsonArray, int index) {
        if (jsonArray == null || jsonArray.length() == 0) {
            return jsonArray;
        }

        if (index < 0 || index > (jsonArray.length() - 1)) {
            return jsonArray;
        }

        JSONArray newJsonArray = new JSONArray();
        int position = 0;
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                if (i != index) {
                    newJsonArray.put(position, jsonArray.get(i));
                    position ++;
                }
            }
            Log.d("JsonHelper", "removeJsonArrayAtIndex: jsonArray = " + jsonArray.toString() + "; newJsonArray = " + newJsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newJsonArray;
    }
}