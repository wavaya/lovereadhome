package com.wayhua.framework.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;


import com.wayhua.framework.R;
import com.wayhua.framework.event.CloseAllEvent;

import de.greenrobot.event.EventBus;


/***
 * 双击事件工具类
 */
public class DoubleClickExitHelper {

    private final Activity mActivity;

    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;

    public DoubleClickExitHelper(Activity activity) {
        mActivity = activity;
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Activity onKeyDown事件
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable);
            if (mBackToast != null) {
                mBackToast.cancel();
            }
            // 退出

            EventBus.getDefault().post(new CloseAllEvent());

            return true;
        } else {
            isOnKeyBacking = true;
            if (mBackToast == null) {
                String s = mActivity.getString(R.string.tip_double_click_exit);
                s += mActivity.getString(R.string.app_name);
                mBackToast = Toast.makeText(mActivity, s, Toast.LENGTH_LONG);
            }
            mBackToast.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }

    private Runnable onBackTimeRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBacking = false;
            if (mBackToast != null) {
                mBackToast.cancel();
            }
        }
    };
}
