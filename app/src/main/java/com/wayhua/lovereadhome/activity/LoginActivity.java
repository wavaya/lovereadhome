package com.wayhua.lovereadhome.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.wayhua.framework.base.BaseActivityToolBar;
import com.wayhua.framework.util.KeyboardUtils;
import com.wayhua.framework.util.SharedPreferencesUtils;
import com.wayhua.framework.util.ToastUtils;
import com.wayhua.lovereadhome.API;
import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.SessionManage;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends BaseActivityToolBar implements View.OnClickListener {
    public static final String LAST_ACCOUNT = "LastAccount";

    EditText user_accout;
    EditText edt_password;
    TextView register_text;
    Button btn_login;

    @Override
    protected int getMainLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        setActionBarCenterTitle(R.string.app_name);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }


    @Override
    public void initView() {
        super.initView();
        user_accout = (EditText) findViewById(R.id.user_accout);
        edt_password = (EditText) findViewById(R.id.edt_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        register_text = (TextView) findViewById(R.id.register_text);

        btn_login.setOnClickListener(this);

        register_text.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);


        String account = (String) SharedPreferencesUtils.get(this, LAST_ACCOUNT, "");
        user_accout.setText(account);
    }

    @Override
    public void destoryView() {
        super.destoryView();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.register_text:
                doRegister();
                break;
        }
    }

    private void doLogin() {

        KeyboardUtils.hideInputMethodWindow(this);
        final String useraccount = user_accout.getText().toString();
        if (TextUtils.isEmpty(useraccount)) {
            ToastUtils.show(this, R.string.accountNoNull);
            return;
        }
        String psw = edt_password.getText().toString();
        if (TextUtils.isEmpty(psw)) {
            ToastUtils.show(this, R.string.pswNoNull);
            return;
        }

        final BmobUser bu2 = new BmobUser();
        bu2.setUsername(useraccount);
        bu2.setPassword(psw);
        bu2.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                SessionManage.setIsLogin(LoginActivity.this, true);
                SessionManage.setAccount(LoginActivity.this, useraccount);

                Intent intent = API.getIntentLogined(LoginActivity.this);
                SharedPreferencesUtils.put(LoginActivity.this, LAST_ACCOUNT, useraccount);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.show(LoginActivity.this, R.string.loginerror);
            }
        });

    }


    private void doRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
