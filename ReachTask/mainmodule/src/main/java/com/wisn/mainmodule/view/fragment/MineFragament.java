package com.wisn.mainmodule.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseLazyFragment;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.presenter.MineFragmentPresenter;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.MineFragmentView;
import com.wisn.mainmodule.view.activity.MyInfoActivity;
import com.wisn.mainmodule.view.activity.MyPhotoActivity;
import com.wisn.mainmodule.widget.CombinationListItemLayout;
import com.wisn.utils.ToastUtils;

import java.util.ArrayList;

/**
 * @author Wisn
 * @time 2018/1/23 20:42
 */


public class MineFragament extends BaseLazyFragment implements View.OnClickListener ,MineFragmentView {

    private ImageView info_photo;
    private TextView info_id;
    private TextView info_nickname;
    private ImageView info_code;
    private CombinationListItemLayout moment;
    private CombinationListItemLayout scan;
    private CombinationListItemLayout mycollection;
    private CombinationListItemLayout setting;
    private LinearLayout info;
    private MineFragmentPresenter  mineFragmentPresenter;
    private RequestOptions options;

    @Override
    public String getTAG() {
        return "MineFragament";
    }

    @Override
    public View onCreateLazyView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);
        if(mineFragmentPresenter!=null){
            mineFragmentPresenter.load();
        }
        return view;
    }

    @Override
    public void firstVisible() {
        super.firstVisible();
        mineFragmentPresenter=new MineFragmentPresenter(this);
        options = new RequestOptions();
        options.centerCrop()
                .placeholder(R.drawable.photo)
                .error(R.drawable.photo)
                .fallback(R.drawable.photo);
    }

    @Override
    public void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
    }

    private void initView(View view) {
        info = (LinearLayout) view.findViewById(R.id.info);
        info_photo = (ImageView) view.findViewById(R.id.info_photo);
        info.setOnClickListener(this);
        info_photo.setOnClickListener(this);
        info_id = (TextView) view.findViewById(R.id.info_id);
        info_nickname = (TextView) view.findViewById(R.id.info_nickname);
        info_code = (ImageView) view.findViewById(R.id.info_code);
        info_code.setOnClickListener(this);
        moment = (CombinationListItemLayout) view.findViewById(R.id.moment);
        moment.setOnClickListener(this);
        scan = (CombinationListItemLayout) view.findViewById(R.id.scan);
        scan.setOnClickListener(this);
        mycollection = (CombinationListItemLayout) view.findViewById(R.id.mycollection);
        mycollection.setOnClickListener(this);
        setting = (CombinationListItemLayout) view.findViewById(R.id.setting);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == info) {
            ToastUtils.show("info");
            startActivity(new Intent(getActivity(),MyInfoActivity.class));
        } else if (v == info_photo) {
            ToastUtils.show("infoPhoto");
            startActivity(new Intent(getActivity(), MyPhotoActivity.class));
        } else if (v == info_code) {
            ToastUtils.show("info_code");
        } else if (v == moment) {
            ToastUtils.show("moment");
        } else if (v == scan) {
            ToastUtils.show("scan");
        } else if (v == mycollection) {
            ToastUtils.show("mycollection");
        } else if (v == setting) {
            ToastUtils.show("setting");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            ArrayList<String> parcelableArrayListExtra = data.getStringArrayListExtra(Contants.Select_Result);
            for (String str : parcelableArrayListExtra) {
                System.err.println("file:" + str);
            }
        }
    }

    @Override
    public void updateInfo(User user) {
        Glide.with(this)
                .load(Contants.baseImage+ user.getIconurl())
                .apply(options).into(info_photo);
        info_id.setText(user.getNameid());
        info_nickname.setText(user.getNickname());
    }


/*
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
    }*/
}
