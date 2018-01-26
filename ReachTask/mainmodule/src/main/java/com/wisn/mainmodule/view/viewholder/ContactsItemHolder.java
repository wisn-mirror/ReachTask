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

    public TextView contact_name;
    public ContactsItemHolder(View  inflate){
        super(inflate);
        contact_name = inflate.findViewById(R.id.contact_name);
    }

}
