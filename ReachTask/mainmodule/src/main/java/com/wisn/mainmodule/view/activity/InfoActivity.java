package com.wisn.mainmodule.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.presenter.InfoPresenter;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.InfoView;

/**
 * @author Wisn
 * @time 2018/1/26 15:06
 */


public class InfoActivity extends BaseAppCompatActivity implements View.OnClickListener,InfoView {
    private ImageView imageView;
    private Button sendMessage;
    private User user;
    private InfoPresenter infoPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
        user= (User) getIntent().getParcelableExtra(Contants.user_flag);
        infoPresenter=new InfoPresenter(this);
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        sendMessage = (Button) findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sendMessage) {
            infoPresenter.sendMessage();
        }
    }

    @Override
    public User getTargetUser() {
        return user;
    }

    @Override
    public void startMessage(Contact contact) {
        Intent intent=   new Intent(this, ChartActivity.class);
        intent.putExtra(Contants.user_flag, user);
        intent.putExtra(Contants.contact_flag, contact);
        startActivity(intent);
    }
}
