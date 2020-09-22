package com.top.kjb.adapter.wuxianhuadongtab;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.top.kjb.R;


/**
 * Created by ruiwen
 * Data:2018/8/16 0016
 * Email:1054750389@qq.com
 * Desc:简写BaseFragment
 */

public abstract class BaseFragment2 extends Fragment {

    SmartRefreshLayout id_SmartRefreshLayout_donggtai;
    RecyclerView goodsRv;
    FrameLayout frameLayout;
    private View mContentView;
    View id_nodate;
    private Context mContext;
    public Activity _mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_goodsly2, container, false);
        goodsRv = mContentView.findViewById(R.id.goodsRv);
        id_SmartRefreshLayout_donggtai = mContentView.findViewById(R.id.id_SmartRefreshLayout_donggtai);
        id_nodate = mContentView.findViewById(R.id.id_nodate);
        frameLayout = mContentView.findViewById(R.id.framelayout);
        mContext = getContext();
        initView();
        setData();
        return mContentView;
    }

    /**
     * 一些Data的相关操作
     */
    protected abstract void setData();

    /**
     * 初始化view
     */
    protected abstract void initView();

    public View getContentView() {
        return mContentView;
    }

    public Context getMContext() {
        return mContext;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}


