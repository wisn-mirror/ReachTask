package com.wisn.mainmodule.presenter;

import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.model.IMessageModel;
import com.wisn.mainmodule.model.IUserModel;
import com.wisn.mainmodule.model.impl.ContactMessageModel;
import com.wisn.mainmodule.model.impl.MessageModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.view.ChatView;

/**
 * @author Wisn
 * @time 2018/1/25 16:59
 */


public class HomePresenter {
    public static String TAG = "MessageChatPresenter";
    ChatView messageView;
    IMessageModel messageModel;
    IUserModel userModel;
    ContactMessageModel contactMessageModel;

    public HomePresenter(ChatView messageView) {
        this.messageView = messageView;
        messageModel = new MessageModel();
        userModel = new UserModel();
        contactMessageModel = new ContactMessageModel();
    }

    public void loadMessage(Contact contact) {
      /*  List<Message> messsagesByContactid = messageModel.getMesssagesByContactid(contact.getContactid());
        if (messsagesByContactid != null && messsagesByContactid.size() != 0) {
            messageView.setMessageList(messsagesByContactid);
        }
        contact.setUnReadMessageNumber(0);
        contactMessageModel.saveContacts(contact);*/
    }

}
