package com.top.kjb.customview;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.top.kjb.R;
import com.top.kjb.bean.bean_user_comment;
import com.top.kjb.utils.Sp;

public class commentjubao_bottom extends BottomPopupView {
    public commentjubao_bottom(@NonNull Context context) {
        super(context);
    }
    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_commentjubao_bottom;
    }

    View  id_click_huifu;
    View  id_click_jubao;
    View  id_cancle;
    @Override
    protected void onCreate() {
        super.onCreate();
        id_cancle=findViewById(R.id.id_cancle);
        id_click_huifu=findViewById(R.id.id_click_huifu);
        id_click_jubao=findViewById(R.id.id_click_jubao);

        id_click_huifu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sp.INSTANCE.getHuifuintent());
                intent.putExtra("parentId", bean.getId());
                intent.putExtra("commentsId", mainid);
                intent.putExtra("replyType", replyType);
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
    bean_user_comment.bean_user_comment bean;
    public void setbean( bean_user_comment.bean_user_comment bean) {
        this.bean=bean;
    }

    int replyType=0;
    public void setreplyType(int i) {
        replyType=i;
    }

    int mainid=0;
    public void setmaind(int mainid) {
        this.mainid=mainid;
    }
}
