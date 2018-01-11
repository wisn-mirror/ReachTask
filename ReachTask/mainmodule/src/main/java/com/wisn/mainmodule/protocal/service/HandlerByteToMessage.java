package com.wisn.mainmodule.protocal.service;

import com.wisn.mainmodule.protocal.coder.Request;
import com.wisn.mainmodule.protocal.coder.Response;
import com.wisn.mainmodule.protocal.constant.ConstantValues;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class HandlerByteToMessage {

    ByteBuffer bytebuffer;
    ByteBuffer mInt = ByteBuffer.allocate(4);
    ByteBuffer mshort = ByteBuffer.allocate(2);
    private short result;
    private short cmd;
    private short module;

    public void read(SelectionKey key) throws IOException {
        if (key == null) return;
        SocketChannel channel = (SocketChannel) key.channel();
        mInt.clear();
        int readHeader = channel.read(mInt);
        if (readHeader == -1) return;
        mInt.flip();
        int anInt = mInt.getInt();
        if (anInt == ConstantValues.MESSAGE_HEADTAG) {
            //重新读取
            resetMark();
        }
        if (module == -100) {
            for (int i = 0; i < 3; i++) {
                mshort.clear();
                int readModule = channel.read(mshort);
                if (readModule == -1) return;
                mshort.flip();
                short value = mshort.getShort();
                if (i == 0) {
                    module = value;
                } else if (i == 1) {
                    cmd = value;
                } else {
                    result = value;
                }
            }
        }
        if (cmd == -100) {
            for (int i = 1; i < 3; i++) {
                mshort.clear();
                int readModule = channel.read(mshort);
                if (readModule == -1) return;
                mshort.flip();
                short value = mshort.getShort();
                if (i == 1) {
                    cmd = value;
                } else {
                    result = value;
                }
            }
        }
        if (result == -100) {
            for (int i = 2; i < 3; i++) {
                mshort.clear();
                int readModule = channel.read(mshort);
                if (readModule == -1) return;
                mshort.flip();
                short value = mshort.getShort();
                result = value;
            }
        }
        readData(channel);
    }

    private boolean readData(SocketChannel channel) throws IOException {
        mInt.clear();
        int readLength = channel.read(mInt);
        if (readLength == -1) {
            return false;
        }
        mInt.flip();
        int length = mInt.getInt();
        if (length == 0) {
            receive(Response.valueOf(module, cmd, result, null));
            return true;
        }
        if (length > 2048) {
            return false;
        }
        if (bytebuffer != null) {
            bytebuffer.clear();
        } else {
            bytebuffer = ByteBuffer.allocate(length);
        }
        int readData = channel.read(bytebuffer);
        if (readData == -1) {
            return false;
        }
        receive(Response.valueOf(module, cmd, result, bytebuffer.array()));
        return true;
    }

    private void resetMark() {
        module = cmd = result = -100;
    }

    public ByteBuffer getBytes(Request request) {
        int dataLength = request.getDataLength();
        ByteBuffer byteBuf = ByteBuffer.allocate(dataLength + 12);
        byteBuf.putInt(request.getTAG());
        byteBuf.putShort(request.getModule());
        byteBuf.putShort(request.getCmd());
        byteBuf.putInt(dataLength);
        if (dataLength != 0) {
            byteBuf.put(request.getData());
        }
        System.out.println("send:" + Arrays.toString(byteBuf.array()));
        byteBuf.flip();
        return byteBuf;
    }

    public void receive(Response response) {
        System.out.println("client:" + response);
    }
}
