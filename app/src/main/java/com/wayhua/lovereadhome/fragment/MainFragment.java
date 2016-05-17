package com.wayhua.lovereadhome.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;
import com.wayhua.doubanapi.ISBNHelper;
import com.wayhua.doubanapi.models.BookInfo;
import com.wayhua.framework.zxing.activity.XCaptureActivity;
import com.wayhua.lovereadhome.BookInfo2XBookInfo;
import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.activity.BookInfoDetailActivity;
import com.wayhua.lovereadhome.adapter.XBookInfoMainAdapter;
import com.wayhua.lovereadhome.bean.XBookInfo;
import com.wayhua.lovereadhome.bmob.BmobManage;
import com.wayhua.lovereadhome.dataIterator.BookInfoDataIterator;
import com.wayhua.lovereadhome.pager.BookInfoPager;

import com.wayhua.framework.adapter.SingleTypeAdapter;
import com.wayhua.framework.core.PageIterator;
import com.wayhua.framework.core.ResourcePager;
import com.wayhua.framework.interf.IDataIterator;
import com.wayhua.framework.util.Logs;
import com.wayhua.framework.util.ToastUtils;

import java.util.List;

import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainFragment extends XBaseFragmentPagedItemList<XBookInfo> {
    private static int SCANREQUESTCODE = 1034;
    private static final int SCANSUCCESSED = 1035;
    private static final int LOADISBNSUCCESSED = 1036;
    private static final int REFRESH = 1037;
    private static final int DOSAVE2BMOB = 1038;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //扫描事件
                Intent intent = new Intent();
                intent.setClass(MainFragment.this.getActivity(), XCaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANREQUESTCODE);
                //   doSCANSuccessed("9787511270788");
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCANSUCCESSED:
                    String isbn = (String) msg.obj;
                    doSCANSuccessed(isbn);

                    break;
                case LOADISBNSUCCESSED:
                    BookInfo info = (BookInfo) msg.obj;
                    doCheckISbnInBmob(info);

                    break;

                case REFRESH:
                    forceRefresh();
                    break;

                case DOSAVE2BMOB:
                    BookInfo info1 = (BookInfo) msg.obj;
                    doSave(info1);
                    break;
            }
        }

    };

    private void doCheckISbnInBmob(final BookInfo info) {
        BmobManage.checkBookInfo(getContext(), info.getIsbn13(), new CountListener() {
            @Override
            public void onSuccess(int i) {
                if (i > 0) {
                    ToastUtils.show(getActivity(), R.string.bookExisted);
                    return;
                } else {

                    Message msg = new Message();
                    msg.what = DOSAVE2BMOB;
                    msg.obj = info;
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Logs.e(i + " " + s);
                ToastUtils.show(getActivity(), "网络访问失败！请下次重试！");
            }
        });


    }

    private void doSave(BookInfo info) {
        XBookInfo bookInfo = BookInfo2XBookInfo.trans2Default(getContext(), info);
        bookInfo.save(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.show(getActivity(), "保存成功！");
                handler.sendEmptyMessage(REFRESH);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.show(getActivity(), "保存失败！");
            }
        });
    }


    private void doSCANSuccessed(String isbn) {
        Observable<BookInfo> observable = ISBNHelper.getBookInfoByIsbn(isbn);
        observable.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<BookInfo>() {
                    @Override
                    public void call(BookInfo bookInfo) {
                        Message msg = new Message();
                        msg.what = LOADISBNSUCCESSED;
                        msg.obj = bookInfo;
                        handler.sendMessage(msg);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.show(getActivity(), throwable.getMessage());
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 处理扫描结果（在界面上显示）
        if (requestCode == SCANREQUESTCODE) {
            if (resultCode == this.getActivity().RESULT_OK) {
                Bundle bundle = data.getExtras();
                String scanResult = bundle.getString("result");
                Logs.e(scanResult);
                Message msg = new Message();
                msg.what = SCANSUCCESSED;
                msg.obj = scanResult;
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    protected SingleTypeAdapter<XBookInfo> createAdapter(List<XBookInfo> items) {
        return new XBookInfoMainAdapter(getContext(), R.layout.book_info_item);
    }

    @Override
    protected ResourcePager<XBookInfo> createPager() {
        return new BookInfoPager() {
            @Override
            public PageIterator<XBookInfo> createIterator(int page, int size) {
                return new PageIterator<XBookInfo>(new PageIterator.XListRequest<XBookInfo>() {
                    @Override
                    public IDataIterator<List<XBookInfo>> execute(int page) {
                        return new BookInfoDataIterator(getContext(), page);
                    }
                }, page);
            }
        };
    }


    @Override
    public void onListItemClick(ListView parent, View v, int position, long id) {
        super.onListItemClick(parent, v, position, id);
        XBookInfo item = (XBookInfo) parent.getItemAtPosition(position);
        Intent intent = new Intent(this.getContext(), BookInfoDetailActivity.class);
        intent.putExtra(BookInfoDetailActivity.BOOKINFO, item);
        startActivity(intent);

    }
}
