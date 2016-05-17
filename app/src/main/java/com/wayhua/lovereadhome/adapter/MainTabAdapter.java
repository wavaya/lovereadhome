package com.wayhua.lovereadhome.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.fragment.MainFragment;
import com.wayhua.lovereadhome.fragment.ReadBookFragment;
import com.wayhua.framework.adapter.FragmentPagerAdapter;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/4.
 */
public class MainTabAdapter extends FragmentPagerAdapter {


    public MainTabAdapter(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return activity.getString(R.string.readbook);
            case 1:
                return activity.getString(R.string.allbooks);

            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReadBookFragment();
            case 1:
                return new MainFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
