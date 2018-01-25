package com.wisn.mainmodule;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.http.HttpApi;
import com.wisn.mainmodule.http.HttpApiService;
import com.wisn.mainmodule.http.response.HttpResponse;
import com.wisn.mainmodule.protocal.coder.Request;
import com.wisn.mainmodule.protocal.coder.Response;
import com.wisn.mainmodule.protocal.protobuf.beans.EMessageMudule;
import com.wisn.mainmodule.protocal.service.ClientManager;
import com.wisn.mainmodule.protocal.service.HandleMessage;
import com.wisn.mainmodule.protocal.service.HandlerByteToMessage;
import com.wisn.mainmodule.protocal.service.MessageAService;
import com.wisn.skinlib.utils.LogUtils;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class TestActivity extends Activity implements View.OnClickListener {
    public static String TAG="TestActivity";

    private TextView mTestResult, shareText, shareTextdelete, register, changePassword, loginOut, login, uploadicon, getusers, connect, sendMessage;
    private EditText url, userName, password, newpassword,host,port;
    private ScrollView mScroll_info;
    private ServiceConnection connection;
    private HandleMessage handleMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = (EditText) findViewById(R.id.url);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        newpassword = (EditText) findViewById(R.id.newpassword);
        host = (EditText) findViewById(R.id.host);
        port = (EditText) findViewById(R.id.port);
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
        connect = (TextView) findViewById(R.id.connect);
        sendMessage = (TextView) findViewById(R.id.sendMessage);
        register.setOnClickListener(this);
        shareText.setOnClickListener(this);
        shareTextdelete.setOnClickListener(this);
        loginOut.setOnClickListener(this);
        login.setOnClickListener(this);
        uploadicon.setOnClickListener(this);
        getusers.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        connect.setOnClickListener(this);
        sendMessage.setOnClickListener(this);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.e(TAG,"MessageAService ");
                MessageAService.HandleMessageImpl service= (MessageAService.HandleMessageImpl) iBinder;
//                DaemonService.HandleMessageImpl service= (DaemonService.HandleMessageImpl) iBinder;
                handleMessage = (HandleMessage) service.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
    }

    @Override
    public void onClick(View view) {
        Intent  intent=new Intent(this, MessageAService.class);
//        Intent  intent=new Intent(this, DaemonService.class);
        String Url =  this.url.getText().toString().trim();
        String userName = this.userName.getText().toString().trim();
        String password =  this.password.getText().toString().trim();
        String newpassword =  this.newpassword.getText().toString().trim();
        HttpApi retrofitService = HttpApiService.createRetrofitService(HttpApi.class, Url);
        final String Host = host.getText().toString().trim();
        final int Port = Integer.parseInt(port.getText().toString().trim());
        if (view == sendMessage) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    EMessageMudule.EMessage eMessage = EMessageMudule.EMessage.newBuilder()
                            .setMessageid(20000)
                            .setFromuserid(222)
                            .setTargetuserid(10000)
                            .setMessagetype(3)
                            .setStatus(-1)
                            .setContent("消息体")
                            .setToken("fdasfdsafdasfdsafdsafdsfdsafdsfdsa")
                            .setCreatetime(System.currentTimeMillis())
                            .setReceivetime(-1).build();
                    Request request = Request.valueOf((short) 1, (short) 2,  eMessage.toByteArray());

                    ClientManager.getInstance().write(request);
                }
            }).start();

        } else if (view == connect) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ClientManager.getInstance().start(Host, Port, new HandlerByteToMessage() {
                        @Override
                        public void receive(Response response) {

                            updateView(response.toString(),true);
                        }
                    });
                }
            }).start();

        } else if (view == changePassword) {

            startService(intent);
        } else if (view == getusers) {
            stopService(intent);
        } else if (view == uploadicon) {
            bindService(intent,connection, Service.BIND_AUTO_CREATE);
        } else if (view == login) {
            unbindService(connection);
        } else if (view == loginOut) {
            handleMessage.sendMessage("hello");
        } else if (view == shareTextdelete) {
            mTestResult.setText("");
        } else if (view == shareText) {

        } else if (view == register) {
            User user=new User();
            user.setPhonenumber(userName);
            user.setPassword(password);
            String s = new Gson().toJson(user);
            LogUtils.e("TestActivity",s);
            RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), s);

            Call<HttpResponse<String>> register = retrofitService.register(body);
            register.enqueue(new Callback<HttpResponse<String>>() {
                @Override
                public void onResponse(Call<HttpResponse<String>> call, retrofit2.Response<HttpResponse<String>> response) {
                    HttpResponse<String> body1 = response.body();
                    updateView(body1.getMessage()+body1.getCode()+body1.getData(),true);
                }

                @Override
                public void onFailure(Call<HttpResponse<String>> call, Throwable t) {

                }
            });
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
