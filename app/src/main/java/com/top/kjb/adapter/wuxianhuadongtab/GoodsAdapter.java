package com.top.kjb.adapter.wuxianhuadongtab;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ruiwenliu.Horizontallibrary.adapter.BaseRecyclerviewAdapter;
import com.ruiwenliu.Horizontallibrary.adapter.RecylcerViewHolder;
import com.top.kjb.MyApplicatipn;
import com.top.kjb.R;
import com.top.kjb.adapter.adapter_head_yuanquan;
import com.top.kjb.adapter.adapter_vip_hor_head;
import com.top.kjb.bean.bean_sports_list_item;
import com.top.kjb.customview.roundimage;
import com.top.kjb.tabfragment.sport.sports_detail_view;
import com.top.kjb.utils.SpacesItemDecoration;
import com.top.kjb.utils.functionClass;

import java.util.ArrayList;

/**
 * Created by ruiwen
 * Data:2018/8/16 0016
 * Email:1054750389@qq.com
 * Desc:
 */

public class GoodsAdapter extends BaseRecyclerviewAdapter<bean_sports_list_item, RecylcerViewHolder> {
    Context context;

    public GoodsAdapter(Activity _mActivity) {
        super(R.layout.item_goodsly);
        context = _mActivity;
    }

    @Override
    protected void convert(RecylcerViewHolder helper, bean_sports_list_item item, int position) {
//        helper.setText(R.id.tv_context, item);

        TextView id_sport_address = helper.getView(R.id.id_sport_address);
        TextView id_sport_name = helper.getView(R.id.id_sport_name);
        TextView id_sport_type = helper.getView(R.id.id_sport_type);
        roundimage id_gym_pic = helper.getView(R.id.id_gym_pic);
        TextView id_sports_nowpeople = helper.getView(R.id.id_sports_nowpeople);
        TextView id_time_string = helper.getView(R.id.id_time_string);
        id_sport_name.setText(item.togetherLogs.getName());
        id_sport_type.setText(item.getSport());
        id_sport_address.setText(item.getGymnasiumAddress());
        ImageLoader.getInstance().displayImage(item.getGymnasiumPic(),id_gym_pic);

        String str = "";
        String start_time = functionClass.INSTANCE.getTime_ms(item.togetherLogs.getTime(), "MM月dd日HH:mm");
        String end_time = functionClass.INSTANCE.getTime_ms(item.togetherLogs.getEndTime(), "HH:mm");
        String chazhi = "";
        try {
            long a = (Long.parseLong(item.togetherLogs.getEndTime()) - Long.parseLong(item.togetherLogs.getTime())) / 3600000;
            chazhi = a + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        str = start_time + " - " + end_time + " ("+chazhi + "小时)";
        id_time_string.setText(str);

        id_sports_nowpeople.setText(item.togetherLogs.getNowPeople() + "人已参与");

        RecyclerView recyclerView = helper.getView(R.id.id_RecyclerView_user_head);
        View id_click_big_view = helper.getView(R.id.id_click_big_view);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        layoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutmanager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutmanager);

        ArrayList<bean_sports_list_item.bean_sports_list_item_userInfo> images_list = item.userInfo;
        ArrayList<String> images = new ArrayList<String>();
        for (int i = 0; i <= images_list.size() - 1; i++) {
            try{
                images.add(images_list.get(i).getHeadImg());
            }catch (Exception e){
                images.add("");
            }

        }
        adapter_head_yuanquan adapter = new adapter_head_yuanquan(context, images);
        recyclerView.setAdapter(adapter);
        id_click_big_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //第一次
                Intent intent = new Intent(context, sports_detail_view.class);
                intent.putExtra("id",item.togetherLogs.getId());
                context.startActivity(intent);

            }
        });

    }

}
