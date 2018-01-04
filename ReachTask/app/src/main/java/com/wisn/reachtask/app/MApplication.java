package com.wisn.reachtask.app;


import com.wisn.skinlib.base.SkinApplication;
import com.wisn.utils.Utils;

/**
 * Created by wisn on 2018/1/4.
 */

public class MApplication extends SkinApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }

    @Override
    public boolean isSupplyRN() {
        return false;
    }
}
