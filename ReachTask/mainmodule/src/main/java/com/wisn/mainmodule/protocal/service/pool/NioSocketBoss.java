package com.wisn.mainmodule.protocal.service.pool;


import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

public class NioSocketBoss extends  AbstractNioSelector implements Boss {

    public NioSocketBoss(Executor executor, String threadName, NioSelectorRunnablePool nioSelectorRunnablePool) {
        super(executor, threadName, nioSelectorRunnablePool);
    }

    @Override
    public void selector(Selector selector) {
        try {
            selector.select();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(Selector selector) {
        try {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator =selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                SocketChannel channel = (SocketChannel) key.channel();
                if (channel.isConnectionPending()) {
                    channel.finishConnect();
                }
                channel.configureBlocking(false);
                Worker worker = nioSelectorRunnablePool.nextWorker();
                worker.registerWorkerChannelTask(channel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerAcceptChannelTask(final SocketChannel serversocketchannel) {
        registerTask(new Runnable() {
            @Override
            public void run() {
                try {
                    serversocketchannel.register(selector,SelectionKey.OP_CONNECT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
