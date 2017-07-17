package com.mm.weclubs.app.base;

import android.support.annotation.NonNull;

import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.data.network.NetError;
import com.mm.weclubs.data.network.bean.WCResponseParamBean;
import com.mm.weclubs.util.StatusCode;
import com.mm.weclubs.util.rx.SchedulerProvider;
import com.socks.library.KLog;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static com.mm.weclubs.data.prefs.AppPreferencesHelper.NULL_ID;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/8 下午3:48
 * 描述:
 */
public class BasePresenter<V extends MVPView> implements MVPPresenter<V> {

    private V mMvpView;

    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public BasePresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        mDataManager = dataManager;
        mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mCompositeDisposable.dispose();
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

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {

        if (!isViewAttachView()){
            throwable.printStackTrace();
            return;
        }

        getMvpView().hideProgressDialog();

        if (throwable instanceof NetError){
            handleNetError((NetError) throwable);
        }else{
            throwable.printStackTrace();
        }
    }

    @Override
    public void handleNetError(@NonNull NetError error) {

        switch (error.getErrorCode()){
            case StatusCode.TOKEN_TIMEOUT:
                KLog.d("token失效");
                getMvpView().backToLoginActivity();
                return;
            case StatusCode.FAIL:
            case StatusCode.ILLEGAL:
            case StatusCode.PARAMETER_VIOLATION:
            case StatusCode.PARAMETER_EMPTY:
            case StatusCode.SIGN_FAIL:
            case StatusCode.CALLER_FAIL:
            case StatusCode.PASSWORD_ERROR:
            case StatusCode.NO_WRITE_PERMISSION:
            case StatusCode.NO_USER:
            case StatusCode.APP_ERROR:
                KLog.d(error.getErrorBody());
                getMvpView().onError(error.getErrorBody());
                return;
            case StatusCode.SHOW_TIPS_AND_CLOSE:
                //弹框需要确定且关闭当前页面tips
                getMvpView().showToast(error.getErrorBody());
                getMvpView().close();
                return;
            case StatusCode.SHOW_TIPS:
                getMvpView().showToast(error.getErrorBody());
                return;
            default:break;
        }
        error.printStackTrace();
    }

    protected void checkResult(WCResponseParamBean responseParamBean) {
        if (responseParamBean.getResult_code() == 3010) {
            getMvpView().backToLoginActivity();
        }
    }

    protected boolean checkUserDirty(){
        User user = getDataManager().getUser();
        return checkUserDirty(user);
    }

    protected boolean checkUserDirty(User user){
        if (user == null){
            return true;
        }
        if (user.getUserId() == NULL_ID || user.getUserId() != getDataManager().getLastTimeLoginId()){
            return true;
        }
        return false;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("在请求数据前请先调用 MVPPresenter.attachView(MVPView).");
        }
    }
}
