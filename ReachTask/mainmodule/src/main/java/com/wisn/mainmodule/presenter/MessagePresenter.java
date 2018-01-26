package com.wisn.mainmodule.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.model.IMessageModel;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.model.impl.MessageModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.protocal.coder.Request;
import com.wisn.mainmodule.protocal.protobuf.beans.EMessageMudule;
import com.wisn.mainmodule.view.MessageView;

/**
 * @author Wisn
 * @time 2018/1/25 16:59
 */


public class MessagePresenter {
    public static String TAG = "MessagePresenter";
    MessageView messageView;
    IMessageModel messageModel;
    IUserModel userModel;

    public MessagePresenter(MessageView messageView) {
        this.messageView = messageView;
        messageModel = new MessageModel();
        userModel = new UserModel();
    }

    public void sendMessage(short module, short cmd, Message message) {
        User activeUser = userModel.getActiveUser();
        if (activeUser != null && !TextUtils.isEmpty(activeUser.getToken())) {
            message.setToken(activeUser.getToken());
            message.setFromuserid(activeUser.getUserid());
            EMessageMudule.EMessage eMessage = message.buildEMessage();
            Request request = Request.valueOf(module, cmd, eMessage.toByteArray());
            messageView.getHandleMessage().sendMessage(request);
        } else {
            Log.e(TAG, "未登录");
        }
    }


}
