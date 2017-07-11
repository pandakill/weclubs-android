package com.mm.weclubs.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.index.WCIndexContract;
import com.mm.weclubs.data.network.pojo.WCIndexClubListInfo;
import com.mm.weclubs.ui.adapter.BannerPageAdapter;
import com.mm.weclubs.ui.adapter.WCClubsListAdapter;
import com.mm.weclubs.util.WCLog;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
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
    public static final String TAG = "WCIndexFragment";
    public static WCIndexFragment newInstance(){
        WCIndexFragment fragment = new WCIndexFragment();
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresh;
    private WCClubsListAdapter mAdapter;

    private Toolbar mToolbar;
    private AppBarLayout mAppBar;

    private ViewPager mViewPager;
    private BannerPageAdapter mPageAdapter;
    private CirclePageIndicator mIndicator;

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

        mToolbar = findViewById(R.id.toolbar,Toolbar.class);
        mAppBar = findViewById(R.id.app_bar,AppBarLayout.class);

        mViewPager = findViewById(R.id.viewPager,ViewPager.class);
        mIndicator = findViewById(R.id.indicator,CirclePageIndicator.class);

        mPageAdapter = new BannerPageAdapter();
        mViewPager.setAdapter(mPageAdapter);
        mIndicator.setViewPager(mViewPager);

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mToolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.colorPrimary),
                        Math.abs(verticalOffset*1.0f)/appBarLayout.getTotalScrollRange()));
            }
        });

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

        // Test
        List<String> picture = new ArrayList<>(5);
        picture.add("https://ws1.sinaimg.cn/large/610dc034ly1fhfmsbxvllj20u00u0q80.jpg");
        picture.add("https://ws1.sinaimg.cn/large/610dc034ly1fhegpeu0h5j20u011iae5.jpg");
        picture.add("https://ws1.sinaimg.cn/large/610dc034ly1fhb0t7ob2mj20u011itd9.jpg");
        picture.add("https://ws1.sinaimg.cn/large/610dc034ly1fh8ox6bmjlj20u00u0mz7.jpg");
        picture.add("https://ws1.sinaimg.cn/large/610dc034ly1fgi3vd6irmj20u011i439.jpg");

        mPageAdapter.setPicture(picture);
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

    public int changeAlpha(int color, float fraction){
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha,red,green,blue);
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
