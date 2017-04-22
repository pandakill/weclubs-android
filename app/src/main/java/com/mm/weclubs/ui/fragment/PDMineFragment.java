package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mm.weclubs.R;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/15 下午2:26
 * 描述:
 */

public class PDMineFragment extends Fragment {

    Toolbar mToolBar;

    private AppCompatActivity mAppCompatActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mAppCompatActivity = (AppCompatActivity) getActivity();
        View fragmentView = inflater.inflate(R.layout.fragment_my_daily, null);
        mToolBar = (Toolbar) fragmentView.findViewById(R.id.toolbar);
        mToolBar.setTitle("个人中心");
        mAppCompatActivity.setSupportActionBar(mToolBar);

        return fragmentView;
    }

}
