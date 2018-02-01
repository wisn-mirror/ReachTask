package com.wisn.mainmodule.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Wisn
 * @time 2018/1/24 15:32
 */


public class GsonTool {
    static Gson gson=null;
    static {
//        gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();// @Expose
        gson=new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                //i_开头的都忽略
                return f.getName().startsWith("i_");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();
    }
    public static String toJson(Object object){
        return gson.toJson(object);
    }
}
