package com.wayhua.framework.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wayhua.framework.util.Logs;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/4/19.
 * <p/>
 * 主要用于下载图片，带有glide功能，只要传入Src就可以了。
 * 下载的时候会显示进度条。
 * <com.xmobile.xframework.view.XImageProcessView
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:id="@+id/processImageView"
 * android:visibility="visible"
 * app:default_src="@mipmap/mr"
 * app:showPercent="true"
 * />
 * <p/>
 * default_src: 为默认图片，要未加载完成时显示
 * showPercent:用于标示是否显示进度条
 */
public class XImageProcessView extends XImageView {
    public XImageProcessView(Context context) {
        super(context);
    }

    public XImageProcessView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XImageProcessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setSrc(String url) {
        handler.sendEmptyMessage(START);
        Glide.with(getContext())
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                        Logs.e(e);
                        handler.sendEmptyMessage(SUCCESS);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        handler.sendEmptyMessage(SUCCESS);
                        return false;
                    }
                })
                .placeholder(default_src)
                .into(image);
    }

    private final int SUCCESS = 0;
    private final int START = 1;
    private final int UPDATE = 2;
    int progress = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    isStop = true;
                    processView.setVisibility(View.GONE);
                    break;
                case START:
                    isStop = false;
                    processView.setVisibility(View.VISIBLE);
                    thread.start();
                    break;
                case UPDATE:
                    processView.setProgress(progress);
                    break;
            }
        }
    };

    //模拟图片上传进度
    boolean isStop = false;
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (isStop) {
                    return;
                }
                if (progress == 100) {//图片上传完成
                    progress = 0;
                }
                progress += 10;
                Log.e("process", "" + progress);
                handler.sendEmptyMessage(UPDATE);
                try {
                    Thread.sleep(200);  //暂停0.2秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });


}
