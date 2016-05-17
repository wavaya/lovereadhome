package com.wayhua.framework.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wayhua.framework.util.ViewFinder;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/1/28.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * View finder bound to the value last specified to
     * {@link #onViewCreated(View, Bundle)}
     */
    protected ViewFinder finder;

    /**
     * Is this fragment usable from the UI-thread
     *
     * @return true if usable, false otherwise
     */
    protected boolean isUsable() {
        return getActivity() != null;
    }

    /**
     * Get serializable extra from activity's intent
     *
     * @param name
     * @return extra
     */
    @SuppressWarnings("unchecked")
    protected <V extends Parcelable> V getParcelableExtra(final String name) {
        Activity activity = getActivity();
        if (activity != null)
            return (V) activity.getIntent().getParcelableExtra(name);
        else
            return null;
    }

    /**
     * Get string extra from activity's intent
     *
     * @param name
     * @return extra
     */
    protected String getStringExtra(final String name) {
        Activity activity = getActivity();
        if (activity != null)
            return activity.getIntent().getStringExtra(name);
        else
            return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finder = new ViewFinder(view);

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }



    @Override public void onDestroy() {
        super.onDestroy();

    }


}
