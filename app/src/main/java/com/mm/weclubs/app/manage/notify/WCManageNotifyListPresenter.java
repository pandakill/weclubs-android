package com.mm.weclubs.app.manage.notify;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.data.network.bean.WCClubNotifyBean;
import com.mm.weclubs.util.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.mm.weclubs.data.prefs.AppPreferencesHelper.NULL_INDEX;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午6:54
 * 描述:
 */

public class WCManageNotifyListPresenter<V extends WCManageNotifyListContract.View> extends BasePresenter<V>
    implements WCManageNotifyListContract.Presenter<V>{

    @Inject
    public WCManageNotifyListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void refresh() {
        getCompositeDisposable().add(
                getDataManager().getUserId()
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .flatMap(new Function<Integer, ObservableSource<WCClubNotifyBean>>() {
                @Override
                public ObservableSource<WCClubNotifyBean> apply(@NonNull Integer userId) throws Exception {
                    if (userId == NULL_INDEX){
                        getMvpView().showToast("用户没有登录");
                        getMvpView().backToLoginActivity();
                        return Observable.empty();
                    }
                    getMvpView().showProgressDialog("加载中...", false);

                    Map<String, Object> params = new HashMap<>();

                    params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
                    params.put("page_no", 1);
                    params.put("sponsor_id", userId);
                    return getDataManager().getMyNotify(params)
                            .subscribeOn(getSchedulerProvider().io());
                }
            }).observeOn(getSchedulerProvider().ui())
            .subscribe(new Consumer<WCClubNotifyBean>() {
                @Override
                public void accept(@NonNull WCClubNotifyBean wcClubNotifyBean) throws Exception {
                    getMvpView().refreshNotifyList(wcClubNotifyBean.getNotify());
                    getMvpView().hideProgressDialog();
                }
            },this));
    }

    @Override
    public void getNotifyListFromServer(final int pageNo) {

        getCompositeDisposable().add(Observable.just(getDataManager().getLastTimeLoginId())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        User user = getDataManager().loadUser();
                        if (user == null || user.getUserId() != integer){
                            return NULL_INDEX;
                        }
                        return user.getUserId();
                    }
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .flatMap(new Function<Integer, ObservableSource<WCClubNotifyBean>>() {
                    @Override
                    public ObservableSource<WCClubNotifyBean> apply(@NonNull Integer userId) throws Exception {
                        if (userId == NULL_INDEX){
                            getMvpView().showToast("用户没有登录");
                            getMvpView().backToLoginActivity();
                            return Observable.empty();
                        }
                        //getMvpView().showProgressDialog("加载中...", false);

                        Map<String, Object> params = new HashMap<>();

                        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
                        params.put("page_no", pageNo);
                        params.put("sponsor_id", userId);
                        return getDataManager().getMyNotify(params)
                                .subscribeOn(getSchedulerProvider().io());
                    }
                }).observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCClubNotifyBean>() {
                    @Override
                    public void accept(@NonNull WCClubNotifyBean wcClubNotifyBean) throws Exception {
                        getMvpView().addNotifyList(wcClubNotifyBean.getNotify(), wcClubNotifyBean.getHas_more() == 1);
                        getMvpView().hideProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        WCManageNotifyListPresenter.this.accept(throwable);
                        if (isViewAttachView()){
                            getMvpView().loadFail();
                        }
                    }
                }));
    }
}
