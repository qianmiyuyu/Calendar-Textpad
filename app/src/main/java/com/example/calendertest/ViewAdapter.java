package com.example.calendertest;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewAdapter extends FragmentPagerAdapter {
    private List<Fragment>datas;
    public ViewAdapter(FragmentManager fm, List<Fragment>list){
        super(fm);
        datas=list;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }
}
