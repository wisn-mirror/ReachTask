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

    public void start(String ip, int port) {
        this.ip = ip;
        this.port = port;
        if (handlerByteToMessage == null) {
            handlerByteToMessage = new HandlerByteToMessage();
        }
        if (mNioSelectorRunnablePool == null) {
            mNioSelectorRunnablePool = new NioSelectorRunnablePool(Executors.newCachedThreadPool(),
                                                                   Executors.newCachedThreadPool(),
                                                                   Executors.newCachedThreadPool(),
                                                                   handlerByteToMessage);
        }

        ClientBootstrap serverBootstrap = new ClientBootstrap(mNioSelectorRunnablePool);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
        mConnect = serverBootstrap.connect(inetSocketAddress);
    }

    public boolean write(Request request) {
        try {
            if (mConnect == null) {
                start(ip, port);
                return false;
            }
            if (mConnect.isConnectionPending()) {
                mConnect.finishConnect();
            }
            mConnect.write(handlerByteToMessage.getBytes(request));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
