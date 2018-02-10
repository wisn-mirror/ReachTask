package com.wisn.mainmodule.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Menu;
import android.view.MenuItem;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.MyPhotoView;
import com.wisn.mainmodule.view.viewholder.ToolbarHolder;
import com.wisn.skinlib.utils.LogUtils;
import com.wisn.utils.ToastUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author Wisn
 * @time 2018/2/8 12:33
 */


public class MyPhotoActivity extends BaseAppCompatActivity implements MyPhotoView {
    private static final String TAG ="MyPhotoActivity" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initToolbarView(ToolbarHolder toolbar) {
        LogUtils.e(TAG,"boolbar"+toolbar);
        toolbar.getToolbar().setTitle("头像");
        toolbar.getToolbar().setNavigationIcon(R.drawable.back);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_myphoto;
    }

    @Override
    public void updatePhoto(String pathUrl) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            ArrayList<String> parcelableArrayListExtra = data.getStringArrayListExtra(Contants.Select_Result);
            for (String str : parcelableArrayListExtra) {
                System.err.println("file:" + str);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.search) {
            ToastUtils.show("search");
            return true;
        } else if (i == R.id.select_newphoto) {
            ToastUtils.show("select_newphoto");
            SelectImageListActivity.start(this,100,1,null);
            return true;
        } else if (i == R.id.save_photo) {
            ToastUtils.show("save_photo");
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
