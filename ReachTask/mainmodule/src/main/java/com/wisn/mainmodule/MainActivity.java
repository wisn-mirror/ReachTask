package com.wisn.mainmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mTestResult,    shareText,  shareTextdelete,register, changePassword, loginOut, login,uploadicon,getusers ;
    private EditText url, userName, password, newpassword;
    private ScrollView mScroll_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = (EditText) findViewById(R.id.url);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        newpassword = (EditText) findViewById(R.id.newpassword);
        mScroll_info = (ScrollView) findViewById(R.id.scroll_info);
        mTestResult = (TextView) findViewById(R.id.testResult);
        shareTextdelete = (TextView) findViewById(R.id.shareTextdelete);
        shareText = (TextView) findViewById(R.id.shareText);
        register = (TextView) findViewById(R.id.register);
        login = (TextView) findViewById(R.id.login);
        uploadicon = (TextView) findViewById(R.id.uploadicon);
        getusers = (TextView) findViewById(R.id.getusers);
        changePassword = (TextView) findViewById(R.id.changePassword);
        loginOut = (TextView) findViewById(R.id.loginOut);
        register.setOnClickListener(this);
        shareText.setOnClickListener(this);
        shareTextdelete.setOnClickListener(this);
        loginOut.setOnClickListener(this);
        login.setOnClickListener(this);
        uploadicon.setOnClickListener(this);
        getusers.setOnClickListener(this);
        changePassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==changePassword){

        }
    }

    public void updateView(final String msg, final boolean isAppend) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isAppend) {
                    mTestResult.append(msg + "\n");
                } else {
                    mTestResult.setText(msg);
                }
                mScroll_info.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
            }
        });
    }
}
