package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.mission_list.WCMissionListContract;
import com.mm.weclubs.data.network.pojo.WCMissionListInfo;
import com.mm.weclubs.data.network.pojo.WCMyClubListInfo;
import com.mm.weclubs.ui.activity.WCMissionDetailActivity;
import com.mm.weclubs.ui.adapter.WCMissionListAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import xyz.zpayh.adapter.OnItemClickListener;
import xyz.zpayh.adapter.OnLoadMoreListener;


/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 上午11:12
 * 描述:  通知列表
 */

public class WCMissionListFragment extends BaseLazyFragment implements WCMissionListContract.View {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private WCMissionListAdapter mMissionListAdapter;
    @Inject
    WCMissionListContract.Presenter<WCMissionListContract.View> mPresenter;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
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
        mMissionListAdapter = new WCMissionListAdapter();
        mRecyclerView.setAdapter(mMissionListAdapter);
        mMissionListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                WCMissionListInfo missionListInfo = mMissionListAdapter.getData(adapterPosition);
                if (missionListInfo == null){
                    return;
                }

                switch (view.getId()) {
                    case R.id.item_dynamic:
                        Bundle extra = new Bundle();
                        extra.putSerializable("missionListInfo", missionListInfo);
                        showIntent(WCMissionDetailActivity.class, extra);
                        break;
                    case R.id.btn_confirm:
                        mPresenter.setMissionConfirm(missionListInfo.getMission_id(), adapterPosition, missionListInfo);
                        break;
                }
            }
        });

        mPresenter.attachView(this);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mPresenter.refresh(mClubListInfo.getClub_id());
            }
        });

        mMissionListAdapter.openAutoLoadMore(true);
        mMissionListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMissionListFromServer(mClubListInfo.getClub_id(), mPageNo);
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        mPresenter.getMissionListFromServer(mClubListInfo.getClub_id(), mPageNo);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void refreshMissionList(ArrayList<WCMissionListInfo> list) {
        mMissionListAdapter.setData(list);

        hideProgressDialog();
    }

    @Override
    public void addMissionList(ArrayList<WCMissionListInfo> list, boolean hasMore) {
        mPageNo ++;
        mMissionListAdapter.addData(list);

        hideProgressDialog();

        if (!hasMore){
            mMissionListAdapter.loadCompleted();
        }
    }

    @Override
    public void loadFail() {
        mMissionListAdapter.loadFailed();
    }

    @Override
    public void notifyChangeList(WCMissionListInfo missionListInfo, int position) {
        mMissionListAdapter.notifyItemChanged(position, missionListInfo);
    }
}
