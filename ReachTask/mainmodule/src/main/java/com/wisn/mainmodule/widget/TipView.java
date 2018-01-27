package com.wisn.mainmodule.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wisn.mainmodule.R;


/**
 * Created by wisn on 2017/9/13.
 */

@SuppressLint("AppCompatCustomView")
public class TipView extends View {
    private int tipTextSize;
    private int tipRedius;
    private int tipTextRedius;
    private int tipRediusMarginTop;
    private int tipRediusMarginRight;
    private int tipTextColor;
    private int tipBackground;
    private Paint mPaint;
    private Rect mRect;
    private String textMsg = null;
    private boolean isTip = false;


    public TipView(Context context) {
        super(context);
    }

    public TipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ButtonView);
        tipTextRedius = a.getDimensionPixelSize(R.styleable.ButtonView_tipTextRedius, 10);
        tipTextSize = a.getDimensionPixelSize(R.styleable.ButtonView_tipTextSize, 10);
        tipRedius = a.getDimensionPixelSize(R.styleable.ButtonView_tipRedius, 10);
        tipRediusMarginTop = a.getDimensionPixelSize(R.styleable.ButtonView_tipRediusMarginTop, 10);
        tipRediusMarginRight = a.getDimensionPixelSize(R.styleable.ButtonView_tipRediusMarginRight, 10);
        tipTextColor = a.getColor(R.styleable.ButtonView_tipTextColor, Color.WHITE);
        tipBackground = a.getColor(R.styleable.ButtonView_tipBackground, Color.RED);
        textMsg = a.getString(R.styleable.ButtonView_tipText);
        if (textMsg != null) checkText(textMsg);
        isTip = a.getBoolean(R.styleable.ButtonView_isTip, false);
        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mRect = new Rect();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaint == null) return;

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (textMsg != null && !isTip) {
            mPaint.setColor(tipBackground);
            canvas.drawCircle( tipRediusMarginRight + tipTextRedius,
                    tipRediusMarginTop + tipTextRedius,
                              tipTextRedius,
                              mPaint);
            mPaint.setColor(tipTextColor);
            //先设置字体，否者第一次测量的字体大小和之后的大小不同
            mPaint.setTextSize(tipTextSize);
            mPaint.getTextBounds(textMsg, 0, textMsg.length(), mRect);
            if ("1".equals(textMsg)) {
                canvas.drawText(textMsg,
                                (float) ( tipRediusMarginRight -
                                         (mRect.width() + mRect.width() / 2.2) / 2 + tipTextRedius),
                        tipRediusMarginTop + tipTextRedius + mRect.height() / 2,
                                mPaint);
            } else {
                canvas.drawText(textMsg,
                                 tipRediusMarginRight - mRect.width() / 2 + tipTextRedius,
                        tipRediusMarginTop + tipTextRedius + mRect.height() / 2,
                                mPaint);
            }

        } else if (isTip) {
            mPaint.setColor(tipBackground);
            canvas.drawCircle( tipRediusMarginRight + tipRedius,
                    tipRediusMarginTop + tipRedius,
                              tipRedius,
                              mPaint);
        }

    }

    public void setTipBackground(int background) {
        this.tipBackground = background;
        invalidate();
    }

    public void setTipText(String text) {
        if (text != null) {
            checkText(text);
            isTip = false;
            invalidate();
        }
    }

    public void checkText(String text) {
        if (text.length() > 2) {
            this.textMsg = "...";
        } else {
            this.textMsg = text;
        }
    }

    public void clearTip() {
        this.textMsg = null;
        isTip = false;
        invalidate();
    }

    public void setTip() {
        isTip = true;
        this.textMsg = null;
        invalidate();
    }

}
