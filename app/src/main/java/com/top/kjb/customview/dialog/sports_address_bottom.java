package com.top.kjb.customview.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.top.kjb.R;
import com.top.kjb.bean.bean_main_item;
import com.top.kjb.customview.roundimage;
import com.top.kjb.utils.Sp;

import java.util.ArrayList;

public class sports_address_bottom extends BottomPopupView {
    public sports_address_bottom(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_sports_address_bottom;
    }


    LinearLayout id_big_address_list;


    @Override
    protected void onCreate() {
        super.onCreate();

        id_big_address_list = findViewById(R.id.id_big_address_list);

        for (int i = 0; i <= list.size()-1; i++) {
            View view = View.inflate(getContext(), R.layout.layout_bottom_sportslistitem,null);
            TextView number=view.findViewById(R.id.id_number_text);
            TextView id_gym_name=view.findViewById(R.id.id_gym_name);
            roundimage id_gym_pic=view.findViewById(R.id.id_gym_pic);
            TextView id_gym_address=view.findViewById(R.id.id_gym_address);
            number.setText(""+(i+1));
            id_gym_name.setText(list.get(i).getName());
            id_gym_address.setText(list.get(i).getAddress());
            ImageLoader.getInstance().displayImage(list.get(i).getHeadImg(),id_gym_pic);
            int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Sp.INSTANCE.getSelect_bottom_address());
                    intent.putExtra("name",list.get(finalI).getName());
                    intent.putExtra("id",list.get(finalI).getGymID());
                    getContext().sendBroadcast(intent);
                    dismiss();
                }
            });
            id_big_address_list.addView(view);
        }


    }


    ArrayList<bean_main_item> list;
    public void setdatalist(ArrayList<bean_main_item> listAddress) {
        list=listAddress;
    }
}
