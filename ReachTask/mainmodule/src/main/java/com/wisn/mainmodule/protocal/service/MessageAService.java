package com.wisn.mainmodule.protocal.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author Wisn
 * @time 2018/1/25 12:14
 */


public class MessageAService extends IntentService implements HandleMessage{
    public static String TAG="MessageAService";
    private HandleMessageImpl handleMessage;

    public MessageAService(String name) {
        super(name);
        Log.e(TAG,"MessageAService ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind ");
//        return super.onBind(intent);
        return handleMessage;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"onUnbind ");
        return super.onUnbind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(TAG,"onHandleIntent ");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate ");
        handleMessage = new HandleMessageImpl();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand ");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy ");

    }

    public class HandleMessageImpl  extends Binder  {
        @Override
        public String getInterfaceDescriptor() {
            return super.getInterfaceDescriptor();
        }

        @Override
        public boolean isBinderAlive() {
            return super.isBinderAlive();
        }
        public  MessageAService getService(){
            return MessageAService.this;
        }
    }

    @Override
    public void sendMessage( String msg) {
        Log.e(TAG,"sendMessage: "+msg);
    }
}
