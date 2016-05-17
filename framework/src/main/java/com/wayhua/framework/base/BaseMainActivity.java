package com.wayhua.framework.base;


import android.view.KeyEvent;

import com.wayhua.framework.Constants;
import com.wayhua.framework.util.DoubleClickExitHelper;
import com.wayhua.framework.util.SharedPreferencesUtils;


/**
 * Created by 黄卫华(wayhua@126.com) on 2016/1/28.
 */
public abstract class BaseMainActivity extends BaseActivityToolBar {

    private DoubleClickExitHelper mDoubleClickExit;



    @Override
    public void initView() {
        super.initView();
        mDoubleClickExit = new DoubleClickExitHelper(this);

    }



    /**
     * 监听返回--是否退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否退出应用
            if ((boolean) SharedPreferencesUtils.get(this, Constants.KEY_DOUBLE_CLICK_EXIT, true)) {
                return mDoubleClickExit.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
