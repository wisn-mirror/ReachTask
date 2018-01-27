package com.wisn.mainmodule.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseActivity;
import com.wisn.mainmodule.presenter.LauncherPresenter;
import com.wisn.mainmodule.view.LauncherView;

/**
 * @author Wisn
 * @time 2018/1/27 21:00
 */


public class LaunchActivity  extends BaseActivity implements LauncherView {
    LauncherPresenter launcherPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        launcherPresenter=new LauncherPresenter(this);
        launcherPresenter.launcher();
    }

    @Override
    public void startNextActivity(final Class clazz, int delaytime) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent( LaunchActivity.this,clazz));
                LaunchActivity.this.finish();
            }
        },delaytime);

    }
}