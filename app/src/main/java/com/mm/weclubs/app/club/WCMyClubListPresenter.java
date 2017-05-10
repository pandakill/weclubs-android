package com.mm.weclubs.app.club;

import android.content.Context;

import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.data.bean.WCMyClubsBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCMyClubListInfo;
import com.mm.weclubs.retrofit.WCServiceFactory;
import com.mm.weclubs.retrofit.service.WCClubService;
import com.mm.weclubs.util.WCLog;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午5:03
 * 描述:
 */

public class WCMyClubListPresenter extends BasePresenter<WCMyClubListView> {

    private WCClubService mClubService = null;

    public WCMyClubListPresenter(Context context) {
        super(context);

        mClubService = WCServiceFactory.getClubService();
    }

    @Override
    protected void initLog() {
        log = new WCLog(WCMyClubListPresenter.class);
    }

    public void getMyClubsList(int pageNo) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("page_no", pageNo);
        params.put("size", 20);

        mClubService.getMyClubs(WCClubService.GET_MY_CLUBS, mHttpParamsPresenter.initRequestParam(mContext, params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCMyClubsBean>>() {
                    @Override
                    public void onCompleted() {
                        log.d("getMyClubsList：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.d("getMyClubsList：onError");
                        e.printStackTrace();
                        getMvpView().showToast(e.getMessage());
                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCMyClubsBean> responseParamBean) {
                        log.d("getMyClubsList：responseParamBean = " + responseParamBean.toString());
                        if (responseParamBean.getResult_code() == 2000) {
                            ArrayList<WCMyClubListInfo> list = responseParamBean.getData().getClub();

                            if (pageNo == 1) {
                                getMvpView().refreshClubList(list);
                            } else {
                                getMvpView().addClubList(list, responseParamBean.getData().getHas_more() == 1);
                            }
                        } else {
                            getMvpView().showToast(responseParamBean.getResult_msg());
                        }

                        getMvpView().hideProgressDialog();

                    }
                });
    }
}
