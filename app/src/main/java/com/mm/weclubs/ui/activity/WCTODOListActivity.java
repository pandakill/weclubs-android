package com.mm.weclubs.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.mm.weclubs.R;
import com.mm.weclubs.ui.adapter.WCTODOListFragmentAdapter;
import com.mm.weclubs.ui.fragment.WCDynamicFragment;
import com.mm.weclubs.ui.fragment.WCIndexFragment;
import com.mm.weclubs.ui.fragment.WCMineFragment;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 上午12:19
 * 描述: 待办事项列表的activity
 */

public class WCTODOListActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private ViewPager mTabViewPager;

    private WCTODOListFragmentAdapter mTODOListFragmentAdapter;
    private ArrayList<Fragment> mFragments;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_todo_list;
    }

    @Override
    protected void initView() {

        mTabLayout = (TabLayout) findViewById(R.id.top_tab_layout);
        mTabViewPager = (ViewPager) findViewById(R.id.tab_view_pager);

        mTODOListFragmentAdapter = new WCTODOListFragmentAdapter(getSupportFragmentManager());
        mFragments = new ArrayList<>();
    }

    @Override
    protected void afterView() {
        String[] fragmentName = new String[3];
        WCDynamicFragment fragment = new WCDynamicFragment();
        mFragments.add(fragment);
        fragmentName[0] = "通知";
        WCIndexFragment indexFragment = new WCIndexFragment();
        mFragments.add(indexFragment);
        fragmentName[1] = "任务";
        WCMineFragment mineFragment = new WCMineFragment();
        mFragments.add(mineFragment);
        fragmentName[2] = "会议";

        mTabViewPager.setOffscreenPageLimit(3);
        mTODOListFragmentAdapter.setFragmentTabName(fragmentName);
        mTODOListFragmentAdapter.setFragments(mFragments);
        mTabViewPager.setAdapter(mTODOListFragmentAdapter);

        mTabLayout.setupWithViewPager(mTabViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    protected void unSubscribeObservable() {

    }
}
