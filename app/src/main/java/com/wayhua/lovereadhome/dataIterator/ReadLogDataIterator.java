package com.wayhua.lovereadhome.dataIterator;

import android.content.Context;

import com.wayhua.framework.exception.NetException;
import com.wayhua.framework.retrofit.DataListIterator;
import com.wayhua.lovereadhome.bean.XReadLog;
import com.wayhua.lovereadhome.bmob.BmobManage;

import java.util.List;

import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/4.
 */
public class ReadLogDataIterator extends DataListIterator<XReadLog> {
    public ReadLogDataIterator(Context context, int nextPage) {
        super(context, nextPage);
    }

    @Override
    public Observable<? extends List<XReadLog>> observable() {
        return Observable.create(new Observable.OnSubscribe<List<XReadLog>>() {
                                     @Override
                                     public void call(final Subscriber<? super List<XReadLog>> subscriber) {

                                         BmobManage.XReadLogQuery(context, nextPage, pageSize, new FindListener<XReadLog>() {

                                             @Override
                                             public void onSuccess(List<XReadLog> list) {
                                                 subscriber.onNext(list);
                                                 subscriber.onCompleted();
                                             }

                                             @Override
                                             public void onError(int i, String s) {
                                                 subscriber.onError(new NetException(s));
                                             }
                                         });

                                     }
                                 }
        );
    }
}
