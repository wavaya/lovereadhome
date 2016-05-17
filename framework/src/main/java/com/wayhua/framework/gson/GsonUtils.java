package com.wayhua.framework.gson;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.text.DateFormat;


/**
 * Created by 黄卫华(wayhua@126.com) on 2016/3/9.
 */
public class GsonUtils {
    private static GsonBuilder gsonBuilder = null;

    public static Gson getGson() {
        GsonBuilder gsonBuilder = getGsonBuilder();
        return gsonBuilder.create();
    }

    /***
     * 注册GSON类别，放在Application的onCreate中
     *
     * @param type
     * @param typeAdapter
     */
    public static void registerTypeAdapter(Type type, Object typeAdapter) {
        GsonBuilder gsonBuilder = getGsonBuilder();
        gsonBuilder.registerTypeAdapter(type, typeAdapter);
    }

    @NonNull
    private static GsonBuilder getGsonBuilder() {
        if (gsonBuilder == null) {
            gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat(DateFormat.LONG)
                    .registerTypeAdapter(java.util.Date.class, new DateSerializer())
                    .registerTypeAdapter(java.util.Date.class, new DateDeserializer());
        }
        return gsonBuilder;
    }

}
