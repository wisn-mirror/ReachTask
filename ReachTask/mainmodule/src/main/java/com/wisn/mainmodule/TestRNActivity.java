package com.wisn.mainmodule;

import android.os.Bundle;

import com.wisn.mainmodule.base.react.BaseReactActivity;

import javax.annotation.Nullable;

/**
 * Created by wisn on 2018/1/5.
 */

public class TestRNActivity extends BaseReactActivity {
    @Nullable
    @Override
    protected Bundle getLaunchOptions() {
        return null;
    }

    @Nullable
    @Override
    protected String getMainComponentName() {
        return "reachtask";
    }
}
