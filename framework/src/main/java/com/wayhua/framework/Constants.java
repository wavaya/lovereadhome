package com.wayhua.framework;

import android.os.Environment;

import com.wayhua.framework.event.CloseAllEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/26.
 */
public class Constants {
    public static final String PREF_FIRST_USE = "first_use";
    public static final String SPLASHTIME = "splash_time";
    public static String splashFileName = "splash.png";
    public static String splashPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/splash/";
    public static final String KEY_DOUBLE_CLICK_EXIT = "KEY_DOUBLE_CLICK_EXIT";

    /***
     * 退出程序
     */
    public static void quit() {
        EventBus.getDefault().post(new CloseAllEvent());
    }
}

