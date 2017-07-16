package com.mm.weclubs.app.club_detail;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.util.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class WCClubDetailPresenter<V extends WCClubDetailContract.View> extends BasePresenter<V>
    implements WCClubDetailContract.Presenter<V>{

    @Inject
    public WCClubDetailPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);

        init();
    }

    private void init() {
        getCompositeDisposable().add(getDataManager().getUser()
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
                            case User.AUTH_FAILD:
                                getMvpView().setAuth("认证失败");
                                break;
                            default:break;
                        }
                    }
                },this)
        );
    }
}
