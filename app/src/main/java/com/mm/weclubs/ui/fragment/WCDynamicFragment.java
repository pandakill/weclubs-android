package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.utils.EmptyUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.app.club.WCMyClubListPresenter;
import com.mm.weclubs.app.club.WCMyClubListView;
import com.mm.weclubs.data.pojo.WCMyClubListInfo;
import com.mm.weclubs.ui.activity.WCTODOListActivity;
import com.mm.weclubs.ui.adapter.WCMyClubListAdapter;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter.OnClickViewListener;
import com.mm.weclubs.util.WCLog;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;
import me.fangx.haorefresh.LoadMoreListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/26 下午4:11
 * 描述:  首页动态的fragment
 */

public class WCDynamicFragment extends BaseLazyFragment implements WCMyClubListView {

    private WCMyClubListPresenter mMyClubListPresenter;

    private SwipeRefreshLayout mRefreshLayout = null;
    private HaoRecyclerView mRecyclerView = null;
    private WCMyClubListAdapter mClubListAdapter = null;

    private int pageNo = 1;

    @Override
    protected int getContentViewLayoutID() {
        log = new WCLog(WCDynamicFragment.class);
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {

        mMyClubListPresenter = new WCMyClubListPresenter(mContext);
        mMyClubListPresenter.attachView(this);

        log.d("动态 initViewsAndEvents");
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout, SwipeRefreshLayout.class);
        mRecyclerView = findViewById(R.id.recycler_view, HaoRecyclerView.class);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        manager.canScrollVertically();
        mRecyclerView.setLayoutManager(manager);

        mClubListAdapter = new WCMyClubListAdapter(mContext);
        mRecyclerView.setAdapter(mClubListAdapter);
        mRecyclerView.setCanloadMore(true);

        mClubListAdapter.setOnClickViewListener(new OnClickViewListener() {
            @Override
            public void onClick(View view, int position) {
                WCMyClubListInfo clubListInfo = mClubListAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.btn_todo:
                        if (clubListInfo != null) {
                            Bundle extra = new Bundle();
                            extra.putSerializable("clubListInfo", clubListInfo);
                            showIntent(WCTODOListActivity.class, extra);
                        }
                        break;
                    case R.id.btn_activity:
                        if (clubListInfo != null) {
                            showToast(clubListInfo.getClub_name() + "：" + clubListInfo.getActivity_count());
                        }
                        break;
                }
            }
        });

        mMyClubListPresenter.getMyClubsList(pageNo);

        attachRefreshLayout(mRefreshLayout, mRecyclerView);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mMyClubListPresenter.getMyClubsList(pageNo);
            }
        });

        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mMyClubListPresenter.getMyClubsList(pageNo);
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        log.d("动态 onFirstUserVisible");
        showToast("动态fragment创建");
    }

    @Override
    protected void onUserVisible() {
        log.d("动态 onUserVisible");
        showToast("动态fragment展示");
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void refreshClubList(ArrayList<WCMyClubListInfo> list) {
        if (EmptyUtils.isEmpty(list)) {
            mRefreshLayout.setEnabled(false);
            showToast("没有更多");
            return;
        }

        mRefreshLayout.setEnabled(true);

        mClubListAdapter.setItems(list);
    }

    @Override
    public void addClubList(ArrayList<WCMyClubListInfo> list, boolean hasMore) {
        if (EmptyUtils.isEmpty(list)) {
            showToast("列表为空");
            return;
        }

        pageNo ++;

        mClubListAdapter.addItems(list);
        mRecyclerView.loadMoreComplete();
        if (!hasMore) {
            mRecyclerView.loadMoreEnd();
        }
    }
}
