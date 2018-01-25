package com.wisn.mainmodule.protocal.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wisn.mainmodule.protocal.coder.Request;
import com.wisn.mainmodule.protocal.coder.Response;
import com.wisn.mainmodule.utils.Contants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Wisn
 * @time 2018/1/25 12:14
 */


public class MessageAService extends Service implements HandleMessage {
    public static String TAG = "MessageAService";
    private HandleMessageImpl handleMessage;
    private BroadcastReceiver mReceiver;
    private HandlerByteToMessage handlerByteToMessage;
    ExecutorService executorService = Executors.newCachedThreadPool();
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
        handleMessage = new HandleMessageImpl();
        handlerByteToMessage = new HandlerByteToMessage() {
            @Override
            public void receive(Response response) {
                showMessage(response);
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
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        //自定义action
        intentFilter.addAction("com.wisn.service.daemonservice");
        //注册广播接者
        registerReceiver(mReceiver, intentFilter);
    }

    public void showMessage(Response response) {

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

                }
            }
        });
    }
}
