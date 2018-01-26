package com.wisn.mainmodule.model;

import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.utils.Contants;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/26 15:07
 */


public interface IContactModel {
    List<Contact> getContacts();
    List<Contact> getContactByTargetid(Long targetid);
    void updateContact(Contact contact);
    Contants getContact(Long contantsid);
    void deleteContact(Long contactid);
    void deleteContactByTargetid(Long targetid);

}
