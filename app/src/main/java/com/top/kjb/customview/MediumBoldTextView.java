package com.top.kjb.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class MediumBoldTextView extends androidx.appcompat.widget.AppCompatTextView {
    public MediumBoldTextView(Context context) {
        super(context);
    }

    public MediumBoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MediumBoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取当前控件的画笔
        TextPaint paint = getPaint();
        //设置画笔的描边宽度值
        paint.setStrokeWidth(1.0f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        super.onDraw(canvas);
    }
}