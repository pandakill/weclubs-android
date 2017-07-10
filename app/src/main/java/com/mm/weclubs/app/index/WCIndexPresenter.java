package com.mm.weclubs.app.index;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.data.network.bean.WCIndexClubBean;
import com.mm.weclubs.util.rx.SchedulerProvider;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午11:29
 * 描述: 用于登录相关的 presenter
 */

public class WCIndexPresenter<V extends WCIndexContract.View> extends BasePresenter<V>
    implements WCIndexContract.Presenter<V>{

    @Inject
    public WCIndexPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);

        refresh();
    }

    @Override
    public void refresh() {
        getCompositeDisposable().add(
                getDataManager().getUser()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .flatMap(new Function<User, ObservableSource<WCIndexClubBean>>() {
                            @Override
                            public ObservableSource<WCIndexClubBean> apply(@NonNull User user) throws Exception {

                                getMvpView().showProgressDialog("加载中...", false);

                                HashMap<String, Object> params = new HashMap<>();

                                params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
                                params.put("page_no", 1);
                                params.put("school_id", user.getSchoolId());

                                return getDataManager().getIndexClub(params)
                                        .subscribeOn(getSchedulerProvider().io());
                            }
                        }).observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<WCIndexClubBean>() {
                            @Override
                            public void accept(@NonNull WCIndexClubBean bean) throws Exception {
                                getMvpView().setData(bean.getClub_list());
                                getMvpView().hideProgressDialog();
                            }
                        },this));
    }

    @Override
    public void getClubListFromServer(final int pageNo) {
        getCompositeDisposable().add(
                getDataManager().getUser()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .flatMap(new Function<User, ObservableSource<WCIndexClubBean>>() {
                            @Override
                            public ObservableSource<WCIndexClubBean> apply(@NonNull User user) throws Exception {
                                getMvpView().showProgressDialog("加载中...", false);

                                HashMap<String, Object> params = new HashMap<>();

                                params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
                                params.put("page_no", pageNo);
                                params.put("school_id", user.getSchoolId());

                                return getDataManager().getIndexClub(params)
                                        .subscribeOn(getSchedulerProvider().io());
                            }
                        }).observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<WCIndexClubBean>() {
                            @Override
                            public void accept(@NonNull WCIndexClubBean bean) throws Exception {
                                getMvpView().addData(bean.getClub_list(), bean.getIs_more() == 1);
                                getMvpView().hideProgressDialog();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                WCIndexPresenter.this.accept(throwable);
                                if (isViewAttachView()){
                                    getMvpView().loadFail();
                                    getMvpView().hideProgressDialog();
                                }
                            }
                        }));
    }
}
