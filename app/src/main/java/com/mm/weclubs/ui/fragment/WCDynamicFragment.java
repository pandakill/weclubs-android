package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.utils.EmptyUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.app.club.WCMyClubListContract;
import com.mm.weclubs.data.network.pojo.WCMyClubListInfo;
import com.mm.weclubs.ui.activity.WCTODOListActivity;
import com.mm.weclubs.ui.adapter.WCMyClubListAdapter;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.util.WCLog;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import xyz.zpayh.adapter.OnItemClickListener;
import xyz.zpayh.adapter.OnLoadMoreListener;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/26 下午4:11
 * 描述:  首页动态的fragment
 */

public class WCDynamicFragment extends BaseLazyFragment implements WCMyClubListContract.View {
    public static final String TAG = "WCDynamicFragment";
    public static WCDynamicFragment newInstance(){
        WCDynamicFragment fragment = new WCDynamicFragment();
        return fragment;
    }
    @Inject
    WCMyClubListContract.Presenter<WCMyClubListContract.View> mPresenter;
    @Inject
    ImageLoaderHelper mImageLoaderHelper;
    private SwipeRefreshLayout mRefreshLayout = null;
    private RecyclerView mRecyclerView = null;
    private WCMyClubListAdapter mAdapter = null;

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
        getActivityComponent().inject(this);
        mPresenter.attachView(this);

        log.d("动态 initViewsAndEvents");
        mRefreshLayout = findViewById(R.id.swipeRefreshLayout, SwipeRefreshLayout.class);
        mRecyclerView = findViewById(R.id.recycler_view, RecyclerView.class);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        manager.canScrollVertically();
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new WCMyClubListAdapter(mImageLoaderHelper);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.openAutoLoadMore(true);
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMyClubsList(pageNo);
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                WCMyClubListInfo clubListInfo = mAdapter.getData(adapterPosition);
                if (clubListInfo == null){
                    return;
                }
                switch (view.getId()) {
                    case R.id.btn_todo:
                        Bundle extra = new Bundle();
                        extra.putSerializable("clubListInfo", clubListInfo);
                        showIntent(WCTODOListActivity.class, extra);
                        break;
                    case R.id.btn_activity:
                        showToast(clubListInfo.getClub_name() + "：" + clubListInfo.getActivity_count());
                        break;
                    case R.id.item_dynamic:
                        showToast(clubListInfo.getClub_name() + ": " + clubListInfo.getClub_id());
                        break;
                }
            }
        });

        mPresenter.refresh();

        attachRefreshLayout(mRefreshLayout, null);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                mPresenter.refresh();
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
    public void addClubList(@NonNull List<WCMyClubListInfo> list, boolean hasMore) {
        if (EmptyUtils.isEmpty(list)) {
            showToast("列表为空");
            mAdapter.loadCompleted();
            return;
        }

        pageNo ++;

        mAdapter.addData(list);
        if (!hasMore) {
            mAdapter.loadCompleted();
            KLog.d("已经没有更多了");
        }else {
            KLog.d("还有更多");
        }
    }

    @Override
    public void setClubList(@NonNull List<WCMyClubListInfo> data) {
        mAdapter.setData(data);
    }

    @Override
    public void loadFail() {
        mAdapter.loadFailed();
    }
}
