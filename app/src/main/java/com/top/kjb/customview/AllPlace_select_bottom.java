package com.top.kjb.customview;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.top.kjb.R;

public class AllPlace_select_bottom extends BottomPopupView {
    public AllPlace_select_bottom(@NonNull Context context) {
        super(context);
    }
    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_allplaceselectbottom;
    }
}
