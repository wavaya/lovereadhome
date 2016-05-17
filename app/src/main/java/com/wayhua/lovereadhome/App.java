package com.wayhua.lovereadhome;

import com.squareup.leakcanary.LeakCanary;
import com.wayhua.framework.base.BaseApplication;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/2.
 */
public class App extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化字体 包
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/lthm.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        //设置BmobConfig
        BmobConfig config =new BmobConfig.Builder()
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setBlockSize(500*1024)
                .build();
        Bmob.getInstance().initConfig(config);
        Bmob.initialize(this, "9f04cfcd3f460f78200af50ff002c274");

        LeakCanary.install(this);
    }
}
