package com.wisn.mainmodule.view;

import com.wisn.mainmodule.entity.User;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/24 21:26
 */


public interface ContactView {
    void updateContactList(List<User> userlist);
    void startRefresh();
    void finishRefresh();
    void refreshError(String msg);
    void setUserData(List<User> userData);
}
