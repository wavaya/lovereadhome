package com.wayhua.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wayhua.framework.R;


/**
 * Created by Administrator on 2015/12/3.
 */
public class SettingItemView extends LinearLayout {

    private ImageView iv_left;

    private ImageView iv_right;
    private View mView;
    //private RelativeLayout rl_base;

    private TextView tv_content;
    private TextView tv_name;


    public SettingItemView(Context context) {
        super(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        getViewByInflater(context);
        if (attrs != null) {
            TypedArray localTypedArray = context.obtainStyledAttributes(
                    attrs,  R.styleable.SettingItemView);
            Drawable localDrawable1 = localTypedArray.getDrawable(R.styleable.SettingItemView_leftImg);//left图
            CharSequence localCharSequence1 = localTypedArray.getText(R.styleable.SettingItemView_titleText);//itemtext 左标题

            Drawable localDrawable2 = localTypedArray.getDrawable(R.styleable.SettingItemView_rightImg);//右图

            Integer localColor = localTypedArray.getColor(R.styleable.SettingItemView_contentColor, getResources().getColor( R.color.font_color));
            Integer leftTextColor = localTypedArray.getColor(R.styleable.SettingItemView_titleColor, getResources().getColor( R.color.font_color));


            localTypedArray.recycle();
            this.iv_left.setImageDrawable(localDrawable1);
            if (localDrawable1 == null)
                this.iv_left.setVisibility(View.GONE);

            if (localCharSequence1 == null)
                this.tv_name.setText("");
            else
                this.tv_name.setText(localCharSequence1);

            if (localDrawable2 == null)
                this.iv_right.setVisibility(View.INVISIBLE);
            this.iv_right.setImageDrawable(localDrawable2);
            if (localColor == null) {
                tv_content.setTextColor(getResources().getColor(
                         R.color.font_color));
            } else {
                tv_content.setTextColor(localColor);
            }
            if (leftTextColor == null) {
                tv_name.setTextColor(getResources()
                        .getColor( R.color.font_color));
            } else {
                tv_name.setTextColor(leftTextColor);
            }
        }

        setFocusable(false);
        setFocusableInTouchMode(false);
        return;

    }

    public String getContentText() {
        return this.tv_content.getText().toString();
    }

    public void setContentText(String paramString) {
        this.tv_content.setText(paramString);
      //  this.tv_content.setVisibility(View.VISIBLE);
    }

    private void getViewByInflater(Context paramContext) {
        mView = LayoutInflater.from(paramContext).inflate(
                 R.layout.setting_item_view, this, true);

        iv_left = ((ImageView) findViewById( R.id.iv_left));
        tv_name = ((TextView) findViewById( R.id.tv_name));
       // rl_base = ((RelativeLayout) findViewById(com.xmobile.xframework.R.id.rl_base));
        tv_content = ((TextView) findViewById( R.id.tv_content));
        iv_right = ((ImageView) findViewById( R.id.iv_right));
    }


}
