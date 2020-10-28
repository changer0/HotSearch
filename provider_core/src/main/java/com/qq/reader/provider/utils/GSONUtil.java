package com.qq.reader.provider.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.qq.reader.provider.log.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghao on 2017/8/9.
 */

public class GSONUtil {


    public static <T> T parseJsonWithGSON(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    public static <T> T parseJsonWithGSON(String jsonData,Type type) throws JsonSyntaxException {
        if (jsonData == null) {
            return null;
        }
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    public static<T> String parseGSONWithJson(Class<T> type){
        Gson gson = new Gson();
        String json="";
        try {
            json= gson.toJson(type);
        }catch (Exception e){
            Logger.e("gson转换对象 -> json","出现异常——————————");
        }
        return json;
    }

    public static<T> String parseListToJson(List<T> list) {

        Gson gson = new Gson();
        String json="";
        try {
            json= gson.toJson(list);
        }catch (Exception e){
            Logger.e("gson转换list -> json","出现异常——————————");
        }
        return json;
    }

    public static <T> List<T> parseJsonToList(String jsonData, Class<T> clazz) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();
            if (jsonData != null && !"".equals(jsonData)) {
                JsonArray jsonarray = parser.parse(jsonData).getAsJsonArray();
                for (JsonElement element : jsonarray
                        ) {
                    list.add(gson.fromJson(element, clazz));
                }
            }
        }catch (Exception e){
            Logger.e("gson转换json -> list","出现异常——————————");
        }
        return list;
    }

    public static <T> String objectToJson(T object) {
        Gson gson = new Gson();
        try {
            return gson.toJson(object);
        }  catch (Exception e) {
            Logger.e("objectToJson gson转换json -> String","出现异常 e = "+e);
        }
        return null;
    }
}
