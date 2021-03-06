package com.wisn.mainmodule.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.widget.TipView;

/**
 * @author Wisn
 * @time 2018/1/25 9:51
 */


public class ContactsItemHolder extends RecyclerView.ViewHolder {

    public TextView contact_name;
    public ImageView contact_imageView;
    public TipView contact_tip;
    public ContactsItemHolder(View  inflate){
        super(inflate);
        contact_name = inflate.findViewById(R.id.contact_name);
        contact_imageView = inflate.findViewById(R.id.contact_imageView);
        contact_tip = inflate.findViewById(R.id.contact_tip);
    }

}
