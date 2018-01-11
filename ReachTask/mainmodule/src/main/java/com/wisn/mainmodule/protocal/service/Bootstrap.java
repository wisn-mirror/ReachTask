package com.wisn.mainmodule.protocal.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Bootstrap {
    private Selector selector;
    SocketChannel channel;
    HandlerByteToMessage byteToMessage;
    private String ip;
    private int port;

    public void bind(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = Selector.open();
        channel.connect(new InetSocketAddress(ip, port));
        channel.register(selector, SelectionKey.OP_CONNECT);
        listen();
    }

    public void handleMessage(HandlerByteToMessage byteToMessage) {
        this.byteToMessage = byteToMessage;
    }

    public void listen() throws IOException {
        while (true) {
            selector.select();
            // 获得selector中选中的项的迭代器
            Iterator ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = (SelectionKey) ite.next();
                ite.remove();
                if (key.isConnectable()) {
                    channel = (SocketChannel) key
                            .channel();
                    if (channel.isConnectionPending()) {
                        channel.finishConnect();
                    }
                    channel.configureBlocking(false);
                    channel.register(this.selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    if (byteToMessage != null) {
                        byteToMessage.read(key);
                    }
                }
            }
        }
    }

    public int write(ByteBuffer byteBuffer) {
        try {
            if (channel != null && channel.isConnected()) {
                return channel.write(byteBuffer);
            } else {
                bind(ip, port);
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
