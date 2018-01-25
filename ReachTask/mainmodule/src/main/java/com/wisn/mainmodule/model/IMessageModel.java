package com.wisn.mainmodule.model;

import com.wisn.mainmodule.entity.Message;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/24 14:40
 */


public interface IMessageModel {
    void saveMessage(Message message);
    List<Message> getMesssages();
    void updateMessage(Message message);
    void deleteMessage(int messageid);
    void deleteMessageByContactUserId(int userid);
    
}
