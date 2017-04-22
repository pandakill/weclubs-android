package com.mm.weclubs.app.base;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/8 下午3:46
 * 描述:
 */
public interface Presenter<V extends MVPView> {

    void attachView(V mvpView);

    void detachView();
}
