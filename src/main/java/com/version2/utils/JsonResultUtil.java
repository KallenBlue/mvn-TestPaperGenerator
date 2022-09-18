package com.version2.utils;

import com.alibaba.fastjson2.JSONObject;

/**
 * @Description: 返回json格式
 */
public class JsonResultUtil {
    /**
     * 1. code : 响应业务状态
     * 2. message: 响应消息
     * 3. result : 响应中的数据
     */
    /**
     * 统一返回前台json格式
     * @return
     */
    public static String getJsonFail(){
        JSONObject json = new JSONObject();
        json.put("code", ResponseCode.SUCCESS.value);
        json.put("message", "fail");
        json.put("data", new Object());
        return String.valueOf(json);
    }
    /**
     * 统一返回前台json格式
     * @return
     */
    public static String getJson(){
        JSONObject json = new JSONObject();
        json.put("code", ResponseCode.SUCCESS.value);
        json.put("message", "success");
        json.put("data", new Object());
        return String.valueOf(json);
    }
    /**
     * 统一返回前台json格式
     * @param data
     * @return
     */
    public static String getJson(Object data){
        JSONObject json = new JSONObject();
        json.put("code", ResponseCode.SUCCESS.value);
        json.put("message", "success");
        json.put("data", data);
        return String.valueOf(json);
    }

    /**
     * 统一返回前台json格式
     * @param data
     * @return
     */
    public static String getJson(String message, Object data){
        JSONObject json = new JSONObject();
        json.put("code", ResponseCode.SUCCESS.value);
        json.put("message", message);
        json.put("data", data);
        return String.valueOf(json);
    }

    /**
     * 统一返回前台json格式
     * @param data
     * @return
     */
    public static String getJson(int code,String message,Object data){
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("message", message);
        json.put("data", data);
        return String.valueOf(json);
    }
    /**
     * 处理返回的json
     * @param result 处理后的结构化数据
     * @param total
     * @return
     */
    public static String getJsonForLog(Object result, int total) {
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "");
        json.put("count", total);
        json.put("data", result);
        return String.valueOf(json);
    }
}
