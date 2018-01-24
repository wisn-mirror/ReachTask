package com.wisn.mainmodule.view;

import com.wisn.mainmodule.http.request.Register;

/**
 * @author Wisn
 * @time 2018/1/24 14:44
 */


public interface RegisterView {
    Register getUser();
    void registerError(String msg);
    void registerSuccess(String msg);
    void showRegisterProgress();
    void hideRegisterPregress();
}
