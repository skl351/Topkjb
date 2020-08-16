package com.top.kjb.customview;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.top.kjb.R;

public class pay_bottom extends BottomPopupView {
    public pay_bottom(@NonNull Context context) {
        super(context);
    }
    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_paybottom;
    }
}
