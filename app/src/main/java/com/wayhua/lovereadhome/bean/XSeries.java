package com.wayhua.lovereadhome.bean;


import java.io.Serializable;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/28.
 */
public class XSeries implements Serializable {
    private String id;

    private String title;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

}
