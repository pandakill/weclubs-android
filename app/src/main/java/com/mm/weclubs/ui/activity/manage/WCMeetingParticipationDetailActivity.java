package com.mm.weclubs.ui.activity.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.mm.weclubs.R;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingPresenter;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingView;
import com.mm.weclubs.data.pojo.WCManageMeetingDetailInfo;
import com.mm.weclubs.data.pojo.WCManageMeetingInfo;
import com.mm.weclubs.data.pojo.WCMeetingParticipationInfo;
import com.mm.weclubs.ui.activity.BaseActivity;
import com.mm.weclubs.ui.adapter.manage.WCMeetingParticipationAdapter;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/16 下午4:10
 * 描述:  会议管理-会议参与详情
 */

public class WCMeetingParticipationDetailActivity extends BaseActivity implements WCManageMeetingView {

    private TextView mTvConfirmCount;
    private TextView mTvSignCount;
    private SwipeRefreshLayout mRefreshLayout;
    private HaoRecyclerView mRecyclerView;

    private WCMeetingParticipationAdapter mMeetingParticipationAdapter;
    private WCManageMeetingPresenter mManageMeetingPresenter;

    private long mMeetingId = 0;

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

        mMeetingId = extras.getLong("meetingId");
    }

    @Override
    protected void onClickRightTitle() {
        super.onClickRightTitle();
        showToast(getTitleBar().getRightCtv().getText().toString());
    }

    @Override
    protected void initView() {

        mTvConfirmCount = (TextView) findViewById(R.id.tv_confirm_count);
        mTvSignCount = (TextView) findViewById(R.id.tv_sign_count);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (HaoRecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(layoutManager);

        mManageMeetingPresenter = new WCManageMeetingPresenter(this);
        mManageMeetingPresenter.attachView(this);

        attachRefreshLayout(mRefreshLayout, mRecyclerView);
    }

    @Override
    protected void afterView() {

        mMeetingParticipationAdapter = new WCMeetingParticipationAdapter(this);
        mRecyclerView.setAdapter(mMeetingParticipationAdapter);

        mManageMeetingPresenter.getMeetingParticipation(mMeetingId);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mManageMeetingPresenter.getMeetingParticipation(mMeetingId);
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
    public void refreshMeetingList(ArrayList<WCManageMeetingInfo> list) {
    }

    @Override
    public void addMeetingList(ArrayList<WCManageMeetingInfo> list, boolean hasMore) {
    }

    @Override
    public void getMeetingDetailSuccess(WCManageMeetingDetailInfo meetingDetailInfo) {
    }

    @Override
    public void getMeetingParticipationSuccess(ArrayList<WCMeetingParticipationInfo> list) {
        mTvSignCount.setText("(TODO)");
        mTvConfirmCount.setText("(TODO)");

        mMeetingParticipationAdapter.setItems(list);
    }
}
