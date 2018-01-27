package com.wisn.mainmodule.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisn.skinlib.base.SkinFragment;

/**
 * Created by wisn on 2017/9/13.
 */

public abstract class BaseLazyFragment extends SkinFragment {

    private View rootView;
    private boolean isFirstVisible = true;
    public abstract  String getTAG();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = onCreateLazyView(inflater, container, savedInstanceState);
        }
        Log.e(getTAG(),"onCreateView");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(getTAG(),"onViewCreated");
        boolean isFrameVisible = getUserVisibleHint();
        if (isFirstVisible && isFrameVisible) {
            firstVisible();
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(getTAG(),"setUserVisibleHint:"+isVisibleToUser);

        boolean isFrameVisible = getUserVisibleHint();
        if (isFirstVisible && isFrameVisible) {
            firstVisible();
            isFirstVisible = false;
        }
        if (isFrameVisible) {
            onFragmentVisibleChange(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(getTAG(),"onDestroyView");

        if (rootView != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(getTAG(),"onDestroy");

        rootView = null;
        isFirstVisible = false;
    }

    /**
     * 当页面显示调用
     * @param isVisible
     */
    public void onFragmentVisibleChange(boolean isVisible) {
        Log.e(getTAG(),"onFragmentVisibleChange:"+isVisible);

    }

    /**
     * 第一次页面显示调用
     */
    public void firstVisible() {
        Log.e(getTAG(),"firstVisible");

    }

    public abstract View onCreateLazyView(LayoutInflater inflater,
                                          @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState);
}
