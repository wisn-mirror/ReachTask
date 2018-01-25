package com.wisn.mainmodule.presenter;

import com.wisn.mainmodule.model.IMessageModel;
import com.wisn.mainmodule.model.impl.MessageModel;
import com.wisn.mainmodule.view.MessageView;

/**
 * @author Wisn
 * @time 2018/1/25 16:59
 */


public class MessagePresenter {
    MessageView messageView;
    IMessageModel  messageModel;
    public MessagePresenter(MessageView messageView){
        this.messageView=messageView;
        messageModel=new MessageModel();
    }
    public void sendMessage(int type,String message){

    }

}
