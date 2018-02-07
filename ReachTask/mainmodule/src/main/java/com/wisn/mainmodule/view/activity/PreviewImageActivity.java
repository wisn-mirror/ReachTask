package com.wisn.mainmodule.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.adapter.PreviewImagePageAdapter;
import com.wisn.mainmodule.base.BaseActivity;
import com.wisn.mainmodule.entity.bean.Image;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.widget.MViewPager;
import com.wisn.utils.ToastUtils;

import java.util.ArrayList;

/**
 * @author Wisn
 * @time 2018/2/7 9:47
 */


public class PreviewImageActivity extends BaseActivity implements View.OnClickListener {
    private MViewPager vp_image;
    private TextView image_back;
    private TextView image_title;
    private Button submit;
    private RelativeLayout chrooseimage_title;
    private TextView pre_review;
    private RelativeLayout chrooseimage_bottom;
    private PreviewImagePageAdapter previewImagePageAdapter;
    private ArrayList<Image> imageslist;
    private ArrayList<Image> selectImageList;
    private int maxCount;
    private ImageView iv_select;
    private int viewPageIndex = 0;

    private boolean showtitle = true;
    private boolean submitfinsh = false;

    public static void start(Activity activity, int requestCode, int maxCount, ArrayList<Image> selectImageList, ArrayList<Image> imageslist) {
        Intent intent = new Intent(activity, PreviewImageActivity.class);
        intent.putExtra(Contants.Select_MaxCount, maxCount);
        intent.putParcelableArrayListExtra(Contants.Select_Select_ImageList, selectImageList);
        intent.putParcelableArrayListExtra(Contants.Select_ImageList, imageslist);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewimage);
        Intent intent = getIntent();
        maxCount = intent.getIntExtra(Contants.Select_MaxCount, 1);
        selectImageList = intent.getParcelableArrayListExtra(Contants.Select_Select_ImageList);
        imageslist = intent.getParcelableArrayListExtra(Contants.Select_ImageList);
        setStatusBarVisible(true);
        initView();
        initListener();
    }

    @Override
    public void finish() {
        Intent intent=new Intent();
        intent.putExtra(Contants.Select_Preview_isSubmit,submitfinsh);
        intent.putParcelableArrayListExtra(Contants.Select_ImageList,selectImageList);
        setResult(200,intent);
        super.finish();
    }

    private void initListener() {
        previewImagePageAdapter = new PreviewImagePageAdapter(this, maxCount, imageslist);
        vp_image.setAdapter(previewImagePageAdapter);
        previewImagePageAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showtitle) {
                    hideTitleAndbottom();
                } else {
                    showTitleAndbottom();
                }
            }
        });
        submit.setOnClickListener(this);
        pre_review.setOnClickListener(this);
        image_back.setOnClickListener(this);
        vp_image.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                image_title.setText(position + 1 + "/" + imageslist.size());
                viewPageIndex = position;
                Image image = imageslist.get(viewPageIndex);
                if (selectImageList.contains(image)) {
                    iv_select.setImageResource(R.drawable.icon_image_select);
                } else {
                    iv_select.setImageResource(R.drawable.icon_image_un_select);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initView() {
        vp_image = (MViewPager) findViewById(R.id.vp_image);
        image_back = (TextView) findViewById(R.id.image_back);
        iv_select = (ImageView) findViewById(R.id.iv_select);
        image_title = (TextView) findViewById(R.id.image_title);
        submit = (Button) findViewById(R.id.submit);
        chrooseimage_title = (RelativeLayout) findViewById(R.id.chrooseimage_title);
        pre_review = (TextView) findViewById(R.id.pre_review);
        chrooseimage_bottom = (RelativeLayout) findViewById(R.id.chrooseimage_bottom);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) chrooseimage_title.getLayoutParams();
        lp.topMargin = getStatusBarHeight(this);
        chrooseimage_title.setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        if (v == submit) {
            submitfinsh=true;
            this.finish();
        } else if (v == pre_review) {
            if (selectImageList.size() >= maxCount) {
                submit.setEnabled(true);
                ToastUtils.show("最多选择" + maxCount + "张");
                return;
            }
            submit.setEnabled(false);
            Image image = imageslist.get(viewPageIndex);
            if (!selectImageList.contains(image)) {
                selectImageList.add(image);
                iv_select.setImageResource(R.drawable.icon_image_select);
            } else {
                selectImageList.remove(image);
                iv_select.setImageResource(R.drawable.icon_image_un_select);
            }
            if (selectImageList.size() >= maxCount) {
                submit.setEnabled(true);
            }
            submit.setText("确定(" + selectImageList.size() + "/" + maxCount + ")");
        }else if(v==image_back){
            this.finish();
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 显示和隐藏状态栏
     *
     * @param show
     */
    private void setStatusBarVisible(boolean show) {
        if (show) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    private void hideTitleAndbottom() {
        showtitle = false;
        chrooseimage_title.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(chrooseimage_title, "translationY"
                        , 0, -chrooseimage_title.getHeight()).setDuration(300);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationCancel(animation);
                        if (chrooseimage_title != null)
                            chrooseimage_title.setVisibility(View.GONE);
                        chrooseimage_title.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setStatusBarVisible(false);
                            }
                        },10);
                    }
                });
                animator.start();
                ObjectAnimator.ofFloat(chrooseimage_bottom, "translationY"
                        , 0, chrooseimage_bottom.getHeight()).setDuration(300).start();
            }
        }, 100);

    }

    private void showTitleAndbottom() {
        showtitle = true;
        setStatusBarVisible(true);
        chrooseimage_title.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(chrooseimage_title, "translationY"
                        , chrooseimage_title.getTranslationY(), 0).setDuration(300);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationCancel(animation);
                        if (chrooseimage_title != null)
                            chrooseimage_title.setVisibility(View.VISIBLE);
                    }
                });
                animator.start();
                ObjectAnimator.ofFloat(chrooseimage_bottom, "translationY"
                        , chrooseimage_bottom.getHeight(), 0).setDuration(300).start();;
            }
        }, 100);

    }
}
