package com.wisn.mainmodule.view.viewholder;


import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wisn.mainmodule.R;

/**
 * @author Wisn
 * @time 2018/2/10 8:20
 */


public class ToolbarHolder {
    private  Toolbar toolbar;
    public  Button toolbar_ib_left,toolbar_ib_right;
    public  TextView toolbar_title_center;

    public ToolbarHolder(Toolbar toolbar){
        this.toolbar=toolbar;
        toolbar_ib_left = toolbar.findViewById(R.id.toolbar_ib_left);
        toolbar_ib_right = toolbar.findViewById(R.id.toolbar_ib_right);
        toolbar_title_center = toolbar.findViewById(R.id.toolbar_title_center);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
    public void setCenterTitle(String text){
        if(toolbar_title_center!=null){
            toolbar_title_center.setVisibility(View.VISIBLE);
            toolbar_title_center.setText(text);
        }
    }
    public void setRightButton(String text, View.OnClickListener onClickListener){
        if(toolbar_ib_right!=null){
            toolbar_ib_right.setVisibility(View.VISIBLE);
            toolbar_ib_right.setText(text);
            toolbar_ib_right.setOnClickListener(onClickListener);
        }
    }
    public void setLeftButton(String text, View.OnClickListener onClickListener){
        if(toolbar_ib_left!=null){
            toolbar_ib_left.setVisibility(View.VISIBLE);
            toolbar_ib_left.setText(text);
            toolbar_ib_left.setOnClickListener(onClickListener);

        }
    }
}
