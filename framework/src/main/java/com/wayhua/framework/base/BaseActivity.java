package com.wayhua.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;

import com.wayhua.framework.NightModelUtils;
import com.wayhua.framework.R;
import com.wayhua.framework.event.ChangeThemeEvent;
import com.wayhua.framework.event.CloseAllEvent;
import com.wayhua.framework.interf.IInitDataInterface;
import com.wayhua.framework.interf.IViewInterface;
import com.wayhua.framework.util.ViewFinder;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/26.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements IViewInterface, IInitDataInterface {
    protected ViewFinder finder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!NightModelUtils.getNightModeSwitch(this)) {
            setTheme(R.style.Theme_XFramework);
        } else {
            setTheme(R.style.Theme_XFramework_Night);
        }
        doSetContentView();
        finder = new ViewFinder(this);

//        //在自己的应用初始Activity中加入如下两行代码
//        RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
//        refWatcher.watch(this);
        EventBus.getDefault().register(this);

        init(savedInstanceState);
        initView();
        initData(savedInstanceState);

    }

    protected void doSetContentView() {
    }


    @Override
    public void initView() {

    }

    @Override
    public void destoryView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    /**
     * Get intent extra
     *
     * @param name
     * @return serializable
     */
    @SuppressWarnings("unchecked")
    protected <V extends Parcelable> V getParcelableExtra(final String name) {
        return (V) getIntent().getParcelableExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return int
     */
    protected int getIntExtra(final String name) {
        return getIntent().getIntExtra(name, -1);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string
     */
    protected String getStringExtra(final String name) {
        return getIntent().getStringExtra(name);
    }

    /**
     * Get intent extra
     *
     * @param name
     * @return string array
     */
    protected String[] getStringArrayExtra(final String name) {
        return getIntent().getStringArrayExtra(name);
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        destoryView();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(ChangeThemeEvent event) {

        this.recreate();
    }

    @Subscribe
    public void onEvent(CloseAllEvent event) {

        this.finish();
    }
}
