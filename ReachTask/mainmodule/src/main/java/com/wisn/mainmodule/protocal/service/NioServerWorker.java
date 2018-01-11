package com.wisn.mainmodule.protocal.service;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

public class NioServerWorker extends AbstractNioSelector  {

    public NioServerWorker(Executor executor, String threadName) {
        super(executor, threadName);
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
                SocketChannel channel = (SocketChannel) next.channel();
                ByteBuffer allocate = ByteBuffer.allocate(256);
                int read = channel.read(allocate);
                if (read != -1) {
                    String msg = new String(allocate.array()).trim();
                    System.out.println("server received message:" + msg);
                    channel.write(ByteBuffer.wrap((msg + new Date()).getBytes()));
                } else {
                    channel.close();
                }
            }
        } catch (IOException e) {
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
