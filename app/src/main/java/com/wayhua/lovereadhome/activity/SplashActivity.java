package com.wayhua.lovereadhome.activity;


import android.content.Intent;

import com.wayhua.lovereadhome.API;
import com.wayhua.framework.base.BaseSplashActivity;
public class SplashActivity extends BaseSplashActivity {


    @Override
    protected Intent openWelcomeScreen() {
        return new Intent(this, WelcomeActivity.class);
    }

    @Override
    protected Intent getJumpItent() {
        return API.getJumpItent(this);
    }
}
