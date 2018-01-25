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

/**
 * @author Wisn
 * @time 2018/1/25 12:14
 * 用于监听MessageAService的存活
 */


public class DaemonService extends Service implements HandleMessage{
    public static String TAG="DaemonService";
    private HandleMessageImpl handleMessage;
    private BroadcastReceiver mReceiver;

    public DaemonService() {
//        super("MessageAService");
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
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate ");
        handleMessage = new HandleMessageImpl();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Intent a = new Intent(DaemonService.this, MessageAService.class);
                startService(a);
            }
        };
        IntentFilter  intentFilter = new IntentFilter();
        //自定义action
        intentFilter.addAction("com.wisn.service.messageaservice");
        //注册广播接者
        registerReceiver(mReceiver, intentFilter);
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
        Intent intent = new Intent();
        intent.setAction("com.wisn.service.daemonservice");
        sendBroadcast(intent);
        unregisterReceiver(mReceiver);
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
        public DaemonService getService(){
            return DaemonService.this;
        }
    }

    @Override
    public void sendMessage( String msg) {
        Log.e(TAG,"sendMessage: "+msg);
    }
}
