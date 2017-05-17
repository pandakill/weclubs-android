package com.mm.weclubs.ui.activity.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.manage.notify.WCManageNotifyPresenter;
import com.mm.weclubs.app.manage.notify.WCManageNotifyView;
import com.mm.weclubs.data.bean.WCNotifyCheckStatusBean;
import com.mm.weclubs.data.pojo.WCManageNotifyInfo;
import com.mm.weclubs.ui.activity.BaseActivity;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter.OnClickViewListener;
import com.mm.weclubs.ui.adapter.manage.WCManageNotifyAdapter;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:39
 * 描述:  通知管理列表页面
 */

public class WCNotifyManageListActivity extends BaseActivity implements WCManageNotifyView {

    private SwipeRefreshLayout mRefreshLayout;
    private HaoRecyclerView mRecyclerView;

    private WCManageNotifyAdapter mManageNotifyAdapter;

    private WCManageNotifyPresenter mManageNotifyPresenter;

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

        getTitleBar().setTitleText("通知管理");
        getTitleBar().setRightText("发起通知");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (HaoRecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(layoutManager);

        mManageNotifyAdapter = new WCManageNotifyAdapter(this);
        mRecyclerView.setAdapter(mManageNotifyAdapter);

        mManageNotifyAdapter.setOnClickViewListener(new OnClickViewListener() {
            @Override
            public void onClick(View view, int position) {
                WCManageNotifyInfo notifyInfo = mManageNotifyAdapter.getItem(position);
                Bundle extra = new Bundle();
                extra.putSerializable("manageNotifyInfo", notifyInfo);
                if (notifyInfo != null) {
                    switch (view.getId()) {
                        case R.id.item_dynamic:
                            showIntent(WCNotifyManageDetailActivity.class, extra);
                            break;
                        case R.id.btn_receive:
                            showToast("提醒确认");
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void afterView() {

        mManageNotifyPresenter = new WCManageNotifyPresenter(this);
        mManageNotifyPresenter.attachView(this);

        attachRefreshLayout(mRefreshLayout, mRecyclerView);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mManageNotifyPresenter.getNotifyListFromServer(mPageNo);
            }
        });

        mManageNotifyPresenter.getNotifyListFromServer(mPageNo);
    }

    @Override
    protected void onClickRightTitle() {
        super.onClickRightTitle();
        showToast("发起通知");
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    protected void unSubscribeObservable() {
    }

    @Override
    public void refreshNotifyList(ArrayList<WCManageNotifyInfo> list) {
        mManageNotifyAdapter.setItems(list);

        hideProgressDialog();
    }

    @Override
    public void addNotifyList(ArrayList<WCManageNotifyInfo> list, boolean hasMore) {
        mPageNo ++;
        mManageNotifyAdapter.addItems(list);

        hideProgressDialog();
    }

    @Override
    public void getNotifyDetailSuccess(WCManageNotifyInfo notifyInfo) {
    }

    @Override
    public void getNotifyReceiveStatusSuccess(WCNotifyCheckStatusBean notifyCheckStatus) {
    }
}
