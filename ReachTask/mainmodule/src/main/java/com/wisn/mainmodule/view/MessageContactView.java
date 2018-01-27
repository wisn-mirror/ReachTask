package com.wisn.mainmodule.view;

import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.User;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/24 21:26
 */


public interface MessageContactView {
    void setContactData(List<Contact> contactData);
    void updateContactData(List<Contact> contactData);
    void toSendMessage(Contact contact,User user);
}
