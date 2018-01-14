package com.wisn.mainmodule.protocal.service;

import com.wisn.mainmodule.protocal.service.pool.Boss;
import com.wisn.mainmodule.protocal.service.pool.NioSelectorRunnablePool;

import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class ClientBootstrap {
    private NioSelectorRunnablePool nioSelectorRunnablePool;

    SocketChannel channel;
    public ClientBootstrap(NioSelectorRunnablePool nioSelectorRunnablePool) {
        this.nioSelectorRunnablePool = nioSelectorRunnablePool;
    }

    public SocketChannel connect(SocketAddress socketAddress) {
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(socketAddress);
            Boss boss = nioSelectorRunnablePool.nextBoss();
            boss.registerAcceptChannelTask(channel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }
}
