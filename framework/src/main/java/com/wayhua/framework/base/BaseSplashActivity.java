package com.wayhua.framework.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wayhua.framework.Constants;
import com.wayhua.framework.R;
import com.wayhua.framework.util.SharedPreferencesUtils;

import java.io.File;

public abstract class BaseSplashActivity extends BaseActivity {

    private static final int SPLASH_DURATION = 2500;

    private ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if ((boolean) SharedPreferencesUtils.get(this, Constants.PREF_FIRST_USE, true)) {
            startActivity(openWelcomeScreen());
            finish();
            return;
        }
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_base_splash);
        splash = (ImageView) findViewById(R.id.splash);

        Constants.splashPath= Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/"
                +((Context)this).getPackageName()+"/splash/";
        loadSplash();

    }

    protected void loadSplash() {

        File splashFile = new File(Constants.splashPath,
                Constants.splashFileName);

        if (splashFile.exists()) {
            Glide.with(this).load(splashFile)//.crossFade(1500)
                    .animate(R.anim.splash_anim)
                    .into(splash);
            startAppDelay();
            return;
        }

        Glide.with(this).load(R.mipmap.splash)//.crossFade(1500)
                .animate(R.anim.splash_anim)
                .into(splash);
        startAppDelay();
    }

    protected void startAppDelay() {


        splash.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, SPLASH_DURATION);

    }

    protected void startApp() {
        Intent intent = getJumpItent();
        // 如果第一次，则进入引导页WelcomeActivity

        startActivity(intent);
        overridePendingTransition(android.support.v7.appcompat.R.anim.abc_grow_fade_in_from_bottom, android.support.v7.appcompat.R.anim.abc_shrink_fade_out_from_bottom);
        finish();
    }

    protected abstract Intent openWelcomeScreen();

    protected abstract Intent getJumpItent();

}
