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
    List<Message> getMesssagesByTargetid(Long targerid);
    List<Message> getMesssagesByContactid(Long contactid);
    void updateMessage(Message message);
    Message getMesssage(Long messageId);
    void deleteMessage(Long messageid);
    void deleteMessageByContactUserId(Long userid);
    
}
