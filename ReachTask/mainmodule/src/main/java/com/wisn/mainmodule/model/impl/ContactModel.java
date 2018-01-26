package com.wisn.mainmodule.model.impl;

import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.model.IContactModel;
import com.wisn.mainmodule.utils.Contants;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/26 15:42
 */


public class ContactModel implements IContactModel{

    @Override
    public List<Contact> getContacts() {
        return null;
    }

    @Override
    public List<Contact> getContactByTargetid(Long targetid) {
        return null;
    }

    @Override
    public void updateContact(Contact contact) {

    }

    @Override
    public Contants getContact(Long contantsid) {
        return null;
    }

    @Override
    public void deleteContact(Long contactid) {

    }

    @Override
    public void deleteContactByTargetid(Long targetid) {

    }
}
