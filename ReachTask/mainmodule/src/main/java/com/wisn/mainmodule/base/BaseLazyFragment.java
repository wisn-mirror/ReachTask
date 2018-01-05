package com.wisn.mainmodule.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wisn.skinlib.base.SkinFragment;

/**
 * Created by wisn on 2017/9/13.
 */

public abstract class BaseLazyFragment extends SkinFragment {
    public String TAG = "BaseLazyFragment";

    private View rootView;
    private boolean isFirstVisible = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = onCreateLazyView(inflater, container, savedInstanceState);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isFrameVisible = getUserVisibleHint();
        if (isFirstVisible && isFrameVisible) {
            firstVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
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
        if (rootView != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rootView = null;
        isFirstVisible = false;
    }

    public void onFragmentVisibleChange(boolean isVisible) {}

    public void firstVisible() {}

    public abstract View onCreateLazyView(LayoutInflater inflater,
                                          @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState);
}
