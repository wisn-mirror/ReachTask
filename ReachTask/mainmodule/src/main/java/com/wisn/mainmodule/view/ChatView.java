package com.wisn.mainmodule.view;

import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.protocal.service.HandleMessage;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/25 17:01
 */


public interface ChatView {

    HandleMessage getHandleMessage();

    /**
     * 部分更新消息列表
     *
     * @param messageList 增量消息
     * @param isTop       是否添加到顶部
     */
    void updateMoreMessage(List<Message> messageList, boolean isTop);

    /**
     * 部分更新消息列表
     *
     * @param message 增量消息
     */
    void updateMoreMessage(Message message);

    /**
     * 全部更新消息列表
     * @param messageList
     */
    void setMessageList(List<Message> messageList);

}
