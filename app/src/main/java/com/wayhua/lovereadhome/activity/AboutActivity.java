package com.wayhua.lovereadhome.activity;


import com.wayhua.framework.base.BaseActivityToolBar;
import com.wayhua.lovereadhome.R;

public class AboutActivity extends BaseActivityToolBar {

    @Override
    public void initActionBar() {
        super.initActionBar();
        setActionBarCenterTitle(R.string.about);
    }

    @Override
    protected int getMainLayoutView() {
        return R.layout.activity_about;
    }
}
