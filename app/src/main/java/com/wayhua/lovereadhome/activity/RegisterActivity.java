package com.wayhua.lovereadhome.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.wayhua.framework.base.BaseActivityToolBar;
import com.wayhua.framework.util.ToastUtils;
import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.bean.XUser;

import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivityToolBar implements View.OnClickListener {

    @Override
    protected int getMainLayoutView() {
        return R.layout.activity_register;
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        setActionBarCenterTitle(R.string.register2);
    }

    EditText edt_account;
    EditText edt_xm;
    EditText edt_password;
    EditText edt_password2;
    EditText edt_email;
    Button reg_btn;

    @Override
    public void initView() {
        super.initView();
        edt_account = (EditText) findViewById(R.id.edt_account);
        edt_xm = (EditText) findViewById(R.id.edt_xm);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_password2 = (EditText) findViewById(R.id.edt_password2);
        reg_btn = (Button) findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(edt_account.getText().toString())) {
            ToastUtils.show(this, R.string.accountNoNull);
            return;
        }
        if (TextUtils.isEmpty(edt_xm.getText().toString())) {
            ToastUtils.show(this, R.string.xmNoNull);
            return;
        }
        if (TextUtils.isEmpty(edt_password.getText().toString())) {
            ToastUtils.show(this, R.string.pswNoNull);
            return;
        }

        if (!TextUtils.equals(edt_password.getText().toString(),
                edt_password2.getText().toString())) {
            ToastUtils.show(this, R.string.pswNoEquals);
            return;
        }

        if (TextUtils.isEmpty(edt_email.getText().toString())) {
            ToastUtils.show(this, R.string.emailNoNull);
            return;
        }


        XUser user = new XUser();
        user.setUsername(edt_account.getText().toString());
        user.setRealName(edt_xm.getText().toString());
        user.setPassword(edt_password.getText().toString());
        user.setEmail(edt_email.getText().toString());

        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.show(RegisterActivity.this, R.string.registerSuccess);
                RegisterActivity.this.finish();
            }

            @Override
            public void onFailure(int i, String s) {
                if (i == 202) {
                    ToastUtils.show(RegisterActivity.this, "用户名已存在！请重新注册用户名");
                } else {
                    String s1 = "注册失败！[错误代码(" + i + ")错误信息：" + s + "]";
                    ToastUtils.show(RegisterActivity.this, s1);
                }
            }
        });
    }
}