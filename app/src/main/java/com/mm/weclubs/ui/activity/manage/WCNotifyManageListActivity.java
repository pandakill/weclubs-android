package com.mm.weclubs.ui.activity.manage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.app.manage.notify.WCManageNotifyListContract;
import com.mm.weclubs.data.network.pojo.WCManageNotifyInfo;
import com.mm.weclubs.ui.adapter.manage.WCManageNotifyAdapter;
import com.mm.weclubs.util.ImageLoaderHelper;

import java.util.ArrayList;

import javax.inject.Inject;

import xyz.zpayh.adapter.OnItemClickListener;
import xyz.zpayh.adapter.OnLoadMoreListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:39
 * 描述:  通知管理列表页面
 */

public class WCNotifyManageListActivity extends BaseActivity implements WCManageNotifyListContract.View {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private WCManageNotifyAdapter mManageNotifyAdapter;

    @Inject
    WCManageNotifyListContract.Presenter<WCManageNotifyListContract.View> mPresenter;
    @Inject
    ImageLoaderHelper mImageLoaderHelper;
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
        getActivityComponent().inject(this);
        getTitleBar().setTitleText("通知管理");
        getTitleBar().setRightText("发起通知");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(layoutManager);

        mManageNotifyAdapter = new WCManageNotifyAdapter(mImageLoaderHelper);
        mRecyclerView.setAdapter(mManageNotifyAdapter);

        mManageNotifyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                WCManageNotifyInfo notifyInfo = mManageNotifyAdapter.getData(adapterPosition);
                Bundle extra = new Bundle();
                extra.putSerializable("manageNotifyInfo", notifyInfo);
                if (notifyInfo != null) {
                    switch (view.getId()) {
                        case R.id.item_dynamic:
                            showIntent(WCNotifyManageDetailActivity.class, extra);
                            break;
                        case R.id.btn_receive:
                            showToast("提醒确认");
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void afterView() {

        mPresenter.attachView(this);

        attachRefreshLayout(mRefreshLayout, null);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mPresenter.refresh();
            }
        });

        mManageNotifyAdapter.openAutoLoadMore(true);
        mManageNotifyAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getNotifyListFromServer(mPageNo);
            }
        });
        mPresenter.refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void onClickRightTitle() {
        super.onClickRightTitle();
        showToast("发起通知");
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    protected void unSubscribeObservable() {
    }

    @Override
    public void refreshNotifyList(ArrayList<WCManageNotifyInfo> list) {
        mManageNotifyAdapter.setData(list);

        hideProgressDialog();
    }

    @Override
    public void addNotifyList(ArrayList<WCManageNotifyInfo> list, boolean hasMore) {
        mPageNo ++;
        mManageNotifyAdapter.addData(list);

        hideProgressDialog();

        if (!hasMore){
            mManageNotifyAdapter.loadCompleted();
        }
    }

    @Override
    public void loadFail() {
        mManageNotifyAdapter.loadFailed();
    }
}
