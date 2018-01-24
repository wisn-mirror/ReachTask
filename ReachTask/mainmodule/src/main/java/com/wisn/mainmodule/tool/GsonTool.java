package com.wisn.mainmodule.tool;

import com.google.gson.Gson;

/**
 * @author Wisn
 * @time 2018/1/24 15:32
 */


public class GsonTool {
    static Gson gson=null;
    static {
        gson=new Gson();
    }
    public static String toJson(Object object){
        return gson.toJson(object);
    }
}
