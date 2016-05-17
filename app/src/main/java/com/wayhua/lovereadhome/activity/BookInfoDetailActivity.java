package com.wayhua.lovereadhome.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.wayhua.framework.base.BaseActivityCollapsingToolbar;
import com.wayhua.framework.util.Logs;
import com.wayhua.framework.util.ToastUtils;
import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.bean.XBookInfo;
import com.wayhua.lovereadhome.bean.XReadLog;
import com.wayhua.lovereadhome.bmob.BmobManage;
import com.wayhua.lovereadhome.util.XUtils;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.SaveListener;

public class BookInfoDetailActivity extends BaseActivityCollapsingToolbar {
    public static final String BOOKINFO = "BOOKINFO";
    XBookInfo bookInfo;

    TextView tv_book_name;
    TextView book_author;
    TextView book_content;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        bookInfo = (XBookInfo) getIntent().getSerializableExtra(BOOKINFO);
    }

    @Override
    public void initView() {
        super.initView();

        tv_book_name = (TextView) findViewById(R.id.tv_book_name);
        book_author = (TextView) findViewById(R.id.book_author);
        book_content = (TextView) findViewById(R.id.book_content);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReadBooks();
            }


        });
    }

    private void doReadBooks() {
//暂时不做判断，直接阅读
        BmobManage.updateReadCount(this, bookInfo);
        String author = XUtils.combine(bookInfo.getAuthor());

        XReadLog readLog = new XReadLog();
        readLog.setAccount(BmobManage.getCurrent().getUsername());
        readLog.setIsbn13(bookInfo.getIsbn13());
        readLog.setReadDate(new BmobDate(new Date()));
        readLog.setImage(bookInfo.getImage());
        readLog.setTitle(bookInfo.getTitle());
        if (!TextUtils.isEmpty(bookInfo.getSummary()))
            readLog.setSummary(bookInfo.getSummary().substring(0, 100));
        readLog.setAuthor(author);

        readLog.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.show(BookInfoDetailActivity.this, "阅读记录保存成功！");
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.show(BookInfoDetailActivity.this, "阅读记录保存失败！");

            }
        });
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        try {

            String title = bookInfo.getTitle();

            tv_book_name.setText(title);

            List<String> authors = bookInfo.getAuthor();
            String author = XUtils.combine(authors);
            String translator = XUtils.combine(bookInfo.getTranslator());

            if (!TextUtils.isEmpty(translator)) {
                author += "\n\r" + translator;
            }

            book_author.setText(author);

            String content = bookInfo.getSummary();
            book_content.setText(content);

        } catch (Exception e) {
            Logs.e(e);
        }

    }

    @Override
    protected int getMainLayoutView() {
        return R.layout.activity_book_info_detail;
    }

    @Override
    public void initActionBar() {
        setActionBarTitle(bookInfo.getTitle(), bookInfo.getImage());
        setFabImage(R.mipmap.read);
    }
}
