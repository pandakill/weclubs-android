package com.mm.weclubs.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.rxbus.RxBus;
import com.mm.weclubs.util.ThreadUtil;
import com.mm.weclubs.util.WCLog;

import cn.bingoogolapple.titlebar.BGATitleBar;
import cn.bingoogolapple.titlebar.BGATitleBar.Delegate;
import me.fangx.haorefresh.HaoRecyclerView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/27 上午9:52
 * 描述:
 */

public abstract class BaseActivity extends AppCompatActivity implements MVPView {

    private BGATitleBar mTitleBar;

    protected WCLog log;

    protected ProgressDialog mProgressDialog;

    private RxBus mBus = null;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    private HaoRecyclerView mRecyclerView = null;

    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);

        setContentView(getContentLayout());

        log = new WCLog(this.getClass());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getBundleExtras(extras);
        }

        initView();
        initTitleBar();
        afterView();
    }

    @Override
    public void finish() {
        super.finish();
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in, R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBus != null) {

        }

        unSubscribeObservable();
    }

    protected abstract int getContentLayout();

    /**
     * 获取bundle数据
     *
     * @param extras bundle数据源,如果有数据的话,{@code extras}不为空,否则会出现{@code extras}为空的情况.
     */
    protected abstract void getBundleExtras(Bundle extras);

    protected abstract void initView();

    protected abstract void afterView();

    protected abstract boolean toggleOverridePendingTransition();

    protected abstract TransitionMode getOverridePendingTransitionMode();

    protected abstract boolean leftBtnIsReturn();

    protected abstract void unSubscribeObservable();

    protected void initTitleBar() {
        mTitleBar = (BGATitleBar) findViewById(R.id.title_bar);

        if (mTitleBar == null) {
            log.d("titleBar == null");
            return;
        }

        mTitleBar.setDelegate(new Delegate() {
            @Override
            public void onClickLeftCtv() {
                onClickLeftTitle();
            }

            @Override
            public void onClickTitleCtv() {
                onClickTitle();
            }

            @Override
            public void onClickRightCtv() {
                onClickRightTitle();
            }

            @Override
            public void onClickRightSecondaryCtv() {
                onClickRightSecondTitle();
            }
        });
    }

    protected void attachRefreshLayout(SwipeRefreshLayout refreshLayout, HaoRecyclerView recyclerView) {
        this.mSwipeRefreshLayout = refreshLayout;
        this.mRecyclerView = recyclerView;
    }

    public BGATitleBar getTitleBar() {
        return mTitleBar;
    }

    protected void onClickTitle() {

        if (getTitleBar() == null) {
            return;
        }

        showToast("点击了标题栏");
    }

    protected void onClickLeftTitle() {

        if (getTitleBar() == null) {
            return;
        }

        if (leftBtnIsReturn()) {
            finish();
        }
    }

    protected void onClickRightTitle() {

        if (getTitleBar() == null) {
            return;
        }

        showToast("点击了右侧按钮");
    }

    protected void onClickRightSecondTitle() {

        if (getTitleBar() == null) {
            return;
        }

        showToast("点击了右侧第二个按钮");
    }

    /**
     * 普通的startActivity
     *
     * @param targetActivity 目标activity
     */
    protected void showIntent(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }

    /**
     * 带有bundle的startActivity
     *
     * @param targetActivity 目标activity
     * @param extras         需要传送的参数
     */
    protected void showIntent(Class<?> targetActivity, Bundle extras) {
        Intent intent = new Intent(this, targetActivity);
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
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
        finish();
    }

    /**
     * 带有bundle的startActivity,跳转之后需要finish本页面
     *
     * @param targetActivity 目标activity
     * @param extras         需要传送的参数
     */
    protected void showIntentThenKill(Class<?> targetActivity, Bundle extras) {
        Intent intent = new Intent(this, targetActivity);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
        finish();
    }

    /**
     * 普通的startActivityForResult
     *
     * @param targetActivity 目标activity
     * @param requestCode    请求码
     */
    protected void showIntentForResult(Class<?> targetActivity, int requestCode) {
        Intent intent = new Intent(this, targetActivity);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 带有bundle的startActivityForResult
     *
     * @param targetActivity 目标activity
     * @param requestCode    请求码
     * @param extras         需要传送的参数
     */
    protected void showIntentForResult(Class<?> targetActivity, int requestCode, Bundle extras) {
        Intent intent = new Intent(this, targetActivity);
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
    public void showProgressDialog(String msg, boolean cancel) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }

        if (mProgressDialog.isShowing()) {
            hideProgressDialog();
        }

        if (EmptyUtils.isEmpty(msg)) {
            msg = "加载中...";
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(cancel);

        ThreadUtil.runInMainThread(this, new Runnable() {
            @Override
            public void run() {
                mProgressDialog.show();
            }
        });
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog == null) {
            return;
        }

        ThreadUtil.runInMainThread(this, new Runnable() {
            @Override
            public void run() {
                mProgressDialog.cancel();
                mProgressDialog.hide();

                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
