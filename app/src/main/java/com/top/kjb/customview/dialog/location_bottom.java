package com.top.kjb.customview.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.top.kjb.R;
import com.top.kjb.bean.bean_main_item;
import com.top.kjb.tabfragment.newfragmentone.fragmentone_newdetail;

import per.wsj.library.AndRatingBar;

public class location_bottom extends BottomPopupView {
    public location_bottom(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_location_bottom_view;
    }

    TextView id_show_score;
    TextView id_place_name;
    TextView id_time_long;
    TextView id_years;
    TextView id_all_area;
    TextView id_all_peoples;
    TextView id_qixie;
    AndRatingBar id_star_view;
    View id_click_big;
    View id_click_gotoother;

    @Override
    protected void onCreate() {
        super.onCreate();
        id_click_big = findViewById(R.id.id_click_big);
        id_all_peoples = findViewById(R.id.id_all_peoples);
        id_all_area = findViewById(R.id.id_all_area);
        id_years = findViewById(R.id.id_years);
        id_time_long = findViewById(R.id.id_time_long);
        id_place_name = findViewById(R.id.id_place_name);
        id_star_view = findViewById(R.id.id_star_view);
        id_show_score = findViewById(R.id.id_show_score);
        id_qixie = findViewById(R.id.id_qixie);
        id_click_gotoother = findViewById(R.id.id_click_gotoother);


        id_click_gotoother.setOnClickListener(view -> {

            selectlocatonitem_dialog dialog = new selectlocatonitem_dialog(getContext());
            dialog.setbeango(item1);
            new XPopup.Builder(getContext())
                    .hasShadowBg(false)
                    .atView(id_click_gotoother)
                    .asCustom(dialog)
                    .show();


        });
        id_click_big.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), fragmentone_newdetail.class);
            intent.putExtra("id", item1.getGymID());
            intent.putExtra("lat", item1.getLat());
            intent.putExtra("lng", item1.getLng());
            getContext().startActivity(intent);
        });

        try {
            id_place_name.setText(item1.getName());
            id_star_view.setRating(item1.getComprehensiveScore());
            id_show_score.setText("" + item1.getComprehensiveScore());
            id_years.setText(item1.getOpenYear()+"年");
            id_all_area.setText( "总面积"+item1.getArea()+"m²");
            id_all_peoples.setText("容纳"+item1.getSeatingCapacity()+"人");
            id_qixie.setText("器械"+item1.getInstrumentTapNum()+"种");

            id_time_long.setText(item1.getBusinessHours().get(0)+"  "+item1.getBusinessHours().get(1));

        } catch (Exception e) {

        }

    }

    bean_main_item item1 = null;

    public void setbeango(bean_main_item bean) {
        item1 = bean;
    }
}
