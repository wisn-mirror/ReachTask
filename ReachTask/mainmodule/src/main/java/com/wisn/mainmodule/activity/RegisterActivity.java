package com.wisn.mainmodule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseActivity;

/**
 * @author Wisn
 * @time 2018/1/23 21:26
 */


public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private TextView login;
    private EditText password;
    private EditText username;
    private EditText userid;
    private TextView forgetpassword;
    private Button register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }

    private void initView() {
        login = (TextView) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.username);
        userid = (EditText) findViewById(R.id.userid);
        forgetpassword = (TextView) findViewById(R.id.forgetpassword);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == login) {
          startActivity(new Intent(this,LoginActivity.class));
          this.finish();
        } else if (v == register) {
            submit();
        }
    }

    private void submit() {
        // validate
        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "password is null ", Toast.LENGTH_SHORT).show();
            return;
        }

        String usernameString = username.getText().toString().trim();
        if (TextUtils.isEmpty(usernameString)) {
            Toast.makeText(this, "Phone is null ", Toast.LENGTH_SHORT).show();
            return;
        }

        String useridString = userid.getText().toString().trim();
        if (TextUtils.isEmpty(useridString)) {
            Toast.makeText(this, "Name is null ", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
