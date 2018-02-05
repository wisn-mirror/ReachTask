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

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/25 16:59
 */


public class MessagePresenter {
    public static String TAG = "MessagePresenter";
    ChatView messageView;
    IMessageModel messageModel;
    IUserModel userModel;
    ContactMessageModel contactMessageModel;

    public MessagePresenter(ChatView messageView) {
        this.messageView = messageView;
        messageModel = new MessageModel();
        userModel = new UserModel();
        contactMessageModel = new ContactMessageModel();
    }

    public void loadMessage(Contact contact) {
        List<Message> messsagesByContactid = messageModel.getMesssagesByContactid(contact.getContactid());
        if (messsagesByContactid != null && messsagesByContactid.size() != 0) {
            messageView.setMessageList(messsagesByContactid);
        }
        contact.setUnReadMessageNumber(0);
        contactMessageModel.saveContacts(contact);
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
            messageModel.saveMessage(message);
            messageView.updateMoreMessage(message);
        } else {
            Log.e(TAG, "未登录");
        }
    }


}
