package com.wayhua.framework.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/18.
 */
public class ProcessView extends View {

    private Paint mPaint;//画笔

    public boolean isShowPercent() {
        return showPercent;
    }

    public void setShowPercent(boolean showPercent) {
        this.showPercent = showPercent;
    }

    boolean showPercent = false;


    int width, height;

    Context context;

    int progress = 0;

    public ProcessView(Context context) {
        this(context, null);
    }

    public ProcessView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProcessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPaint = new Paint();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setColor(Color.parseColor("#70000000"));//半透明
        canvas.drawRect(0, 0, getWidth(), getHeight() - getHeight() * progress / 100, mPaint);

        mPaint.setColor(Color.parseColor("#00000000"));//全透明
        canvas.drawRect(0, getHeight() - getHeight() * progress / 100, getWidth(), getHeight(), mPaint);

        if (showPercent) {
            mPaint.setTextSize(30);
            mPaint.setColor(Color.parseColor("#FFFFFF"));
            mPaint.setStrokeWidth(2);
            Rect rect = new Rect();
            mPaint.getTextBounds("100%", 0, "100%".length(), rect);//确定文字的宽度
            canvas.drawText(progress + "%", getWidth() / 2 - rect.width() / 2, getHeight() / 2, mPaint);
        }
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }


}
