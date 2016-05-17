package com.wayhua.lovereadhome;

import android.content.Context;
import android.text.TextUtils;

import com.wayhua.framework.util.SharedPreferencesUtils;


/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/2.
 */
public class SessionManage {
    private static final String ISLOGIN = "ISLOGIN";
    private static final String ACCOUNT = "ACCOUNT";
    private static final String REALNAME="REALNAME";
    private static boolean isLogin = false;
    private static String account = "";
    private static  String realname="";

    public static String getRealname(Context context) {
        if (TextUtils.isEmpty(realname)) {
            realname = (String) SharedPreferencesUtils.get(context.getApplicationContext(), REALNAME, "");
        }  return realname;
    }

    public static void setRealname(Context context,String realname) {
        SessionManage.realname = realname;
        SharedPreferencesUtils.put(context.getApplicationContext(), REALNAME, realname);
    }


    public static String getAccount(Context context) {
        if (TextUtils.isEmpty(account)) {
            account = (String) SharedPreferencesUtils.get(context.getApplicationContext(), ACCOUNT, "");
        }
        return account;
    }

    public static void setAccount(Context context, String account) {
        SessionManage.account = account;
        SharedPreferencesUtils.put(context.getApplicationContext(), ACCOUNT, account);
    }

    public static void setIsLogin(Context context, boolean logined) {
        SharedPreferencesUtils.put(context.getApplicationContext(), ISLOGIN, logined);
    }

    public static boolean isLogined(Context context) {
        boolean logined = (boolean) SharedPreferencesUtils.get(context.getApplicationContext(), ISLOGIN, false);
        return logined;
    }


}
