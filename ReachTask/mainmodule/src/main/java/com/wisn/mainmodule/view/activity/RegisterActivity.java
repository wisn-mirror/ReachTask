package com.wisn.mainmodule.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.http.request.Register;
import com.wisn.mainmodule.presenter.RegisterPresenter;
import com.wisn.mainmodule.view.RegisterView;
import com.wisn.utils.ToastUtils;

/**
 * @author Wisn
 * @time 2018/1/23 21:26
 */


public class RegisterActivity extends BaseAppCompatActivity implements View.OnClickListener ,RegisterView{
    private TextView login;
    private EditText password;
    private EditText username;
    private EditText userid;
    private TextView forgetpassword;
    private Button register;
    private RegisterPresenter registerPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        registerPresenter=new RegisterPresenter(this);

    }

    @Override
    public void initToolbarView(Toolbar toolbar) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
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
        registerPresenter.register();

    }

    @Override
    public Register getUser() {
        Register register=new Register();
        register.setPassword(password.getText().toString().trim());
        register.setPhonenumber(username.getText().toString().trim());
        register.setNameid(userid.getText().toString().trim());
        return register;
    }

    @Override
    public void registerError(String msg) {
        if(msg==null)return ;
        ToastUtils.show(msg);
    }

    @Override
    public void registerSuccess(String msg) {
        ToastUtils.show(msg);
//        startActivity(new Intent(this,LoginActivity.class));
        this.finish();
    }

    @Override
    public void showRegisterProgress() {

    }

    @Override
    public void hideRegisterPregress() {

    }
}
