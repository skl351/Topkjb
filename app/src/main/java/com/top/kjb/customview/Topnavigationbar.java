package com.top.kjb.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.top.kjb.R;


//统一导航栏
public class Topnavigationbar extends RelativeLayout {

    private TextView top_text;
    private View id_back;
    private View topTab;

    public Topnavigationbar(Context context) {
        this(context, null);
    }

    public Topnavigationbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Topnavigationbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topnavigationbar);
        int n = ta.getIndexCount();


        for (int i = 0; i < n; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.Topnavigationbar_top_text:
                    String str = ta.getString(attr);
                    top_text.setText(str);
                    break;
                case R.styleable.Topnavigationbar_top_backgraound_color:
                    topTab.setBackgroundColor(ta.getColor(attr, Color.WHITE));
                    break;
                default:
                    break;
            }
        }


    }

    private void initview(Context context) {
        View.inflate(context, R.layout.layout_topnavigationbar, this);
        topTab = findViewById(R.id.topTab);
        top_text = findViewById(R.id.top_text);
        id_back = findViewById(R.id.id_back);
    }
}
