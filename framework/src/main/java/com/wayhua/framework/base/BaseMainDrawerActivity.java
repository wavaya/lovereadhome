package com.wayhua.framework.base;

import android.os.Bundle;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.wayhua.framework.interf.IInitToolBar;
import com.wayhua.framework.interf.IMainContentView;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/29.
 */
public abstract class BaseMainDrawerActivity extends BaseMainActivity implements IInitToolBar, IMainContentView {
    protected Drawer result = null;
    protected AccountHeaderBuilder headerBuilder;
    protected AccountHeader headerResult;
    protected DrawerBuilder drawerBuilder;


    @Override
    public void initView() {
        super.initView();

        setMainContentView(getMainLayoutView());

    }


    protected void setHomeAsUpIndication(int id) {
        actionBar.setHomeAsUpIndicator(id);
    }

    private void initDrawer(Bundle savedInstanceState) {
        headerBuilder = initHeaderBulider();
        if (headerBuilder != null) {
            headerResult = headerBuilder.build();
        }

        drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true);

        //   drawerBuilder.withDrawerWidthDp(72);
        //  drawerBuilder.withGenerateMiniDrawer(true);
        if (headerResult != null) {
            drawerBuilder.withAccountHeader(headerResult);
        }
        initDrawerItem();
        result = drawerBuilder.withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                //  .withItemAnimator(new AlphaCrossFadeAnimator())

                .build();

    }

    protected void initDrawerItem() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initView();

        initDrawer(savedInstanceState);


    }

    protected AccountHeaderBuilder initHeaderBulider() {
        return null;
    }

}
