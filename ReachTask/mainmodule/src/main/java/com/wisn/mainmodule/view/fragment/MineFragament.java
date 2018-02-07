package com.wisn.mainmodule.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseLazyFragment;
import com.wisn.mainmodule.entity.Moment;
import com.wisn.mainmodule.http.response.HttpResponse;
import com.wisn.mainmodule.model.impl.MomentModel;
import com.wisn.mainmodule.model.impl.UserModel;
import com.wisn.mainmodule.presenter.HttpCallback;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.activity.SelectImageListActivity;

import java.util.ArrayList;
import java.util.List;

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
//        updateIcon();
//        sendMoment();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            ArrayList<String> parcelableArrayListExtra =   data.getStringArrayListExtra(Contants.Select_Result);
            for(String str:parcelableArrayListExtra){
                System.err.println("file:"+str);
            }
        }
    }

    private void sendMoment() {
        MomentModel model=new MomentModel();
        List<String> data=new ArrayList<>();
        data.add("/storage/emulated/0/aaa.jpg");
        data.add("/storage/emulated/0/aaa.jpg");
        data.add("/storage/emulated/0/aaa.jpg");
        data.add("/storage/emulated/0/aaa.jpg");
        data.add("/storage/emulated/0/aaa.jpg");
        model.sendMoment("android test","日本",data,data, new HttpCallback<String>() {
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
        model.getMoments(0, 1000, new HttpCallback<List<Moment>>() {
            @Override
            public void onSuccess(HttpResponse<List<Moment>> response) {
                Log.e(getTAG(),"response:"+response);
            }

            @Override
            public void onError(String msg) {
                Log.e(getTAG(),"onError msg:"+msg);
            }

            @Override
            public void onFinsh() {

            }
        });
    }

    private void updateIcon() {
        UserModel model=new UserModel();
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
    }
}
