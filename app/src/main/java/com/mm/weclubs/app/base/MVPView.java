package com.mm.weclubs.app.base;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/8 下午3:47
 * 描述: mvpView的一些常用的方法
 */
public interface MVPView {

    void showToast(String text);

    void showProgressDialog(String msg, boolean cancel);

    void hideProgressDialog();

    /**
     * 返回登录页面，但是不需要请求用户数据
     */
    void backToLoginActivity();
}
