package com.wayhua.lovereadhome.bean;


import java.io.Serializable;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/28.
 */
public class XTags implements Serializable {
    private int count;

    private String name;

    private String title;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
}
