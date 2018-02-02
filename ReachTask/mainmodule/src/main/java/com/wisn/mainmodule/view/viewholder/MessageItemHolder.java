package com.wisn.mainmodule.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wisn.mainmodule.R;
import com.wisn.mainmodule.widget.TipImageView;

/**
 * @author Wisn
 * @time 2018/1/25 9:51
 */


public class MessageItemHolder extends RecyclerView.ViewHolder {

    public TextView contact_name;
    public TextView contact_message;
    public TextView contact_time;
    public TipImageView contact_imageView;
    public MessageItemHolder(View  inflate){
        super(inflate);
        contact_name = inflate.findViewById(R.id.contact_name);
        contact_message = inflate.findViewById(R.id.contact_message);
        contact_time = inflate.findViewById(R.id.contact_time);
        contact_imageView = inflate.findViewById(R.id.contact_imageView);
    }

}
