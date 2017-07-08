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
import com.mm.weclubs.app.manage.meeting.WCManageMeetingListContract;
import com.mm.weclubs.data.network.pojo.WCManageMeetingInfo;
import com.mm.weclubs.ui.adapter.manage.WCManageMeetingAdapter;

import java.util.List;

import javax.inject.Inject;

import xyz.zpayh.adapter.OnItemClickListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:39
 * 描述:  会议管理列表页面
 */

public class WCMeetingManageListActivity extends BaseActivity implements WCManageMeetingListContract.View {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private WCManageMeetingAdapter mManageMeetingAdapter;

    @Inject
    WCManageMeetingListContract.Presenter<WCManageMeetingListContract.View> mPresenter;

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
        getTitleBar().setTitleText("会议管理");
        getTitleBar().setRightText("发起会议");

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(layoutManager);

        mManageMeetingAdapter = new WCManageMeetingAdapter();
        mRecyclerView.setAdapter(mManageMeetingAdapter);

        mManageMeetingAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                WCManageMeetingInfo manageMeetingInfo = mManageMeetingAdapter.getData(adapterPosition);
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

        mPresenter.attachView(this);

        attachRefreshLayout(mRefreshLayout, mRecyclerView);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNo = 1;
                mPresenter.refresh();
            }
        });

        mPresenter.getMeetingListFromServer(mPageNo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
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
    public void refreshMeetingList(List<WCManageMeetingInfo> list) {
        mManageMeetingAdapter.setData(list);

        hideProgressDialog();
    }

    @Override
    public void addMeetingList(List<WCManageMeetingInfo> list, boolean hasMore) {
        mPageNo ++;
        mManageMeetingAdapter.addData(list);

        hideProgressDialog();
    }

    @Override
    public void loadFail() {
        mManageMeetingAdapter.loadFailed();
    }
}
