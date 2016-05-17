package com.wayhua.framework.retrofit;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.wayhua.framework.gson.GsonUtils;
import com.wayhua.framework.util.Logs;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/2/21.
 */
public class RetrofitUtil {
    private static int REQ_TIMEOUT = 10;
    private static String BASE_URL;

    public static void setBASE_URL(String base_url) {
        BASE_URL = base_url;
    }

    public static Retrofit getRetrofit() {
        if (TextUtils.isEmpty(BASE_URL)) {
            Logs.e("没有配置Retrofit的BaseUrl，请在APPlication的onCreate中进行配置");
            return null;
        }
        return getRetrofit(BASE_URL);
    }

    public static Retrofit getRetrofit(String baseUrl) {

        Retrofit.Builder restrofitBuilder = buildRetrofitBuilder(baseUrl);

        OkHttpClient client = buildOkHttpClient();

        restrofitBuilder.client(client);

        return restrofitBuilder.build();
    }


    @NonNull
    private static Retrofit.Builder buildRetrofitBuilder(String baseUrl) {
        Gson gson = GsonUtils.getGson();
        return new Retrofit.Builder().baseUrl(baseUrl)

                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .addConverterFactory(GsonConverterFactory.create(gson));
    }

    @NonNull
    public static OkHttpClient buildOkHttpClient() {


        OkHttpClient.Builder httpClient = buildOkHttpClientBuilder();


        httpClient.connectTimeout(REQ_TIMEOUT, TimeUnit.SECONDS);
        httpClient.connectTimeout(REQ_TIMEOUT, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();


        return client;
    }

    @NonNull
    private static OkHttpClient.Builder buildOkHttpClientBuilder() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!


        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Customize the request
                Request request = original.newBuilder()
                        .header("Accept", "application/json")

                        .build();

                Response response = chain.proceed(request);

                // Customize or return the response
                return response;
            }
        });
        return httpClient;
    }


}
