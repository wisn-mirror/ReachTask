package com.wisn.mainmodule.view.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.entity.User;
import com.wisn.mainmodule.presenter.MessageChatPresenter;
import com.wisn.mainmodule.protocal.constant.CmdId;
import com.wisn.mainmodule.protocal.constant.ModuleId;
import com.wisn.mainmodule.protocal.service.HandleMessage;
import com.wisn.mainmodule.protocal.service.MessageAService;
import com.wisn.mainmodule.protocal.service.MessageChangeListener;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.ChatView;
import com.wisn.mainmodule.view.viewholder.TextMessageLeftHolder;
import com.wisn.mainmodule.view.viewholder.ToolbarHolder;
import com.wisn.skinlib.utils.LogUtils;
import com.wisn.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/25 10:49
 */


public class ChatActivity extends BaseAppCompatActivity implements View.OnClickListener, ChatView, MessageChangeListener {
    public static String TAG = "ChatActivity";
    private RecyclerView message_list;
    private SwipeRefreshLayout message_list_refresh;
    private EditText message_content;
    private Button message_send;
    private HandleMessage handleMessage;
    private ServiceConnection connection;
    private MessageChatPresenter messagePresenter;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    List<Message> messageList = new ArrayList<>();
    private User targetUser;
    private Contact contact;
    private LinearLayoutManager mLinearLayoutManager;
    private User activeUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        targetUser = (User) getIntent().getParcelableExtra(Contants.user_flag);
        contact = (Contact) getIntent().getParcelableExtra(Contants.contact_flag);
        super.onCreate(savedInstanceState);
        initView();
        messagePresenter = new MessageChatPresenter(this);
        activeUser = messagePresenter.getActiveUser();
        Log.e(TAG, "targetUser: " + targetUser + "  contact:" + contact);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.e(TAG, "MessageAService ");
                MessageAService.HandleMessageImpl service = (MessageAService.HandleMessageImpl) iBinder;
//                DaemonService.HandleMessageImpl service= (DaemonService.HandleMessageImpl) iBinder;
                handleMessage = (HandleMessage) service.getService();
                handleMessage.addMessageListener(ChatActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                handleMessage.removeMessageListener(ChatActivity.this);
            }
        };
        Intent intent = new Intent(this, MessageAService.class);
        bindService(intent, connection, Service.BIND_AUTO_CREATE);
        messagePresenter.loadMessage(contact);
    }

    @Override
    public void initToolbarView(ToolbarHolder toolbar) {
        toolbar.getToolbar().setTitle(targetUser.getNickname());
        toolbar.getToolbar().setNavigationIcon(R.drawable.back);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    public void updateMoreMessage(List<Message> messages, boolean isTop) {
        if(messages==null||messages.size()==0){
            ToastUtils.show("没有更多数据了");
            return;
        }
        message_list_refresh.setRefreshing(false);
        if(isTop){
            messageList.addAll(0,messages);
            int firstVisibleItemPosition=mLinearLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPosition=mLinearLayoutManager.findLastVisibleItemPosition();
            adapter.notifyDataSetChanged();
            moveToPosition(messages.size()+lastVisibleItemPosition-firstVisibleItemPosition);
        }

    }
    private void initView() {
        final RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.drawable.photo)
                .error(R.drawable.photo)
                .fallback(R.drawable.photo);
        message_list_refresh = (SwipeRefreshLayout) findViewById(R.id.message_list_refresh);
        message_list = (RecyclerView) findViewById(R.id.message_list);
        message_list.setOnClickListener(this);
        message_content = (EditText) findViewById(R.id.message_content);
        message_content.setOnClickListener(this);
        message_send = (Button) findViewById(R.id.message_send);
        message_send.setOnClickListener(this);
        message_list_refresh.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        message_list_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: 2018/1/26
                messagePresenter.loadMoreMessage(contact);
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this);
        message_list.setLayoutManager(mLinearLayoutManager);
        message_list.setItemAnimator(new DefaultItemAnimator());
        message_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mLastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItem + 5 > mLinearLayoutManager.getItemCount()) {
//                    contactPresenter.addMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取加载的最后一个可见视图在适配器的位置。
                mLastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });
        adapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if(viewType==1){
                    View inflate = LayoutInflater.from(ChatActivity.this).inflate(R.layout.item_chat_send_textmessage, parent, false);
                    TextMessageLeftHolder contactsItemHolder = new TextMessageLeftHolder(inflate);
                    Glide.with(ChatActivity.this)
                            .load(Contants.baseImage + activeUser.getIconurl())
                            .apply(options).into(contactsItemHolder.photo);
                    return contactsItemHolder;
                }else{
                    final View inflate = LayoutInflater.from(ChatActivity.this).inflate(R.layout.item_chat_received_textmessage, parent, false);
                    TextMessageLeftHolder contactsItemHolder = new TextMessageLeftHolder(inflate);
                    Glide.with(ChatActivity.this)
                            .load(Contants.baseImage + targetUser.getIconurl())
                            .apply(options).into(contactsItemHolder.photo);
                    return contactsItemHolder;
                }

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                if (holder instanceof TextMessageLeftHolder) {
                    TextMessageLeftHolder contactsItemHolder = (TextMessageLeftHolder) holder;
                    Message message = messageList.get(position);
                    contactsItemHolder.contact_text.setText(message .getContent() + " ");
                }
            }

            @Override
            public int getItemCount() {
                return messageList.size();
            }

            @Override
            public int getItemViewType(int position) {
                Message message = messageList.get(position);
                if(message.getFromuserid()== targetUser.getUserid()){
                    return 2;
                }else{
                    return 1;
                }
            }
        };
        message_list.setAdapter(adapter);
    }
    public  void dataChange(){
        adapter.notifyDataSetChanged();
        smoothMoveToPosition(messageList.size());
    }
    public void smoothMoveToPosition(int n){
        int firstVisibleItemPosition=mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition=mLinearLayoutManager.findLastVisibleItemPosition();
        if(n<=firstVisibleItemPosition){
            //在之前
            message_list.smoothScrollToPosition(n);
        }else if(n<=lastVisibleItemPosition){
            //在中间
            int top=message_list.getChildAt(n-firstVisibleItemPosition).getTop();//找出差值
            message_list.smoothScrollBy(0,top);
        }else{
            message_list.smoothScrollToPosition(n);
        }
    }
    public void moveToPosition(int n){
        int firstVisibleItemPosition=mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition=mLinearLayoutManager.findLastVisibleItemPosition();
        if(n<=firstVisibleItemPosition){
            //在之前
            message_list.scrollToPosition(n);
        }else if(n<=lastVisibleItemPosition){
            //在中间
            int top=message_list.getChildAt(n-firstVisibleItemPosition).getTop();//找出差值
            message_list.scrollBy(0,top);
        }else{
            message_list.scrollToPosition(n);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.message_send) {
            submit();
        }
    }


    private void submit() {
        // validate
        String content = message_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "content不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Message message = new Message();
        message.setContent(content);
        message.setContactid(contact.getContactid());
        message.setTargetuserid(targetUser.getUserid());
        messagePresenter.sendMessage(ModuleId.chatMessage, CmdId.ChartMessage.sendMessageToAll, message);
        // TODO validate success, do something

    }

    @Override
    protected void onPause() {
        LogUtils.e(TAG,"onPause");
        messagePresenter.clearTip(contact);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LogUtils.e(TAG,"destory");
        if (connection != null) {
            handleMessage.removeMessageListener(ChatActivity.this);
            unbindService(connection);
        }
        super.onDestroy();
    }

    @Override
    public HandleMessage getHandleMessage() {
        return handleMessage;
    }


    @Override
    public void updateMoreMessage(Message message) {
        if (message != null) {
            messageList.add(message);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setMessageList(List<Message> messages) {
        if (messages != null && messages.size() != 0) {
            messageList.addAll(messages);
           dataChange();
        }
    }

    @Override
    public void newMessage(Contact contants, short module, short cmd, Message message) {
        Log.e(TAG, "module:" + module + " cmd:" + cmd + " message：" + message);
        if (message != null) {
            messageList.add(message);
            dataChange();
        }
    }

    @Override
    public void receiptMessage(short module, short cmd, long messageId, long receiveTime, short resultCode) {
        Log.e(TAG, "module:" + module + " cmd:" + cmd + " messageId：" + messageId + "receiveTime:" + receiveTime + "resultCode:" + resultCode);
    }
}
