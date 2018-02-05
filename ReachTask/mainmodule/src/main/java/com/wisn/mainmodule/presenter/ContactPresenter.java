package com.wisn.mainmodule.presenter;

import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.http.response.HttpResponse;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.view.ContactView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/24 21:25
 */


public class ContactPresenter implements HttpCallback<List<User>> {
    ContactView contactView;
    IUserModel userModel;
    int offset = 0;
    int limit = 100;

    public ContactPresenter(ContactView contactView) {
        this.contactView = contactView;
        userModel = new UserModel();
    }

    public void refreshContact() {
        offset = 0;
        contactView.startRefresh();
        userModel.getUser(this, offset, 100);
    }
    public void addMore(){
        contactView.startRefresh();
        userModel.getUser(this, offset, 100);
        offset = offset+limit;
    }

    public void getUsers() {
        List<User> users = userModel.getUsers(null);
        if (users == null) {
            users = new ArrayList<>();
        }
        contactView.setUserData(users);
    }


    @Override
    public void onSuccess(HttpResponse<List<User>> response) {
        List<User> data = response.getData();
        userModel.saveUsers(data);
        List<User> users = userModel.getUsers(null);
        contactView.setUserData(users);
    }

    @Override
    public void onError(String msg) {
        contactView.refreshError(msg);
    }

    @Override
    public void onFinsh() {
        contactView.finishRefresh();
    }
}
