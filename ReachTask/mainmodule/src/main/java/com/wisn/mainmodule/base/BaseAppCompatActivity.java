package com.wisn.mainmodule.base;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.view.viewholder.ToolbarHolder;
import com.wisn.skinlib.base.SkinAppCompatActivity;
import com.wisn.skinlib.utils.LogUtils;

/**
 * Created by wisn on 2018/1/5.
 */

public abstract class BaseAppCompatActivity extends SkinAppCompatActivity {


    private static final String TAG = "BaseAppCompatActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (toolbar == null) {
            LogUtils.e(TAG, "toolbar================" + toolbar);
//            toolbar = findViewById(R.id.include_toolbar).findViewById(R.id.my_toolbar);
            toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        }
        LogUtils.e(TAG, "toolbar:::::" + toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            //不显示原生主题
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            initToolbarView(new ToolbarHolder(toolbar));
        }
    }

    public abstract void initToolbarView(ToolbarHolder toolbar);

    public abstract int getContentViewId();

    public boolean requestPermissionsM(String[] premissions, int requestCode) {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean allOk=true;
            for(String premissionOne:premissions){
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, premissionOne);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    allOk=false;
                    break;
                }
            }
            if (!allOk) {
                ActivityCompat.requestPermissions(this, premissions,
                        requestCode);
                return false;
            }
        }
        return true;
    }

}
