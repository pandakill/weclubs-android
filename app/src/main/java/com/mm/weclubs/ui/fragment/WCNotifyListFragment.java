package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.notify_list.WCNotifyListContract;
import com.mm.weclubs.data.network.pojo.WCMyClubListInfo;
import com.mm.weclubs.data.network.pojo.WCNotifyListInfo;
import com.mm.weclubs.ui.activity.WCNotifyDetailActivity;
import com.mm.weclubs.ui.adapter.WCNotifyListAdapter;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import xyz.zpayh.adapter.OnItemClickListener;
import xyz.zpayh.adapter.OnLoadMoreListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 上午11:12
 * 描述:  通知列表
 */

public class WCNotifyListFragment extends BaseLazyFragment implements WCNotifyListContract.View {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private WCNotifyListAdapter mNotifyListAdapter;
    @Inject
    WCNotifyListContract.Presenter<WCNotifyListContract.View> mPresenter;
    @Inject
    ImageLoaderHelper mImageLoaderHelper;
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
        getActivityComponent().inject(this);
        if (mExtra == null) {
            mExtra = getArguments();
            mClubListInfo = (WCMyClubListInfo) mExtra.getSerializable("clubListInfo");
        }

        initView();
        afterView();
    }

    private void initView() {
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout, SwipeRefreshLayout.class);
        mRecyclerView = findViewById(R.id.recycler_view, RecyclerView.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.canScrollVertically();
        mRecyclerView.setLayoutManager(linearLayoutManager);

        attachRefreshLayout(mRefreshLayout, null);
    }

    private void afterView() {
        mNotifyListAdapter = new WCNotifyListAdapter(mImageLoaderHelper);
        mRecyclerView.setAdapter(mNotifyListAdapter);
        mNotifyListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                WCNotifyListInfo notifyListInfo = mNotifyListAdapter.getData(adapterPosition);
                if (notifyListInfo == null){
                    return;
                }
                switch (view.getId()) {
                    case R.id.item_dynamic:
                        Bundle extra = new Bundle();
                        extra.putSerializable("notifyListInfo", notifyListInfo);
                        showIntent(WCNotifyDetailActivity.class, extra);
                        break;
                    case R.id.btn_receive:
                        mPresenter.setNotifyConfirm(notifyListInfo.getNotify_id(), adapterPosition, notifyListInfo);
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

        mNotifyListAdapter.openAutoLoadMore(true);
        mNotifyListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getNotifyFromServer(mClubListInfo.getClub_id(), mPageNo);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    protected void onFirstUserVisible() {
        mPresenter.getNotifyFromServer(mClubListInfo.getClub_id(), mPageNo);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void refreshNotifyList(List<WCNotifyListInfo> list) {
        mNotifyListAdapter.setData(list);

        hideProgressDialog();
    }

    @Override
    public void addNotifyList(List<WCNotifyListInfo> list, boolean hasMore) {
        mPageNo ++;
        mNotifyListAdapter.addData(list);

        hideProgressDialog();

        if (!hasMore) {
            mNotifyListAdapter.loadCompleted();
            KLog.d("已经没有更多了");
        }else {
            KLog.d("还有更多");
        }
    }

    @Override
    public void loadFail() {
        mNotifyListAdapter.loadFailed();
    }

    @Override
    public void notifyChangeList(WCNotifyListInfo notifyListInfo, int position) {
        mNotifyListAdapter.notifyItemChanged(position, notifyListInfo);
    }
}
