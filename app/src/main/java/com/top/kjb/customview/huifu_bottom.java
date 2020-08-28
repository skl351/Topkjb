package com.top.kjb.customview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.core.BottomPopupView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.top.kjb.R;
import com.top.kjb.adapter.adapter_user_comment2;
import com.top.kjb.adapter.adapter_user_comment_huifu;
import com.top.kjb.bean.Result;
import com.top.kjb.bean.bean_user_comment;
import com.top.kjb.model.TwoModel;
import com.top.kjb.utils.Show_toast;
import com.top.kjb.utils.functionClass;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class huifu_bottom extends BottomPopupView {
    public huifu_bottom(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_long_bottom_huifu;
    }

    View id_click_big_view;
    View id_close_view;
    ImageView id_head_img;
    TextView id_username;
    TextView id_time;
    TextView id_use_commit_text;
    RecyclerView id_RecyclerView;

    @Override
    protected void onCreate() {
        super.onCreate();
        id_RecyclerView = findViewById(R.id.id_RecyclerView);
        id_click_big_view = findViewById(R.id.id_click_big_view);
        id_head_img = findViewById(R.id.id_head_img);
        id_username = findViewById(R.id.id_username);
        id_time = findViewById(R.id.id_time);
        id_use_commit_text = findViewById(R.id.id_use_commit_text);
        id_close_view = findViewById(R.id.id_close_view);

        LinearLayoutManager layoutmanager = new LinearLayoutManager(getContext());
        id_RecyclerView.setLayoutManager(layoutmanager);

        ImageLoader.getInstance().displayImage(bean.getHeadImg(),id_head_img);
        id_username.setText(bean.getUsername());
        id_time.setText(functionClass.INSTANCE.getTime_ms(bean.getStartTime().toString(), "yyyy-MM-dd hh:mm"));
        id_use_commit_text.setText(bean.getCommentsText());
        switch (textType){
            case 1:quanzi();break;
            case 2:xuanliangdian();break;
            case 3:zixun();break;

        }

        id_click_big_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        id_close_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
    TwoModel  twoModel=new TwoModel();
    int currentpage=1;
    int pagesize=10;
    ArrayList list;
    adapter_user_comment_huifu adapter_comment;
    private void xuanliangdian() {
        twoModel.highlightsReplyselectReply(
                functionClass.INSTANCE.getToken(),
                bean.getId(),
                currentpage,
                pagesize
        ).enqueue(new Callback<Result<bean_user_comment>>() {
            @Override
            public void onResponse(Call<Result<bean_user_comment>> call, Response<Result<bean_user_comment>> response) {
                bean_user_comment bean = response.body().getResult();
                if ("success".equals( response.body().getFlag())) {
                    list = bean.list;
                            adapter_comment =
                            new adapter_user_comment_huifu(getContext(), list);
                    adapter_comment.setTextType(2);
                    id_RecyclerView.setAdapter(adapter_comment);
                } else {
                    Show_toast.showText(getContext(), "炫亮点回复评论list失败");
                }
            }

            @Override
            public void onFailure(Call<Result<bean_user_comment>> call, Throwable t) {

            }
        });

    }
    private void quanzi() {
        twoModel.circleReplyselectReply(
                functionClass.INSTANCE.getToken(),
                bean.getId(),
                currentpage,
                pagesize
        ).enqueue(new Callback<Result<bean_user_comment>>() {
            @Override
            public void onResponse(Call<Result<bean_user_comment>> call, Response<Result<bean_user_comment>> response) {
                bean_user_comment bean = response.body().getResult();
                if ("success".equals( response.body().getFlag())) {
                    list = bean.list;
                    adapter_comment =
                            new adapter_user_comment_huifu(getContext(), list);
                    adapter_comment.setTextType(2);
                    id_RecyclerView.setAdapter(adapter_comment);
                } else {
                    Show_toast.showText(getContext(), "炫亮点回复评论list失败");
                }
            }

            @Override
            public void onFailure(Call<Result<bean_user_comment>> call, Throwable t) {

            }
        });

    }
    private void zixun() {
        twoModel.informationReplyselectReply(
                functionClass.INSTANCE.getToken(),
                bean.getId(),
                currentpage,
                pagesize
        ).enqueue(new Callback<Result<bean_user_comment>>() {
            @Override
            public void onResponse(Call<Result<bean_user_comment>> call, Response<Result<bean_user_comment>> response) {
                bean_user_comment bean = response.body().getResult();
                if ("success".equals( response.body().getFlag())) {
                    list = bean.list;
                    adapter_comment =
                            new adapter_user_comment_huifu(getContext(), list);
                    adapter_comment.setTextType(2);
                    id_RecyclerView.setAdapter(adapter_comment);
                } else {
                    Show_toast.showText(getContext(), "炫亮点回复评论list失败");
                }
            }

            @Override
            public void onFailure(Call<Result<bean_user_comment>> call, Throwable t) {

            }
        });

    }
    bean_user_comment.bean_user_comment bean;
    public void setbean(@Nullable bean_user_comment.bean_user_comment bean) {
        this.bean=bean;
    }

    int textType;
    public void settexttype(int textType) {
        this.textType=textType;
    }
}
