package com.wayhua.framework.retrofit;

import retrofit2.Retrofit;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/1/29.
 */
public class RetrofitService {

    Retrofit retrofit;
    private static RetrofitService service;

    private RetrofitService() {
        retrofit = RetrofitUtil.getRetrofit();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }


    public static RetrofitService instance() {

        if (service == null) {
            service = new RetrofitService();
        }
        return service;
    }


}
