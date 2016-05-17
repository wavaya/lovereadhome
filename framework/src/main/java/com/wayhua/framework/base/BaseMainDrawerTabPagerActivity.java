package com.wayhua.framework.base;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wayhua.framework.R;
import com.wayhua.framework.interf.FragmentProvider;
import com.wayhua.framework.interf.IInitToolBar;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/4.
 */
public abstract class BaseMainDrawerTabPagerActivity<V extends PagerAdapter & FragmentProvider>
        extends BaseMainDrawerActivity implements View.OnClickListener,
        IInitToolBar {
    protected TabLayout tabLayout;
    /**
     * Pager adapter
     */
    protected V adapter;

    @Override
    public void initView() {
        super.initView();


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    protected int getMainLayoutView() {
        return R.layout.main_tab_content;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = createAdapter();

        viewPager.setAdapter(adapter);
    }

    /**
     * Create pager adapter
     *
     * @return pager adapter
     */
    protected abstract V createAdapter();




    @Override
    public void onClick(View v) {

    }
}
