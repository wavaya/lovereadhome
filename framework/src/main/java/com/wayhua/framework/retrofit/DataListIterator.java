package com.wayhua.framework.retrofit;

import android.content.Context;

import java.util.List;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/3/29.
 */
public abstract class DataListIterator<V> extends DataIterator<List<V>> {
    protected int pageSize = 10;
    protected int nextPage;


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }


    public DataListIterator(Context context, int nextPage) {
        super(context);
        this.context = context;
        this.nextPage = nextPage;
    }

    public DataListIterator(Context context, int nextPage, int pageSize) {
        super(context);
        this.context = context;
        this.nextPage = nextPage;
        this.pageSize = pageSize;
    }


    public DataListIterator(Context context) {
        super(context);
    }
}
