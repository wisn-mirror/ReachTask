package com.wisn.mainmodule.view;

import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.User;

/**
 * @author Wisn
 * @time 2018/1/26 16:31
 */


public interface InfoView {
    User getTargetUser();
    void startMessage(Contact contact);
}
