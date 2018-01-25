package com.wisn.mainmodule.view.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseActivity;
import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.protocal.service.HandleMessage;
import com.wisn.mainmodule.protocal.service.MessageAService;
import com.wisn.mainmodule.view.MessageView;
import com.wisn.utils.ToastUtils;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/25 10:49
 */


public class MessageActivity extends BaseActivity implements View.OnClickListener,MessageView {
    public static String TAG="MessageActivity";
    private Button message_back;
    private TextView message_title;
    private Button message_info;
    private RecyclerView message_list;
    private EditText message_content;
    private Button message_send;
    private HandleMessage handleMessage;
    private ServiceConnection connection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
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
        Intent intent=new Intent(this, MessageAService.class);
        bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    private void initView() {
        message_back = (Button) findViewById(R.id.message_back);
        message_back.setOnClickListener(this);
        message_title = (TextView) findViewById(R.id.message_title);
        message_title.setOnClickListener(this);
        message_info = (Button) findViewById(R.id.message_info);
        message_info.setOnClickListener(this);
        message_list = (RecyclerView) findViewById(R.id.message_list);
        message_list.setOnClickListener(this);
        message_content = (EditText) findViewById(R.id.message_content);
        message_content.setOnClickListener(this);
        message_send = (Button) findViewById(R.id.message_send);
        message_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.message_back) {
            this.finish();
        } else if (i == R.id.message_info) {
            ToastUtils.show("info");
        } else if (i == R.id.message_send) {

        }
    }

    private void submit() {
        // validate
        String content = message_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "content不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connection!=null){
            unbindService(connection);
        }
    }

    @Override
    public HandleMessage getHandleMessage() {
        return null;
    }

    @Override
    public void updateMoreMessage(List<Message> messageList, boolean isTop) {

    }

    @Override
    public void setMessageList(List<Message> messageList) {

    }

    @Override
    public String getMessageContent() {
        return null;
    }
}
