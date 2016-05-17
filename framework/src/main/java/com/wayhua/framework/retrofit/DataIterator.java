package com.wayhua.framework.retrofit;

import android.content.Context;

import com.wayhua.framework.interf.IDataIterator;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/3/21.
 */
public abstract class DataIterator<K> implements IDataIterator<K> {
    protected Context context;

    public DataIterator(Context context) {
        this.context = context;
    }


}


