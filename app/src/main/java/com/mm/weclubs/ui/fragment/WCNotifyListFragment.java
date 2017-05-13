package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.notify_list.WCNotifyListPresenter;
import com.mm.weclubs.app.notify_list.WCNotifyListView;
import com.mm.weclubs.data.pojo.WCMyClubListInfo;
import com.mm.weclubs.data.pojo.WCNotifyListInfo;
import com.mm.weclubs.ui.activity.WCNotifyDetailActivity;
import com.mm.weclubs.ui.adapter.WCNotifyListAdapter;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter.OnClickViewListener;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;
import me.fangx.haorefresh.LoadMoreListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 上午11:12
 * 描述:  通知列表
 */

public class WCNotifyListFragment extends BaseLazyFragment implements WCNotifyListView {

    private SwipeRefreshLayout mRefreshLayout;
    private HaoRecyclerView mRecyclerView;

    private WCNotifyListAdapter mNotifyListAdapter;
    private WCNotifyListPresenter mNotifyListPresenter;

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
        mNotifyListAdapter = new WCNotifyListAdapter(mContext);
        mRecyclerView.setAdapter(mNotifyListAdapter);
        mNotifyListAdapter.setOnClickViewListener(new OnClickViewListener() {
            @Override
            public void onClick(View view, int position) {
                WCNotifyListInfo notifyListInfo = mNotifyListAdapter.getItem(position);
                Bundle extra = new Bundle();
                extra.putSerializable("notifyListInfo", notifyListInfo);
                switch (view.getId()) {
                    case R.id.item_dynamic:
                        showIntent(WCNotifyDetailActivity.class, extra);
                        break;
                }
            }
        });

        mNotifyListPresenter = new WCNotifyListPresenter(mContext);
        mNotifyListPresenter.attachView(this);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mNotifyListPresenter.getNotifyFromServer(mClubListInfo.getClub_id(), mPageNo);
            }
        });

        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mNotifyListPresenter.getNotifyFromServer(mClubListInfo.getClub_id(), mPageNo);
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        mNotifyListPresenter.getNotifyFromServer(mClubListInfo.getClub_id(), mPageNo);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void refreshNotifyList(ArrayList<WCNotifyListInfo> list) {
        mNotifyListAdapter.setItems(list);

        hideProgressDialog();
    }

    @Override
    public void addNotifyList(ArrayList<WCNotifyListInfo> list, boolean hasMore) {
        mPageNo ++;
        mNotifyListAdapter.addItems(list);

        hideProgressDialog();
    }
}
