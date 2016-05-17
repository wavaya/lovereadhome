package com.wayhua.lovereadhome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.octicons_typeface_library.Octicons;
import com.wayhua.lovereadhome.R;
import com.wayhua.lovereadhome.SessionManage;
import com.wayhua.lovereadhome.adapter.MainTabAdapter;
import com.wayhua.lovereadhome.bean.XUser;
import com.wayhua.lovereadhome.bmob.BmobManage;
import com.wayhua.framework.Constants;
import com.wayhua.framework.NightModelUtils;
import com.wayhua.framework.base.BaseMainDrawerTabPagerActivity;

public class MainNewActivity extends BaseMainDrawerTabPagerActivity {

    @Override
    protected PagerAdapter createAdapter() {
        return new MainTabAdapter(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        BmobManage.setCurrent(this);
    }

    //Drawer相关
    protected void initDrawerItem() {
        //1 个人中心
        PrimaryDrawerItem item10 = new PrimaryDrawerItem();
        item10.withName(R.string.rdls)
                .withIcon(GoogleMaterial.Icon.gmd_calendar_note)
                .withIdentifier(10).withSelectable(false);


        //1 个人中心
        PrimaryDrawerItem item1 = new PrimaryDrawerItem();
        item1.withName(R.string.grzl)
                .withIcon(GoogleMaterial.Icon.gmd_account)
                .withIdentifier(1).withSelectable(false);
        //2 分享
        PrimaryDrawerItem item2 = new PrimaryDrawerItem();
        item2.withName(R.string.fx)
                .withIcon(GoogleMaterial.Icon.gmd_share)
                .withIdentifier(2).withSelectable(false);

        //3 关于
        PrimaryDrawerItem item3 = new PrimaryDrawerItem();
        item3.withName(R.string.about)
                .withIcon(GoogleMaterial.Icon.gmd_help)
                .withIdentifier(3).withSelectable(false);
        //4 分隔线
        DividerDrawerItem item4 = new DividerDrawerItem();
        PrimaryDrawerItem item5 = new PrimaryDrawerItem();
        item5.withName(R.string.sz)
                .withIcon(GoogleMaterial.Icon.gmd_settings)
                .withIdentifier(5).withSelectable(false);


        boolean ischecked = NightModelUtils.getNightModeSwitch(this);
        SwitchDrawerItem item6 = new SwitchDrawerItem().withName(R.string.yjmx)
                .withIcon(Octicons.Icon.oct_tools)
                .withChecked(ischecked)
                .withOnCheckedChangeListener(onCheckedChangeListener);

        DividerDrawerItem item7 = new DividerDrawerItem();


        PrimaryDrawerItem item8 = new PrimaryDrawerItem();
        item8.withName(R.string.log_out)
                .withIcon(GoogleMaterial.Icon.gmd_power)
                .withIdentifier(8).withSelectable(false);

        drawerBuilder.addDrawerItems(
                item10,
                item1,
                item2,
                item3,
                item4,
                item5,
                item6,
                item7,
                item8
        )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            Intent intent = null;
                            if (drawerItem.getIdentifier() == 3) {
                                intent = new Intent(MainNewActivity.this, AboutActivity.class);
                            }
                            if (drawerItem.getIdentifier() == 1) {
                                intent = new Intent(MainNewActivity.this, MineNewActivity.class);
                            }
                            if (drawerItem.getIdentifier() == 8) {
                                // do something before quit
                                SessionManage.setIsLogin(MainNewActivity.this, false);
                                Constants.quit();
                            }
                            if (drawerItem.getIdentifier() == 10) {
                                intent = new Intent(MainNewActivity.this, ReadLogActivity.class);
                            }
                            if (intent != null) {
                                MainNewActivity.this.startActivity(intent);
                            }
                        }
                        return false;
                    }
                });
    }

    protected AccountHeaderBuilder initHeaderBulider() {
        ProfileDrawerItem profile = new ProfileDrawerItem();

        String account = SessionManage.getAccount(this);
        // getString(R.string.anonymous);
        // XUser user = (XUser) BmobUser.getCurrentUser(this, XUser.class);
        //  String name = user.getRealName();

        final XUser bmobUser = BmobManage.getCurrent();
        String name = bmobUser.getRealName();
        String headUrl = "";
        if (bmobUser.getPic() != null) {
            headUrl = bmobUser.getPic().getFileUrl(this);
        }
        if (TextUtils.isEmpty(headUrl)) {

            profile.withName(account)
                    .withEmail(name)
                    //.withIcon(R.mipmap.ic_launcher);
                    .withIcon(R.mipmap.read);
        } else {


            profile.withName(account)
                    .withEmail(name)
                    //.withIcon(R.mipmap.ic_launcher);
                    .withIcon(headUrl);
        }
        int color = R.mipmap.header;

        AccountHeaderBuilder result = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(color)
                .addProfiles(profile);
        return result;
    }

    private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            NightModelUtils.switchTheme(MainNewActivity.this);
        }
    };

}
