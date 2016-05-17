package com.wayhua.lovereadhome.activity;

import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.fragment.ReadLogFragment;
import com.wayhua.framework.base.BaseActivityPagedItemList;

public class ReadLogActivity extends BaseActivityPagedItemList<ReadLogFragment> {

    @Override
    public void initActionBar() {
        super.initActionBar();
        setActionBarCenterTitle(R.string.rdls);
    }

    @Override
    protected ReadLogFragment createFragment() {
        return new ReadLogFragment();
    }
}
