package com.wayhua.lovereadhome.dataIterator;

import android.content.Context;

import com.wayhua.framework.retrofit.DataListIterator;
import com.wayhua.lovereadhome.bean.XBookInfo;
import com.wayhua.lovereadhome.bmob.BmobManage;
import com.wayhua.framework.exception.NetException;


import java.util.List;

import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/2.
 */
public class BookInfoDataIterator extends DataListIterator<XBookInfo> {
    public BookInfoDataIterator(Context context, int nextPage) {
        super(context, nextPage);

    }

    @Override
    public Observable<? extends List<XBookInfo>> observable() {
        return Observable.create(new Observable.OnSubscribe<List<XBookInfo>>() {
            @Override
            public void call(final Subscriber<? super List<XBookInfo>> subscriber) {

                BmobManage.XBookInfoQuery(context, nextPage, pageSize, new FindListener<XBookInfo>() {

                    @Override
                    public void onSuccess(List<XBookInfo> list) {
                        subscriber.onNext(list);
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(int i, String s) {
                        subscriber.onError(new NetException(s));
                    }
                });

//                BmobQuery<XBookInfo> query = new BmobQuery<>();
//                query.setLimit(pageSize);
//                int skips = (nextPage - 1) * pageSize;
//                query.setSkip(skips);
//
//                query.findObjects(context, new FindListener<XBookInfo>() {
//                    @Override
//                    public void onSuccess(List<XBookInfo> list) {
//                        subscriber.onNext(list);
//                        subscriber.onCompleted();
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//                        subscriber.onError(new NetException(s));
//                    }
//                });

            }
        });


//        Realm realm = RealmDB.getRealm();
//        final ArrayList<XBookInfo> bookInfos = new ArrayList<>(realm.where(XBookInfo.class).findAll());
//        return Observable.create(new Observable.OnSubscribe<List<XBookInfo>>() {
//
//            @Override
//            public void call(Subscriber<? super List<XBookInfo>> subscriber) {
//                subscriber.onNext(bookInfos);
//
//                subscriber.onCompleted();
//            }
//        });
    }
}
