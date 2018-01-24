package com.wisn.mainmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseActivity;

/**
 * @author Wisn
 * @time 2018/1/23 21:26
 */


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button login;
    private EditText password;
    private EditText username;
    private ImageView imageView;
    private TextView forgetpassword;
    private TextView register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        login = (Button) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);
        imageView = (ImageView) findViewById(R.id.imageView);
        forgetpassword = (TextView) findViewById(R.id.forgetpassword);
        register = (TextView) findViewById(R.id.register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            submit();
        } else if (v == register) {
            startActivity(new Intent(this,RegisterActivity.class));
            this.finish();
        }
    }

    private void submit() {
        // validate
        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "password is null", Toast.LENGTH_SHORT).show();
            return;
        }

        String usernameString = username.getText().toString().trim();
        if (TextUtils.isEmpty(usernameString)) {
            Toast.makeText(this, "Phone is null ", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}