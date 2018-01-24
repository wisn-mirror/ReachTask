package com.wisn.mainmodule.model;

import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.http.request.ChangePassword;
import com.wisn.mainmodule.http.request.Login;
import com.wisn.mainmodule.http.request.Register;
import com.wisn.mainmodule.presenter.HttpCallback;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/24 14:40
 */


public interface IUserModel {
    void updatePassword(ChangePassword changePassword, HttpCallback<String> callback);

    void register(Register user, HttpCallback<String> callback);

    void login(Login user, HttpCallback<User> callback);

    void getUser(HttpCallback<List<User>> callback, int offset, int limit);

    void loginOut(HttpCallback<String> callback);

    boolean isLogin(String phoneNumber);

    void saveUser(User user, boolean isActive);

    void saveUsers(List<User> user);

    void deleteUser(User user);

    void updateUser(User user);

    String getTokenByActiveUser();

    List<User> getUsers(String phoneNumber);
}
