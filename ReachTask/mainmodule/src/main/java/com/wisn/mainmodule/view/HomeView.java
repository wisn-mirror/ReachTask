package com.wisn.mainmodule.view;

/**
 * @author Wisn
 * @time 2018/2/2 20:39
 */


public interface HomeView {
    /**
     * 提示信息  -1 tip  0 清除消息， >0 为提示消息
     * @param index  0 消息，1 联系人，2 工作 ，3 个人中心
     * @param messageCount
     */
    void updateTipMessage(int index,int messageCount);
}
