package com.wisn.mainmodule.protocal.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.protobuf.InvalidProtocolBufferException;
import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.model.impl.ContactMessageModel;
import com.wisn.mainmodule.model.impl.MessageModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.protocal.coder.Request;
import com.wisn.mainmodule.protocal.coder.Response;
import com.wisn.mainmodule.protocal.constant.ResponseCode;
import com.wisn.mainmodule.protocal.protobuf.beans.EMessageMudule;
import com.wisn.mainmodule.utils.Contants;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Wisn
 * @time 2018/1/25 12:14
 */


public class MessageAService extends Service implements HandleMessage {
    private List<MessageChangeListener> messageChangeListeners = new LinkedList<>();

    public static String TAG = "MessageAService";
    private HandleMessageImpl handleMessage;
    private BroadcastReceiver mReceiver;
    private HandlerByteToMessage handlerByteToMessage;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private MessageModel messageModel;
    private ContactMessageModel contactMessageModel;
    private UserModel iUserModel;

    public MessageAService() {
        Log.e(TAG, "MessageAService ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind ");
//        return super.onBind(intent);
        return handleMessage;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind ");
        return super.onUnbind(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate ");
        messageModel = new MessageModel();
        iUserModel = new UserModel();
        handleMessage = new HandleMessageImpl();
        contactMessageModel = new ContactMessageModel();

        handlerByteToMessage = new HandlerByteToMessage() {
            @Override
            public void receive(final Response response) {
                Log.e(TAG, "receiver:" + response.toString());
                if (messageChangeListeners != null) {
                    if (messageChangeListeners.size() > 0) {
                        for (final MessageChangeListener messageChangeListener : messageChangeListeners) {
                            try {
                                EMessageMudule.EMessage eMessage = EMessageMudule.EMessage.parseFrom(response.getData());
                                //新的消息
                                final Message message = new Message().valueOf(eMessage);

                                if (response.getResultCode() == ResponseCode.newMessage) {
                                    Log.e(TAG, "newMessage ");
                                    // TODO: 2018/1/26 添加联系关系
                                    long contactid = Long.parseLong(String.valueOf(message.getTargetuserid()) + String.valueOf(message.getFromuserid()));
                                    Contact contact = contactMessageModel.getContactByTargetid(message.getFromuserid());
                                    if (contact == null) {
                                        User userbyUserid = iUserModel.getUserbyUserid(message.getFromuserid());
                                        contact = new Contact();
                                        contact.setContactid(contactid);
                                        contact.setFromuserid(message.getTargetuserid());
                                        contact.setTargetuserid(userbyUserid.getUserid());
                                        contact.setIcon(userbyUserid.getIconurl());
                                        contact.setName(userbyUserid.getNickname());
                                        contactMessageModel.saveContacts(contact);
                                    }
                                    // TODO: 2018/1/26 存数据库
                                    messageModel.saveMessage(message);
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            messageChangeListener.newMessage(response.getModule(), response.getCmd(), message);
                                        }
                                    });

                                } else {
                                    Log.e(TAG, "receiptMessage ");
                                    //回执消息
                                    messageChangeListener.receiptMessage(response.getModule(), response.getCmd(), message.getMessageid(), message.getReceivetime(), response.getResultCode());
                                }
                            } catch (InvalidProtocolBufferException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        };
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Intent a = new Intent(MessageAService.this, DaemonService.class);
                startService(a);
            }
        };
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                ClientManager.getInstance().start(Contants.messageHost, Contants.messagePort, handlerByteToMessage);
                Log.e(TAG, "connect  ");
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        //自定义action
        intentFilter.addAction("com.wisn.service.daemonservice");
        //注册广播接者
        registerReceiver(mReceiver, intentFilter);
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy ");
        Intent intent = new Intent();
        intent.setAction("com.wisn.service.messageaservice");
        sendBroadcast(intent);
        unregisterReceiver(mReceiver);
    }

    public class HandleMessageImpl extends Binder {
        @Override
        public String getInterfaceDescriptor() {
            return super.getInterfaceDescriptor();
        }

        @Override
        public boolean isBinderAlive() {
            return super.isBinderAlive();
        }

        public MessageAService getService() {
            return MessageAService.this;
        }
    }

    @Override
    public void sendMessage(final Request request) {
        Log.e(TAG, "sendMessage: " + request);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (ClientManager.getInstance().isActive()) {
                    ClientManager.getInstance().write(request);
                } else {
                    //保存未发送消息
                    Log.e(TAG, "未发送消息sendMessage: " + request);
                    ClientManager.getInstance().tryConnectServer();
                }
            }
        });
    }

    @Override
    public void addMessageListener(MessageChangeListener messageChangeListener) {
        if (messageChangeListeners != null){
            Log.e(TAG,messageChangeListener+" is added");
            messageChangeListeners.add(messageChangeListener);
        }
    }

    @Override
    public void removeMessageListener(MessageChangeListener messageChangeListener) {
        if (messageChangeListeners != null){
            Log.e(TAG,messageChangeListener+" is removed");
            messageChangeListeners.remove(messageChangeListener);
        }
    }
}
