package com.mm.weclubs.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.mm.weclubs.R;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.data.network.pojo.WCMyClubListInfo;
import com.mm.weclubs.ui.adapter.WCTODOListFragmentAdapter;
import com.mm.weclubs.ui.fragment.WCMeetingListFragment;
import com.mm.weclubs.ui.fragment.WCMissionListFragment;
import com.mm.weclubs.ui.fragment.WCNotifyListFragment;

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

    private WCMyClubListInfo mClubListInfo;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_todo_list;
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
        mClubListInfo = (WCMyClubListInfo) extras.getSerializable("clubListInfo");
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
        Bundle extra = new Bundle();
        extra.putSerializable("clubListInfo", mClubListInfo);
        String[] fragmentName = new String[3];
        WCNotifyListFragment notifyListFragment = new WCNotifyListFragment();
        notifyListFragment.setArguments(extra);
        mFragments.add(notifyListFragment);
        fragmentName[0] = "通知";
        WCMissionListFragment missionListFragment = new WCMissionListFragment();
        missionListFragment.setArguments(extra);
        mFragments.add(missionListFragment);
        fragmentName[1] = "任务";
        WCMeetingListFragment meetingListFragment = new WCMeetingListFragment();
        meetingListFragment.setArguments(extra);
        mFragments.add(meetingListFragment);
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
