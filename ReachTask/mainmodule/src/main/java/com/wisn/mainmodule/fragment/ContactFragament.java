package com.wisn.mainmodule.fragment;

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


public class ContactFragament extends BaseLazyFragment{
    @Override
    public View onCreateLazyView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        Bundle bundle = this.getArguments();
//        String tag = bundle.getString("TAG");
        TextView textView = (TextView) view.findViewById(R.id.fragment_textView);
        textView.setText("ContactFragament");
        return view;
    }
}
