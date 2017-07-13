package com.mm.weclubs.app.index;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.data.network.bean.WCIndexClubBean;
import com.mm.weclubs.data.network.bean.WCIndexDataBean;
import com.mm.weclubs.util.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午11:29
 * 描述: 用于登录相关的 presenter
 */

public class WCIndexPresenter<V extends WCIndexContract.View> extends BasePresenter<V>
    implements WCIndexContract.Presenter<V>{

    public static final Long TIME_INTERVAL = 4500L;

    private Disposable mClockDisposable;

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
        getCompositeDisposable().addAll(
                getDataManager().getUser()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .flatMap(new Function<User, ObservableSource<WCIndexClubBean>>() {
                            @Override
                            public ObservableSource<WCIndexClubBean> apply(@NonNull User user) throws Exception {

                                getMvpView().showProgressDialog("加载中...", false);

                                Map<String, Object> params = new HashMap<>();

                                params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
                                params.put("page_no", 1);
                                params.put("school_id", user.getSchoolId());

                                // 设置学校名
                                getMvpView().setSchoolName(user.getSchoolName());

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
                        },this),
                getDataManager().getUser()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .flatMap(new Function<User, ObservableSource<WCIndexDataBean>>() {
                            @Override
                            public ObservableSource<WCIndexDataBean> apply(@NonNull User user) throws Exception {

                                Map<String, Object> params = new HashMap<>();
                                params.put("school_id", user.getSchoolId());

                                return getDataManager().getIndexData(params)
                                        .subscribeOn(getSchedulerProvider().io());
                            }
                        }).observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<WCIndexDataBean>() {
                            @Override
                            public void accept(@NonNull WCIndexDataBean bean) throws Exception {
                                getMvpView().setBanner(bean.getBanner());
                                getMvpView().setHotClubs(bean.getHot_club());
                                getMvpView().hideProgressDialog();
                            }
                        },this)
        );
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
                                }
                            }
                        }));
    }

    @Override
    public void startBanner() {
        if (mClockDisposable == null || mClockDisposable.isDisposed()){
            mClockDisposable = Observable.interval(TIME_INTERVAL, TimeUnit.MILLISECONDS)
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@NonNull Long aLong) throws Exception {
                            if (isViewAttachView()){
                                getMvpView().autoBanner();
                            }
                        }
                    },this);
            getCompositeDisposable().add(mClockDisposable);
        }
    }

    @Override
    public void stopBanner() {
        if (mClockDisposable != null){
            getCompositeDisposable().remove(mClockDisposable);
            mClockDisposable = null;
        }
    }
}
