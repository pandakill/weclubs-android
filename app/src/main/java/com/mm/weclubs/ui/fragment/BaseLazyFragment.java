package com.mm.weclubs.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.ToastUtils;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.di.component.ActivityComponent;
import com.mm.weclubs.ui.activity.WCLoginActivity;
import com.mm.weclubs.util.WCLog;
import com.socks.library.KLog;

import java.lang.reflect.Field;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/8 上午11:20
 * 描述: fragment基类
 */
public abstract class BaseLazyFragment extends Fragment implements MVPView {

    protected static String TAG = null;

    protected WCLog log;

    protected Context mContext = null;
    protected Bundle mExtra = null;

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared = true;

    private View mRootView;
    private boolean mCreatedView = false;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    private RecyclerView mRecyclerView = null;

    public ActivityComponent getActivityComponent() {
        return ((BaseActivity)getActivity()).getActivityComponent();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            mRootView = inflater.inflate(getContentViewLayoutID(), null);
            mCreatedView = true;
            return mRootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mExtra = getArguments();
        getBundleExtras(mExtra);

        initViewsAndEvents();
    }

    protected <T> T findViewById(int viewId, Class<T> targetClass) {
        return (T) mRootView.findViewById(viewId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden && mCreatedView){
            onShow();
        }
        if (hidden && mCreatedView){
            onHide();
        }
    }

    public void onShow(){
        KLog.d("onShow");
    }

    public void onHide() {
        KLog.d("onHide");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    private synchronized void initPrepare() {
        if (!isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = false;
        }
    }

    protected void attachRefreshLayout(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView) {
        this.mSwipeRefreshLayout = refreshLayout;
        this.mRecyclerView = recyclerView;
    }

    /**
     * fragment的layout文件id
     *
     * @return layout的文件id
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 获取bundle数据
     *
     * @param extras bundle数据源,如果有数据的话,{@code extras}不为空,否则会出现{@code extras}为空的情况.
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * 初始化控件和页面事件
     */
    protected abstract void initViewsAndEvents();

    protected abstract void onFirstUserVisible();

    protected abstract void onUserVisible();

    private void onFirstUserInvisible() {
    }

    protected abstract void onUserInvisible();

    /**
     * 获取supportFragmentManager的方法
     *
     * @return 返回该fragment的fragmentManager
     */
    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    /**
     * 普通的startActivity
     *
     * @param targetActivity 目标activity
     */
    protected void showIntent(Class<?> targetActivity) {
        Intent intent = new Intent(getActivity(), targetActivity);
        startActivity(intent);
    }

    /**
     * 带有bundle的startActivity
     *
     * @param targetActivity 目标activity
     * @param extras         需要传送的参数
     */
    protected void showIntent(Class<?> targetActivity, Bundle extras) {
        Intent intent = new Intent(getActivity(), targetActivity);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    /**
     * 普通的startActivity,跳转之后需要finish本页面
     *
     * @param targetActivity 目标activity
     */
    protected void showIntentThenKill(Class<?> targetActivity) {
        Intent intent = new Intent(getActivity(), targetActivity);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * 带有bundle的startActivity,跳转之后需要finish本页面
     *
     * @param targetActivity 目标activity
     * @param extras         需要传送的参数
     */
    protected void showIntentThenKill(Class<?> targetActivity, Bundle extras) {
        Intent intent = new Intent(getActivity(), targetActivity);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * 普通的startActivityForResult
     *
     * @param targetActivity 目标activity
     * @param requestCode    请求码
     */
    protected void showIntentForResult(Class<?> targetActivity, int requestCode) {
        Intent intent = new Intent(getActivity(), targetActivity);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 带有bundle的startActivityForResult
     *
     * @param targetActivity 目标activity
     * @param requestCode    请求码
     * @param extras         需要传送的参数
     */
    protected void showInetentForResult(Class<?> targetActivity, int requestCode, Bundle extras) {
        Intent intent = new Intent(getActivity(), targetActivity);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void showToast(String text) {
        ToastUtils.showShortToastSafe(text);
    }

    @Override
    public void onError(@StringRes int resId) {
        ToastUtils.showShortToastSafe(resId);
    }

    @Override
    public void onError(String message) {
        ToastUtils.showShortToastSafe(message);
    }

    @Override
    public void showToast(@StringRes int resId) {
        ToastUtils.showShortToastSafe(resId);
    }

    @Override
    public void showProgressDialog(String msg, boolean cancel) {
        ((BaseActivity) getActivity()).showProgressDialog(msg, cancel);
    }

    @Override
    public void hideProgressDialog() {
        ((BaseActivity) getActivity()).hideProgressDialog();

        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void backToLoginActivity() {
        Bundle extra = new Bundle();
        extra.putBoolean("getUserInfo", false);
        showIntentThenKill(WCLoginActivity.class, extra);
    }

    @Override
    public void close() {
        getActivity().finish();
    }
}
