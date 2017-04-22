package com.mm.weclubs.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/8 上午11:20
 * 描述: fragment基类
 */
public abstract class BaseLazyFragment extends Fragment {

    protected static String TAG = null;

    protected Context mContext = null;

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared = true;

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
            return inflater.inflate(getContentViewLayoutID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewsAndEvents();
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

    /**
     * fragment的layout文件id
     *
     * @return layout的文件id
     */
    protected abstract int getContentViewLayoutID();

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
}
