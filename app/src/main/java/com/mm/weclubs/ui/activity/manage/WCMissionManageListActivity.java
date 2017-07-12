package com.mm.weclubs.ui.activity.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mm.weclubs.R;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.app.manage.mission.WCManageMissionListContract;
import com.mm.weclubs.data.network.pojo.WCManageMissionInfo;
import com.mm.weclubs.ui.adapter.manage.WCManageMissionAdapter;
import com.mm.weclubs.util.ImageLoaderHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:39
 * 描述:  任务管理列表页面
 */

public class WCMissionManageListActivity extends BaseActivity implements WCManageMissionListContract.View {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private WCManageMissionAdapter mManageMissionAdapter;

    @Inject
    WCManageMissionListContract.Presenter<WCManageMissionListContract.View> mPresenter;
    @Inject
    ImageLoaderHelper mImageLoaderHelper;
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
        getActivityComponent().inject(this);

        getTitleBar().setTitleText("任务管理");
        getTitleBar().setRightText("发起任务");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(layoutManager);

        mManageMissionAdapter = new WCManageMissionAdapter(mImageLoaderHelper);
        mRecyclerView.setAdapter(mManageMissionAdapter);
    }

    @Override
    protected void afterView() {
        mPresenter.attachView(this);

        attachRefreshLayout(mRefreshLayout, mRecyclerView);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mPresenter.getMissionListFromServer(mPageNo);
            }
        });

        mPresenter.getMissionListFromServer(mPageNo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void onClickRightTitle() {
        super.onClickRightTitle();
        showToast("发起任务");
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    protected void unSubscribeObservable() {
    }

    @Override
    public void refreshMissionList(List<WCManageMissionInfo> list) {
        mManageMissionAdapter.setData(list);

        hideProgressDialog();
    }

    @Override
    public void addMissionList(ArrayList<WCManageMissionInfo> list, boolean hasMore) {
        mPageNo ++;
        mManageMissionAdapter.addData(list);

        hideProgressDialog();
    }

    @Override
    public void loadFail() {
        mManageMissionAdapter.loadFailed();
    }
}
