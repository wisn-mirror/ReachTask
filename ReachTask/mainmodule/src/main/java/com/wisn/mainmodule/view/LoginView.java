package com.wisn.mainmodule.view;

import com.wisn.mainmodule.http.request.Login;

/**
 * @author Wisn
 * @time 2018/1/24 14:44
 */


public interface LoginView {
    Login getUser();
    void loginError(String msg);
    void loginSuccess(String msg);
    void showLoginProgress();
    void hideLoginPregress();
}
