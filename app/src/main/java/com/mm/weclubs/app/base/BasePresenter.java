package com.mm.weclubs.app.base;

import android.content.Context;

import com.mm.weclubs.app.security.WCHttpParamsPresenter;
import com.mm.weclubs.util.WCLog;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/8 下午3:48
 * 描述:
 */
public abstract class BasePresenter<V extends MVPView> implements Presenter<V> {

    private V mMvpView;
    protected Context mContext;

    protected WCLog log;
    protected WCHttpParamsPresenter mHttpParamsPresenter;

    public BasePresenter(Context context) {
        mContext = context;
        mHttpParamsPresenter = new WCHttpParamsPresenter();
        initLog();
    }

    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttachView() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttachView()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("在请求数据前请先调用 Presenter.attachView(MVPView).");
        }
    }

    protected abstract void initLog();
}
