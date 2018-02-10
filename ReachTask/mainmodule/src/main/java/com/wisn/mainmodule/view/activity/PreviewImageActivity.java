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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.adapter.PreviewImagePageAdapter;
import com.wisn.mainmodule.base.BaseAppCompatActivity;
import com.wisn.mainmodule.entity.bean.Image;
import com.wisn.mainmodule.utils.Contants;
import com.wisn.mainmodule.view.viewholder.ToolbarHolder;
import com.wisn.mainmodule.widget.MViewPager;
import com.wisn.utils.ToastUtils;

import java.util.ArrayList;

/**
 * @author Wisn
 * @time 2018/2/7 9:47
 */


public class PreviewImageActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private MViewPager vp_image;
//    private TextView image_back;
//    private TextView image_title;
//    private Button submit;
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
    private ToolbarHolder toolbarHolder;

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
        Intent intent = getIntent();
        maxCount = intent.getIntExtra(Contants.Select_MaxCount, 1);
        selectImageList = intent.getParcelableArrayListExtra(Contants.Select_Select_ImageList);
        imageslist = intent.getParcelableArrayListExtra(Contants.Select_ImageList);
        toolbarHolder.getToolbar().setTitle("预览("+1 + "/" + imageslist.size()+")");
        setStatusBarVisible(true);
        initView();
        initListener();
    }

    @Override
    public void initToolbarView(ToolbarHolder toolbar) {
        toolbar.getToolbar().setTitle("预览");
        toolbar.getToolbar().setNavigationIcon(R.drawable.back);
        toolbarHolder =toolbar;
        toolbar.setRightButton("确定",this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_previewimage;
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
        pre_review.setOnClickListener(this);
        vp_image.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                toolbarHolder.getToolbar().setTitle("预览("+(position + 1) + "/" + imageslist.size()+")");
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
         iv_select = (ImageView) findViewById(R.id.iv_select);
        pre_review = (TextView) findViewById(R.id.pre_review);
        chrooseimage_bottom = (RelativeLayout) findViewById(R.id.chrooseimage_bottom);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) toolbarHolder.getToolbar().getLayoutParams();
        lp.topMargin = getStatusBarHeight(this);
        toolbarHolder.getToolbar().setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        if (v == toolbarHolder.toolbar_ib_right) {
            submitfinsh=true;
            this.finish();
        } else if (v == pre_review) {
            if (selectImageList.size() >= maxCount) {
                toolbarHolder.toolbar_ib_right.setEnabled(true);
                ToastUtils.show("最多选择" + maxCount + "张");
                return;
            }
            toolbarHolder.toolbar_ib_right.setEnabled(false);
            Image image = imageslist.get(viewPageIndex);
            if (!selectImageList.contains(image)) {
                selectImageList.add(image);
                iv_select.setImageResource(R.drawable.icon_image_select);
            } else {
                selectImageList.remove(image);
                iv_select.setImageResource(R.drawable.icon_image_un_select);
            }
            if (selectImageList.size() >= maxCount) {
                toolbarHolder.toolbar_ib_right.setEnabled(true);
            }
            toolbarHolder.toolbar_ib_right.setText("确定(" + selectImageList.size() + "/" + maxCount + ")");
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
        toolbarHolder.getToolbar().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(toolbarHolder.getToolbar(), "translationY"
                        , 0, -toolbarHolder.getToolbar().getHeight()).setDuration(300);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationCancel(animation);
                        if (toolbarHolder.getToolbar() != null)
                            toolbarHolder.getToolbar().setVisibility(View.GONE);
                        toolbarHolder.getToolbar().postDelayed(new Runnable() {
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
        toolbarHolder.getToolbar().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator animator = ObjectAnimator.ofFloat(toolbarHolder.getToolbar(), "translationY"
                        , toolbarHolder.getToolbar().getTranslationY(), 0).setDuration(300);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationCancel(animation);
                        if (toolbarHolder.getToolbar() != null)
                            toolbarHolder.getToolbar().setVisibility(View.VISIBLE);
                    }
                });
                animator.start();
                ObjectAnimator.ofFloat(chrooseimage_bottom, "translationY"
                        , chrooseimage_bottom.getHeight(), 0).setDuration(300).start();;
            }
        }, 100);

    }
}
