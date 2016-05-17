package com.wayhua.framework.base;

import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.wayhua.framework.R;
import com.wayhua.framework.interf.IInitToolBar;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/3/21.
 */
public abstract class BaseActivityCollapsingToolbar extends BaseActivity   implements IInitToolBar {
    public static final String TITLENAME = "titlename";

    protected Toolbar toolbar;
    protected CollapsingToolbarLayout collapsingToolbar;
    protected ImageView imageView;
    protected LinearLayout baseLayout;
    protected FloatingActionButton fab;

    @Override
    public void initView() {
        super.initView();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        baseLayout = (LinearLayout) findViewById(R.id.contentbasecollapsingtoolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        imageView = (ImageView) findViewById(R.id.backdrop);


        setMainContentView(getMainLayoutView());
        initActionBar();
    }

    protected abstract int getMainLayoutView();

    public void setMainContentView(int paramInt) {
        LayoutInflater.from(this).inflate(paramInt, this.baseLayout);
    }

    public void setFabImage(int imageid) {
        fab.setImageResource(imageid);
    }
    public void setFabImage(Drawable drawable) {
       fab.setImageDrawable(drawable);
    }


    @Override
    protected void doSetContentView() {
        super.doSetContentView();
        super.setContentView(R.layout.activity_base_collapsing_toolbar);
    }


//    @Override
//    public void initActionBar() {
//       setActionBarTitle(R.string.app_name, R.mipmap.img_slide_1);
//    }

    /**
     * 设置标题
     */
    protected void setActionBarTitle(int titleId, int imgid) {
        String title = getString(titleId);

        collapsingToolbar.setTitle(title);
        Glide.with(this).load(imgid)
                .centerCrop().into(imageView);

    }


    protected void setActionBarTitle(String title, int imgid) {
        collapsingToolbar.setTitle(title);
        Glide.with(this).load(imgid).centerCrop().into(imageView);
    }

    protected void setActionBarTitle(int titleId, String imgurl) {
        String title = getString(titleId);
        collapsingToolbar.setTitle(title);
        Glide.with(this).load(imgurl).centerCrop().into(imageView);

    }


    protected void setActionBarTitle(String title, String imgurl) {
        collapsingToolbar.setTitle(title);
        Glide.with(this).load(imgurl).centerCrop().into(imageView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
