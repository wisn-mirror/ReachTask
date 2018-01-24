package com.wisn.mainmodule.presenter;

import com.wisn.mainmodule.http.response.HttpResponse;

/**
 * @author Wisn
 * @time 2018/1/24 14:50
 */


public interface HttpCallback<T> {
     void onSuccess(HttpResponse<T> response);
     void onError(String msg);
}
