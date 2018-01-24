package com.wisn.mainmodule.view.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.adapter.HomeActivityAdapter;
import com.wisn.mainmodule.base.BaseActivity;
import com.wisn.mainmodule.widget.MyRadioButton;

/**
 * @author Wisn
 * @time 2018/1/23 16:27
 */


public class HomeActivity  extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

    private RadioGroup mRadioButton;
    private MyRadioButton mRadiobutton_bg_home;
    private MyRadioButton mRadiobutton_bg_gift;
    private MyRadioButton mRadiobutton_bg_start;
    private MyRadioButton mRadiobutton_bg_watch;
    private ViewPager mViewpager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRadioButton = (RadioGroup)findViewById(R.id.bottom_radiogroup);
        mViewpager = (ViewPager)findViewById(R.id.viewpager);
        mRadiobutton_bg_home = (MyRadioButton)findViewById(R.id.radiobutton_bg_message);
        mRadiobutton_bg_gift = (MyRadioButton)findViewById(R.id.radiobutton_bg_contact);
        mRadiobutton_bg_start = (MyRadioButton)findViewById(R.id.radiobutton_bg_work);
        mRadiobutton_bg_watch = (MyRadioButton)findViewById(R.id.radiobutton_bg_mine);
        mRadioButton.setOnCheckedChangeListener(this);
        setDefaultFragment();
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
}
