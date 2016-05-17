package com.wayhua.lovereadhome.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.wayhua.lovereadhome.Constants;
import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.bean.XUser;
import com.wayhua.lovereadhome.bmob.BmobManage;
import com.wayhua.framework.base.BaseActivityToolBar;
import com.wayhua.framework.util.KeyboardUtils;
import com.wayhua.framework.util.Logs;
import com.wayhua.framework.util.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;


public class MineActivity extends BaseActivityToolBar {
    private MenuItem doneMenuItem;
    protected boolean isEdit = false;
    CircleImageView circleImageView;
    EditText edt_xm;
    EditText edt_age;
    EditText edt_sj;
    EditText edt_email;
    EditText edt_qq;
    EditText edt_dz;

    @Override
    public void initView() {
        super.initView();

        circleImageView = (CircleImageView) findViewById(R.id.head);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyHead();
            }

        });

        edt_xm = (EditText) findViewById(R.id.edt_xm);
        edt_age = (EditText) findViewById(R.id.edt_age);
        edt_sj = (EditText) findViewById(R.id.edt_sj);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_qq = (EditText) findViewById(R.id.edt_qq);
        edt_dz = (EditText) findViewById(R.id.edt_dz);

        changeModified(isEdit);


        initdialog();
    }

    private Bitmap head;//头像Bitmap
    private Dialog dlg;
    TextView tv_title;
    TextView tv_sure;
    TextView tv_cancel;
    private LinearLayout localLinearLayout;

    private void initdialog() {

        dlg = new Dialog(this, R.style.NewRequestDialogStyle);
        localLinearLayout = (LinearLayout) ((LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.new_request_dialog, null);
        localLinearLayout.setMinimumWidth(10000);
        tv_title = ((TextView) localLinearLayout
                .findViewById(R.id.tv_title));
        tv_sure = ((TextView) localLinearLayout.findViewById(R.id.tv_sure));
        tv_cancel = ((TextView) localLinearLayout
                .findViewById(R.id.tv_cancel));


    }

    private final int STARTUPLOAD = 1102;

    private void modifyHead() {
        tv_title.setText("修改头像？");
        tv_sure.setText("拍摄");
        tv_sure.setGravity(Gravity.CENTER);
        tv_cancel.setText("相册");
        WindowManager.LayoutParams localLayoutParams = dlg.getWindow()
                .getAttributes();
        localLayoutParams.x = 0;
        localLayoutParams.y = -1000;
        localLayoutParams.gravity = 80;
        dlg.onWindowAttributesChanged(localLayoutParams);
        dlg.setCanceledOnTouchOutside(true);
        dlg.setContentView(localLinearLayout);
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 相册中选择照片
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                MineActivity.this.startActivityForResult(intent1, 1001);

            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //调用系统相机拍摄照片

                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.headFileName)));
                MineActivity.this.startActivityForResult(intent2, 1002);//采用ForResult打开


            }
        });


        dlg.show();

    }

    Uri uri;


    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        this.uri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        // intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, 1003);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(Constants.path);

        if (!file.exists())
            file.mkdirs();// 创建文件夹
        String fileName = Constants.headFileName;// path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1001:
                if (resultCode == Activity.RESULT_OK) {

                    cropPhoto(data.getData());//裁剪图片
                }

                break;
            case 1002:
                if (resultCode == Activity.RESULT_OK) {
                    File temp = new File(Constants.headFileName);
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }

                break;
            case 1003:
                try {
                    head = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                    if (head != null) {


                        setPicToView(head);//保存在SD卡中
                        //  circleImageView.setImageBitmap(head);//用ImageView显示出来
                        Glide.with(this).load(Constants.headFileName)
                                .signature(new StringSignature(UUID.randomUUID().toString()))
                                .error(R.mipmap.read)
                                .into(circleImageView);

/**
 * 上传服务器代码
 */
                        String fileName = Constants.headFileName;//图片名字

                        XUser user = BmobManage.getCurrent();
                        File file= new File(Constants.path,Constants.filename);
                       BmobFile bfile= new BmobFile(file);

                        user.setPic(bfile);
                        handler.sendEmptyMessage(STARTUPLOAD);
                        dlg.dismiss();
                        // }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
//                if (data != null) {
//                    Bundle extras = data.getExtras();
//                    head = extras.getParcelable("data");

                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case STARTUPLOAD:
                    uploadPic();
                    break;
            }
        }
    };

    private void uploadPic() {
        XUser bmobUser = BmobManage.getCurrent();
        bmobUser.update(this, new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtils.show(MineActivity.this, "图像上传成功！");

            }

            @Override
            public void onFailure(int i, String s) {
                Logs.e("错误编码："+i+" 错误原因:"+s);
                ToastUtils.show(MineActivity.this, "图像上传失败！");
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        try {
            XUser bmobUser = BmobManage.getCurrent();

            String headUrl = "";
            if (bmobUser.getPic() != null) {
                headUrl = bmobUser.getPic().getFileUrl(this);
            }
            if (TextUtils.isEmpty(headUrl)) {
                Glide.with(this).load(R.mipmap.read).into(circleImageView);
            } else {
                Glide.with(this).load(headUrl).into(circleImageView);
            }

            String name = bmobUser.getRealName();
            edt_xm.setText(object2Str(name));
            edt_age.setText(object2Str(bmobUser.getAge()));
            edt_sj.setText(object2Str(bmobUser.getMobilePhoneNumber()));
            edt_email.setText(object2Str(bmobUser.getEmail()));
            edt_qq.setText(object2Str(bmobUser.getQQ()));
            edt_dz.setText(object2Str(bmobUser.getAddress()));

        } catch (Exception e) {
            Logs.e(e);
        }


    }

    public String object2Str(Object o) {
        if (o == null) {
            return "";

        }
        return o.toString();
    }

    private void changeModified(boolean b) {
        edt_xm.setEnabled(b);
        edt_sj.setEnabled(b);
        edt_email.setEnabled(b);
        edt_qq.setEnabled(b);
        edt_dz.setEnabled(b);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_modify) {
            doModify();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doModify() {
        if (isEdit) {
            isEdit = false;
            doneMenuItem.setTitle("修改");
            changeModified(isEdit);
            doSave();
        } else {
            isEdit = true;
            changeModified(isEdit);
            doneMenuItem.setTitle("完成");
        }

    }

    private void doSave() {
        KeyboardUtils.hideInputMethodWindow(this);
        XUser bmobUser = BmobManage.getCurrent();

        bmobUser.setRealName(edt_xm.getText().toString());
        int age = Integer.parseInt(edt_age.getText().toString());
        bmobUser.setAge(age);
        bmobUser.setMobilePhoneNumber(edt_sj.getText().toString());
        bmobUser.setEmail(edt_email.getText().toString());
        bmobUser.setQQ(edt_qq.getText().toString());
        bmobUser.setAddress(edt_dz.getText().toString());
        bmobUser.update(this, new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastUtils.show(MineActivity.this, "修改成功！");
                MineActivity.this.finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.show(MineActivity.this, "修改失败！");
            }
        });

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        doneMenuItem = menu.getItem(0);
        doneMenuItem.setVisible(true);
        if (isEdit) {
            doneMenuItem.setTitle("完成");
        } else doneMenuItem.setTitle("修改");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected int getMainLayoutView() {
        return R.layout.activity_mine;
    }

    @Override
    public void initActionBar() {
        setActionBarCenterTitle(R.string.grzl);
    }
}
