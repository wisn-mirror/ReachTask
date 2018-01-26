package com.wisn.mainmodule.protocal.service;

import com.wisn.mainmodule.protocal.coder.Request;

/**
 * @author Wisn
 * @time 2018/1/25 14:29
 */


public interface HandleMessage {
    void sendMessage(Request request);
    void addMessageListener(MessageChangeListener  messageChangeListener);
    void removeMessageListener(MessageChangeListener  messageChangeListener);
}
