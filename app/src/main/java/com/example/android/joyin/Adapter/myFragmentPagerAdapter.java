package com.example.android.joyin.Adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/6/24.
 */
public class myFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<ListFragment> list;
    public myFragmentPagerAdapter(FragmentManager fm, ArrayList<ListFragment> list){
        super(fm);
        this.list=list;
    }
    @Override
    public ListFragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
