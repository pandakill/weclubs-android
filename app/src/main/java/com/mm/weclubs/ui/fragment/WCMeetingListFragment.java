package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.meeting_list.WCMeetingListContract;
import com.mm.weclubs.data.network.pojo.WCMeetingListInfo;
import com.mm.weclubs.data.network.pojo.WCMyClubListInfo;
import com.mm.weclubs.ui.activity.WCMeetingDetailActivity;
import com.mm.weclubs.ui.adapter.WCMeetingListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;
import xyz.zpayh.adapter.OnItemClickListener;
import xyz.zpayh.adapter.OnLoadMoreListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 上午11:12
 * 描述:  通知列表
 */

public class WCMeetingListFragment extends BaseLazyFragment implements WCMeetingListContract.View {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private WCMeetingListAdapter mMeetingListAdapter;

    @Inject
    WCMeetingListContract.Presenter<WCMeetingListContract.View> mMeetingListPresenter;

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
        getActivityComponent().inject(this);

        mRefreshLayout = findViewById(R.id.swipeRefreshLayout, SwipeRefreshLayout.class);
        mRecyclerView = findViewById(R.id.recycler_view, RecyclerView.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(linearLayoutManager);

        attachRefreshLayout(mRefreshLayout, null);
    }

    private void afterView() {
        mMeetingListAdapter = new WCMeetingListAdapter();
        mRecyclerView.setAdapter(mMeetingListAdapter);
        mMeetingListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                WCMeetingListInfo meetingListInfo = mMeetingListAdapter.getData(adapterPosition);
                if (meetingListInfo == null){
                    return;
                }
                switch (view.getId()) {
                    case R.id.item_dynamic:
                        Bundle extra = new Bundle();
                        extra.putSerializable("meetingListInfo", meetingListInfo);
                        showIntent(WCMeetingDetailActivity.class, extra);
                        break;
                    case R.id.btn_confirm:
                        mMeetingListPresenter.setMeetingConfirm(meetingListInfo.getMeeting_id(), adapterPosition, meetingListInfo);
                        break;
                }
            }
        });


        mMeetingListPresenter.attachView(this);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mMeetingListPresenter.refresh(mClubListInfo.getClub_id());
            }
        });

        mMeetingListAdapter.openAutoLoadMore(true);
        mMeetingListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mMeetingListPresenter.getMeetingListFromServer(mClubListInfo.getClub_id(), mPageNo);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMeetingListPresenter.detachView();
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
        mMeetingListAdapter.setData(list);

        hideProgressDialog();
    }

    @Override
    public void addMeetingList(ArrayList<WCMeetingListInfo> list, boolean hasMore) {
        mPageNo ++;
        mMeetingListAdapter.addData(list);

        hideProgressDialog();

        if (!hasMore){
            mMeetingListAdapter.loadCompleted();
        }
    }

    @Override
    public void loadFail() {
        mMeetingListAdapter.loadFailed();
    }

    @Override
    public void notifyChangeList(WCMeetingListInfo meetingListInfo, int position) {
        mMeetingListAdapter.notifyItemChanged(position, meetingListInfo);
    }
}
