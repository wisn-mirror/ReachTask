package com.wisn.mainmodule.model.impl;

import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.model.IMessageModel;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/25 17:10
 */


public class MessageModel implements IMessageModel{
    @Override
    public void saveMessage(Message message) {

    }

    @Override
    public List<Message> getMesssages() {
        return null;
    }

    @Override
    public void updateMessage(Message message) {

    }

    @Override
    public void deleteMessage(int messageid) {

    }

    @Override
    public void deleteMessageByContactUserId(int userid) {

    }
}
