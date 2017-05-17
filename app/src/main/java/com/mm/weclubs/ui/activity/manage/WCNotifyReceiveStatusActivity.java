package com.mm.weclubs.ui.activity.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.mm.weclubs.R;
import com.mm.weclubs.app.manage.notify.WCManageNotifyPresenter;
import com.mm.weclubs.app.manage.notify.WCManageNotifyView;
import com.mm.weclubs.data.bean.WCNotifyCheckStatusBean;
import com.mm.weclubs.data.pojo.WCManageNotifyInfo;
import com.mm.weclubs.ui.activity.BaseActivity;
import com.mm.weclubs.ui.adapter.manage.WCNotifyRecieveAdapter;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/16 下午4:10
 * 描述:  通知管理-通知确认详情
 */

public class WCNotifyReceiveStatusActivity extends BaseActivity implements WCManageNotifyView {

    private TextView mTvNotifyListTitle;
    private TextView mTvConfirmCount;
    private TextView mTvSignCount;
    private SwipeRefreshLayout mRefreshLayout;
    private HaoRecyclerView mRecyclerView;

    private WCNotifyRecieveAdapter mNotifyRecieveAdapter;
    private WCManageNotifyPresenter mManageNotifyPresenter;

    private long mNotifyId = 0;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_meeting_participation;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras == null) {
            log.e("getBundleExtras：extras 不能为空");
            onBackPressed();
            return;
        }

        mNotifyId = extras.getLong("notifyId");
    }

    @Override
    protected void onClickRightTitle() {
        super.onClickRightTitle();
        showToast(getTitleBar().getRightCtv().getText().toString());
    }

    @Override
    protected void initView() {

        getTitleBar().setRightText("再次通知");
        getTitleBar().setTitleText("确认详情");

        mTvNotifyListTitle = (TextView) findViewById(R.id.tv_meeting_participation_title);
        mTvConfirmCount = (TextView) findViewById(R.id.tv_confirm_count);
        mTvSignCount = (TextView) findViewById(R.id.tv_sign_count);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (HaoRecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(layoutManager);

        mManageNotifyPresenter = new WCManageNotifyPresenter(this);
        mManageNotifyPresenter.attachView(this);

        attachRefreshLayout(mRefreshLayout, mRecyclerView);
    }

    @Override
    protected void afterView() {

        mTvNotifyListTitle.setText("通知成员");
        mTvConfirmCount.setVisibility(View.GONE);

        mNotifyRecieveAdapter = new WCNotifyRecieveAdapter(this);
        mRecyclerView.setAdapter(mNotifyRecieveAdapter);

        mManageNotifyPresenter.getNotifyConfirmStatusFromServer(mNotifyId);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mManageNotifyPresenter.getNotifyConfirmStatusFromServer(mNotifyId);
            }
        });
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
    }

    @Override
    public void addNotifyList(ArrayList<WCManageNotifyInfo> list, boolean hasMore) {
    }

    @Override
    public void getNotifyDetailSuccess(WCManageNotifyInfo notifyInfo) {
    }

    @Override
    public void getNotifyReceiveStatusSuccess(WCNotifyCheckStatusBean notifyCheckStatus) {

        String count = "(" + notifyCheckStatus.getUnread_count()
                + "/" + notifyCheckStatus.getTotal_count() + ")";

        mTvSignCount.setText(count);

        mNotifyRecieveAdapter.setItems(notifyCheckStatus.getConfirm_status());
    }
}
