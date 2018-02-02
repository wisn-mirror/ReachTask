package com.wisn.mainmodule.protocal.service;


import com.wisn.mainmodule.protocal.coder.Request;
import com.wisn.mainmodule.protocal.service.pool.NioSelectorRunnablePool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

public class ClientManager {
    private String ip;
    private int port;
    private HandlerByteToMessage handlerByteToMessage;
    private SocketChannel mConnect;
    private NioSelectorRunnablePool mNioSelectorRunnablePool;
    private static ClientManager clientManager = null;
    private ClientBootstrap serverBootstrap;
    private InetSocketAddress inetSocketAddress;
    private Timer timer;

    private ClientManager() {
    }

    public static ClientManager getInstance() {
        if (clientManager == null) {
            synchronized (ClientManager.class) {
                if (clientManager == null) {
                    clientManager = new ClientManager();
                }
            }
        }
        return clientManager;
    }

    public boolean isActive() {
        if (mConnect == null || !mConnect.isConnected()) {
            return false;
        }
        return true;
    }

    public void start(String ip, int port, HandlerByteToMessage handlerByteToMessage) {
        this.ip = ip;
        this.port = port;
        this.handlerByteToMessage = handlerByteToMessage;
        if (mNioSelectorRunnablePool == null) {
            mNioSelectorRunnablePool = new NioSelectorRunnablePool(Executors.newCachedThreadPool(),
                    Executors.newCachedThreadPool(),
                    Executors.newCachedThreadPool(),
                    this.handlerByteToMessage);
        }

        serverBootstrap = new ClientBootstrap(mNioSelectorRunnablePool);
        inetSocketAddress = new InetSocketAddress(ip, port);
        mConnect = serverBootstrap.connect(inetSocketAddress);

    }

    public void tryConnectServer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mNioSelectorRunnablePool != null && serverBootstrap != null && mConnect != null&& ! mConnect.isConnected()) {
                    mConnect = serverBootstrap.connect(inetSocketAddress);
                }else{
                    timer.cancel();
                }
            }
        }, new Date(), 2000);
    }

    public boolean write(Request request) {
        try {
            if (mConnect == null) {
                if (ip == null || port == 0) return false;
                start(ip, port, this.handlerByteToMessage);
            }
            if (mConnect.isConnectionPending()) {
                mConnect.finishConnect();
            }
            if (mConnect.isConnected()) {
                mConnect.write(handlerByteToMessage.getBytes(request));
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            mConnect = serverBootstrap.connect(inetSocketAddress);
        }
        return false;
    }

}
