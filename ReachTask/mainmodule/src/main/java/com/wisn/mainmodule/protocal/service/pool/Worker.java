package com.wisn.mainmodule.protocal.service.pool;

import java.nio.channels.SocketChannel;

public interface Worker {
    void  registerWorkerChannelTask(SocketChannel socketChannel);
}
