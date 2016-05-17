package com.wayhua.lovereadhome.bmob;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.wayhua.lovereadhome.bean.XBookInfo;
import com.wayhua.lovereadhome.bean.XReadLog;
import com.wayhua.lovereadhome.bean.XUser;
import com.wayhua.framework.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/4.
 */
public class BmobManage {
    private static XUser current;

    public static void setCurrent(Context context) {
        current = BmobUser.getCurrentUser(context, XUser.class);
    }

    public static XUser getCurrent() {
        return current;
    }

    public static void checkBookInfo(Context context, String isbn, CountListener countListener) {
        BmobQuery<XBookInfo> and1 = createCurrentUserQuery();
        BmobQuery<XBookInfo> and2 = new BmobQuery<>();
        and2.addWhereEqualTo("isbn13", isbn);
        BmobQuery<XBookInfo> and3 = createDefaultQuery();
        List<BmobQuery<XBookInfo>> ands = new ArrayList<>();
        ands.add(and1);
        ands.add(and2);
        ands.add(and3);
        BmobQuery<XBookInfo> query = new BmobQuery<>();
        query.and(ands);
        query.count(context, XBookInfo.class, countListener);
    }

    public static void XBookInfoQuery(Context context, int nextPage, int pageSize, FindListener listener) {
        //1.筛选条件
        BmobQuery<XBookInfo> query = new BmobQuery<>();
        List<BmobQuery<XBookInfo>> ands = getxBookInfoBmobQueryCondition();
        query.and(ands);

        query.setLimit(pageSize);
        int skips = (nextPage - 1) * pageSize;
        query.setSkip(skips);
        query.order("readCount");

        query.findObjects(context, listener);
    }

    public static void queryXBookInfo(Context context, String isbn, FindListener findCallback) {
        BmobQuery<XBookInfo> query = new BmobQuery<>();
        List<BmobQuery<XBookInfo>> ands = getxBookInfoBmobQueryCondition();
        BmobQuery<XBookInfo> and2 = new BmobQuery<>();
        and2.addWhereEqualTo("isbn13", isbn);
        ands.add(and2);

        query.findObjects(context, findCallback);

    }

    @NonNull
    private static List<BmobQuery<XBookInfo>> getxBookInfoBmobQueryCondition() {

        List<BmobQuery<XBookInfo>> ands = new ArrayList<>();
        BmobQuery<XBookInfo> and1 = createCurrentUserQuery();
        BmobQuery<XBookInfo> and3 = createDefaultQuery();
        ands.add(and1);
        ands.add(and3);

        return ands;
    }

    private static BmobQuery<XBookInfo> createDefaultQuery() {
        BmobQuery<XBookInfo> and1 = new BmobQuery<>();
        and1.addWhereEqualTo("booktype", XBookInfo.DEFAULT);
        return and1;
    }

    private static BmobQuery<XBookInfo> createCurrentUserQuery() {
        BmobQuery<XBookInfo> and1 = new BmobQuery<>();
        and1.addWhereEqualTo("account", current.getUsername());
        return and1;
    }

    public static void XReadLogQuery(Context context, int nextPage, int pageSize, FindListener<XReadLog> findListener) {
        BmobQuery<XReadLog> query = new BmobQuery<>();
        query.addWhereEqualTo("account", current.getUsername());
        query.setLimit(pageSize);
        int skips = (nextPage - 1) * pageSize;
        query.setSkip(skips);
        query.order("-readDate");


        query.findObjects(context, findListener);
    }

    public static void updateReadCount(Context context, XBookInfo bookInfo) {
        int readcount = bookInfo.getReadCount();
        bookInfo.setReadCount(++readcount);
        bookInfo.update(context);
    }

    public static void batchUpdateReadCount(final Activity context, List<XBookInfo> list) {
        List<BmobObject> thelist = new ArrayList<>();
        for (XBookInfo bookInfo : list) {
            int count = bookInfo.getReadCount();
            count++;
            bookInfo.setReadCount(count);
            thelist.add(bookInfo);

        }
        BmobObject bmobObject = new BmobObject();
        bmobObject.updateBatch(context, thelist, new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtils.show(context, "批量更新数据成功!");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.show(context, "批量更新数据失败!");
            }
        });


    }
}


