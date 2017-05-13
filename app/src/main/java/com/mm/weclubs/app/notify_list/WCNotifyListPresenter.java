package com.mm.weclubs.app.notify_list;

import android.content.Context;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.bean.WCNotifyListBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCNotifyListInfo;
import com.mm.weclubs.retrofit.WCServiceFactory;
import com.mm.weclubs.retrofit.service.WCDynamicService;
import com.mm.weclubs.util.WCLog;

import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 下午5:33
 * 描述:
 */

public class WCNotifyListPresenter extends BasePresenter<WCNotifyListView> {

    private WCDynamicService mDynamicService = null;

    public WCNotifyListPresenter(Context context) {
        super(context);

        mDynamicService = WCServiceFactory.getDynamicService();
    }

    @Override
    protected void initLog() {
        log = new WCLog(WCNotifyListPresenter.class);
    }

    public void getNotifyFromServer(long clubId, int pageNo) {

        getMvpView().showProgressDialog("注册中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);
        params.put("page_no", pageNo);
        params.put("club_id", clubId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_NOTIFY);

        mDynamicService.getNotifyList(WCDynamicService.GET_DYNAMIC_LIST,
                mHttpParamsPresenter.initRequestParam(mContext, params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCNotifyListBean>>() {
                    @Override
                    public void onCompleted() {
                        log.d("getNotifyFromServer：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.d("getNotifyFromServer：onError");
                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCNotifyListBean> object) {
                        log.d("getNotifyFromServer：onNext = " + object.toString());

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

    public void getNotifyDetail(long notifyId) {

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();

        params.put("dynamic_id", notifyId);
        params.put("dynamic_type", WCConstantsUtil.DYNAMIC_TYPE_NOTIFY);

        mDynamicService.getNotifyDetail(WCDynamicService.GET_DYNAMIC_DETAIL,
                mHttpParamsPresenter.initRequestParam(mContext, params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCNotifyListInfo>>() {
                    @Override
                    public void onCompleted() {
                        log.d("getNotifyDetail：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.d("getNotifyDetail：onError");
                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCNotifyListInfo> object) {
                        log.d("getNotifyDetail：onNext = " + object.toString());

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
