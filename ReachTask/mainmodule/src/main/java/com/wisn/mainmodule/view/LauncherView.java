package com.wisn.mainmodule.view;

/**
 * @author Wisn
 * @time 2018/1/27 21:13
 */


public interface LauncherView {
    /**
     *
     * @param clazz  下一个activity
     * @param delaytime 延迟几秒
     */
    void startNextActivity(Class clazz,int delaytime);
}
