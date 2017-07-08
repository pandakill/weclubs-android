package com.mm.weclubs.app.manage.meeting;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.bean.WCMeetingParticipationBean;
import com.mm.weclubs.util.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午6:54
 * 描述:
 */

public class WCMeetingParticipationDetailPresenter<V extends WCMeetingParticipationDetailContract.View>
        extends BasePresenter<V> implements WCMeetingParticipationDetailContract.Presenter<V>{

    @Inject
    public WCMeetingParticipationDetailPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    /**
     * 读取会议参与详情
     *
     * @param meetingId 会议id
     */
    @Override
    public void getMeetingParticipation(long meetingId) {

        getMvpView().showProgressDialog("加载中...", false);

        Map<String, Object> params = new HashMap<>();

        params.put("meeting_id", meetingId);

        getCompositeDisposable().add(getDataManager().getMeetingParticipation(params)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(new Consumer<WCMeetingParticipationBean>() {
                @Override
                public void accept(@NonNull WCMeetingParticipationBean wcMeetingParticipationBean) throws Exception {
                    getMvpView().getMeetingParticipationSuccess(wcMeetingParticipationBean);
                    getMvpView().hideProgressDialog();
                }
            },this));
    }
}
