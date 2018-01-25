package com.wisn.mainmodule.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wisn.mainmodule.R;

/**
 * @author Wisn
 * @time 2018/1/25 9:51
 */


public class ContactsItemHolder extends RecyclerView.ViewHolder {

    public TextView textview;
    public ContactsItemHolder(View  inflate){
        super(inflate);
        textview = inflate.findViewById(R.id.textView);
    }

}
