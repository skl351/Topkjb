package com.top.kjb.customview.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.top.kjb.R;
import com.top.kjb.utils.Sp;

public class sex_bottom extends BottomPopupView {
    public sex_bottom(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_sex;
    }

    View id_sex_nan;
    View id_sex_nv;
    View id_cancel;

    @Override
    protected void onCreate() {
        super.onCreate();
        id_sex_nan = findViewById(R.id.id_sex_nan);
        id_sex_nv = findViewById(R.id.id_sex_nv);
        id_cancel = findViewById(R.id.id_cancel);

        id_sex_nan.setOnClickListener(view -> {
            Intent intent = new Intent(Sp.INSTANCE.getSexselect());
            intent.putExtra("sex","男");
            getContext().sendBroadcast(intent);
            dismiss();
        });
        id_sex_nv.setOnClickListener(view -> {
            Intent intent = new Intent(Sp.INSTANCE.getSexselect());
            intent.putExtra("sex","女");
            getContext().sendBroadcast(intent);
            dismiss();
        });
        id_cancel.setOnClickListener(view -> {
            dismiss();
        });
    }
}
