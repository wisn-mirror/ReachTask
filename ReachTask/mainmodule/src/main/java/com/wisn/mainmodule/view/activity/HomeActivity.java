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
import android.util.Log;
import android.widget.RadioGroup;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.adapter.HomeActivityAdapter;
import com.wisn.mainmodule.base.BaseActivity;
import com.wisn.mainmodule.entity.Message;
import com.wisn.mainmodule.presenter.MessagePresenter;
import com.wisn.mainmodule.protocal.service.HandleMessage;
import com.wisn.mainmodule.protocal.service.MessageAService;
import com.wisn.mainmodule.utils.CmdId;
import com.wisn.mainmodule.utils.ModuleId;
import com.wisn.mainmodule.view.MessageView;
import com.wisn.mainmodule.widget.TipRadioButton;

import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/23 16:27
 */


public class HomeActivity  extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener ,MessageView{
    public static String TAG="HomeActivity";

    private RadioGroup mRadioButton;
    private TipRadioButton mRadiobutton_bg_home;
    private TipRadioButton mRadiobutton_bg_gift;
    private TipRadioButton mRadiobutton_bg_start;
    private TipRadioButton mRadiobutton_bg_watch;
    private ViewPager mViewpager;
    private  MessagePresenter  messagePresenter;
    private ServiceConnection connection;
    private HandleMessage handleMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRadioButton = (RadioGroup)findViewById(R.id.bottom_radiogroup);
        mViewpager = (ViewPager)findViewById(R.id.viewpager);
        mRadiobutton_bg_home = (TipRadioButton)findViewById(R.id.radiobutton_bg_message);
        mRadiobutton_bg_gift = (TipRadioButton)findViewById(R.id.radiobutton_bg_contact);
        mRadiobutton_bg_start = (TipRadioButton)findViewById(R.id.radiobutton_bg_work);
        mRadiobutton_bg_watch = (TipRadioButton)findViewById(R.id.radiobutton_bg_mine);
        mRadioButton.setOnCheckedChangeListener(this);
        setDefaultFragment();
        messagePresenter=new MessagePresenter(this);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.e(TAG,"MessageAService ");
                MessageAService.HandleMessageImpl service= (MessageAService.HandleMessageImpl) iBinder;
                handleMessage = (HandleMessage) service.getService();
                messagePresenter.sendMessage(ModuleId.AuthMessage, CmdId.AuthMessage.register,new Message());
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        Intent intent=new Intent(this, MessageAService.class);
        bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setDefaultFragment() {
        mRadiobutton_bg_home.setChecked(true);
        HomeActivityAdapter fragmentAdapter=new HomeActivityAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(fragmentAdapter);
        mViewpager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int  index=0;
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
        switch (position){
            case 0:
                mRadiobutton_bg_home.setChecked(true);
                break;
            case 1:
                mRadiobutton_bg_gift.setChecked(true);
                break;
            case 2:
                mRadiobutton_bg_start.setChecked(true);
                break;
            case 3:
                mRadiobutton_bg_watch.setChecked(true);
                break;
        }
        mRadioButton.setOnCheckedChangeListener(this);
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
    public void setMessageList(List<Message> messageList) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(connection!=null){
            unbindService(connection);
        }
    }
}
