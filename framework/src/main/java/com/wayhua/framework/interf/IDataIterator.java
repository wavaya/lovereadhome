package com.wayhua.framework.interf;


import rx.Observable;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/2/16.
 */
public interface IDataIterator<K> {
    Observable<? extends  K > observable();

}
