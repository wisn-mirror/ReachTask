package com.wisn.mainmodule.model.impl;

import com.wisn.mainmodule.app.MApplication;
import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.entity.MessageDao;
import com.wisn.mainmodule.model.IMessageModel;
import com.wisn.skinlib.utils.LogUtils;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Query;

import java.util.Collections;
import java.util.List;


/**
 * @author Wisn
 * @time 2018/1/25 17:10
 */


public class MessageModel implements IMessageModel {
    public  static String TAG="MessageModel";
    @Override
    public void saveMessage(Message message) {
        LogUtils.e(TAG,"  saveMessage:" + message);
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        messageDao.insertOrReplace(message);
    }

    @Override
    public List<Message> getMesssages() {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        List<Message> messages = messageDao.loadAll();
        Collections.sort(messages);
        for (Message message : messages) {
           LogUtils.e(TAG,"  ddd:" + message);
        }
        return messages;
    }

    public List<Message> getMesssagesByTargetid(Long targerid) {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        Query<Message> build = messageDao.queryBuilder().where(MessageDao.Properties.Targetuserid.eq(targerid))
                .orderAsc(MessageDao.Properties._id)
                .build();
        List<Message> list = build.list();
        for (Message message : list) {
           LogUtils.e(TAG,"  ddd:" + message);
        }
        return list;
    }

    @Override
    public List<Message> getMesssagesByContactid(Long contactid) {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        Query<Message> build = messageDao.queryBuilder().where(MessageDao.Properties.Contactid.eq(contactid))
                .orderAsc(MessageDao.Properties._id)
                .build();
        List<Message> list = build.list();
        for (Message message : list) {
           LogUtils.e(TAG,"  ddd:" + message);
        }
        return list;
    }

    public Message getMesssage(Long messageId) {
        MessageDao messageDao = MApplication.getInstance().getDaoSession().getMessageDao();
        Query<Message> build = messageDao.queryBuilder().where(MessageDao.Properties.Messageid.eq(messageId)).build();
        Message unique = build.unique();
        if (unique != null) {
           LogUtils.e(TAG,"getMesssage"+unique);
            return unique;
        }
        return null;
    }

    @Override
    public void updateMessage(Message message) {
        LogUtils.e(TAG,"updateMessage");
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
