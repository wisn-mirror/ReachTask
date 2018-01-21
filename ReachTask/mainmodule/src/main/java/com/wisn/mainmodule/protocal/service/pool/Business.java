package com.wisn.mainmodule.protocal.service.pool;

import java.nio.channels.SocketChannel;

public interface Business {
    void  registerBusinessChannelTask(SocketChannel socketChannel);

}
