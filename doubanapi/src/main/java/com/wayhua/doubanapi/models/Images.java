package com.wayhua.doubanapi.models;

import java.io.Serializable;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/28.
 */
public class Images implements Serializable {
    private String small;

    private String large;

    private String medium;

    public void setSmall(String small){
        this.small = small;
    }
    public String getSmall(){
        return this.small;
    }
    public void setLarge(String large){
        this.large = large;
    }
    public String getLarge(){
        return this.large;
    }
    public void setMedium(String medium){
        this.medium = medium;
    }
    public String getMedium(){
        return this.medium;
    }

}
