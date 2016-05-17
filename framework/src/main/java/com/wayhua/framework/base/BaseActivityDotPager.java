package com.wayhua.framework.base;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;

import com.wayhua.framework.R;
import com.wayhua.framework.interf.FragmentProvider;
import com.wayhua.framework.view.DotPageIndicator;
import com.wayhua.framework.view.ViewPager;


/**
 * Created by  黄卫华 on 2016/1/20.
 */
public abstract class BaseActivityDotPager<V extends PagerAdapter>
        extends BaseActivityPager {

    private ViewPager pager;
    protected DotPageIndicator dotPageIndicator;

    /**
     * Pager adapter
     */
    protected V adapter;

    @Override
    public void onPageSelected(final int position) {
        super.onPageSelected(position);
    }

    /**
     * Create pager adapter
     *
     * @return pager adapter
     */
    protected abstract V createAdapter();


    /**
     * Creates the adapter and passes it to the {@link ViewPager}
     */
    private void createPager() {
        adapter = createAdapter();
        invalidateOptionsMenu();
        pager.setAdapter(adapter);
    }

    /**
     * Creates the pager and passes it to the {@link DotPageIndicator}
     */
    protected void configureDotPager() {
        if (adapter == null) {
            createPager();
            dotPageIndicator.setViewPager(pager);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            // On Lollipop, the action bar shadow is provided by default, so have to remove it explicitly
            getSupportActionBar().setElevation(0);
        }

        pager = (ViewPager) findViewById(R.id.vp_pages);
        pager.addOnPageChangeListener(this);
        dotPageIndicator = (DotPageIndicator) findViewById(R.id.dot_page_indicator);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pager.removeOnPageChangeListener(this);
    }

    @Override
    protected FragmentProvider getProvider() {
        return null;
    }

    public int getContentView() {
        return R.layout.pager_with_dots;
    }

    public ViewPager getViewPager(){
        return pager;
    }
}
