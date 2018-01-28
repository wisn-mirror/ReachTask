package com.wisn.mainmodule.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisn.mainmodule.R;

/**
 * @author Wisn
 * @time 2018/1/28 18:23
 */


public class CombinationListItemLayout extends RelativeLayout {
    private ImageView item_imageview;
    private TextView item_text;
    private TipView item_tipview;
    private ImageView item_imageview_right;
    private void init(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.combination_item_list, this, true);
        item_imageview=  findViewById(R.id.item_imageview);
        item_text=  findViewById(R.id.item_text);
//        item_text .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        item_tipview=  findViewById(R.id.item_tipview);
        item_imageview_right=  findViewById(R.id.item_imageview_right);
        if(attributeSet==null)return ;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CombinationListItemLayout);

    }
    public CombinationListItemLayout(Context context) {
        super(context);
        init(context, null);
    }

    public CombinationListItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CombinationListItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }



}
