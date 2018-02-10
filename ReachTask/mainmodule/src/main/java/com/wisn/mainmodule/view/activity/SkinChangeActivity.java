package com.wisn.mainmodule.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.view.viewholder.ToolbarHolder;

/**
 * @author Wisn
 * @time 2018/2/5 13:48
 */


public class SkinChangeActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initToolbarView(ToolbarHolder toolbar) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_changeskin;
    }
}
