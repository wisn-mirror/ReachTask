package com.wisn.mainmodule.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.base.BaseLazyFragment;

/**
 * @author Wisn
 * @time 2018/1/23 20:42
 */


public class WorkFragament extends BaseLazyFragment{
    @Override
    public String getTAG() {
        return "WorkFragament";
    }
    @Override
    public View onCreateLazyView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        Bundle bundle = this.getArguments();
//        String tag = bundle.getString("TAG");
        TextView textView = (TextView) view.findViewById(R.id.fragment_textView);
        textView.setText("WorkFragament");
        return view;
    }
}
