package com.mm.weclubs.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/18 下午2:01
 * 描述:
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragments;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        if (mFragments == null || mFragments.size() == 0) {
            return 0;
        }
        return mFragments.size();
    }
}
