package com.mm.weclubs.ui.activity.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;

import com.mm.weclubs.R;
import com.mm.weclubs.app.manage.mission.WCManageMissionPresenter;
import com.mm.weclubs.app.manage.mission.WCManageMissionView;
import com.mm.weclubs.data.pojo.WCManageMissionInfo;
import com.mm.weclubs.ui.activity.BaseActivity;
import com.mm.weclubs.ui.adapter.manage.WCManageMissionAdapter;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:39
 * 描述:  任务管理列表页面
 */

public class WCMissionManageListActivity extends BaseActivity implements WCManageMissionView {

    private SwipeRefreshLayout mRefreshLayout;
    private HaoRecyclerView mRecyclerView;

    private WCManageMissionAdapter mManageMissionAdapter;

    private WCManageMissionPresenter mManageMissionPresenter;

    private int mPageNo = 1;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_notify_manage_list;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    @Override
    protected void initView() {

        getTitleBar().setTitleText("会议管理");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (HaoRecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(layoutManager);

        mManageMissionAdapter = new WCManageMissionAdapter(this);
        mRecyclerView.setAdapter(mManageMissionAdapter);
    }

    @Override
    protected void afterView() {

        mManageMissionPresenter = new WCManageMissionPresenter(this);
        mManageMissionPresenter.attachView(this);

        attachRefreshLayout(mRefreshLayout, mRecyclerView);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mManageMissionPresenter.getMissionListFromServer(mPageNo);
            }
        });

        mManageMissionPresenter.getMissionListFromServer(mPageNo);
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    protected void unSubscribeObservable() {
    }

    @Override
    public void refreshMissionList(ArrayList<WCManageMissionInfo> list) {
        mManageMissionAdapter.setItems(list);

        hideProgressDialog();
    }

    @Override
    public void addMissionList(ArrayList<WCManageMissionInfo> list, boolean hasMore) {
        mPageNo ++;
        mManageMissionAdapter.addItems(list);

        hideProgressDialog();
    }
}
