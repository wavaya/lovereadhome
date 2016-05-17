package com.wayhua.lovereadhome.fragment;

import android.support.v4.app.Fragment;

import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.adapter.ReadLogItemAdapter;
import com.wayhua.lovereadhome.bean.XReadLog;
import com.wayhua.lovereadhome.dataIterator.ReadLogDataIterator;
import com.wayhua.lovereadhome.pager.ReadLogPager;
import com.wayhua.framework.adapter.SingleTypeAdapter;
import com.wayhua.framework.core.PageIterator;
import com.wayhua.framework.core.ResourcePager;
import com.wayhua.framework.interf.IDataIterator;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadLogFragment extends XBaseFragmentPagedItemList<XReadLog> {

    @Override
    protected ResourcePager<XReadLog> createPager() {
        return new ReadLogPager() {
            @Override
            public PageIterator<XReadLog> createIterator(int page, int size) {
                return new PageIterator(new PageIterator.XListRequest<XReadLog>() {

                    @Override
                    public IDataIterator<List<XReadLog>> execute(int page) {
                        return new ReadLogDataIterator(getContext(), page);
                    }
                }, page);
            }
        };
    }


    @Override
    protected SingleTypeAdapter<XReadLog> createAdapter(List<XReadLog> items) {
        return new ReadLogItemAdapter(getContext(), R.layout.readlog_item);
    }
}
