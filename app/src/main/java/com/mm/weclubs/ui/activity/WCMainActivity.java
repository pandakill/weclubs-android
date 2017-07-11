package com.mm.weclubs.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.mm.weclubs.R;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.rxbus.RxEvents;
import com.mm.weclubs.ui.fragment.WCDynamicFragment;
import com.mm.weclubs.ui.fragment.WCIndexFragment;
import com.mm.weclubs.ui.fragment.WCMineFragment;
import com.mm.weclubs.ui.fragment.WCToolsFragment;
import com.mm.weclubs.util.StatusBarUtil;
import com.mm.weclubs.widget.BottomNavigationViewEx;
import com.mm.weclubs.widget.FragmentTabHost;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class WCMainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    public static final int TAB_NONE = 0;
    public static final int TAB_INDEX = 1;
    public static final int TAB_DYNAMIC = 2;
    public static final int TAB_TOOLS = 3;
    public static final int TAB_MINE = 4;

    private TabWidget mTabs;
    private FragmentTabHost mTabHost;

    private FragmentManager mFragmentManager;

    // tabHost 图标
    private int mFragmentDrawables[] = {
            R.drawable.tab_host_index,
            R.drawable.tab_host_dynamic,
            R.drawable.tab_host_tools,
            R.drawable.tab_host_mine
    };
    // tabHost 标题
    private String mFragmentTags[] = {
            "首页", "动态", "工具", "我的"
    };

    // fragment
    private Class<?> mFragments[] = {
            WCIndexFragment.class,
            WCDynamicFragment.class,
            WCToolsFragment.class,
            WCMineFragment.class
    };

    private int mCurrentTab = TAB_NONE;

    private FragmentManager mManager;
    private FragmentTransaction mTransaction;

    private BottomNavigationViewEx mNavigation;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0,null);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //}
        //mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //mTabs = (TabWidget) findViewById(android.R.id.tabs);

        //mFragmentManager = getSupportFragmentManager();

        mNavigation = (BottomNavigationViewEx)findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(this);
        mNavigation.enableShiftingMode(false);
        mNavigation.enableItemShiftingMode(false);

        mManager = getSupportFragmentManager();
    }

    @Override
    protected void afterView() {
        //initTabHost();
        tabChanged(TAB_INDEX);

        mBus.addDisposable(this,mBus.toObservable(RxEvents.class)
                .subscribe(new Consumer<RxEvents>() {
                    @Override
                    public void accept(@NonNull RxEvents rxEvents) throws Exception {
                        switch (rxEvents.getEventName()) {
                            case RxEvents.loginSuccess:
                                showToast("登录成功");
                                break;
                        }
                    }
                }));
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return false;
    }

    @Override
    protected void getBundleExtras(@android.support.annotation.NonNull Bundle extras) {

    }

    @Override
    public boolean onNavigationItemSelected(@android.support.annotation.NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_index:
                tabChanged(TAB_INDEX);
                return true;
            case R.id.navigation_dynamic:
                tabChanged(TAB_DYNAMIC);
                return true;
            case R.id.navigation_tools:
                tabChanged(TAB_TOOLS);
                return true;
            case R.id.navigation_mine:
                tabChanged(TAB_MINE);
                return true;
        }
        return false;
    }

    public void tabChanged(int tab){
        if (tab == mCurrentTab){
            return;
        }
        if (mTransaction == null){
            mTransaction = mManager.beginTransaction();
        }
        //隐藏其他界面
        hideFragment();
        mCurrentTab = tab;
        switch (mCurrentTab){
            case TAB_INDEX:
                mNavigation.setCurrentItem(0);
                Fragment homeFragment = mManager.findFragmentByTag(WCIndexFragment.TAG);
                if (homeFragment != null){
                    mTransaction.show(homeFragment);
                } else {
                    homeFragment = WCIndexFragment.newInstance();
                    mTransaction.add(R.id.content,homeFragment,WCIndexFragment.TAG);
                }
                break;
            case TAB_DYNAMIC:
                mNavigation.setCurrentItem(1);
                Fragment newFragment = mManager.findFragmentByTag(WCDynamicFragment.TAG);
                if (newFragment != null){
                    mTransaction.show(newFragment);
                } else {
                    newFragment = WCDynamicFragment.newInstance();
                    mTransaction.add(R.id.content,newFragment,WCDynamicFragment.TAG);
                }
                break;
            case TAB_TOOLS:
                mNavigation.setCurrentItem(2);
                Fragment showFragment = mManager.findFragmentByTag(WCToolsFragment.TAG);
                if (showFragment != null){
                    mTransaction.show(showFragment);
                } else {
                    showFragment = WCToolsFragment.newInstance();
                    mTransaction.add(R.id.content,showFragment,WCToolsFragment.TAG);
                }
                break;
            case TAB_MINE:
                mNavigation.setCurrentItem(3);
                Fragment shoppingFragment = mManager.findFragmentByTag(WCMineFragment.TAG);
                if (shoppingFragment != null){
                    mTransaction.show(shoppingFragment);
                } else {
                    shoppingFragment = WCMineFragment.newInstance();
                    mTransaction.add(R.id.content,shoppingFragment,WCMineFragment.TAG);
                }
                break;
        }

        mTransaction.commitNowAllowingStateLoss();
        mTransaction = null;
    }

    // 隐藏所有片段
    private void hideFragment() {
        Fragment fragment = mManager.findFragmentByTag(WCIndexFragment.TAG);
        if (fragment != null){
            mTransaction.hide(fragment);
        }
        fragment = mManager.findFragmentByTag(WCDynamicFragment.TAG);
        if (fragment != null){
            mTransaction.hide(fragment);
        }
        fragment = mManager.findFragmentByTag(WCToolsFragment.TAG);
        if (fragment != null){
            mTransaction.hide(fragment);
        }
        fragment = mManager.findFragmentByTag(WCMineFragment.TAG);
        if (fragment != null){
            mTransaction.hide(fragment);
        }
    }

    private void initTabHost() {
        mTabHost.setup(this, mFragmentManager, android.R.id.tabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);   // 去除分割线
        mTabHost.clearAllTabs();

        for (int i = 0; i < mFragmentTags.length; i++) {
            // 添加图片和文字
            TabSpec tabSpec = mTabHost.newTabSpec(mFragmentTags[i]).setIndicator(getImageView(i));
            // 设置对应的fragment
            mTabHost.addTab(tabSpec, mFragments[i], null);
            // 设置背景
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(android.R.color.white);
            mTabHost.getTabWidget().getChildAt(i).setTag(i);
            mTabHost.getTabWidget().getChildAt(i).setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch ((Integer) view.getTag()) {
                                case 0:
                                    clickTagHost((Integer) view.getTag(), mFragmentTags[0]);
                                    break;
                                case 1:
                                    clickTagHost((Integer) view.getTag(), mFragmentTags[1]);
                                    break;
                                case 2:
                                    clickTagHost((Integer) view.getTag(), mFragmentTags[2]);
                                    break;
                                case 3:
                                    clickTagHost((Integer) view.getTag(), mFragmentTags[3]);
                                    break;
                            }
                        }
                    });
        }
    }

    private void clickTagHost(int tag, String tagId) {
        mTabs.setCurrentTab(tag);
        mTabHost.onTabChanged(tagId);
    }

    // 获得图片资源
    private View getImageView(int index) {
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.view_tab_indicator, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_iv_image);
        TextView textView = (TextView) view.findViewById(R.id.tab_tv_title);
        textView.setText(mFragmentTags[index]);
        imageView.setImageResource(mFragmentDrawables[index]);
        return view;
    }
}
