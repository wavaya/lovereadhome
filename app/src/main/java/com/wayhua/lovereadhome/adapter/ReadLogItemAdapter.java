package com.wayhua.lovereadhome.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.bean.XReadLog;
import com.wayhua.framework.adapter.SingleTypeAdapter;
import com.wayhua.framework.util.TimeUtils;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/4.
 */
public class ReadLogItemAdapter extends SingleTypeAdapter<XReadLog> {
    Context context;

    public ReadLogItemAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.context = context;
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{
                R.id.textView_time//时间
                , R.id.tv_book_name//书名
                , R.id.book_author//作者
                , R.id.image//图片
                , R.id.book_content//摘要
        };
    }

    @Override
    protected void update(int position, XReadLog item) {
        String s = TimeUtils.friendly_time(item.getReadDate().getDate());
        textView(0).setText(s);
        textView(1).setText(item.getTitle());
        textView(2).setText(item.getAuthor());

        String url = "";
        if (null != item.getImage())
            url = item.getImage();
        Glide.with(context).load(url).into(imageView(3));
        textView(4).setText(item.getSummary());
    }
}
