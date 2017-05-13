package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.meeting_list.WCMeetingListPresenter;
import com.mm.weclubs.app.meeting_list.WCMeetingListView;
import com.mm.weclubs.data.pojo.WCMeetingDetailInfo;
import com.mm.weclubs.data.pojo.WCMeetingListInfo;
import com.mm.weclubs.data.pojo.WCMyClubListInfo;
import com.mm.weclubs.ui.activity.WCMeetingDetailActivity;
import com.mm.weclubs.ui.adapter.WCMeetingListAdapter;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter.OnClickViewListener;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;
import me.fangx.haorefresh.LoadMoreListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 上午11:12
 * 描述:  通知列表
 */

public class WCMeetingListFragment extends BaseLazyFragment implements WCMeetingListView {

    private SwipeRefreshLayout mRefreshLayout;
    private HaoRecyclerView mRecyclerView;

    private WCMeetingListAdapter mMeetingListAdapter;
    private WCMeetingListPresenter mMeetingListPresenter;

    private int mPageNo = 1;

    private WCMyClubListInfo mClubListInfo;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_notify_list;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras == null) {
            log.e("getBundleExtras.extras == null");
            return;
        }

        mClubListInfo = (WCMyClubListInfo) extras.getSerializable("clubListInfo");
    }

    @Override
    protected void initViewsAndEvents() {
        if (mExtra == null) {
            mExtra = getArguments();
            mClubListInfo = (WCMyClubListInfo) mExtra.getSerializable("clubListInfo");
        }

        initView();
        afterView();
    }

    private void initView() {
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout, SwipeRefreshLayout.class);
        mRecyclerView = findViewById(R.id.recycler_view, HaoRecyclerView.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(linearLayoutManager);

        attachRefreshLayout(mRefreshLayout, mRecyclerView);
    }

    private void afterView() {
        mMeetingListAdapter = new WCMeetingListAdapter(mContext);
        mRecyclerView.setAdapter(mMeetingListAdapter);
        mMeetingListAdapter.setOnClickViewListener(new OnClickViewListener() {
            @Override
            public void onClick(View view, int position) {
                WCMeetingListInfo meetingListInfo = mMeetingListAdapter.getItem(position);
                Bundle extra = new Bundle();
                extra.putSerializable("meetingListInfo", meetingListInfo);
                switch (view.getId()) {
                    case R.id.item_dynamic:
                        showIntent(WCMeetingDetailActivity.class, extra);
                        break;
                }
            }
        });

        mMeetingListPresenter = new WCMeetingListPresenter(mContext);
        mMeetingListPresenter.attachView(this);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mMeetingListPresenter.getMeetingListFromServer(mClubListInfo.getClub_id(), mPageNo);
            }
        });

        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mMeetingListPresenter.getMeetingListFromServer(mClubListInfo.getClub_id(), mPageNo);
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        mMeetingListPresenter.getMeetingListFromServer(mClubListInfo.getClub_id(), mPageNo);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void refreshMeetingList(ArrayList<WCMeetingListInfo> list) {
        mMeetingListAdapter.setItems(list);

        hideProgressDialog();
    }

    @Override
    public void addMeetingList(ArrayList<WCMeetingListInfo> list, boolean hasMore) {
        mPageNo ++;
        mMeetingListAdapter.addItems(list);

        hideProgressDialog();
    }

    @Override
    public void getMeetingDetailSuccess(WCMeetingDetailInfo meetingDetailInfo) {
    }
}
