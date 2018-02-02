package com.wisn.mainmodule.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseActivity;
import com.wisn.mainmodule.presenter.LauncherPresenter;
import com.wisn.mainmodule.view.LauncherView;
import com.wisn.mainmodule.widget.CountdownView;
import com.wisn.mainmodule.widget.IndicatorScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wisn
 * @time 2018/1/23 21:27
 */


public class WelcomeActivity extends BaseActivity implements LauncherView {
    private ViewPager Viewpager;
    private CountdownView CountdownView;
    private IndicatorScrollView IndicatorScrollView;
    private int[] ids= {R.mipmap.start_launcher,R.mipmap.start_launcher,R.mipmap.start_launcher,R.mipmap.start_launcher};
    private List<ImageView> imageViewList=new ArrayList<>();
    LauncherPresenter launcherPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        launcherPresenter=new LauncherPresenter(this);
        initData();
        initView();
    }

    private void initData() {
        for(int id:ids){
            ImageView imageView=new ImageView(this);
            imageView.setAdjustViewBounds(true);
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(id);
            imageViewList.add(imageView);
        }
    }

    private void initView() {
        Viewpager = (ViewPager) findViewById(R.id.Viewpager);
        CountdownView = (CountdownView) findViewById(R.id.CountdownView);
        IndicatorScrollView = (IndicatorScrollView) findViewById(R.id.IndicatorScrollView);
        CountdownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcherPresenter.updateLauncherFlag(true);
            }
        });
        Viewpager.setAdapter(new GuidePageAdapter());
        Viewpager.addOnPageChangeListener(IndicatorScrollView);
        Viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position>=imageViewList.size()-1){
                    CountdownView.setVisibility(View.VISIBLE);
                    CountdownView.startCountDown();
                    CountdownView.setAddCountDownListener(new CountdownView.OnCountDownFinishListener() {
                        @Override
                        public void countDownFinished() {
                            launcherPresenter.updateLauncherFlag(true);
                        }
                    });
                }else if(position==imageViewList.size()-2){
                    CountdownView.cancelCountDown();
                    CountdownView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void startNextActivity(final Class clazz, int delaytime) {

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent( WelcomeActivity.this,clazz));
                    WelcomeActivity.this.finish();
                }
            },delaytime);

    }

    public class GuidePageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            ImageView imageView= imageViewList.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);

        }
    }
}
