package com.wayhua.lovereadhome.fragment;

import android.os.Bundle;
import android.view.View;

import com.wayhua.lovereadhome.R;
import com.wayhua.framework.base.BaseFragmentPagedItemList;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/4.
 */
public abstract class XBaseFragmentPagedItemList<T> extends BaseFragmentPagedItemList<T> {
    @Override
    protected int getLoadingMessage() {
        return R.string.loadMsg;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyText(R.string.nobookInfo);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.errormsg;
    }
}
