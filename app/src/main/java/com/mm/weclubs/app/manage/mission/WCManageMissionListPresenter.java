package com.mm.weclubs.app.manage.mission;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.bean.WCClubMissionBean;
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

public class WCManageMissionListPresenter<V extends WCManageMissionListContract.View> extends BasePresenter<V>
    implements WCManageMissionListContract.Presenter<V>{

    @Inject
    public WCManageMissionListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void refresh() {
        getCompositeDisposable().add(
                getDataManager().getUserId()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .flatMap(new Function<Integer, ObservableSource<WCClubMissionBean>>() {
                            @Override
                            public ObservableSource<WCClubMissionBean> apply(@NonNull Integer userId) throws Exception {
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
                                return getDataManager().getMyMission(params)
                                        .subscribeOn(getSchedulerProvider().io());
                            }
                        }).observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<WCClubMissionBean>() {
                            @Override
                            public void accept(@NonNull WCClubMissionBean bean) throws Exception {
                                getMvpView().refreshMissionList(bean.getMission());
                                getMvpView().hideProgressDialog();
                            }
                        },this));
    }

    @Override
    public void getMissionListFromServer(final int pageNo) {

        getCompositeDisposable().add(
                getDataManager().getUserId()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .flatMap(new Function<Integer, ObservableSource<WCClubMissionBean>>() {
                            @Override
                            public ObservableSource<WCClubMissionBean> apply(@NonNull Integer userId) throws Exception {
                                if (userId == NULL_INDEX){
                                    getMvpView().showToast("用户没有登录");
                                    getMvpView().backToLoginActivity();
                                    return Observable.empty();
                                }
                                getMvpView().showProgressDialog("加载中...", false);

                                Map<String, Object> params = new HashMap<>();

                                params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
                                params.put("page_no", pageNo);
                                params.put("sponsor_id", userId);
                                return getDataManager().getMyMission(params)
                                        .subscribeOn(getSchedulerProvider().io());
                            }
                        }).observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<WCClubMissionBean>() {
                            @Override
                            public void accept(@NonNull WCClubMissionBean bean) throws Exception {
                                getMvpView().addMissionList(bean.getMission(), bean.getHas_more() == 1);
                                getMvpView().hideProgressDialog();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                WCManageMissionListPresenter.this.accept(throwable);
                                if (isViewAttachView()){
                                    getMvpView().loadFail();
                                }
                            }
                        }));
    }
}
