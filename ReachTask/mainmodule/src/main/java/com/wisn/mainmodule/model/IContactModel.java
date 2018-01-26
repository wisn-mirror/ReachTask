package com.wisn.mainmodule.model;

import com.wisn.mainmodule.entity.Contact;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/26 15:07
 */


public interface IContactModel {
    void savecontacts(Contact contact);
    List<Contact> getContacts();
    Contact getContactByTargetid(Long targetid);
    void updateContact(Contact contact);
    Contact getContact(Long contantsid);
    void deleteContact(Long contactid);
    void deleteContact(Contact contact);
    void deleteContactByTargetid(Long targetid);

}
