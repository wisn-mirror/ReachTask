package com.wisn.mainmodule.presenter;

import com.wisn.mainmodule.http.response.HttpResponse;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.view.RegisterView;

/**
 * @author Wisn
 * @time 2018/1/24 14:47
 */


public class RegisterPresenter implements HttpCallback<String> {
    RegisterView registerView;
    IUserModel userModel;
    public RegisterPresenter(RegisterView registerView){
        this.registerView=registerView;
        userModel=new UserModel();
    }
    public void register() {
        userModel.register(registerView.getUser(),this);
        registerView.showRegisterProgress();
    }

    @Override
    public void onSuccess(HttpResponse<String> response) {
        registerView.hideRegisterPregress();
        registerView.registerSuccess(response.getMessage());
    }

    @Override
    public void onError(String msg) {
        registerView.hideRegisterPregress();
        registerView.registerError(msg);
    }
}
