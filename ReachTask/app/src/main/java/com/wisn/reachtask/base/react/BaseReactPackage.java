package com.wisn.reachtask.base.react;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisn on 2017/11/13.
 */

public class BaseReactPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(final ReactApplicationContext reactContext) {
        return new ArrayList<NativeModule>(){{
//            add(new TestModule(reactContext));
            // TODO: 2018/1/5 添加module
        }};
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return new ArrayList<ViewManager>(){{

        }};
    }
}
