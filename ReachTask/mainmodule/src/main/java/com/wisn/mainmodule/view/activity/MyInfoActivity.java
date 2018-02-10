package com.wisn.mainmodule.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.view.viewholder.ToolbarHolder;
import com.wisn.mainmodule.widget.CombinationListItemLayout;

/**
 * @author Wisn
 * @time 2018/2/8 11:18
 */


public class MyInfoActivity extends BaseAppCompatActivity {
    private ImageView info_photo;
    private CombinationListItemLayout info_nickname;
    private CombinationListItemLayout info_id;
    private CombinationListItemLayout in_mycode;
    private CombinationListItemLayout info_myword;
    private CombinationListItemLayout info_sex;
    private CombinationListItemLayout info_location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void initToolbarView(ToolbarHolder toolbar) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_activity_myinfo;
    }

    private void initView() {
        info_photo = (ImageView) findViewById(R.id.info_photo);
        info_nickname = (CombinationListItemLayout) findViewById(R.id.info_nickname);
        info_id = (CombinationListItemLayout) findViewById(R.id.info_id);
        in_mycode = (CombinationListItemLayout) findViewById(R.id.in_mycode);
        info_myword = (CombinationListItemLayout) findViewById(R.id.info_myword);
        info_sex = (CombinationListItemLayout) findViewById(R.id.info_sex);
        info_location = (CombinationListItemLayout) findViewById(R.id.info_location);
    }
}
