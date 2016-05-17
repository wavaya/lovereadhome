package com.wayhua.framework.base;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.wayhua.framework.interf.FragmentProvider;

/**
 * Created by  黄卫华 on 2016/1/20.
 */
public  abstract class BaseActivityPager extends BaseActivity  implements
    ViewPager.OnPageChangeListener
    {

        private boolean menuCreated;

        /**
         * Get provider of the currently selected fragment
         *
         * @return fragment provider
         */
    protected abstract FragmentProvider getProvider();

    /**
     * Get selected fragment
     *
     * @return fragment
     */
    protected Fragment getFragment() {
        FragmentProvider provider = getProvider();
        if (provider != null)
            return provider.getSelected();
        else
            return null;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Fragment fragment = getFragment();
        if (fragment != null)
            return fragment.onOptionsItemSelected(item);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void invalidateOptionsMenu() {
        if (menuCreated)
            super.invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Fragment fragment = getFragment();
        if (fragment != null)
            fragment.onCreateOptionsMenu(menu, getMenuInflater());

        boolean created = super.onCreateOptionsMenu(menu);
        menuCreated = true;
        return created;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Fragment fragment = getFragment();
        if (fragment != null)
            fragment.onPrepareOptionsMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // Intentionally left blank
    }

    @Override
    public void onPageSelected(int position) {
        invalidateOptionsMenu();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // Intentionally left blank
    }
}
