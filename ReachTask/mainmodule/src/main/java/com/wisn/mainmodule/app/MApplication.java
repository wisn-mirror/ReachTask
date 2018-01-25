package com.wisn.mainmodule.app;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.wisn.mainmodule.base.react.BaseReactPackage;
import com.wisn.mainmodule.entity.DaoMaster;
import com.wisn.mainmodule.entity.DaoSession;
import com.wisn.mainmodule.protocal.service.DaemonService;
import com.wisn.mainmodule.protocal.service.MessageAService;
import com.wisn.skinlib.base.SkinApplication;
import com.wisn.utils.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wisn on 2018/1/4.
 */

public class MApplication extends SkinApplication implements ReactApplication {

    private DaoSession daoSession;
    private static MApplication  app;
    @Override
    public ReactNativeHost getReactNativeHost() {
        return host;
    }
    public static MApplication getInstance(){
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        SoLoader.init(this, /* native exopackage */ false);
        Utils.init(this);
        setDatabase();
    }

    public void setDatabase(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "reachtask.db", null);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster  daoMaster=new DaoMaster(writableDatabase);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        if(daoSession==null){
            setDatabase();
        }
        return daoSession;
    }

    @Override
    public boolean isSupplyRN() {
        return false;
    }

    private final ReactNativeHost host = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return true;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new BaseReactPackage()
            );
        }
    };
    private ReactContext mReactContext;

    public ReactContext getReactContext() {
        return mReactContext;
    }

    public void startMessageService(){
        Intent MessageAService=new Intent(this, MessageAService.class);
        Intent DaemonService=new Intent(this, DaemonService.class);
        startService(MessageAService);
        startService(DaemonService);
    }

}