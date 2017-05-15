package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.bean.WCClubNotifyBean;
import com.mm.weclubs.data.bean.WCRequestParamBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCManageNotifyInfo;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午11:52
 * 描述:
 */

public interface WCClubNotifyService {

    String GET_MY_NOTIFY = "/manage/club_notify/get_my_notify";
    String GET_MY_NOTIFY_DETAIL = "/manage/club_notify/get_my_notify_detail";

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCClubNotifyBean>> getMyNotify(@Url String url, @Body WCRequestParamBean requestBean);

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCManageNotifyInfo>> getMyNotifyDetail(@Url String url, @Body WCRequestParamBean requestBean);
}
