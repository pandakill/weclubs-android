package com.mm.weclubs.ui.activity.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mm.weclubs.R;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.app.manage.notify.WCNotifyReceiveStatusContract;
import com.mm.weclubs.data.network.bean.WCNotifyCheckStatusBean;
import com.mm.weclubs.ui.adapter.manage.WCNotifyReceiveAdapter;
import com.socks.library.KLog;

import javax.inject.Inject;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/16 下午4:10
 * 描述:  通知管理-通知确认详情
 */

public class WCNotifyReceiveStatusActivity extends BaseActivity implements WCNotifyReceiveStatusContract.View {

    private TextView mTvNotifyListTitle;
    private TextView mTvConfirmCount;
    private TextView mTvSignCount;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private WCNotifyReceiveAdapter mAdapter;
    @Inject
    WCNotifyReceiveStatusContract.Presenter<WCNotifyReceiveStatusContract.View> mPresenter;

    private long mNotifyId = 0;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_meeting_participation;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras == null) {
            KLog.e("getBundleExtras：extras 不能为空");
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
        getActivityComponent().inject(this);
        getTitleBar().setRightText("再次通知");
        getTitleBar().setTitleText("确认详情");

        mTvNotifyListTitle = (TextView) findViewById(R.id.tv_meeting_participation_title);
        mTvConfirmCount = (TextView) findViewById(R.id.tv_confirm_count);
        mTvSignCount = (TextView) findViewById(R.id.tv_sign_count);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.attachView(this);

        attachRefreshLayout(mRefreshLayout, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void afterView() {

        mTvNotifyListTitle.setText("通知成员");
        mTvConfirmCount.setVisibility(View.GONE);

        mAdapter = new WCNotifyReceiveAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.getNotifyConfirmStatusFromServer(mNotifyId);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getNotifyConfirmStatusFromServer(mNotifyId);
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
    public void getNotifyReceiveStatusSuccess(WCNotifyCheckStatusBean notifyCheckStatus) {

        String count = "(" + notifyCheckStatus.getUnread_count()
                + "/" + notifyCheckStatus.getTotal_count() + ")";

        mTvSignCount.setText(count);

        mAdapter.setData(notifyCheckStatus.getConfirm_status());
    }
}
