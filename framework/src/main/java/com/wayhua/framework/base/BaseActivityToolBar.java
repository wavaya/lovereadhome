package com.wayhua.framework.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wayhua.framework.R;
import com.wayhua.framework.interf.IInitToolBar;
import com.wayhua.framework.interf.IMainContentView;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/2/18.
 * 带ToolBar的Activity
 */
public abstract class BaseActivityToolBar extends BaseActivity
        implements IInitToolBar, IMainContentView {
    protected LinearLayout baseLayout;
    protected Toolbar toolbar;
    protected ActionBar actionBar;
    protected TextView toolbar_title;

    @Override
    protected void doSetContentView() {
        super.doSetContentView();
        super.setContentView(R.layout.activity_base_toolbar);
    }


    @Override
    public void initView() {
        super.initView();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        baseLayout = (LinearLayout) findViewById(R.id.contentbasetoolbar);


        // On Lollipop, the action bar shadow is provided by default, so have to remove it explicitly
        actionBar.setElevation(0);

        setMainContentView(getMainLayoutView());
        initActionBar();


    }

    /**
     * 设置标题
     */
    protected void setActionBarTitle(int id) {
        actionBar.setTitle(id);
        toolbar_title.setVisibility(View.GONE);
    }


    protected void setActionBarTitle(String title) {
        actionBar.setTitle(title);
        toolbar_title.setVisibility(View.GONE);
    }

    protected void setActionBarCenterTitle(int id) {
        actionBar.setTitle("");
        toolbar_title.setText(id);
    }

    protected void setActionBarCenterTitle(String title) {
        actionBar.setTitle("");
        toolbar_title.setText(title);
    }

    protected void setHomeAsUpIndication(int id) {

        actionBar.setHomeAsUpIndicator(id);

    }

    public void initActionBar() {
        // actionBar.setHomeAsUpIndicator( R.drawable.abc_ic_ab_back_material);
        //actionBar.setHomeAsUpIndicator( R.drawable.abc_ic_ab_back_mtrl_am_alpha);


        //  actionBar.setHomeAsUpIndicator(XUtils.back_material_white(this));
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected abstract int getMainLayoutView();

    public void setMainContentView(int paramInt) {
        LayoutInflater.from(this).inflate(paramInt, this.baseLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
