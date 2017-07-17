package com.mm.weclubs.app.mine;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午11:29
 * 描述: 用于登录相关的 presenter
 */

public class WCMinePresenter<V extends WCMineContract.View> extends BasePresenter<V>
    implements WCMineContract.Presenter<V>{

    @Inject
    public WCMinePresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);

        init();
    }

    private void init() {
        getCompositeDisposable().add(getDataManager().getUserAsync()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        getMvpView().setAvatar(user.getAvatarUrl());
                        getMvpView().setName(user.getNickName());
                        getMvpView().setInfo(user.getGraduateYear()+user.getClassName(),user.getGender());
                        switch (user.getIsAuth()){
                            case User.AUTH_NO:
                                getMvpView().setAuth("未认证");
                                break;
                            case User.AUTH_ING:
                                getMvpView().setAuth("认证中");
                                break;
                            case User.AUTH_SUCCESS:
                                getMvpView().setAuth("认证通过");
                                break;
                            case User.AUTH_FAIL:
                                getMvpView().setAuth("认证失败");
                                break;
                            default:break;
                        }
                    }
                },this)
        );
    }
}
