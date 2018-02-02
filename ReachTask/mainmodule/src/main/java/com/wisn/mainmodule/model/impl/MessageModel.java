package com.wisn.mainmodule.model.impl;

import com.wisn.mainmodule.app.MApplication;
import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.entity.MessageDao;
import com.wisn.mainmodule.model.IMessageModel;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Query;

import java.util.Collections;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/25 17:10
 */


public class MessageModel implements IMessageModel {
    @Override
    public void saveMessage(Message message) {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        messageDao.insertOrReplace(message);
    }

    @Override
    public List<Message> getMesssages() {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        List<Message> messages = messageDao.loadAll();
        Collections.sort(messages);
        for (Message message : messages) {
            System.err.println("  ddd:" + message);
        }
        System.err.println("  dddddd:" + messages);
        return messages;
    }

    public List<Message> getMesssagesByTargetid(Long targerid) {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        Query<Message> build = messageDao.queryBuilder().where(MessageDao.Properties.Targetuserid.eq(targerid))
                .orderDesc(MessageDao.Properties.Receivetime)
                .build();
        List<Message> list = build.list();
        for (Message message : list) {
            System.err.println("  ddd:" + message);
        }
        System.err.println("  dddddd:" + list);
        return list;
    }

    @Override
    public List<Message> getMesssagesByContactid(Long contactid) {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        Query<Message> build = messageDao.queryBuilder().where(MessageDao.Properties.Contactid.eq(contactid))
                .orderDesc(MessageDao.Properties.Receivetime)
                .build();
        List<Message> list = build.list();
        for (Message message : list) {
            System.err.println("  ddd:" + message);
        }
        System.err.println("  dddddd:" + list);
        return list;
    }

    public Message getMesssage(Long messageId) {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        Query<Message> build = messageDao.queryBuilder().where(MessageDao.Properties.Messageid.eq(messageId)).build();
        Message unique = build.unique();
        if (unique != null) {
            System.err.println(unique);
            return unique;
        }
        return null;
    }

    @Override
    public void updateMessage(Message message) {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        messageDao.update(message);
    }

    @Override
    public void deleteMessage(Long messageid) {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        messageDao.deleteByKey(messageid);
    }

    @Override
    public void deleteMessageByContactUserId(Long userid) {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        DeleteQuery<Message> messageDeleteQuery = messageDao.queryBuilder().where(MessageDao.Properties.Targetuserid.eq(userid)).buildDelete();
        messageDeleteQuery.executeDeleteWithoutDetachingEntities();
    }
}
