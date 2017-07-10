package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.index.WCIndexContract;
import com.mm.weclubs.data.network.pojo.WCIndexClubListInfo;
import com.mm.weclubs.ui.adapter.WCClubsListAdapter;
import com.mm.weclubs.util.WCLog;

import java.util.List;

import javax.inject.Inject;

import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.OnItemClickListener;
import xyz.zpayh.adapter.OnLoadMoreListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/26 下午4:11
 * 描述:  首页动态的fragment
 */

public class WCIndexFragment extends BaseLazyFragment implements WCIndexContract.View{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresh;
    private WCClubsListAdapter mAdapter;

    private int pageNo = 1;

    @Inject
    WCIndexContract.Presenter<WCIndexContract.View> mPresenter;

    @Override
    protected int getContentViewLayoutID() {
        log = new WCLog(WCIndexFragment.class);
        return R.layout.fragment_index;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {

        log.d("首页 initViewsAndEvents");

        initView();
    }

    private void initView() {
        getActivityComponent().inject(this);
        mRefresh = findViewById(R.id.swipeRefreshLayout,SwipeRefreshLayout.class);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo=1;
                mPresenter.refresh();
            }
        });

        attachRefreshLayout(mRefresh,mRecyclerView);

        mRecyclerView = findViewById(R.id.recycler_view,RecyclerView.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WCClubsListAdapter() {
            @Override
            public void bind(BaseViewHolder holder, int layoutRes) {

            }
        };
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addHeadLayout(R.layout.item_index_head);

        pageNo = 1;

        mAdapter.openAutoLoadMore(true);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {

            }
        });

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getClubListFromServer(pageNo);
            }
        });


        mPresenter.attachView(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    protected void onFirstUserVisible() {
        log.d("首页 onFirstUserVisible");
        showToast("首页fragment创建");
    }

    @Override
    protected void onUserVisible() {
        log.d("首页 onUserVisible");
        showToast("首页fragment展示");
    }

    @Override
    protected void onUserInvisible() {

    }

    //======================= MVP View ===================
    @Override
    public void setData(List<WCIndexClubListInfo> list) {
        mAdapter.setData(list);
        pageNo++;
    }

    @Override
    public void addData(List<WCIndexClubListInfo> list, boolean hasMore) {
        if (list.isEmpty()){
            mAdapter.loadCompleted();
            return;
        }
        mAdapter.addData(list);
        pageNo++;
        if (!hasMore){
            mAdapter.loadCompleted();
        }
    }

    @Override
    public void loadFail() {
        mAdapter.loadFailed();
    }
}
