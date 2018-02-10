package com.wisn.mainmodule.view.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.adapter.HomeActivityAdapter;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.entity.Contact;
import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.presenter.MessageChatPresenter;
import com.wisn.mainmodule.protocal.constant.CmdId;
import com.wisn.mainmodule.protocal.constant.ModuleId;
import com.wisn.mainmodule.protocal.service.HandleMessage;
import com.wisn.mainmodule.protocal.service.MessageAService;
import com.wisn.mainmodule.protocal.service.MessageChangeListener;
import com.wisn.mainmodule.view.BaseUpdateView;
import com.wisn.mainmodule.view.ChatView;
import com.wisn.mainmodule.view.HomeView;
import com.wisn.mainmodule.view.fragment.HomeFragmentFactory;
import com.wisn.mainmodule.view.viewholder.ToolbarHolder;
import com.wisn.mainmodule.widget.TipRadioButton;
import com.wisn.utils.ToastUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/23 16:27
 */


public class HomeActivity extends BaseAppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, ChatView, MessageChangeListener, HomeView {
    public static String TAG = "HomeActivity";

    private RadioGroup mRadioButton;
    private TipRadioButton radiobutton_bg_message;
    private TipRadioButton radiobutton_bg_contact;
    private TipRadioButton radiobutton_bg_work;
    private TipRadioButton radiobutton_bg_mine;
    private ViewPager mViewpager;
    private MessageChatPresenter messagePresenter;
    private ServiceConnection connection;
    private HandleMessage handleMessage;
    private HomeActivityAdapter fragmentAdapter;
    @Override
    public void initToolbarView(ToolbarHolder toolbar) {
        toolbar.getToolbar().setTitle("任务通");

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRadioButton = (RadioGroup) findViewById(R.id.bottom_radiogroup);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        radiobutton_bg_message = (TipRadioButton) findViewById(R.id.radiobutton_bg_message);
        radiobutton_bg_contact = (TipRadioButton) findViewById(R.id.radiobutton_bg_contact);
        radiobutton_bg_work = (TipRadioButton) findViewById(R.id.radiobutton_bg_work);
        radiobutton_bg_mine = (TipRadioButton) findViewById(R.id.radiobutton_bg_mine);
        mRadioButton.setOnCheckedChangeListener(this);
        setDefaultFragment();
        messagePresenter = new MessageChatPresenter(this);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.e(TAG, "MessageAService ");
                MessageAService.HandleMessageImpl service = (MessageAService.HandleMessageImpl) iBinder;
                handleMessage = (HandleMessage) service.getService();
                handleMessage.addMessageListener(HomeActivity.this);
                messagePresenter.sendMessage(ModuleId.AuthMessage, CmdId.AuthMessage.register, new Message());
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        Intent intent = new Intent(this, MessageAService.class);
        bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setDefaultFragment() {
        radiobutton_bg_message.setChecked(true);
        fragmentAdapter = new HomeActivityAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(fragmentAdapter);
        mViewpager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int index = 0;
        if (checkedId == R.id.radiobutton_bg_message) {
            index = 0;

        } else if (checkedId == R.id.radiobutton_bg_contact) {
            index = 1;

        } else if (checkedId == R.id.radiobutton_bg_work) {
            index = 2;

        } else if (checkedId == R.id.radiobutton_bg_mine) {
            index = 3;

        }
        mViewpager.setCurrentItem(index);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mRadioButton.setOnCheckedChangeListener(null);
        switch (position) {
            case 0:
                radiobutton_bg_message.setChecked(true);
                break;
            case 1:
                radiobutton_bg_contact.setChecked(true);
                break;
            case 2:
                radiobutton_bg_work.setChecked(true);
                break;
            case 3:
                radiobutton_bg_mine.setChecked(true);
                break;
        }
        mRadioButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void updateTipMessage(int index, int messageCount) {
        TipRadioButton tipRadioButton = null;
        switch (index) {
            case 0:
                tipRadioButton = radiobutton_bg_message;
                break;
            case 1:
                tipRadioButton = radiobutton_bg_contact;
                break;
            case 2:
                tipRadioButton = radiobutton_bg_work;
                break;
            case 3:
                tipRadioButton = radiobutton_bg_mine;
                break;
        }
        if (messageCount == 0) {
            tipRadioButton.clearTip();
        } else if (messageCount > 0) {
            tipRadioButton.setTipText(String.valueOf(messageCount));
        } else {
            tipRadioButton.setTip();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public HandleMessage getHandleMessage() {
        return handleMessage;
    }

    @Override
    public void updateMoreMessage(List<Message> messageList, boolean isTop) {

    }

    @Override
    public void updateMoreMessage(Message message) {

    }

    @Override
    public void setMessageList(List<Message> messageList) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseUpdateView item = (BaseUpdateView) fragmentAdapter.getItem(0);
        item.update();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
        }
    }

    @Override
    public void newMessage(Contact contants, short module, short cmd, Message message) {
        switch (module){
            case ModuleId.AuthMessage:
                //
                break;
            case ModuleId.chatMessage:
                BaseUpdateView item = (BaseUpdateView) HomeFragmentFactory.getFragment("Message");
                item.update();
                break;
        }
    }

    @Override
    public void receiptMessage(short module, short cmd, long messageId, long receiveTime, short resultCode) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);

        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.search) {
            ToastUtils.show("search");
            return true;
        } else if (i == R.id.addfrends) {
            ToastUtils.show("addfrends");
//            SelectImageListActivity.start(this,100,1,null);
            return true;
        } else if (i == R.id.myinfo_code) {
            ToastUtils.show("myinfo_code");
            return true;
        }else if (i == R.id.scan_code) {
            ToastUtils.show("scan_code");
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
