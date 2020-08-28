package com.top.kjb.customview;

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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllPlace_select_bottom extends BottomPopupView {
    public AllPlace_select_bottom(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_allplaceselectbottom;
    }

    LinearLayout id_big_view_all_mendian;

    ArrayList<View> list_view;
    ArrayList<ImageView> list_image;

    @Override
    protected void onCreate() {
        super.onCreate();
        id_big_view_all_mendian = findViewById(R.id.id_big_view_all_mendian);
        list_view = new ArrayList();
        list_image = new ArrayList();
        for (int i = 0; i <= list.size() - 1; i++) {
            View view = View.inflate(getContext(), R.layout.layout_select_place_item, null);
            View id_click_one = view.findViewById(R.id.id_click_one);
            ImageView id_select_image = view.findViewById(R.id.id_select_image);
            list_view.add(id_click_one);
            list_image.add(id_select_image);
            TextView text = view.findViewById(R.id.id_place_name);
            text.setText(list.get(i).getGymName());
            id_big_view_all_mendian.addView(view);
        }
        for (int i = 0; i <= list_view.size() - 1; i++) {
            int finalI = i;
            list_view.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 0; j <= list_image.size() - 1; j++) {
                        list_image.get(j).setSelected(false);
                    }
                    list_image.get(finalI).setSelected(true);
                    Intent intent = new Intent(Sp.INSTANCE.getSelectplacelast());
                    intent.putExtra("id",list.get(finalI).getId());
                    intent.putExtra("place",list.get(finalI).getGymName());
                    getContext().sendBroadcast(intent);
                    dismiss();
                }
            });
        }
    }

    ArrayList<bean_gym_payed_item> list;

    public void setplacelist(ArrayList<bean_gym_payed_item> listPlace) {
        list = listPlace;
    }
}
