package com.wayhua.lovereadhome.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/4.
 */
public class XReadLog extends BmobObject implements Serializable {
    //帐号
    private String account;
    //图书ISBN 13位
    private String isbn13;
    //图书名
    private String title;
    //图书作者
    private String author;

    private String image;

    private String summary;

    //阅读时间
    private BmobDate readDate;

    private int tag;
    private int tag2;
    private String tags;
    private String tags2;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BmobDate getReadDate() {
        return readDate;
    }

    public void setReadDate(BmobDate readDate) {
        this.readDate = readDate;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag2() {
        return tag2;
    }

    public void setTag2(int tag2) {
        this.tag2 = tag2;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTags2() {
        return tags2;
    }

    public void setTags2(String tags2) {
        this.tags2 = tags2;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
