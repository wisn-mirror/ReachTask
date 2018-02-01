package com.wisn.mainmodule.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseLazyFragment;
import com.wisn.mainmodule.http.response.HttpResponse;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.presenter.HttpCallback;

/**
 * @author Wisn
 * @time 2018/1/23 20:42
 */


public class MineFragament extends BaseLazyFragment{
    @Override
    public String getTAG() {
        return "MineFragament";
    }
    @Override
    public View onCreateLazyView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
//        Bundle bundle = this.getArguments();
////        String tag = bundle.getString("TAG");
//        TextView textView = (TextView) view.findViewById(R.id.fragment_textView);
//        textView.setText("MineFragament");
        UserModel  model=new UserModel();
        model.updateIcon("/storage/emulated/0/aaa.jpg", new HttpCallback<String>() {
            @Override
            public void onSuccess(HttpResponse<String> response) {
                Log.e(getTAG(),"response:"+response);
            }

            @Override
            public void onError(String msg) {
                Log.e(getTAG(),"onError msg:"+msg);
            }

            @Override
            public void onFinsh() {
                Log.e(getTAG(),"onFinsh  ");
            }
        });
        return view;
    }
}
