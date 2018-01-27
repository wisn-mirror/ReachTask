package com.wisn.mainmodule.presenter;

import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.model.IContactMessageModel;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.model.impl.ContactMessageModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.view.MessageContactView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/24 21:25
 */


public class MessageContactPresenter  {
    MessageContactView contactView;
    IContactMessageModel contactMessageModel;
    IUserModel userModel;

    public MessageContactPresenter(MessageContactView contactView) {
        this.contactView = contactView;
        contactMessageModel = new ContactMessageModel();
        userModel=new UserModel();
    }
    public void getContacts(){
        List<Contact> contacts = contactMessageModel.getContacts();
        if (contacts == null) {
            contacts = new ArrayList<>();
        }
        contactView.setContactData(contacts);
    }


    public void toSendMessage(Contact contact) {
        User userbyUserid = userModel.getUserbyUserid(contact.getTargetuserid());
        contactView.toSendMessage(contact,userbyUserid);
    }
}
