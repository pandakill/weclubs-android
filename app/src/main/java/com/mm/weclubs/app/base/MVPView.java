package com.mm.weclubs.app.base;

import android.support.annotation.StringRes;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/8 下午3:47
 * 描述: mvpView的一些常用的方法
 */
public interface MVPView {

    void showToast(String text);

    void onError(@StringRes int resId);

    void onError(String message);

    void showToast(@StringRes int resId);

    void showProgressDialog(String msg, boolean cancel);

    void hideProgressDialog();

    /**
     * 返回登录页面，但是不需要请求用户数据
     */
    void backToLoginActivity();

    /**
     * 关闭当前页面
     */
    void close();
}
