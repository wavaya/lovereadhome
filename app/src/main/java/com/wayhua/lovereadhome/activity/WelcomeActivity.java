package com.wayhua.lovereadhome.activity;


import android.content.Intent;
import android.support.v4.view.PagerAdapter;

import com.wayhua.lovereadhome.API;
import com.wayhua.lovereadhome.R;
import com.wayhua.framework.adapter.WelcomePagerAdapter;
import com.wayhua.framework.base.BaseWelcomeActivity;

public class WelcomeActivity extends BaseWelcomeActivity {

    @Override
    protected Intent closeAndOpenNextActivity() {
        return API.getJumpItent(this);
    }

    @Override
    protected PagerAdapter createAdapter() {
        return new WelcomePagerAdapter(this, new int[]{R.drawable.waving_android,
                R.drawable.welcome_app_icon,
                R.drawable.thumb_up},
                getResources().getStringArray(R.array.welcome_texts),
                getResources().getStringArray(R.array.welcome_titles));
    }
}
