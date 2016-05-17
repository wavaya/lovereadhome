package com.wayhua.lovereadhome.pager;

import com.wayhua.lovereadhome.bean.XReadLog;
import com.wayhua.framework.core.ResourcePager;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/4.
 */
public abstract class ReadLogPager extends ResourcePager<XReadLog> {
    @Override
    protected Object getId(XReadLog resource) {
        return resource.getObjectId();
    }
}
