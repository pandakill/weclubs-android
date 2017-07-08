package com.mm.weclubs.app.meeting_list;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.bean.WCMeetingListBean;
import com.mm.weclubs.data.network.pojo.WCMeetingListInfo;
import com.mm.weclubs.util.rx.SchedulerProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:17
 * 描述:
 */

public class WCMeetingListPresenter<V extends WCMeetingListContract.View> extends BasePresenter<V>
    implements WCMeetingListContract.Presenter<V>{

    @Inject
    public WCMeetingListPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void refresh(long clubId) {
        getMvpView().showProgressDialog("加载中...", false);

        Map<String, Object> params = new HashMap<>();

        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
        params.put("page_no", 1);
        params.put("club_id", clubId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_MEETING);

        getCompositeDisposable().add(getDataManager().getMeetingList(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCMeetingListBean>() {
                    @Override
                    public void accept(@NonNull WCMeetingListBean wcMeetingListBean) throws Exception {
                        getMvpView().refreshMeetingList(wcMeetingListBean.getMeeting());
                        getMvpView().hideProgressDialog();
                    }
                },this));
    }

    @Override
    public void getMeetingListFromServer(long clubId, final int pageNo) {

        getMvpView().showProgressDialog("加载中...", false);

        Map<String, Object> params = new HashMap<>();

        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
        params.put("page_no", pageNo);
        params.put("club_id", clubId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_MEETING);

        getCompositeDisposable().add(getDataManager().getMeetingList(params)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(new Consumer<WCMeetingListBean>() {
                @Override
                public void accept(@NonNull WCMeetingListBean wcMeetingListBean) throws Exception {
                    getMvpView().addMeetingList(wcMeetingListBean.getMeeting(), wcMeetingListBean.getHas_more() == 1);
                    getMvpView().hideProgressDialog();
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    WCMeetingListPresenter.this.accept(throwable);
                    if (isViewAttachView()){
                        //加载更多失败
                        getMvpView().loadFail();
                    }
                }
            }));
    }

    @Override
    public void setMeetingConfirm(long meetingId,final int position,final WCMeetingListInfo meetingListInfo) {

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("dynamic_id", meetingId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_MEETING);
        params.put("status", "confirm");

        getCompositeDisposable().add(getDataManager().setMissionStatus(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        getMvpView().hideProgressDialog();
                        getMvpView().showToast("任务确认成功");
                        if (meetingListInfo != null) {
                            meetingListInfo.setConfirm_join(1);
                            getMvpView().notifyChangeList(meetingListInfo, position);
                        }
                    }
                },this));
    }
}
