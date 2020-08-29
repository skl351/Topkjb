package com.top.kjb.customview;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.top.kjb.R;
import com.top.kjb.utils.Sp;

public class upcenterimage_bottom extends BottomPopupView {
    public upcenterimage_bottom(@NonNull Context context) {
        super(context);
    }
    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_upcenterimage_bottom;
    }

    View  id_click_updata_image;
    View  id_cancle;
    @Override
    protected void onCreate() {
        super.onCreate();
        id_cancle=findViewById(R.id.id_cancle);
        id_click_updata_image=findViewById(R.id.id_click_updata_image);

        id_click_updata_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sp.INSTANCE.getUpdatabackimage());
                getContext().sendBroadcast(intent);
                dismiss();
            }
        });
        id_cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
