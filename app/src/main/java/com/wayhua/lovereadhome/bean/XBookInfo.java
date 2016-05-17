package com.wayhua.lovereadhome.bean;


import android.content.Context;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/28.
 */
public class XBookInfo extends BmobObject implements Serializable {
    public static String DEFAULT = "default";
    public static String LIBRARY = "library";
    /***
     * 属于哪个帐号
     */
    private String account;
    /***
     * 图书类别
     * default :为默认当前类别
     * 其他类别如：图书馆 library
     */
    private String booktype;
    /***
     * 阅读次数
     */
    private int readCount;


    public static XBookInfo createDefault(Context context) {
        XBookInfo bookInfo = createBookInfo(context);
        bookInfo.booktype = DEFAULT;
        return bookInfo;
    }

    public static XBookInfo createLibrary(Context context) {
        XBookInfo bookInfo = createBookInfo(context);
        bookInfo.booktype = DEFAULT;
        return bookInfo;
    }

    @NonNull
    public static XBookInfo createBookInfo(Context context) {
        XBookInfo bookInfo = new XBookInfo();
        XUser current = BmobUser.getCurrentUser(context, XUser.class);
        bookInfo.account = current.getUsername();
        return bookInfo;
    }

    private String id;

    private String isbn10;

    private String isbn13;

    private String title;

    private String origin_title;

    private String alt_title;

    private String subtitle;

    private String url;

    private String alt;

    private String image;

    private XImages images;

    private List<String> author;

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    public List<XTags> getTags() {
        return tags;
    }

    public void setTags(List<XTags> tags) {
        this.tags = tags;
    }

    private List<String> translator;

    private String publisher;

    private String pubdate;

    private XRating rating;

    private List<XTags> tags;

    private String binding;

    private String price;

    private XSeries series;

    private String pages;

    private String author_intro;

    private String summary;

    private String catalog;

    private String ebook_url;

    private String ebook_price;



    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn10() {
        return this.isbn10;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getIsbn13() {
        return this.isbn13;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getOrigin_title() {
        return this.origin_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAlt_title() {
        return this.alt_title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getAlt() {
        return this.alt;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setImages(XImages images) {
        this.images = images;
    }

    public XImages getImages() {
        return this.images;
    }


    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPubdate() {
        return this.pubdate;
    }

    public void setRating(XRating rating) {
        this.rating = rating;
    }

    public XRating getRating() {
        return this.rating;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getBinding() {
        return this.binding;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return this.price;
    }

    public void setSeries(XSeries series) {
        this.series = series;
    }

    public XSeries getSeries() {
        return this.series;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPages() {
        return this.pages;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getAuthor_intro() {
        return this.author_intro;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getCatalog() {
        return this.catalog;
    }

    public void setEbook_url(String ebook_url) {
        this.ebook_url = ebook_url;
    }

    public String getEbook_url() {
        return this.ebook_url;
    }

    public void setEbook_price(String ebook_price) {
        this.ebook_price = ebook_price;
    }

    public String getEbook_price() {
        return this.ebook_price;
    }
    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

}
