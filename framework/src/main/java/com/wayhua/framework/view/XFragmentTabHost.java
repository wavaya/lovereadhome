package com.wayhua.framework.view;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/2/18.
 */
public class XFragmentTabHost extends FragmentTabHost {
    private String mCurrentTag;

    private String mNoTabChangedTag;

    public XFragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onTabChanged(String tag) {
        if (tag.equals(mNoTabChangedTag)) {
            setCurrentTabByTag(mCurrentTag);
        } else {
            super.onTabChanged(tag);
            mCurrentTag = tag;
        }
    }

    public void setNoTabChangedTag(String tag) {
        this.mNoTabChangedTag = tag;
    }
}
