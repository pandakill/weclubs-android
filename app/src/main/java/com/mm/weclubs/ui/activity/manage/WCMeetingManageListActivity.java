package com.mm.weclubs.ui.activity.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingPresenter;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingView;
import com.mm.weclubs.data.pojo.WCManageMeetingDetailInfo;
import com.mm.weclubs.data.pojo.WCManageMeetingInfo;
import com.mm.weclubs.data.pojo.WCMeetingParticipationInfo;
import com.mm.weclubs.ui.activity.BaseActivity;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter.OnClickViewListener;
import com.mm.weclubs.ui.adapter.manage.WCManageMeetingAdapter;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:39
 * 描述:  会议管理列表页面
 */

public class WCMeetingManageListActivity extends BaseActivity implements WCManageMeetingView {

    private SwipeRefreshLayout mRefreshLayout;
    private HaoRecyclerView mRecyclerView;

    private WCManageMeetingAdapter mManageMeetingAdapter;

    private WCManageMeetingPresenter mManageMeetingPresenter;

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
        getTitleBar().setRightText("发起会议");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (HaoRecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(layoutManager);

        mManageMeetingAdapter = new WCManageMeetingAdapter(this);
        mRecyclerView.setAdapter(mManageMeetingAdapter);

        mManageMeetingAdapter.setOnClickViewListener(new OnClickViewListener() {
            @Override
            public void onClick(View view, int position) {
                WCManageMeetingInfo manageMeetingInfo = mManageMeetingAdapter.getItem(position);
                Bundle extra = new Bundle();
                extra.putSerializable("manageMeetingInfo", manageMeetingInfo);
                if (manageMeetingInfo != null) {
                    switch (view.getId()) {
                        case R.id.item_dynamic:
                            showIntent(WCMeetingManageDetailActivity.class, extra);
                            break;
                        case R.id.btn_confirm:
                            showToast("确认");
                            break;
                        case R.id.btn_sign:
                            showToast("签到");
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void afterView() {

        mManageMeetingPresenter = new WCManageMeetingPresenter(this);
        mManageMeetingPresenter.attachView(this);

        attachRefreshLayout(mRefreshLayout, mRecyclerView);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mManageMeetingPresenter.getMeetingListFromServer(mPageNo);
            }
        });

        mManageMeetingPresenter.getMeetingListFromServer(mPageNo);
    }

    @Override
    protected void onClickRightTitle() {
        super.onClickRightTitle();
        showToast("发起会议");
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
        mManageMeetingAdapter.setItems(list);

        hideProgressDialog();
    }

    @Override
    public void addMeetingList(ArrayList<WCManageMeetingInfo> list, boolean hasMore) {
        mPageNo ++;
        mManageMeetingAdapter.addItems(list);

        hideProgressDialog();
    }

    @Override
    public void getMeetingDetailSuccess(WCManageMeetingDetailInfo meetingDetailInfo) {
    }

    @Override
    public void getMeetingParticipationSuccess(ArrayList<WCMeetingParticipationInfo> list) {
    }
}
