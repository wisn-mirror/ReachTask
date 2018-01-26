package com.wisn.mainmodule.model.impl;

import com.wisn.mainmodule.app.MApplication;
import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.ContactDao;
import com.wisn.mainmodule.model.IContactModel;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/26 15:42
 */

public class ContactModel implements IContactModel {

    @Override
    public void savecontacts(Contact contact) {
        ContactDao contactDao = MApplication.getInstance().getDaoSession().getContactDao();
        contactDao.insertOrReplace(contact);
    }

    @Override
    public List<Contact> getContacts() {
        ContactDao contactDao = MApplication.getInstance().getDaoSession().getContactDao();
        List<Contact> contacts = contactDao.loadAll();
        for (Contact contact : contacts) {
            System.err.println("  ddd:" + contact);
        }
        System.err.println("  dddddd:" + contacts);
        return contacts;
    }

    @Override
    public Contact getContactByTargetid(Long targetid) {
        ContactDao contactDao = MApplication.getInstance().getDaoSession().getContactDao();
        Query<Contact> build = contactDao.queryBuilder().where(ContactDao.Properties.Targetuserid.eq(targetid)).build();
        Contact unique = build.unique();
        if (unique != null) {
            System.err.println(unique);
            return unique;
        }
        return null;
    }

    @Override
    public void updateContact(Contact contact) {
        ContactDao contactDao = MApplication.getInstance().getDaoSession().getContactDao();
        contactDao.update(contact);
    }

    @Override
    public Contact getContact(Long contantsid) {
        ContactDao contactDao = MApplication.getInstance().getDaoSession().getContactDao();
        Query<Contact> build = contactDao.queryBuilder().where(ContactDao.Properties.Targetuserid.eq(contantsid)).build();
        Contact unique = build.unique();
        if (unique != null) {
            System.err.println(unique);
            return unique;
        }
        return null;
    }

    @Override
    public void deleteContact(Long contactid) {
        ContactDao contactDao = MApplication.getInstance().getDaoSession().getContactDao();
        contactDao.deleteByKey(contactid);
    }

    @Override
    public void deleteContact(Contact contact) {
        ContactDao contactDao = MApplication.getInstance().getDaoSession().getContactDao();
        contactDao.delete(contact);
    }

    @Override
    public void deleteContactByTargetid(Long targetid) {
        ContactDao contactDao = MApplication.getInstance().getDaoSession().getContactDao();
        DeleteQuery<Contact> messageDeleteQuery = contactDao.queryBuilder().where(ContactDao.Properties.Targetuserid.eq(targetid)).buildDelete();
        messageDeleteQuery.executeDeleteWithoutDetachingEntities();
    }
}
