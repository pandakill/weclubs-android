package com.mm.weclubs.app.manage.notify;

import android.content.Context;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.bean.WCClubNotifyBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCManageNotifyInfo;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;
import com.mm.weclubs.datacenter.WCUserDataCenter;
import com.mm.weclubs.retrofit.WCServiceFactory;
import com.mm.weclubs.retrofit.service.WCClubNotifyService;
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

public class WCManageNotifyPresenter extends BasePresenter<WCManageNotifyView> {

    private WCClubNotifyService mClubNotifyService;

    public WCManageNotifyPresenter(Context context) {
        super(context);

        mClubNotifyService = WCServiceFactory.getClubNotifyService();
    }

    @Override
    protected void initLog() {
        log = new WCLog(WCManageNotifyPresenter.class);
    }

    public void getNotifyListFromServer(int pageNo) {

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
        params.put("sponsor_id", WCUserDataCenter.getInstance(mContext.getApplicationContext()).getCurrentUserInfo().getUser_id());

        mClubNotifyService.getMyNotify(WCClubNotifyService.GET_MY_NOTIFY,
                mHttpParamsPresenter.initRequestParam(mContext, params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCClubNotifyBean>>() {
                    @Override
                    public void onCompleted() {
                        log.d("getNotifyListFromServer：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.d("getNotifyListFromServer：onError");
                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCClubNotifyBean> object) {
                        log.d("getNotifyListFromServer：onNext = " + object.toString());

                        if (object.getResult_code() == 2000) {
                            if (pageNo == 1) {
                                getMvpView().refreshNotifyList(object.getData().getNotify());
                            } else {
                                getMvpView().addNotifyList(object.getData().getNotify(), object.getData().getHas_more() == 1);
                            }
                        } else {
                            getMvpView().showToast(object.getResult_msg());

                            checkResult(object);
                        }

                        getMvpView().hideProgressDialog();
                    }
                });
    }

    public void getNotifyDetailFromServer(long notifyId) {

        WCUserInfoInfo userInfoInfo = WCUserDataCenter.getInstance(mContext.getApplicationContext()).getCurrentUserInfo();
        if (userInfoInfo == null) {
            getMvpView().showToast("用户没有登录");
            getMvpView().backToLoginActivity();
            return;
        }

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("notify_id", notifyId);

        mClubNotifyService.getMyNotifyDetail(WCClubNotifyService.GET_MY_NOTIFY_DETAIL,
                mHttpParamsPresenter.initRequestParam(mContext, params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCManageNotifyInfo>>() {
                    @Override
                    public void onCompleted() {
                        log.d("getNotifyDetailFromServer：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        log.d("getNotifyDetailFromServer：onError：" + e.getMessage());
                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCManageNotifyInfo> object) {
                        log.d("getNotifyDetailFromServer：onNext = " + object.toString());

                        if (object.getResult_code() == 2000) {
                            getMvpView().getNotifyDetailSuccess(object.getData());
                        } else {
                            getMvpView().showToast(object.getResult_msg());

                            checkResult(object);
                        }

                        getMvpView().hideProgressDialog();
                    }
                });
    }
}
