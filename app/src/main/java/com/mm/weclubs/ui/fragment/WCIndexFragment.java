package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.app.index.WCIndexContract;
import com.mm.weclubs.data.network.pojo.WCBannerInfo;
import com.mm.weclubs.data.network.pojo.WCHotClubListInfo;
import com.mm.weclubs.data.network.pojo.WCIndexClubListInfo;
import com.mm.weclubs.di.DeviceWidth;
import com.mm.weclubs.ui.activity.club.WCClubDetailActivity;
import com.mm.weclubs.ui.adapter.BannerPageAdapter;
import com.mm.weclubs.ui.adapter.WCClubsListAdapter;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.util.WCLog;
import com.mm.weclubs.widget.AuthDialog;
import com.mm.weclubs.widget.RoundImageView;
import com.socks.library.KLog;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import javax.inject.Inject;

import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.OnItemClickListener;
import xyz.zpayh.adapter.OnLoadMoreListener;
import xyz.zpayh.adapter.ViewCallback;

import static com.mm.weclubs.util.ColorUtils.changeAlpha;
import static com.mm.weclubs.util.ColorUtils.old2new;

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

    private TextView mSchoolName;
    private ImageView mScanView;
    private ImageView mSearchView;

    private AuthDialog mAuthDialog;

    private int pageNo = 1;

    @Inject
    WCIndexContract.Presenter<WCIndexContract.View> mPresenter;
    @Inject
    ImageLoaderHelper mImageLoaderHelper;
    @Inject
    @DeviceWidth
    int mWidth;

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
        initEvent();
    }

    private void initEvent() {
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo=1;
                mPresenter.refresh();
            }
        });
        mAdapter.openAutoLoadMore(true);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int adapterPosition) {
                final int id = view.getId();
                WCIndexClubListInfo club = mAdapter.getData(adapterPosition);
                switch (id){
                    case R.id.iv_student_1://跳转到社团成员
                    case R.id.iv_student_2://跳转到社团成员
                    case R.id.iv_student_3://跳转到社团成员
                    case R.id.iv_student_4://跳转到社团成员
                    case R.id.iv_student_5://跳转到社团成员
                    case R.id.iv_student_6://跳转到社团成员
                    case R.id.iv_more_student://跳转到社团成员
                        if (club != null) {
                            KLog.d("跳转到" + club.getClub_name() + "的社团成员列表");
                            mPresenter.moreStudent(club.getClub_id());
                        }
                        break;
                    case R.id.ll_item_hot_clubs1://跳转到社团详情
                        WCHotClubListInfo hotClub1 = mAdapter.getHotClub(0);
                        if (hotClub1 != null){
                            KLog.d("跳转到" + hotClub1.getClub_name() + "的社团成员列表");
                            mPresenter.club(hotClub1.getClub_id());
                        }
                        break;
                    case R.id.ll_item_hot_clubs2://跳转到社团详情
                        WCHotClubListInfo hotClub2 = mAdapter.getHotClub(1);
                        if (hotClub2 != null){
                            KLog.d("跳转到" + hotClub2.getClub_name() + "的社团成员列表");
                            mPresenter.club(hotClub2.getClub_id());
                        }
                        break;
                    case R.id.ll_item_hot_clubs3://跳转到社团详情
                        WCHotClubListInfo hotClub3 = mAdapter.getHotClub(2);
                        if (hotClub3 != null){
                            KLog.d("跳转到" + hotClub3.getClub_name() + "的社团成员列表");
                            mPresenter.club(hotClub3.getClub_id());
                        }
                        break;
                    case R.id.ll_item_hot_clubs4://跳转到社团详情
                        WCHotClubListInfo hotClub4 = mAdapter.getHotClub(3);
                        if (hotClub4 != null){
                            KLog.d("跳转到" + hotClub4.getClub_name() + "的社团成员列表");
                            mPresenter.club(hotClub4.getClub_id());
                        }
                        break;
                    case R.id.ll_item_hot_clubs5://跳转到社团详情
                        WCHotClubListInfo hotClub5 = mAdapter.getHotClub(4);
                        if (hotClub5 != null){
                            KLog.d("跳转到" + hotClub5.getClub_name() + "的社团成员列表");
                            mPresenter.club(hotClub5.getClub_id());
                        }
                        break;
                    case R.id.item_club://跳转到社团详情
                        if (club != null){
                            KLog.d("跳转到" + club.getClub_name() + "的社团成员列表");
                            mPresenter.club(club.getClub_id());
                        }
                    default:break;
                }
            }
        });

        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getClubListFromServer(pageNo);
            }
        });

        mPageAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WCBannerInfo info = mPageAdapter.getBanner(mViewPager.getCurrentItem());
                if (info == null) return;

                ToastUtils.showLongToast(info.getTitle());
            }
        });

        mScanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.scan();
            }
        });

        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.search();
            }
        });
    }

    private void initView() {
        getActivityComponent().inject(this);

        mToolbar = findViewById(R.id.toolbar,Toolbar.class);
        mAppBar = findViewById(R.id.app_bar,AppBarLayout.class);
        mSchoolName = findViewById(R.id.tv_school_name, TextView.class);
        mScanView = findViewById(R.id.iv_scan,ImageView.class);
        mSearchView = findViewById(R.id.iv_search,ImageView.class);

        mViewPager = findViewById(R.id.viewPager,ViewPager.class);
        mIndicator = findViewById(R.id.indicator,CirclePageIndicator.class);

        mPageAdapter = new BannerPageAdapter(mImageLoaderHelper);
        mViewPager.setAdapter(mPageAdapter);
        mIndicator.setViewPager(mViewPager);

        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                final float fraction = Math.abs(verticalOffset*1.0f)/appBarLayout.getTotalScrollRange();
                mToolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.colorPrimary),
                        fraction));
                mSchoolName.setTextColor(old2new(getResources().getColor(R.color.colorPrimary),
                        getResources().getColor(R.color.themeColor), fraction));
                if (fraction < 0.6){
                    mScanView.setImageResource(R.mipmap.navitem_scan_white);
                    mSearchView.setImageResource(R.mipmap.navitem_search_white);
                }else{
                    mScanView.setImageResource(R.mipmap.navitem_scan_blue);
                    mSearchView.setImageResource(R.mipmap.navitem_search_blue);
                }
            }
        });

        mRefresh = findViewById(R.id.swipeRefreshLayout,SwipeRefreshLayout.class);
        attachRefreshLayout(mRefresh,mRecyclerView);
        mRecyclerView = findViewById(R.id.recycler_view,RecyclerView.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WCClubsListAdapter(mImageLoaderHelper) {
            @Override
            public void bind(BaseViewHolder holder, int layoutRes) {
                if (layoutRes == R.layout.view_index_club_item) {
                    resize(holder);
                    holder.setCheckable(R.id.item_club,true);
                }else if (layoutRes == R.layout.item_index_head){
                    resizeHotClubs(holder);
                }
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addHeadLayout(R.layout.item_index_head);
        mAdapter.addHeadLayout(R.layout.view_index_all_clubs_title);

        pageNo = 1;
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    @Override
    public void onShow() {
        mPresenter.startBanner();
    }

    @Override
    public void onHide() {
        mPresenter.stopBanner();
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

    private void resize(@NonNull BaseViewHolder holder) {
        final int lW = mWidth - SizeUtils.dp2px((16+16+2)*2);
        KLog.d("总宽度:"+mWidth);
        KLog.d("LinearLayout宽度:"+lW);

        final int imageWidth = lW / 7;
        final int margin = SizeUtils.dp2px(8);

        ViewCallback<RoundImageView> callback = new ViewCallback<RoundImageView>() {
            @Override
            public void callback(@NonNull RoundImageView view) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.width = imageWidth - margin;
                params.height = imageWidth - margin;
                view.setLayoutParams(params);
                view.setRectAdius(imageWidth-margin);
            }
        };

        holder.setView(R.id.iv_student_1,callback);
        holder.setView(R.id.iv_student_2,callback);
        holder.setView(R.id.iv_student_3,callback);
        holder.setView(R.id.iv_student_4,callback);
        holder.setView(R.id.iv_student_5,callback);
        holder.setView(R.id.iv_student_6,callback);
        holder.setView(R.id.iv_more_student,callback);

        holder.setClickable(R.id.iv_student_1,true);
        holder.setClickable(R.id.iv_student_2,true);
        holder.setClickable(R.id.iv_student_3,true);
        holder.setClickable(R.id.iv_student_4,true);
        holder.setClickable(R.id.iv_student_5,true);
        holder.setClickable(R.id.iv_student_6,true);
        holder.setClickable(R.id.iv_more_student,true);
    }

    private void resizeHotClubs(BaseViewHolder holder) {
        final int lW = mWidth - SizeUtils.dp2px(16*2);
        KLog.d("总宽度:"+mWidth);
        KLog.d("LinearLayout宽度:"+lW);

        final int imageWidth = lW / 5;
        final int margin = SizeUtils.dp2px(4);
        final int ImageMargin = SizeUtils.dp2px(10);

        ViewCallback<LinearLayout> layoutViewCallback = new ViewCallback<LinearLayout>() {
            @Override
            public void callback(@NonNull LinearLayout view) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.width = imageWidth - margin;
                view.setLayoutParams(params);
            }
        };

        ViewCallback<RoundImageView> callback = new ViewCallback<RoundImageView>() {
            @Override
            public void callback(@NonNull RoundImageView view) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.width = imageWidth - ImageMargin;
                params.height = imageWidth - ImageMargin;
                view.setLayoutParams(params);
                view.setRectAdius(imageWidth-ImageMargin);
            }
        };

        holder.setView(R.id.ll_item_hot_clubs1,layoutViewCallback);
        holder.setView(R.id.ll_item_hot_clubs2,layoutViewCallback);
        holder.setView(R.id.ll_item_hot_clubs3,layoutViewCallback);
        holder.setView(R.id.ll_item_hot_clubs4,layoutViewCallback);
        holder.setView(R.id.ll_item_hot_clubs5,layoutViewCallback);

        holder.setView(R.id.img_club_logo1,callback);
        holder.setView(R.id.img_club_logo2,callback);
        holder.setView(R.id.img_club_logo3,callback);
        holder.setView(R.id.img_club_logo4,callback);
        holder.setView(R.id.img_club_logo5,callback);

        holder.setClickable(R.id.ll_item_hot_clubs1,true);
        holder.setClickable(R.id.ll_item_hot_clubs2,true);
        holder.setClickable(R.id.ll_item_hot_clubs3,true);
        holder.setClickable(R.id.ll_item_hot_clubs4,true);
        holder.setClickable(R.id.ll_item_hot_clubs5,true);
    }

    //======================= MVP View ===================


    @Override
    public void setSchoolName(@NonNull String schoolName) {
        mSchoolName.setText(schoolName);
    }

    @Override
    public void setHotClubs(@NonNull List<WCHotClubListInfo> hotClubs) {
        KLog.d("热门社区的人数:"+hotClubs.size());
        mAdapter.setHotClubs(hotClubs);
    }

    @Override
    public void setBanner(@NonNull List<WCBannerInfo> banners) {
        mPageAdapter.setBanners(banners);
        mPresenter.startBanner();
    }

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

    @Override
    public void autoBanner() {
        final int currentItem = mViewPager.getCurrentItem();
        final int count = mPageAdapter.getCount();
        if (count == 0){
            return;
        }
        final int nextItem = (currentItem+1)%count;
        mViewPager.setCurrentItem(nextItem,true);
    }

    @Override
    public void showAuthDialog() {
        if (mAuthDialog == null) {
            mAuthDialog = new AuthDialog();
            mAuthDialog.setCancelable(false);
            mAuthDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAuthActivity();
                    mAuthDialog.dismissAllowingStateLoss();
                }
            });
        }
        mAuthDialog.show(getActivity().getSupportFragmentManager(),AuthDialog.TAG);
    }

    @Override
    public void openScanActivity() {
        ToastUtils.showLongToast("打开搜索页面");
    }

    @Override
    public void openSearchActivity() {
        ToastUtils.showLongToast("打开扫一扫页面");
    }

    @Override
    public void openClubDetailActivity(long club_id) {
        Bundle bundle = new Bundle();
        bundle.putLong(WCClubDetailActivity.CLUB_ID,club_id);
        showIntent(WCClubDetailActivity.class,bundle);
    }

    @Override
    public void openStudentListActivity(long club_id) {
        ToastUtils.showLongToast("打开社团成员页面");
    }

    private void openAuthActivity(){
        ToastUtils.showLongToast("跳转到认证界面");
    }
}
