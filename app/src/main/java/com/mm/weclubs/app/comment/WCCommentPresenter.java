package com.mm.weclubs.app.comment;

import android.content.Context;

import com.blankj.utilcode.utils.EmptyUtils;
import com.mm.weclubs.app.base.BasePresenter;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.bean.WCCommentListBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCCommentListInfo;
import com.mm.weclubs.retrofit.WCServiceFactory;
import com.mm.weclubs.retrofit.service.WCCommentService;
import com.mm.weclubs.util.WCLog;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午4:18
 * 描述:
 */

public class WCCommentPresenter extends BasePresenter<WCCommentView> {

    private WCCommentService mCommentService;

    public WCCommentPresenter(Context context) {
        super(context);

        mCommentService = WCServiceFactory.getCommentService();
    }

    @Override
    protected void initLog() {
        log = new WCLog(WCCommentPresenter.class);
    }

    public void getCommentList(String type, long sourceId, int pageNo) {

        if (EmptyUtils.isEmpty(type)) {
            log.e("type类型不能为空");
            return;
        }

        if (sourceId <= 0) {
            log.e("sourceId不能小于等于0");
            return;
        }

        if (!WCConstantsUtil.DYNAMIC_TYPE_MEETING.equals(type)
                && !WCConstantsUtil.DYNAMIC_TYPE_ACTIVITY.equals(type)
                && !WCConstantsUtil.DYNAMIC_TYPE_MISSION.equals(type)
                && !WCConstantsUtil.DYNAMIC_TYPE_NOTIFY.equals(type)) {
            log.e("传入的 type 类型不正确，传入的类型为：" + type);
            return;
        }

        getMvpView().showProgressDialog("加载中...", false);

        HashMap<String, Object> params = new HashMap<>();
        params.put("source_type", type);
        params.put("source_id", sourceId);
        params.put("page_no", pageNo);
        params.put("size", WCConfigConstants.ONE_PAGE_SIZE);

        mCommentService.getCommentList(WCCommentService.GET_COMMENT_LIST,
                mHttpParamsPresenter.initRequestParam(mContext.getApplicationContext(), params))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WCResponseParamBean<WCCommentListBean>>() {
                    @Override
                    public void onCompleted() {
                        log.d("register：onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        log.d("register：onError");

                        getMvpView().hideProgressDialog();
                    }

                    @Override
                    public void onNext(WCResponseParamBean<WCCommentListBean> userInfo) {
                        log.d("register：onNext = " + userInfo.toString());
                        if (userInfo.getResult_code() == 2000) {

                            ArrayList<WCCommentListInfo> list = userInfo.getData().getComment();

                            if (pageNo == 1) {
                                getMvpView().refreshCommentList(list);
                            } else {
                                getMvpView().addCommentList(list, userInfo.getData().getHas_more() == 1);
                            }
                        } else {
                            getMvpView().showToast(userInfo.getResult_msg());

                            checkResult(userInfo);
                        }

                        getMvpView().hideProgressDialog();
                    }
                });
    }
}
