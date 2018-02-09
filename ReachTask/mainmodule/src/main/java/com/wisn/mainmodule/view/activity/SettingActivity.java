package com.wisn.mainmodule.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseAppCompatActivity;

/**
 * @author Wisn
 * @time 2018/2/5 13:45
 */


public class SettingActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initToolbarView(Toolbar toolbar) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_setting;
    }
}
