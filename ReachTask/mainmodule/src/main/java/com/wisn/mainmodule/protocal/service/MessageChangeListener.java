package com.wisn.mainmodule.protocal.service;

import com.wisn.mainmodule.entity.Message;

/**
 * @author Wisn
 * @time 2018/1/26 12:25
 */


public interface MessageChangeListener {
    void newMessage(short module,short cmd,Message message);
    void receiptMessage(short module,short cmd,long messageId,long receiveTime,short resultCode);
}
