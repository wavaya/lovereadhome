package com.wayhua.framework.base;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.wayhua.framework.Constants;
import com.wayhua.framework.R;
import com.wayhua.framework.util.SharedPreferencesUtils;
import com.wayhua.framework.view.ViewPager;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/1/22.
 */
public abstract class BaseWelcomeActivity extends BaseActivityDotPager {
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferencesUtils.put(BaseWelcomeActivity.this, Constants.PREF_FIRST_USE, false);

            startActivity(closeAndOpenNextActivity());
            finish();
        }
    };

    protected abstract Intent closeAndOpenNextActivity();


    private int[] colors;
    @ColorInt
    int accentsColorDark;
    @ColorInt int accentsColorLight = Color.parseColor("#54FFFFFF");
    @ColorInt int darkDividerColor = Color.parseColor("#24000000");

    private View divider;
    private Button doneBtn;
    private Button skipBtn;
    private ImageButton nextBtn;

    /**
     * Find views and do needed initialization
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureDotPager();
        loadColors();

        doneBtn = (Button) findViewById(R.id.btn_done);
        skipBtn = (Button) findViewById(R.id.btn_skip);
        nextBtn = (ImageButton) findViewById(R.id.btn_next);
        divider = findViewById(R.id.divider);

        skipBtn.setOnClickListener(onClickListener);
        doneBtn.setOnClickListener(onClickListener);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager pager = getViewPager();
                if (pager.getCurrentItem() + 1 < adapter.getCount()) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1, true);
                }else {
                    configureDotPager();
                }
            }
        });
    }


    /**
     * "Removes" listeners
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        findViewById(R.id.btn_skip).setOnClickListener(null);
        findViewById(R.id.btn_done).setOnClickListener(null);
    }

    /**
     * Update background color depending on scroll position
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        @ColorInt int color;
        @ColorInt int accentsColor;
        if(positionOffsetPixels >= 0) {
            color = blendColors(getBackgroundColor(position), getBackgroundColor(position + 1), (1 - positionOffset));
            accentsColor = blendColors(getAccentsColor(position), getAccentsColor(position +1), (1 - positionOffset));
        } else {
            color = blendColors(getBackgroundColor(position + 1), getBackgroundColor(position), (1 - positionOffset));
            accentsColor = blendColors(getAccentsColor(position + 1), getAccentsColor(position), (1 - positionOffset));
        }

        setBackgroundColor(color);
        skipBtn.setTextColor(accentsColor);
        nextBtn.setColorFilter(accentsColor);
        divider.setBackgroundColor(accentsColor);
        dotPageIndicator.setSelectedDotColor(accentsColor);
    }

    /**
     * Show/hide buttons depending on page
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        if(getViewPager().getCurrentItem() == adapter.getCount() - 1) {
            doneBtn.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.GONE);
            skipBtn.setVisibility(View.GONE);
        }else{
            doneBtn.setVisibility(View.GONE);
            nextBtn.setVisibility(View.VISIBLE);
            skipBtn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @return Layout id of the activity content view
     */
    @Override
    public int getContentView() {
        return R.layout.welcome_activity;
    }

    /**
     * Loads the color array
     */
    private void loadColors() {
        TypedArray ta = getResources().obtainTypedArray(R.array.welcome_screen_colors);
        colors = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            colors[i] = ta.getColor(i, 0);
        }
        ta.recycle();
        accentsColorDark = ContextCompat.getColor(this, R.color.colorPrimary);
    }

    /**
     * Safe way to get colors from the color array
     * @param pos
     * @return The color
     */
    private int getBackgroundColor(int pos){
        if(pos < colors.length)
            return colors[pos];
        else
            return colors[colors.length - 1];
    }

    private int getAccentsColor(int pos) {
        if (pos == 0) {
            return accentsColorDark;
        } else {
            return accentsColorLight;
        }
    }

    private int divider(int pos) {
        if (pos == 0) {
            return darkDividerColor;
        } else {
            return accentsColorLight;
        }
    }

    /**
     * Sets the view background color
     * @param color
     */
    private void setBackgroundColor(int color){
        findViewById(R.id.container).setBackgroundColor(color);
    }

    /**
     * Blends two colors with a specified ratio
     *
     * (Borrowed form http://developer.android.com/samples/SlidingTabsColors/src/com.example.android.common/view/SlidingTabStrip.html)
     *
     * @param color1
     * @param color2
     * @param ratio
     * @return The blended colors
     */
    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.rgb((int) r, (int) g, (int) b);
    }


}
