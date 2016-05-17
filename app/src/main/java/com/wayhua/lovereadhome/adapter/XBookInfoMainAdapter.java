package com.wayhua.lovereadhome.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.bean.XBookInfo;
import com.wayhua.lovereadhome.util.XUtils;
import com.wayhua.framework.adapter.SingleTypeAdapter;
import com.wayhua.framework.util.Logs;

import java.util.List;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/2.
 */
public class XBookInfoMainAdapter extends SingleTypeAdapter<XBookInfo> {
    Context context;

    public XBookInfoMainAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.context = context;
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{
                R.id.image//图像
                , R.id.tv_book_name //图书名
                , R.id.book_author//作者
                , R.id.book_content//简介
        };
    }

    @Override
    protected void update(int position, XBookInfo item) {
        try {
            String url = "";
            if (null != item.getImages())
                url = item.getImages().getSmall();
            Glide.with(context).load(url).into(imageView(0));

            String title = item.getTitle();

            textView(1).setText(title);

             List<String> authors=item.getAuthor();
            String author= XUtils.combine(authors);
            textView(2).setText(author);

            String content = item.getSummary();
            textView(3).setText(content);

        } catch (Exception e) {
            Logs.e(e);
        }

    }
}
