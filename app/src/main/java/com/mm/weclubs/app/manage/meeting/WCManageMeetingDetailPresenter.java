package com.mm.weclubs.app.manage.meeting;

import com.blankj.utilcode.utils.EmptyUtils;
import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.DataManager;
import com.mm.weclubs.data.network.bean.WCCommentListBean;
import com.mm.weclubs.data.network.pojo.WCManageMeetingDetailInfo;
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
 * 创建时间: 2017/5/15 下午6:54
 * 描述:
 */

public class WCManageMeetingDetailPresenter<V extends WCManageMeetingDetailContract.View> extends BasePresenter<V>
    implements WCManageMeetingDetailContract.Presenter<V>{

    @Inject
    public WCManageMeetingDetailPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
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

        Map<String, Object> params = new HashMap<>();

        params.put("meeting_id", meetingId);

        getCompositeDisposable().add(getDataManager().getMyMeetingDetail(params)
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(new Consumer<WCManageMeetingDetailInfo>() {
                @Override
                public void accept(@NonNull WCManageMeetingDetailInfo wcManageMeetingDetailInfo) throws Exception {
                    getMvpView().getMeetingDetailSuccess(wcManageMeetingDetailInfo);
                    getMvpView().hideProgressDialog();
                }
            },this));
    }
}
