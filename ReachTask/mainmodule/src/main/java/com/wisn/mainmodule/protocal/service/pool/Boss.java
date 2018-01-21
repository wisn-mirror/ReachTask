package com.wisn.mainmodule.protocal.service.pool;


import java.nio.channels.SocketChannel;


public interface Boss {
   void  registerAcceptChannelTask(SocketChannel serverSocketChannel);
}
