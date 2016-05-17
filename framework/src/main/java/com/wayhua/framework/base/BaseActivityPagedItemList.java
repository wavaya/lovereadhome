package com.wayhua.framework.base;

import android.os.Bundle;

import com.wayhua.framework.R;

/**
 * Created by Administrator on 2016/3/15.
 */
public abstract class BaseActivityPagedItemList<V extends BaseFragmentPagedItemList> extends BaseActivityToolBar {

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        V fragment = createFragment();
        Bundle bundle = new Bundle();
        setBundles(bundle);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rl, fragment).commit();
    }

    protected void setBundles(Bundle bundle) {

    }


    protected abstract V createFragment();

    @Override
    protected int getMainLayoutView() {
        return R.layout.activity_paged_item_list;
    }
}
