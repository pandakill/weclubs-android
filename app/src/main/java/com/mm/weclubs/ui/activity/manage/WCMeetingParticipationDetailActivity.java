package com.mm.weclubs.ui.activity.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mm.weclubs.R;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.app.manage.meeting.WCMeetingParticipationDetailContract;
import com.mm.weclubs.data.network.bean.WCMeetingParticipationBean;
import com.mm.weclubs.ui.adapter.manage.WCMeetingParticipationAdapter;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.socks.library.KLog;

import javax.inject.Inject;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/16 下午4:10
 * 描述:  会议管理-会议参与详情
 */

public class WCMeetingParticipationDetailActivity extends BaseActivity implements WCMeetingParticipationDetailContract.View {

    private TextView mTvConfirmCount;
    private TextView mTvSignCount;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private WCMeetingParticipationAdapter mMeetingParticipationAdapter;
    @Inject
    WCMeetingParticipationDetailContract.Presenter<WCMeetingParticipationDetailContract.View> mPresenter;
    @Inject
    ImageLoaderHelper mImageLoaderHelper;
    private long mMeetingId = 0;

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

        mMeetingId = extras.getLong("meetingId");
    }

    @Override
    protected void onClickRightTitle() {
        super.onClickRightTitle();
        showToast(getTitleBar().getRightCtv().getText().toString());
    }

    @Override
    protected void initView() {
        getActivityComponent().inject(this);
        mTvConfirmCount = (TextView) findViewById(R.id.tv_confirm_count);
        mTvSignCount = (TextView) findViewById(R.id.tv_sign_count);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.attachView(this);

        attachRefreshLayout(mRefreshLayout, mRecyclerView);
    }

    @Override
    protected void afterView() {

        mMeetingParticipationAdapter = new WCMeetingParticipationAdapter(mImageLoaderHelper);
        mRecyclerView.setAdapter(mMeetingParticipationAdapter);

        mPresenter.getMeetingParticipation(mMeetingId);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMeetingParticipation(mMeetingId);
            }
        });
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    public void getMeetingParticipationSuccess(WCMeetingParticipationBean participationBean) {
        String signCount = participationBean.getAlready_sign_count() + "/"
                + participationBean.getTotal_count();
        String confirmCount = participationBean.getAlready_confirm_count() + "/"
                + participationBean.getTotal_count();
        mTvSignCount.setText("(" + signCount + ")");
        mTvConfirmCount.setText("(" + confirmCount + ")");

        mMeetingParticipationAdapter.setData(participationBean.getParticipation());
    }
}
