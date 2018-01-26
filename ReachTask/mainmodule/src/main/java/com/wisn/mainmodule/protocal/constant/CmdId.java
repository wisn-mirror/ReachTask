package com.wisn.mainmodule.protocal.constant;

public interface CmdId {
    interface AuthMessage {
        short register = 1;
    }

    interface ChartMessage {
        short sendMessageToOne = 1;
        short sendMessageToAll = 2;
    }

    interface SystemMessage {
        short sendMessageToOne = 1;
        short sendMessageToAll = 2;
    }
}
