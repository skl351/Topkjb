package com.top.kjb.customview.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.top.kjb.R;
import com.top.kjb.bean.bean_gym_payed_item;
import com.top.kjb.utils.Sp;

import java.util.ArrayList;

public class jubao_select_bottom extends BottomPopupView {
    public jubao_select_bottom(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_jubao_selectbottom;
    }

    View id_click_one;
    View id_click_two;
    View id_click_three;


    @Override
    protected void onCreate() {
        super.onCreate();
        id_click_one = findViewById(R.id.id_click_one);
        id_click_two = findViewById(R.id.id_click_two);
        id_click_three = findViewById(R.id.id_click_three);

        id_click_one.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sp.INSTANCE.getJubaoinfo());
                intent.putExtra("jubao", "色情");
                getContext().sendBroadcast(intent);
                dismiss();
            }
        });

        id_click_two.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sp.INSTANCE.getJubaoinfo());
                intent.putExtra("jubao", "广告");
                getContext().sendBroadcast(intent);
                dismiss();
            }
        });
        id_click_three.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sp.INSTANCE.getJubaoinfo());
                intent.putExtra("jubao", "政治");
                getContext().sendBroadcast(intent);
                dismiss();
            }
        });

    }

}
