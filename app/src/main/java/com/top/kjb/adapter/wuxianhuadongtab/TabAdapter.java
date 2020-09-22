package com.top.kjb.adapter.wuxianhuadongtab;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;


import java.util.List;

/**
 * Created by ruiwen
 * Data:2018/8/16 0016
 * Email:1054750389@qq.com
 * Desc:
 */

public class TabAdapter extends FragmentPagerAdapter {
    private int position;

    private List<BaseFragment> list;

    public TabAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.list = list;
    }

    public Fragment getCurrentFragment() {
        return list.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        this.position = position;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    /**
     * 刷新新数据
     */
    public void refreshData(List<BaseFragment> listFragment){
        list.clear();
        list=listFragment;
        notifyDataSetChanged();
        }
}
