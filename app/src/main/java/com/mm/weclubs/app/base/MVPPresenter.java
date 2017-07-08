package com.mm.weclubs.app.base;

import android.support.annotation.NonNull;

import com.mm.weclubs.data.network.NetError;

import io.reactivex.functions.Consumer;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/8 下午3:46
 * 描述:
 */
public interface MVPPresenter<V extends MVPView>  extends Consumer<Throwable> {

    void attachView(V mvpView);

    void detachView();

    void handleNetError(@NonNull NetError error);
}
