package com.wayhua.lovereadhome;

import android.content.Context;
import android.content.Intent;

import com.wayhua.lovereadhome.activity.LoginActivity;
import com.wayhua.lovereadhome.activity.MainNewActivity;


/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/29.
 */
public class API {
    public static Intent getJumpItent(Context context) {

        boolean logined = SessionManage.isLogined(context);
        if (!logined)
            return new Intent(context, LoginActivity.class);

        return getIntentLogined(context);
    }


    public static Intent getIntentLogined(Context context) {
        return new Intent(context, MainNewActivity.class);
    }
}

