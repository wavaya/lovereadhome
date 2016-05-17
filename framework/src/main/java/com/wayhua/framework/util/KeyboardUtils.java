package com.wayhua.framework.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by 黄卫华(danny) on 2015/9/14.
 */
public class KeyboardUtils {
    public static void hideKeyBoard(Context context, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    // 关闭输入法
    public static void hideInputMethodWindow(Activity act) {
        InputMethodManager imm = (InputMethodManager) act
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }


    public static void HintAutoHide(EditText editText){

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                EditText _v=(EditText)v;
                if (!hasFocus) {// 失去焦点
                    _v.setHint(_v.getTag().toString());
                } else {
                    String hint=_v.getHint().toString();
                    _v.setTag(hint);
                    _v.setHint("");
                }
            }
        });
    }
}
