package com.wisn.mainmodule.presenter;

import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.model.IContactMessageModel;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.model.impl.ContactMessageModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.view.InfoView;

/**
 * @author Wisn
 * @time 2018/1/26 16:31
 */


public class InfoPresenter {
    private InfoView infoView;
    private IContactMessageModel contactMessageModel;
    private IUserModel iUserModel;

    public InfoPresenter(InfoView infoView) {
        this.infoView = infoView;
        contactMessageModel = new ContactMessageModel();
        iUserModel = new UserModel();
    }

    public void sendMessage() {
        User targetUser = infoView.getTargetUser();
        User activeUser = iUserModel.getActiveUser();
        Contact contact = contactMessageModel.getContactByTargetid(targetUser.getUserid());
        if (contact == null) {
            contact = new Contact();
            long contactid = Long.parseLong(String.valueOf(activeUser.getUserid()) + String.valueOf(targetUser.getUserid()));
            contact.setContactid(contactid);
            contact.setFromuserid(activeUser.getUserid());
            contact.setTargetuserid(targetUser.getUserid());
            contact.setIcon(targetUser.getIconurl());
            contact.setName(targetUser.getNickname());
            contact.setLastcontacttime(0l);
            contactMessageModel.saveContacts(contact);
        }

        infoView.startMessage(contact);
    }
}
