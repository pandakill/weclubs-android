package com.mm.weclubs.app.club;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.bean.WCMyClubsBean;
import com.mm.weclubs.data.network.pojo.WCMyClubListInfo;
import com.mm.weclubs.util.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午5:03
 * 描述:
 */

public class WCMyClubListPresenter<V extends WCMyClubListContract.View> extends BasePresenter<V>
    implements WCMyClubListContract.Presenter<V>{

    @Inject
    public WCMyClubListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void refresh() {
        Map<String, Object> params = new HashMap<>();
        params.put("page_no", 1);
        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);

        getCompositeDisposable().add(getDataManager().getMyClubs(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCMyClubsBean>() {
                    @Override
                    public void accept(@NonNull WCMyClubsBean wcMyClubsBean) throws Exception {
                        getMvpView().setClubList(wcMyClubsBean.getClub());
                        getMvpView().hideProgressDialog();
                    }
                },this));
    }

    @Override
    public void getMyClubsList(final int pageNo) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("page_no", pageNo);
        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);

        getCompositeDisposable().add(getDataManager().getMyClubs(params)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(new Consumer<WCMyClubsBean>() {
                @Override
                public void accept(@NonNull WCMyClubsBean wcMyClubsBean) throws Exception {
                    ArrayList<WCMyClubListInfo> list = wcMyClubsBean.getClub();
                    getMvpView().addClubList(list, wcMyClubsBean.getHas_more() == 1);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    WCMyClubListPresenter.this.accept(throwable);
                    if (isViewAttachView()){
                        //加载更多失败
                        getMvpView().loadFail();
                    }
                }
            }));
    }
}
