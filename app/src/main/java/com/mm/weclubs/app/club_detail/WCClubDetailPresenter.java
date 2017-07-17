package com.mm.weclubs.app.club_detail;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.pojo.WCClubDetail;
import com.mm.weclubs.util.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

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
    public void loadDetail(long club_id) {
        if (checkUserDirty()){
            getMvpView().backToLoginActivity();
            return;
        }

        getMvpView().showProgressDialog("加载数据中",true);

        Map<String,Object> params = new HashMap<>();
        params.put("club_id",club_id);
        getCompositeDisposable().add(getDataManager().getClubDetail(params)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(new Consumer<WCClubDetail>() {
                @Override
                public void accept(@NonNull WCClubDetail wcClubDetail) throws Exception {
                    getMvpView().hideProgressDialog();
                    getMvpView().setSlogan(wcClubDetail.getSlogan());
                    getMvpView().setAvatar(wcClubDetail.getAvatar_url());
                    getMvpView().setAttr(wcClubDetail.getAttribution());
                    getMvpView().setClubName(wcClubDetail.getClub_name());
                    getMvpView().setHonorList(wcClubDetail.getClub_honor());
                    getMvpView().setLevel(wcClubDetail.getClub_level());
                    getMvpView().setMemberList(wcClubDetail.getMember());
                }
            },this));

    }

    @Override
    public void apply() {

    }

    @Override
    public void showQRCode() {
        getMvpView().showQRView("");
    }

    @Override
    public void clickMember(int viewId) {

    }
}
