package com.mm.weclubs.app.manage.meeting;

import android.content.Context;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.bean.WCClubMeetingBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;
import com.mm.weclubs.datacenter.WCUserDataCenter;
import com.mm.weclubs.retrofit.WCServiceFactory;
import com.mm.weclubs.retrofit.service.WCClubMeetingService;
import com.mm.weclubs.util.WCLog;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午6:54
 * 描述:
 */

public class WCManageMeetingPresenter extends BasePresenter<WCManageMeetingView> {

    private WCClubMeetingService mClubMeetingService;

    public WCManageMeetingPresenter(Context context) {
        super(context);

        mClubMeetingService = WCServiceFactory.getClubMeetingService();
    }

    @Override
    protected void initLog() {
        log = new WCLog(WCManageMeetingPresenter.class);
    }

    public void getMeetingListFromServer(int pageNo) {

        WCUserInfoInfo userInfoInfo = WCUserDataCenter.getInstance(mContext.getApplicationContext()).getCurrentUserInfo();
        if (userInfoInfo == null) {
            getMvpView().showToast("用户没有");
            getMvpView().backToLoginActivity();
            return;
        }

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
        params.put("page_no", pageNo);
        params.put("sponsor_id", WCUserDataCenter.getInstance(mContext.getApplicationContext()).getCurrentUserInfo().getUser_id());

        mClubMeetingService.getMyMeeting(WCClubMeetingService.GET_MY_MEETING,
                mHttpParamsPresenter.initRequestParam(mContext, params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCClubMeetingBean>>() {
                    @Override
                    public void onCompleted() {
                        log.d("getMyMeeting：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.d("getMyMeeting：onError");
                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCClubMeetingBean> object) {
                        log.d("getMyMeeting：onNext = " + object.toString());

                        if (object.getResult_code() == 2000) {
                            if (pageNo == 1) {
                                getMvpView().refreshMeetingList(object.getData().getMeeting());
                            } else {
                                getMvpView().addMeetingList(object.getData().getMeeting(), object.getData().getHas_more() == 1);
                            }
                        } else {
                            getMvpView().showToast(object.getResult_msg());

                            checkResult(object);
                        }

                        getMvpView().hideProgressDialog();
                    }
                });
    }
}
