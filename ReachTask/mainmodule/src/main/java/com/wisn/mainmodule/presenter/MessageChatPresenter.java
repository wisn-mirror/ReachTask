package com.wisn.mainmodule.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.model.IMessageModel;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.model.impl.ContactMessageModel;
import com.wisn.mainmodule.model.impl.MessageModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.protocal.coder.Request;
import com.wisn.mainmodule.protocal.protobuf.beans.EMessageMudule;
import com.wisn.mainmodule.view.ChatView;
import com.wisn.skinlib.utils.LogUtils;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/25 16:59
 */


public class MessageChatPresenter {
    public static String TAG = "MessageChatPresenter";
    ChatView messageView;
    IMessageModel messageModel;
    IUserModel userModel;
    ContactMessageModel contactMessageModel;
    private int historyMessageCount;
    private int offsetIndex=1;
    private int limit=15;
    public MessageChatPresenter(ChatView messageView) {
        this.messageView = messageView;
        messageModel = new MessageModel();
        userModel = new UserModel();
        contactMessageModel = new ContactMessageModel();

    }

    public void loadMessage(Contact contact) {
        List<Message> messsagesByTargetid = messageModel.getMesssagesByContactidAll(contact.getContactid());
        historyMessageCount = messsagesByTargetid.size();
        List<Message> messsagesByContactid = messageModel.getMesssagesByContactid(contact.getContactid(),historyMessageCount-limit,limit);
        if (messsagesByContactid != null && messsagesByContactid.size() != 0) {
            messageView.setMessageList(messsagesByContactid);
        }
    }
    public void loadMoreMessage(Contact contact) {
        offsetIndex++;
        List<Message> messsagesByContactid = messageModel.getMesssagesByContactid(contact.getContactid(),historyMessageCount-offsetIndex*limit,limit);
        if (messsagesByContactid != null && messsagesByContactid.size() != 0) {
            messageView.updateMoreMessage(messsagesByContactid,true);
        }
    }
    public void clearTip(Contact contact){
        contact.setUnReadMessageNumber(0);
        contactMessageModel.saveContacts(contact);
    }
    public User getActiveUser(){
       return  userModel.getActiveUser();
    }

    public void sendMessage(short module, short cmd, Message message) {
        User activeUser = userModel.getActiveUser();
        if (activeUser != null && !TextUtils.isEmpty(activeUser.getToken())) {
            message.setToken(activeUser.getToken());
            message.setMessageid(message.getMessageid());
            message.setFromuserid(activeUser.getUserid());
            Log.e(TAG, "sendMessagetttttttttttt: " + message);
            EMessageMudule.EMessage eMessage = message.buildEMessage();
            Request request = Request.valueOf(module, cmd, eMessage.toByteArray());
            messageView.getHandleMessage().sendMessage(request);
            LogUtils.e(TAG,"saveMessage:"+message);
            messageModel.saveMessage(message);
            messageView.updateMoreMessage(message);
        } else {
            Log.e(TAG, "未登录");
        }
    }


}
