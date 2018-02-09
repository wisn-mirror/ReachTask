package com.wisn.mainmodule.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.view.MyPhotoView;
import com.wisn.utils.ToastUtils;

import java.lang.reflect.Method;

/**
 * @author Wisn
 * @time 2018/2/8 12:33
 */


public class MyPhotoActivity extends BaseAppCompatActivity implements MyPhotoView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myphoto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setTitle("啊哈哈hahah");
//        getSupportActionBar().setSubtitle("www");
//        toolbar.setSubtitle("啊哈哈哈哈哈");
        toolbar.setNavigationIcon(R.drawable.back);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public void updatePhoto(String pathUrl) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.myphoto_menu,menu);

        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.search) {
            ToastUtils.show("search");
            return true;
        } else if (i == R.id.select_newphoto) {
            ToastUtils.show("select_newphoto");
            return true;
        } else if (i == R.id.save_photo) {
            ToastUtils.show("save_photo");
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
