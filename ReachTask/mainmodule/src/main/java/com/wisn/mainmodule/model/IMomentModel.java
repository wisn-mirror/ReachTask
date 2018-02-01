package com.wisn.mainmodule.model;

import com.wisn.mainmodule.entity.Moment;
import com.wisn.mainmodule.presenter.HttpCallback;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/2/1 11:39
 */


public interface IMomentModel {
    List<Moment> getMoments(int offset ,int limit);
    void sendMoment(String content,String location,List<String> imagepath,List<String> videopath, HttpCallback<String> callback);

}
