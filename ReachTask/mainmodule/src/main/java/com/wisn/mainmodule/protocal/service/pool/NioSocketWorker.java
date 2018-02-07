package com.wisn.mainmodule.protocal.service.pool;


import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

public class NioSocketWorker extends AbstractNioSelector implements Worker {

    public NioSocketWorker(Executor executor, String threadName, NioSelectorRunnablePool nioSelectorRunnablePool) {
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
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();
                nioSelectorRunnablePool.getHandlerByteToMessage().read(next);

//                SocketChannel channel = (SocketChannel) next.channel();
//                channel.configureBlocking(false);
//                Business business = nioSelectorRunnablePool.nextBusiness();
//                business.registerBusinessChannelTask(channel);
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public void registerWorkerChannelTask(final SocketChannel socketChannel) {
        registerTask(new Runnable() {
            @Override
            public void run() {
                try {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
