package com.top.kjb.customview.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.AttachPopupView;
import com.top.kjb.R;
import com.top.kjb.bean.bean_main_item;
import com.top.kjb.utils.Show_toast;
import com.top.kjb.utils.java_util;

public class selectlocatonitem_dialog extends AttachPopupView {

    public selectlocatonitem_dialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.selectlocation_dialog_view;
    }

    View id_click_baidu;
    View id_click_gaode;

    @Override
    protected void onCreate() {
        super.onCreate();
        id_click_baidu = findViewById(R.id.id_click_baidu);
        id_click_gaode = findViewById(R.id.id_click_gaode);

        id_click_baidu.setOnClickListener(view -> {
           try {
               Intent intent = new Intent();
               intent.setData(Uri.parse("baidumap://map/marker?location=" + item1.getLat() + "," + item1.getLng() + "&title=" + item1.getName() + "&content=" + item1.getGDescribe() + "&traffic=on&src=andr.baidu.openAPIdemo"));
               getContext().startActivity(intent);
           }catch (Exception e){
               e.printStackTrace();
               Show_toast.showText(getContext(),"未安装百度地图");
           }
            dismiss();
        });
        id_click_gaode.setOnClickListener(view -> {
            try {
                String a = new java_util().bmapTranQQMap(Double.valueOf(item1.getLat()), Double.valueOf(item1.getLng()));
                String str1=a.split(",")[0];
                String str2=a.split(",")[1];
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                Uri uri = Uri.parse("androidamap://viewMap?sourceApplication=kjb&poiname=" + item1.getName() + "&lat=" +
                        str1 + "&lon=" + str2 + "&dev=0");
                //将功能Scheme以URI的方式传入data
                intent.setData(uri);
                //启动该页面即可
                getContext().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Show_toast.showText(getContext(), "未安装高德地图");
            }
        });
    }

    bean_main_item item1 = null;

    public void setbeango(bean_main_item bean) {
        item1 = bean;
    }
}
