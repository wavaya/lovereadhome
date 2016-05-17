package com.wayhua.lovereadhome;

import android.content.Context;

import com.google.gson.Gson;
import com.wayhua.doubanapi.models.BookInfo;
import com.wayhua.framework.gson.GsonUtils;
import com.wayhua.lovereadhome.bean.XBookInfo;
import com.wayhua.lovereadhome.bean.XUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/3.
 */
public class BookInfo2XBookInfo {
    public static XBookInfo trans(Context context, BookInfo bookInfo, String bookType) {
        Gson gson = GsonUtils.getGson();
        String json = gson.toJson(bookInfo);
        XBookInfo result = gson.fromJson(json, XBookInfo.class);
        result.setBooktype(bookType);

        final XUser bmobUser = BmobUser.getCurrentUser(context, XUser.class);
        String account = bmobUser.getUsername();
        result.setAccount(account);
        return result;
    }

    public static XBookInfo trans2Default(Context context, BookInfo bookInfo) {
        return trans(context, bookInfo, XBookInfo.DEFAULT);
    }

    public static XBookInfo trans2Library(Context context, BookInfo bookInfo) {
        return trans(context, bookInfo, XBookInfo.LIBRARY);
    }
}
