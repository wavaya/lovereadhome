package com.wayhua.doubanapi;


import com.wayhua.doubanapi.models.BookInfo;
import com.wayhua.doubanapi.service.DoubanService;
import com.wayhua.framework.retrofit.RetrofitUtil;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/28.
 */
public class ISBNHelper {
    private static String fromDouban = "https://api.douban.com";
    //"https://api.douban.com/v2/book/isbn/";

    public static Observable<BookInfo> getBookInfoByIsbn(String isbn) {
        Retrofit retrofit = RetrofitUtil.getRetrofit(fromDouban);
        DoubanService service = retrofit.create(DoubanService.class);
        return service.getBookInfoByIsbn(isbn);
    }
}
