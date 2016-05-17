package com.wayhua.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wayhua.framework.R;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/19.
 */
public class XImageView extends LinearLayout {
    Drawable default_src;
    Drawable img_src;
    ImageView image;
    ProcessView processView;
    boolean showPercent = false;

    public XImageView(Context context) {
        super(context);
        initLayout(context);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ProcessView getProcessView() {
        return processView;
    }

    public void setProcessView(ProcessView processView) {
        this.processView = processView;
    }

    public XImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
        init(context, attrs);
    }

    public XImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
        init(context, attrs);
    }

    private void initLayout(Context context) {
        View mView = LayoutInflater.from(context).inflate(R.layout.x_image_layout, this, true);
        image = (ImageView) mView.findViewById(R.id.image111);
        processView = (ProcessView) mView.findViewById(R.id.processView);

    }

    public void setProgress(int progress) {
        processView.setProgress(progress);
        postInvalidate();
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray localTypedArray = context.obtainStyledAttributes(
                    attrs, R.styleable.x_image_view);
            default_src = localTypedArray.getDrawable(R.styleable.x_image_view_default_src);//默认图
            img_src = localTypedArray.getDrawable(R.styleable.x_image_view_img_src);//实际图
            if (img_src != null) {
                // Glide.with(getContext()).load(img_src).into(image);
                image.setImageDrawable(img_src);
            } else {
                if (default_src != null) {
                    image.setImageDrawable(default_src);
                    //  Glide.with(getContext()).load(default_src).into(image);
                }
            }

            showPercent = localTypedArray.getBoolean(R.styleable.x_image_view_showPercent, false);

            processView.setShowPercent(showPercent);
            localTypedArray.recycle();
        }

    }

}
