package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.mission_list.WCMissionListPresenter;
import com.mm.weclubs.app.mission_list.WCMissionListView;
import com.mm.weclubs.data.pojo.WCMissionListInfo;
import com.mm.weclubs.data.pojo.WCMyClubListInfo;
import com.mm.weclubs.ui.activity.WCMissionDetailActivity;
import com.mm.weclubs.ui.adapter.WCMissionListAdapter;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter.OnClickViewListener;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;
import me.fangx.haorefresh.LoadMoreListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 上午11:12
 * 描述:  通知列表
 */

public class WCMissionListFragment extends BaseLazyFragment implements WCMissionListView {

    private SwipeRefreshLayout mRefreshLayout;
    private HaoRecyclerView mRecyclerView;

    private WCMissionListAdapter mMissionListAdapter;
    private WCMissionListPresenter mMissionListPresenter;

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
        mMissionListAdapter = new WCMissionListAdapter(mContext);
        mRecyclerView.setAdapter(mMissionListAdapter);
        mMissionListAdapter.setOnClickViewListener(new OnClickViewListener() {
            @Override
            public void onClick(View view, int position) {
                WCMissionListInfo missionListInfo = mMissionListAdapter.getItem(position);
                Bundle extra = new Bundle();
                extra.putSerializable("missionListInfo", missionListInfo);
                switch (view.getId()) {
                    case R.id.item_dynamic:
                        showIntent(WCMissionDetailActivity.class, extra);
                        break;
                }
            }
        });

        mMissionListPresenter = new WCMissionListPresenter(mContext);
        mMissionListPresenter.attachView(this);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mMissionListPresenter.getMissionListFromServer(mClubListInfo.getClub_id(), mPageNo);
            }
        });

        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mMissionListPresenter.getMissionListFromServer(mClubListInfo.getClub_id(), mPageNo);
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        mMissionListPresenter.getMissionListFromServer(mClubListInfo.getClub_id(), mPageNo);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void refreshMissionList(ArrayList<WCMissionListInfo> list) {
        mMissionListAdapter.setItems(list);

        hideProgressDialog();
    }

    @Override
    public void addMissionList(ArrayList<WCMissionListInfo> list, boolean hasMore) {
        mPageNo ++;
        mMissionListAdapter.addItems(list);

        hideProgressDialog();
    }
}
