package com.wisn.mainmodule.presenter;

import android.util.Log;

import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.http.response.HttpResponse;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.view.LoginView;

/**
 * @author Wisn
 * @time 2018/1/24 14:47
 */


public class LoginPresenter implements HttpCallback<User> {
    LoginView loginView;
    IUserModel userModel;
    public LoginPresenter(LoginView loginView){
        this.loginView=loginView;
        userModel=new UserModel();
    }
    public void login() {
        userModel.login(loginView.getUser(),this);
        loginView.showLoginProgress();
    }


    @Override
    public void onSuccess(HttpResponse<User> response) {
        Log.e("onSuccess  ss",response.toString());
        userModel.saveUser(response.getData(),true);
        loginView.loginSuccess(response.getMessage());
    }

    @Override
    public void onError(String msg) {

        loginView.loginError(msg);
    }

    @Override
    public void onFinsh() {
        loginView.hideLoginPregress();
    }
}
