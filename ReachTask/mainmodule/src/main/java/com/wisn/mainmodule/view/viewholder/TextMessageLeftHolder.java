package com.wisn.mainmodule.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wisn.mainmodule.R;

/**
 * @author Wisn
 * @time 2018/1/25 9:51
 */


public class TextMessageLeftHolder extends RecyclerView.ViewHolder {

    public ImageView photo;
    public TextView contact_text;
    public TextMessageLeftHolder(View  inflate){
        super(inflate);
        photo = inflate.findViewById(R.id.photo);
        contact_text = inflate.findViewById(R.id.contact_text);
    }

}
