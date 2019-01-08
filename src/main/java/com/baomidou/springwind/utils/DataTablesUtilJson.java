package com.baomidou.springwind.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class DataTablesUtilJson {
    /**
     * 转换为DataTables能解析的JSON格式
     * @param json
     * @return
     */
    public static String toDataTablesJson(String json){
        return "{ \"data\":"+json+"}";
    }
    /**
     * 转换为DataTables能解析的JSON格式
     * @param object
     * @return
     */
    public static<T> String toDataTablesJson(T object){
        return toDataTablesJson(JSONObject.toJSON(object));
    }

    public static <T> String arrayToDataTablesJson(T array){
        return toDataTablesJson(JSONArray.toJSONString(array));
    }
}
