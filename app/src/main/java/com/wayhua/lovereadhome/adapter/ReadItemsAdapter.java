package com.wayhua.lovereadhome.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.wayhua.doubanapi.models.BookInfo;
import com.wayhua.lovereadhome.R;

import com.wayhua.framework.adapter.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/4.
 */
public class ReadItemsAdapter extends SingleTypeAdapter<BookInfo> {
    Context context;

    public ReadItemsAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.context = context;
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{
                R.id.item_grida_image
                , R.id.item_grida_bt //删除
        };
    }

    @Override
    protected void update(int position, final BookInfo item) {
        String url = "";
        if (null != item.getImage())
            url = item.getImage();
        Glide.with(context).load(url).into(imageView(0));
        Button button = view(1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BookInfo> infos = new ArrayList<BookInfo>(getItems());//ReadItemsAdapter.this.getItems();
                infos.remove(item);
                setItems(infos);
            }
        });
    }


}
