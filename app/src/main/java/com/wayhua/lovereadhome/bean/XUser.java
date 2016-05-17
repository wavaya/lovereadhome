package com.wayhua.lovereadhome.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/2.
 */
public class XUser extends BmobUser {
    private String QQ;
    private String address;
    private int age;

    private int tag;
    private int tag2;
    private String tags;
    private String tags2;

    private String realName;

    private BmobFile pic;

    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
