package com.wisn.mainmodule.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.adapter.SelectImageAdapter;
import com.wisn.mainmodule.adapter.SelectImageFolderAdapter;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.entity.bean.Folder;
import com.wisn.mainmodule.entity.bean.Image;
import com.wisn.mainmodule.model.impl.MediaModel;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.viewholder.ToolbarHolder;
import com.wisn.utils.DateUtils;

import java.util.ArrayList;

/**
 * @author Wisn
 * @time 2018/2/5 12:22
 */


public class SelectImageListActivity extends BaseAppCompatActivity implements View.OnClickListener, SelectImageAdapter.SelectImageListener, SelectImageFolderAdapter.SelectImageFolderListener {
    private static final String TAG ="SelectImageListActivity" ;
//    private TextView image_back;
//    private TextView image_title;
//    private Button submit;
    private TextView select_dir;
    private TextView pre_review;
    private RecyclerView image_list;
    private TextView image_date;
    private View mark;
    private RecyclerView dir_list;
    private boolean isOpenFolder = false;
    private SelectImageAdapter imageAdapter;
    private MediaModel mediaModel;
    private SelectImageFolderAdapter folderAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private GridLayoutManager gridLayoutManager;
    private int firstVisibleItemPosition;
    private boolean isShowTime;
    private int maxCount;
    private ToolbarHolder toolbar;

    public static void start(Activity activity, int requestCode, int maxCount, ArrayList<Image> imageslist) {
        Intent intent = new Intent(activity, SelectImageListActivity.class);
        intent.putExtra(Contants.Select_MaxCount, maxCount);
        intent.putParcelableArrayListExtra(Contants.Select_ImageList, imageslist);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        maxCount = intent.getIntExtra(Contants.Select_MaxCount, 4);
        ArrayList<Image> imageslist = intent.getParcelableArrayListExtra(Contants.Select_ImageList);
        imageAdapter = new SelectImageAdapter(this, maxCount, imageslist);
        folderAdapter = new SelectImageFolderAdapter(this);
        mediaModel = new MediaModel();
        initView();
        initListener();
        changeTime();
    }

    @Override
    public void initToolbarView(ToolbarHolder toolbar) {
         toolbar.getToolbar().setTitle("相册");
         toolbar.getToolbar().setNavigationIcon(R.drawable.back);
         toolbar.setRightButton("确定", this);
         this.toolbar=toolbar;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_chrooseimage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            ArrayList<Image> select_image= data.getParcelableArrayListExtra(Contants.Select_ImageList);
            if(select_image!=null&&select_image.size()>0){
                imageAdapter.setSelectImageList(select_image);
            }
            if(data.getBooleanExtra(Contants.Select_Preview_isSubmit,false)){
                finish();
            }
        }
    }

    public void finish() {
        ArrayList<String> select_image = new ArrayList<>();
        if (imageAdapter != null) {
            ArrayList<Image> selectImageList = imageAdapter.getSelectImageList();
            for (Image image : selectImageList) {
                select_image.add(image.getPath());
            }
        }
        Intent intent = new Intent();
        intent.putStringArrayListExtra(Contants.Select_Result, select_image);
        setResult(200, intent);
        super.finish();
    }


    private void initListener() {
        select_dir.setOnClickListener(this);
        pre_review.setOnClickListener(this);
        mark.setOnClickListener(this);
        gridLayoutManager = new GridLayoutManager(this, 3);
        image_list.setLayoutManager(gridLayoutManager);
        imageAdapter.setSelectImageListener(this);
        image_list.setAdapter(imageAdapter);
        dir_list.setLayoutManager(new LinearLayoutManager(this));
        dir_list.setAdapter(folderAdapter);
        folderAdapter.setSelectImageFolderListener(this);
        mediaModel.loadImageForSDCard(new MediaModel.DataCallback() {
            @Override
            public void onSuccess(final ArrayList<Folder> folders) {
                if (folders != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            folderAdapter.refresh(folders);
                        }
                    });
                }
            }
        });
        image_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    changeTime();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                changeTime();
            }
        });
    }

    private void changeTime() {
        int firstvisibleIndex = gridLayoutManager.findFirstVisibleItemPosition();
        if (firstvisibleIndex == firstVisibleItemPosition) return;
        firstVisibleItemPosition = firstvisibleIndex;
        long firstVisibleTime = imageAdapter.getFirstVisibleTime(firstVisibleItemPosition);
        if (firstVisibleTime > 0) {
            System.out.println(System.currentTimeMillis() + ": " + firstVisibleItemPosition + " time" + firstVisibleTime);
            String dateDespre = DateUtils.getDateDespre(firstVisibleTime * 1000);
            image_date.setText(dateDespre);
            showTime();
            handler.removeCallbacks(hideTime);
            handler.postDelayed(hideTime, 1500);
        }
    }

    private void hideTime() {
        if (isShowTime) {
            ObjectAnimator.ofFloat(image_date, "alpha", 1, 0).setDuration(300).start();
            isShowTime = false;
        }
    }

    private Runnable hideTime = new Runnable() {
        @Override
        public void run() {
            hideTime();
        }
    };

    private void showTime() {
        if (!isShowTime) {
            ObjectAnimator.ofFloat(image_date, "alpha", 0, 1).setDuration(300).start();
            isShowTime = true;
        }
    }

    private void initView() {

        select_dir = (TextView) findViewById(R.id.select_dir);
        pre_review = (TextView) findViewById(R.id.pre_review);
        image_list = (RecyclerView) findViewById(R.id.image_list);
        image_date = (TextView) findViewById(R.id.image_date);
        mark = (View) findViewById(R.id.mark);
        dir_list = (RecyclerView) findViewById(R.id.dir_list);

    }

    @Override
    public void select(String name, ArrayList<Image> imageList) {
        select_dir.setText(name);
        imageAdapter.refresh(imageList);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                closeFolder();
            }
        }, 300);

    }

    @Override
    public void imageSelect(int current, int max, ArrayList<Image> images) {
        toolbar.getToolbar().setTitle("相册("+current + "/" + max+")");
        pre_review.setText("预览(" + current + ")");
        if (current == max) {
            toolbar.toolbar_ib_right.setEnabled(true);
        } else {
            toolbar.toolbar_ib_right.setEnabled(false);
        }
    }

    @Override
    public void imageSelectPreview(int max, ArrayList<Image> selectImages, ArrayList<Image> allImage) {
        PreviewImageActivity.start(this, 100, max, selectImages, allImage);
    }

    @Override
    public void onClick(View v) {
        if (v == toolbar.toolbar_ib_right) {
            finish();
        } else if (v == mark) {
            if (isOpenFolder) {
                closeFolder();
            }
        } else if (v == pre_review) {
            PreviewImageActivity.start(this, 100, maxCount, imageAdapter.getSelectImageList(), imageAdapter.getSelectImageList());
        } else if (v == select_dir) {
            if (isOpenFolder) {
                closeFolder();
            } else {
                openFolder();
            }
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
