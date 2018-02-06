package com.wisn.mainmodule.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.adapter.SelectImageAdapter;
import com.wisn.mainmodule.adapter.SelectImageFolderAdapter;
import com.wisn.mainmodule.base.BaseActivity;
import com.wisn.mainmodule.entity.bean.Folder;
import com.wisn.mainmodule.entity.bean.Image;
import com.wisn.mainmodule.entity.bean.ImgFolderBean;
import com.wisn.mainmodule.model.impl.MediaModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/2/5 12:22
 */


public class ChrooseImageListActivity extends BaseActivity implements View.OnClickListener, SelectImageAdapter.SelectImageListener, SelectImageFolderAdapter.SelectImageFolderListener {
    private TextView image_back;
    private TextView image_title;
    private Button submit;
    private TextView select_dir;
    private TextView pre_review;
    private RecyclerView image_list;
    private TextView image_date;
    private View mark;
    private RecyclerView dir_list;
    private boolean isOpenFolder = false;
    private SelectImageAdapter imageAdapter;
    private MediaModel mediaModel;
    private List<ImgFolderBean> imageFolders;
    private SelectImageFolderAdapter folderAdapter;

    public static void start(int maxCount){

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrooseimage);
        mediaModel = new MediaModel();
        initView();
        initListener();
    }

    private void initListener() {
        submit.setOnClickListener(this);
        select_dir.setOnClickListener(this);
        image_back.setOnClickListener(this);
        pre_review.setOnClickListener(this);
        mark.setOnClickListener(this);
        imageAdapter = new SelectImageAdapter(this,1);
        GridLayoutManager  gridLayoutManager=new GridLayoutManager(this,3);
        image_list.setLayoutManager(gridLayoutManager);
        image_list.setAdapter(imageAdapter);
        imageAdapter.setSelectImageListener(this);
        folderAdapter = new SelectImageFolderAdapter(this);
        dir_list.setLayoutManager(new  LinearLayoutManager(this));
        dir_list.setAdapter(folderAdapter);
        folderAdapter.setSelectImageFolderListener(this);
        mediaModel.loadImageForSDCard(new MediaModel.DataCallback() {
            @Override
            public void onSuccess(final ArrayList<Folder> folders) {
                if(folders!=null){
                    System.out.println(" data:"+folders);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageAdapter.refresh(folders.get(0).getImages());
                            folderAdapter.refresh(folders);
                        }
                    });

                }
            }
        });
    }

    private void initView() {
        image_back = (TextView) findViewById(R.id.image_back);
        image_title = (TextView) findViewById(R.id.image_title);
        submit = (Button) findViewById(R.id.submit);
        select_dir = (TextView) findViewById(R.id.select_dir);
        pre_review = (TextView) findViewById(R.id.pre_review);
        image_list = (RecyclerView) findViewById(R.id.image_list);
        image_date = (TextView) findViewById(R.id.image_date);
        mark = (View) findViewById(R.id.mark);
        dir_list = (RecyclerView) findViewById(R.id.dir_list);

    }

    @Override
    public void select(String name, List<Image> imageList) {
        select_dir.setText(name);
        imageAdapter.refresh(imageList);
        closeFolder();
    }
    @Override
    public void select(int current, int max, List<Image> images) {
        image_title.setText(current+"/"+max);
        if(current==max){
            submit.setEnabled(true);
        }else{
            submit.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == submit) {

        } else if (v == mark) {
            if (isOpenFolder) {
                closeFolder();
            }
        } else if (v == pre_review) {

        } else if (v == select_dir) {
            if (isOpenFolder) {
                closeFolder();
            } else {
                openFolder();
            }
        } else if (v == image_back) {
            this.finish();
        }
    }

    private void openFolder() {

        ObjectAnimator animator = ObjectAnimator.ofFloat(dir_list, "translationY", dir_list.getHeight(), 0).setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationCancel(animation);
                mark.setVisibility(View.VISIBLE);
                dir_list.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
        isOpenFolder = true;
    }

    private void closeFolder() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(dir_list, "translationY", 0, dir_list.getHeight()).setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationCancel(animation);
                mark.setVisibility(View.GONE);
                dir_list.setVisibility(View.GONE);
            }
        });
        animator.start();
        isOpenFolder = false;
    }



}
