package com.wayhua.framework;

import android.app.Activity;
import android.content.Context;


import com.wayhua.framework.event.ChangeThemeEvent;
import com.wayhua.framework.util.SharedPreferencesUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/26.
 */
public class NightModelUtils {
    public static final String KEY_NIGHT_MODE_SWITCH = "night_mode_switch";

    public static boolean getNightModeSwitch(Context context) {
        boolean result = (boolean) SharedPreferencesUtils.get(context.getApplicationContext(), KEY_NIGHT_MODE_SWITCH, false);
        return result;
    }

    public static void setNightModeSwitch(Context context, boolean on) {
        SharedPreferencesUtils.put(context.getApplicationContext(), KEY_NIGHT_MODE_SWITCH, on);
    }

    /**
     * 切换主题
     */
    public static void switchTheme(Activity context) {
        boolean b = getNightModeSwitch(context);
        setNightModeSwitch(context, !b);
        EventBus.getDefault().post(new ChangeThemeEvent());
    }
}
