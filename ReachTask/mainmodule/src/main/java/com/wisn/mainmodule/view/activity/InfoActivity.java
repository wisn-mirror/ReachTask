package com.wisn.mainmodule.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseActivity;

/**
 * @author Wisn
 * @time 2018/1/26 15:06
 */


public class InfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView imageView;
    private Button sendMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();

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
        }
    }
}
