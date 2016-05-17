package com.wayhua.doubanapi.service;



import com.wayhua.doubanapi.models.BookInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/28.
 */
public interface DoubanService {
    /**
     * 获取组织/机关类别 返回的名称：名称(如:省机关、合肥市、……)
     * String MessageType = "getOrgType";
     */
    @GET("/v2/book/isbn/{isbn}")
    public Observable<BookInfo> getBookInfoByIsbn(@Path("isbn") String isbn);

}
