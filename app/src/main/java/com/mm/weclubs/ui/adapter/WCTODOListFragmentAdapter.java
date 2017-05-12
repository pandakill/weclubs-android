package com.mm.weclubs.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blankj.utilcode.utils.EmptyUtils;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/18 下午2:01
 * 描述:
 */
public class WCTODOListFragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragments;
    private String[] mFragmentTabName;

    public WCTODOListFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        mFragments = fragments;
    }

    public void setFragmentTabName(String[] names) {
        mFragmentTabName = names;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle extras = mFragments.get(position).getArguments();
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putString("tab_name", mFragmentTabName[position]);
        mFragments.get(position).setArguments(extras);
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (EmptyUtils.isEmpty(mFragmentTabName)) {
            return null;
        }
        return mFragmentTabName[position];
    }

    @Override
    public int getCount() {
        if (EmptyUtils.isEmpty(mFragments)) {
            return 0;
        }
        return mFragments.size();
    }
}
