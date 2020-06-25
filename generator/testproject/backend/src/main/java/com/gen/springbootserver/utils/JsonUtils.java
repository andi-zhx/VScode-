package com.gen.springbootserver.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class JsonUtils {

    public static JSONObject getJsonResource(String fileName) throws IOException {
        fileName += ".json";
        ClassLoader classLoader = getClassLoader();
        URL resources= classLoader.getResource(fileName);
        String json=Resources.toString(resources, Charsets.UTF_8);
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(JSON.parseObject(json));
        return jsonObject;
    }
    public static void updateJsonResource(String fileName) throws IOException {
        fileName += ".json";
        ClassLoader classLoader = getClassLoader();
        URL resources= classLoader.getResource(fileName);
        String json=Resources.toString(resources, Charsets.UTF_8);
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(JSON.parseObject(json));
        jsonObject.replace("save", true);
        BufferedWriter bw = new BufferedWriter(new FileWriter(resources.getPath()));
        bw.write(jsonObject.toJSONString());
        bw.flush();
    }
    private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        return JsonUtils.class.getClassLoader();
    }

    /**
     * 私有构造方法，防止工具类被new
     */
    private JsonUtils() {
        throw new IllegalAccessError();
    }
}
