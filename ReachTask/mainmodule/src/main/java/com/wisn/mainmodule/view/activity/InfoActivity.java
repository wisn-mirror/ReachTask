package com.wisn.mainmodule.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.presenter.InfoPresenter;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.InfoView;
import com.wisn.mainmodule.view.viewholder.ToolbarHolder;

/**
 * @author Wisn
 * @time 2018/1/26 15:06
 */


public class InfoActivity extends BaseAppCompatActivity implements View.OnClickListener, InfoView {
    private User user;
    private InfoPresenter infoPresenter;
    private ImageView info_photo;
    private TextView info_tag_nickname;
    private TextView info_nameid;
    private TextView info_nickname;
    private ImageView info_tip;
    private TextView info_seting_tag;
    private TextView info_photo_number;
    private TextView info_location;
    private TextView info_more;
    private Button info_sendMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getParcelableExtra(Contants.user_flag);
        initView();
        infoPresenter = new InfoPresenter(this);
    }

    @Override
    public void initToolbarView(ToolbarHolder toolbar) {
        toolbar.getToolbar().setTitle("详细资料");
        toolbar.getToolbar().setNavigationIcon(R.drawable.back);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_info;
    }

    private void initView() {
        info_photo = (ImageView) findViewById(R.id.info_photo);
        info_photo.setOnClickListener(this);
        info_tag_nickname = (TextView) findViewById(R.id.info_tag_nickname);
        info_tag_nickname.setOnClickListener(this);
        info_nameid = (TextView) findViewById(R.id.info_nameid);
        info_nameid.setOnClickListener(this);
        info_nickname = (TextView) findViewById(R.id.info_nickname);
        info_nickname.setOnClickListener(this);
        info_tip = (ImageView) findViewById(R.id.info_tip);
        info_tip.setOnClickListener(this);
        info_seting_tag = (TextView) findViewById(R.id.info_seting_tag);
        info_seting_tag.setOnClickListener(this);
        info_photo_number = (TextView) findViewById(R.id.info_photo_number);
        info_photo_number.setOnClickListener(this);
        info_location = (TextView) findViewById(R.id.info_location);
        info_location.setOnClickListener(this);
        info_more = (TextView) findViewById(R.id.info_more);
        info_more.setOnClickListener(this);
        info_sendMessage = (Button) findViewById(R.id.info_sendMessage);
        info_sendMessage.setOnClickListener(this);
        if(user!=null){
             RequestOptions options = new RequestOptions().centerCrop()
                    .placeholder(R.drawable.photo)
                    .error(R.drawable.photo)
                    .fallback(R.drawable.photo);
            Glide.with(InfoActivity.this)
                    .load(Contants.baseImage + user.getIconurl())
                    .apply(options).into(info_photo);
            info_photo_number.setText(user.getPhonenumber());
            info_tag_nickname.setText(user.getNickname());
            info_nameid.setText(user.getNameid());
            info_nickname.setText(user.getNickname());

        }
    }

    @Override
    public void onClick(View v) {
        if (v== info_sendMessage) {
            infoPresenter.sendMessage();
        }
    }

    @Override
    public User getTargetUser() {
        return user;
    }

    @Override
    public void startMessage(Contact contact) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(Contants.user_flag, user);
        intent.putExtra(Contants.contact_flag, contact);
        startActivity(intent);
    }
}
