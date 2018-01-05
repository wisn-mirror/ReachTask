package com.wisn.reachtask.app;


import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.wisn.reachtask.base.react.BaseReactPackage;
import com.wisn.skinlib.base.SkinApplication;
import com.wisn.utils.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wisn on 2018/1/4.
 */

public class MApplication extends SkinApplication implements ReactApplication {

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

    @Override
    public ReactNativeHost getReactNativeHost() {
        return host;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        SoLoader.init(this, /* native exopackage */ false);
    }


    @Override
    public boolean isSupplyRN() {
        return false;
    }
}
