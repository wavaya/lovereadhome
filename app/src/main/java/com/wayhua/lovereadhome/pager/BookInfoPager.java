package com.wayhua.lovereadhome.pager;


import com.wayhua.lovereadhome.bean.XBookInfo;
import com.wayhua.framework.core.ResourcePager;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/2.
 */
public abstract class BookInfoPager extends ResourcePager<XBookInfo> {
    @Override
    protected Object getId(XBookInfo resource) {
        return resource.getId();
    }
}
