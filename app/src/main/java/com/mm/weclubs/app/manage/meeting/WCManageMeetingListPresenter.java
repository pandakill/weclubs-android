package com.mm.weclubs.app.manage.meeting;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.bean.WCClubMeetingBean;
import com.mm.weclubs.util.rx.SchedulerProvider;

import java.util.HashMap;

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

public class WCManageMeetingListPresenter<V extends WCManageMeetingListContract.View> extends BasePresenter<V>
    implements WCManageMeetingListContract.Presenter<V>{

    @Inject
    public WCManageMeetingListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void refresh() {
        getCompositeDisposable().add(
                getDataManager().getUserId()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .flatMap(new Function<Integer, ObservableSource<WCClubMeetingBean>>() {
                            @Override
                            public ObservableSource<WCClubMeetingBean> apply(@NonNull Integer userId) throws Exception {
                                if (userId == NULL_INDEX){
                                    getMvpView().showToast("用户没有登录");
                                    getMvpView().backToLoginActivity();
                                    return Observable.empty();
                                }
                                getMvpView().showProgressDialog("加载中...", false);

                                HashMap<String, Object> params = new HashMap<>();

                                params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
                                params.put("page_no", 1);
                                params.put("sponsor_id", userId);

                                return getDataManager().getMyMeeting(params)
                                        .subscribeOn(getSchedulerProvider().io());
                            }
                        }).observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<WCClubMeetingBean>() {
                            @Override
                            public void accept(@NonNull WCClubMeetingBean bean) throws Exception {
                                getMvpView().refreshMeetingList(bean.getMeeting());
                                getMvpView().hideProgressDialog();
                            }
                        },this));
    }

    @Override
    public void getMeetingListFromServer(final int pageNo) {
        getCompositeDisposable().add(
                getDataManager().getUserId()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .flatMap(new Function<Integer, ObservableSource<WCClubMeetingBean>>() {
                            @Override
                            public ObservableSource<WCClubMeetingBean> apply(@NonNull Integer userId) throws Exception {
                                if (userId == NULL_INDEX){
                                    getMvpView().showToast("用户没有登录");
                                    getMvpView().backToLoginActivity();
                                    return Observable.empty();
                                }
                                getMvpView().showProgressDialog("加载中...", false);

                                HashMap<String, Object> params = new HashMap<>();

                                params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
                                params.put("page_no", 1);
                                params.put("sponsor_id", userId);

                                return getDataManager().getMyMeeting(params)
                                        .subscribeOn(getSchedulerProvider().io());
                            }
                        }).observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<WCClubMeetingBean>() {
                            @Override
                            public void accept(@NonNull WCClubMeetingBean bean) throws Exception {
                                getMvpView().addMeetingList(bean.getMeeting(), bean.getHas_more() == 1);
                                getMvpView().hideProgressDialog();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                WCManageMeetingListPresenter.this.accept(throwable);
                                if (isViewAttachView()){
                                    getMvpView().loadFail();
                                }
                            }
                        }));
    }
}
