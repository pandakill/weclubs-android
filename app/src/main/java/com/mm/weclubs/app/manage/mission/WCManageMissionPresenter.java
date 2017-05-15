package com.mm.weclubs.app.manage.mission;

import android.content.Context;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingPresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.bean.WCClubMissionBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;
import com.mm.weclubs.datacenter.WCUserDataCenter;
import com.mm.weclubs.retrofit.WCServiceFactory;
import com.mm.weclubs.retrofit.service.WCClubMissionService;
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

public class WCManageMissionPresenter extends BasePresenter<WCManageMissionView> {

    private WCClubMissionService mClubMissionService;

    public WCManageMissionPresenter(Context context) {
        super(context);

        mClubMissionService = WCServiceFactory.getClubMissionService();
    }

    @Override
    protected void initLog() {
        log = new WCLog(WCManageMeetingPresenter.class);
    }

    public void getMissionListFromServer(int pageNo) {

        WCUserInfoInfo userInfoInfo = WCUserDataCenter.getInstance(mContext.getApplicationContext()).getCurrentUserInfo();
        if (userInfoInfo == null) {
            getMvpView().showToast("用户没有登录");
            getMvpView().backToLoginActivity();
            return;
        }

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
        params.put("page_no", pageNo);
        params.put("sponsor_id", userInfoInfo.getUser_id());

        mClubMissionService.getMyMission(WCClubMissionService.GET_MY_MISSION,
                mHttpParamsPresenter.initRequestParam(mContext, params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCClubMissionBean>>() {
                    @Override
                    public void onCompleted() {
                        log.d("getMissionListFromServer：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.d("getMissionListFromServer：onError");
                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCClubMissionBean> object) {
                        log.d("getMissionListFromServer：onNext = " + object.toString());

                        if (object.getResult_code() == 2000) {
                            if (pageNo == 1) {
                                getMvpView().refreshMissionList(object.getData().getMission());
                            } else {
                                getMvpView().addMissionList(object.getData().getMission(), object.getData().getHas_more() == 1);
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
