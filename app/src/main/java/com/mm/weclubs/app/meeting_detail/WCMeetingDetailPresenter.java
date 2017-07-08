package com.mm.weclubs.app.meeting_detail;

import com.blankj.utilcode.utils.EmptyUtils;
import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.bean.WCCommentListBean;
import com.mm.weclubs.data.network.pojo.WCMeetingDetailInfo;
import com.mm.weclubs.util.rx.SchedulerProvider;
import com.socks.library.KLog;

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

public class WCMeetingDetailPresenter<V extends WCMeetingDetailContract.View> extends BasePresenter<V>
    implements WCMeetingDetailContract.Presenter<V>{

    @Inject
    public WCMeetingDetailPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void getCommentList(String type, long sourceId, final int pageNo) {
        if (EmptyUtils.isEmpty(type)) {
            KLog.e("type类型不能为空");
            return;
        }

        if (sourceId <= 0) {
            KLog.e("sourceId不能小于等于0");
            return;
        }

        if (!WCConstantsUtil.DYNAMIC_TYPE_MEETING.equals(type)
                && !WCConstantsUtil.DYNAMIC_TYPE_ACTIVITY.equals(type)
                && !WCConstantsUtil.DYNAMIC_TYPE_MISSION.equals(type)
                && !WCConstantsUtil.DYNAMIC_TYPE_NOTIFY.equals(type)) {
            KLog.e("传入的 type 类型不正确，传入的类型为：" + type);
            return;
        }

        getMvpView().showProgressDialog("加载中...", false);

        Map<String, Object> params = new HashMap<>();
        params.put("source_type", type);
        params.put("source_id", sourceId);
        params.put("page_no", pageNo);
        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);

        getCompositeDisposable().add(getDataManager()
                .getCommentList(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCCommentListBean>() {
                    @Override
                    public void accept(@NonNull WCCommentListBean wcCommentListBean) throws Exception {
                        if (pageNo == 1){
                            getMvpView().refreshCommentList(wcCommentListBean.getComment());
                        }else{
                            getMvpView().addCommentList(wcCommentListBean.getComment(),wcCommentListBean.getHas_more() == 1);
                        }
                        getMvpView().hideProgressDialog();
                    }
                },this));
    }

    public void getMeetingDetail(long meetingId) {

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("dynamic_id", meetingId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_MEETING);

        getCompositeDisposable().add(getDataManager().getMeetingDetail(params)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<WCMeetingDetailInfo>() {
                    @Override
                    public void accept(@NonNull WCMeetingDetailInfo info) throws Exception {
                        getMvpView().hideProgressDialog();
                        getMvpView().getMeetingDetailSuccess(info);
                    }
                },this));
    }

    public void setMeetingConfirm(long meetingId) {
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
                    }
                },this));
    }

}
