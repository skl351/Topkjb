package com.top.kjb.customview.dialog;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.core.PositionPopupView;
import com.top.kjb.R;
import com.top.kjb.adapter.adapter_type_sports;
import com.top.kjb.bean.bean_type_item;
import com.top.kjb.utils.functionClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class sport_type_dialog extends PositionPopupView {
    public sport_type_dialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_sport_type_dialog_view;
    }

    RecyclerView id_RecyclerView_type;
    View id_click_dialog_detele;
    View smart_type_top;
    @Override
    protected void onCreate() {
        super.onCreate();
        smart_type_top = findViewById(R.id.smart_type_top);
        id_click_dialog_detele = findViewById(R.id.id_click_dialog_detele);
        id_RecyclerView_type = findViewById(R.id.id_RecyclerView_type);
        smart_type_top.getLayoutParams().height= functionClass.INSTANCE.getbarHight(getContext());
        GridLayoutManager layoutmanager = new GridLayoutManager(getContext(), 3);
        id_RecyclerView_type.setLayoutManager(layoutmanager);
        id_click_dialog_detele.setOnClickListener(view -> {
            dismiss();
        });

        adapter_type_sports adapter = new adapter_type_sports(getContext(), list);
        adapter.setContextdialog(this);
        id_RecyclerView_type.setAdapter(adapter);
    }

    ArrayList<bean_type_item> list;

    public void setlist(ArrayList<bean_type_item> listType) {
        list = listType;
    }
}
