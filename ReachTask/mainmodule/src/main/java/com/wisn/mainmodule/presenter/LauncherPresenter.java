package com.wisn.mainmodule.presenter;

import android.text.TextUtils;

import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.LauncherView;
import com.wisn.mainmodule.view.activity.HomeActivity;
import com.wisn.mainmodule.view.activity.LoginActivity;
import com.wisn.mainmodule.view.activity.WelcomeActivity;
import com.wisn.utils.PreferencesUtils;

/**
 * @author Wisn
 * @time 2018/1/27 21:13
 */


public class LauncherPresenter {
    LauncherView launcherView;
    IUserModel userModel;

    public LauncherPresenter(LauncherView launcherView) {
        this.launcherView = launcherView;
        userModel = new UserModel();
    }

    public void updateLauncherFlag(boolean isFirstLauncher) {
        PreferencesUtils.putBoolean(Contants.sp_isstarted_Welcome_flag, true);
        launcher();
    }

    public void launcher() {
        boolean isFirst = PreferencesUtils.getBoolean(Contants.sp_isstarted_Welcome_flag);
        if (!isFirst) {
            //第一次启动
            launcherView.startNextActivity(WelcomeActivity.class, 0);
        } else {
            User user = userModel.getActiveUser();
            if (user != null && !user.isExpired() && !TextUtils.isEmpty(user.getToken())) {
                launcherView.startNextActivity(HomeActivity.class, 0);
            } else {
                launcherView.startNextActivity(LoginActivity.class, 0);
            }
        }
    }
}
