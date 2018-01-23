package com.wisn.mainmodule.protocal.service;


import com.wisn.mainmodule.protocal.coder.Request;
import com.wisn.mainmodule.protocal.service.pool.NioSelectorRunnablePool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;

public class ClientManager {
    private String ip;
    private int port;
    private HandlerByteToMessage handlerByteToMessage;
    private SocketChannel mConnect;
    private NioSelectorRunnablePool mNioSelectorRunnablePool;
    private static ClientManager clientManager = null;

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

        ClientBootstrap serverBootstrap = new ClientBootstrap(mNioSelectorRunnablePool);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
        mConnect = serverBootstrap.connect(inetSocketAddress);
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
        }
        return false;
    }

}
