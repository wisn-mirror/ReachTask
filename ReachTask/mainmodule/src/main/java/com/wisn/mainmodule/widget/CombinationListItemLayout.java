package com.wisn.mainmodule.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
    private View item_line;
    private void init(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.combination_item_list, this, true);
        item_imageview=  findViewById(R.id.item_imageview);
        item_text=  findViewById(R.id.item_text);
//        item_text .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        item_tipview=  findViewById(R.id.item_tipview);
        item_line=  findViewById(R.id.item_line);
        item_imageview_right=  findViewById(R.id.item_imageview_right);
        if(attributeSet==null)return ;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CombinationListItemLayout);
        boolean leftDrawableVisable = typedArray.getBoolean(R.styleable.CombinationListItemLayout_bottomLineVisable, true);
        boolean rightDrawableVisable = typedArray.getBoolean(R.styleable.CombinationListItemLayout_rightDrawableVisable, true);
        boolean bottomLineVisable = typedArray.getBoolean(R.styleable.CombinationListItemLayout_bottomLineVisable, true);
        String combinText = typedArray.getString(R.styleable.CombinationListItemLayout_combinText);
        String combinTipText = typedArray.getString(R.styleable.CombinationListItemLayout_combinTipText);
        Drawable leftDrawable = typedArray.getDrawable(R.styleable.CombinationListItemLayout_leftDrawable);
        Drawable rightDrawable = typedArray.getDrawable(R.styleable.CombinationListItemLayout_rightDrawable);
        if(leftDrawableVisable){
            item_imageview.setVisibility(View.VISIBLE);
        }else{
            item_imageview.setVisibility(View.GONE);
        }
        if(rightDrawableVisable){
            item_imageview_right.setVisibility(View.VISIBLE);
        }else{
            item_imageview_right.setVisibility(View.GONE);
        }
        if(bottomLineVisable){
            item_line.setVisibility(View.VISIBLE);
        }else{
            item_line.setVisibility(View.GONE);
        }
        if(!TextUtils.isEmpty(combinText)){
            item_text.setText(combinText);
        }
        if(!TextUtils.isEmpty(combinTipText)){
            item_tipview.setVisibility(View.VISIBLE);
            item_tipview.setTipText(combinTipText);
        }else{
            item_tipview.clearTip();
            item_tipview.setVisibility(View.GONE);
        }
        if(leftDrawable!=null){
            item_imageview.setImageDrawable(leftDrawable);
        }
        if(rightDrawable!=null){
            item_imageview_right.setImageDrawable(rightDrawable);
        }
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
