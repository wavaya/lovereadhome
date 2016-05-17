package com.wayhua.lovereadhome.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.wayhua.doubanapi.ISBNHelper;
import com.wayhua.framework.zxing.activity.XCaptureActivity;
import com.wayhua.lovereadhome.BookInfo2XBookInfo;
import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.SessionManage;
import com.wayhua.lovereadhome.adapter.ReadItemsAdapter;
import com.wayhua.lovereadhome.bean.XBookInfo;
import com.wayhua.lovereadhome.bean.XReadLog;
import com.wayhua.lovereadhome.bmob.BmobManage;
import com.wayhua.lovereadhome.util.XUtils;

import com.wayhua.doubanapi.models.BookInfo;
import com.wayhua.framework.base.BaseFragment;
import com.wayhua.framework.util.Logs;
import com.wayhua.framework.util.ToastUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadBookFragment extends BaseFragment implements View.OnClickListener {
    private static final int SCANREQUESTCODER = 2033;
    private static final int SCANRSUCCESSED = 2034;
    private static final int REFRESH = 2035;
    private static final int DOSAVE2BMOB = 2036;
    private static final int NEEDSAVETOMINE = 2037;
    private static final int GENERREADLOG = 2038;
    private static final int LOADISBNSUCCESSED = 2039;
    private static final int NEEDUPDATECOUNT = 2300;
    private static final int UPCOUNT = 2301;
    private static final int BATCHUPDATECOUNT = 2302;
    Button btn_mine;
    Button btn_others;
    GridView gridViewbook;
    Button btn_add;

    boolean ismine = false;

    ReadItemsAdapter adapter;

    public ReadBookFragment() {
    }

    List<BookInfo> bookInfos = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_read_book, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_mine = (Button) view.findViewById(R.id.btn_mine);
        btn_others = (Button) view.findViewById(R.id.btn_others);
        gridViewbook = (GridView) view.findViewById(R.id.gridViewbook);
        btn_add = (Button) view.findViewById(R.id.btn_add);
        adapter = new ReadItemsAdapter(getContext(), R.layout.item_readbook_selected);
        gridViewbook.setAdapter(adapter);
        gridViewbook.setEmptyView(view.findViewById(R.id.textview_datanull));
        btn_mine.setOnClickListener(this);
        btn_others.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mine:
                //添加我的图书，如果没在库里找到，会添加进来
                ismine = true;
                scanBooks();
                break;
            case R.id.btn_others:
                //只添加到阅读里，不添加到我的图书中
                ismine = false;
                scanBooks();
                break;
            case R.id.btn_add:
                //s
                addLogs();
                break;
        }
    }

    private void addLogs() {
        try {
            List<BookInfo> infos = new ArrayList<BookInfo>(adapter.getItems());//ReadItemsAdapter.this.getItems();
            List<BookInfo> needUpdateCount = new ArrayList<>();
            List<BmobObject> logs = new ArrayList<>();
            for (BookInfo bookInfo : infos) {
                if (bookInfo.getTag() == NEEDUPDATECOUNT) {
                    needUpdateCount.add(bookInfo);
                }
                XReadLog readLog = new XReadLog();
                readLog.setAccount(BmobManage.getCurrent().getUsername());
                readLog.setIsbn13(bookInfo.getIsbn13());
                readLog.setReadDate(new BmobDate(new Date()));
                readLog.setImage(bookInfo.getImage());
                readLog.setTitle(bookInfo.getTitle());
                if (!TextUtils.isEmpty(bookInfo.getSummary()))
                    readLog.setSummary(bookInfo.getSummary().substring(0, 100));
                String author = XUtils.combine(bookInfo.getAuthor());
                readLog.setAuthor(author);
                logs.add(readLog);
            }

            updateBookCount(needUpdateCount);
            insertLogs(logs);
        } catch (Exception ex) {
            Logs.e(ex);
        }
    }

    private void insertLogs(List<BmobObject> logs) {
        try {
            BmobObject bmobObject = new BmobObject();

            bmobObject.insertBatch(getContext(), logs, new SaveListener() {
                @Override
                public void onSuccess() {
                    ToastUtils.show(getActivity(), "插入阅读记录成功！");
                      adapter.setItems((Object[]) null);
                }

                @Override
                public void onFailure(int i, String s) {
                    ToastUtils.show(getActivity(), "插入阅读记录失败！");
                }
            });
        } catch (Exception ex) {
            Logs.e(ex);
        }
    }

    private void updateBookCount(List<BookInfo> needUpdateCount) {
        try {
            if (needUpdateCount == null || needUpdateCount.size() == 0) return;
            String instr = "";
            for (int i = 0; i < needUpdateCount.size(); i++) {
                BookInfo bookInfo = needUpdateCount.get(i);
                instr += "'" + bookInfo.getIsbn13() + "'";
                if (i < needUpdateCount.size() - 1) {
                    instr += ",";

                }
            }
            String sql = "select * from XBookInfo where account='" + SessionManage.getAccount(getContext())
                    + "' and booktype='default' and isbn13 in (" + instr + ")";
            BmobQuery<XBookInfo> query = new BmobQuery<XBookInfo>();
            query.doSQLQuery(getContext(), sql, new SQLQueryListener<XBookInfo>() {
                @Override
                public void done(BmobQueryResult<XBookInfo> result, BmobException e) {
                    if (e == null) {
                        List<XBookInfo> list = (List<XBookInfo>) result.getResults();
                        Message msg = new Message();
                        msg.what = BATCHUPDATECOUNT;
                        msg.obj = list;
                        handler.sendMessage(msg);
                    } else {
                        ToastUtils.show(getActivity(), e.getMessage());
                    }
                }
            });

        } catch (Exception ex) {
            Logs.e(ex);
        }
    }

    private void scanBooks() {
        //扫描事件
        Intent intent = new Intent();
        intent.setClass(ReadBookFragment.this.getActivity(), XCaptureActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANREQUESTCODER);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 处理扫描结果（在界面上显示）
        if (requestCode == SCANREQUESTCODER) {
            if (resultCode == this.getActivity().RESULT_OK) {
                Bundle bundle = data.getExtras();
                String scanResult = bundle.getString("result");
                Logs.e(scanResult);
                Message msg = new Message();
                msg.what = SCANRSUCCESSED;
                msg.obj = scanResult;
                handler.sendMessage(msg);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCANRSUCCESSED:
                    String isbn = (String) msg.obj;
                    doGetBookInfo(isbn);
                    break;
                case LOADISBNSUCCESSED:
                    BookInfo bookInfo = (BookInfo) msg.obj;
                    if (ismine) {
                        bookInfo.setTag(NEEDUPDATECOUNT);
                        docheckAndAddToMine(bookInfo);
                    }
                    doAddReadGrid(bookInfo);

                    break;

                case DOSAVE2BMOB:
                    BookInfo info1 = (BookInfo) msg.obj;
                    doSave(info1);
                    break;


                case UPCOUNT:
                    XBookInfo info2 = (XBookInfo) msg.obj;
                    BmobManage.updateReadCount(getContext(), info2);
                    break;

                case BATCHUPDATECOUNT:
                    List<XBookInfo> list = (List<XBookInfo>) msg.obj;
                    BmobManage.batchUpdateReadCount(getActivity(), list);
                    break;
            }
        }
    };

    private void doSave(BookInfo info) {
        XBookInfo bookInfo = BookInfo2XBookInfo.trans2Default(getContext(), info);
        bookInfo.save(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.show(getActivity(), "保存成功！");

            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.show(getActivity(), "保存失败！");
            }
        });
    }


    private void doAddReadGrid(BookInfo bookInfo) {
        bookInfos.add(bookInfo);
        adapter.setItems(bookInfos);
    }

    private void docheckAndAddToMine(final BookInfo info) {
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
                ToastUtils.show(getActivity(), R.string.netError);
            }
        });
    }

    private void doGetBookInfo(String isbn) {
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
                        ToastUtils.show(getActivity(), R.string.netError);
                    }
                });

    }


}
