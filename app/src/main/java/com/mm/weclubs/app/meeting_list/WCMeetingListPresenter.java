package com.mm.weclubs.app.meeting_list;

import android.content.Context;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.app.mission_list.WCMissionListPresenter;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.bean.WCMeetingListBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.retrofit.WCServiceFactory;
import com.mm.weclubs.retrofit.service.WCDynamicService;
import com.mm.weclubs.util.WCLog;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:17
 * 描述:
 */

public class WCMeetingListPresenter extends BasePresenter<WCMeetingListView> {

    private WCDynamicService mDynamicService = null;

    public WCMeetingListPresenter(Context context) {
        super(context);

        mDynamicService = WCServiceFactory.getDynamicService();
    }

    @Override
    protected void initLog() {
        log = new WCLog(WCMissionListPresenter.class);
    }

    public void getMeetingListFromServer(long clubId, int pageNo) {

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("size", 20);
        params.put("page_no", pageNo);
        params.put("club_id", clubId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_MEETING);

        mDynamicService.getMeetingList(WCDynamicService.GET_DYNAMIC_LIST,
                mHttpParamsPresenter.initRequestParam(mContext, params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCMeetingListBean>>() {
                    @Override
                    public void onCompleted() {
                        log.d("getMyDynamicFromServer：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.d("getMyDynamicFromServer：onError");
                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCMeetingListBean> object) {
                        log.d("getMyDynamicFromServer：onNext = " + object.toString());

                        if (object.getResult_code() == 2000) {
                            if (pageNo == 1) {
                                getMvpView().refreshMeetingList(object.getData().getMeeting());
                            } else {
                                getMvpView().addMeetingList(object.getData().getMeeting(), object.getData().getHas_more() == 1);
                            }
                        } else {
                            getMvpView().showToast(object.getResult_msg());
                        }

                        getMvpView().hideProgressDialog();
                    }
                });
    }
}
