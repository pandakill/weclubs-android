package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.network.bean.WCClubNotifyBean;
import com.mm.weclubs.data.network.bean.WCNotifyCheckStatusBean;
import com.mm.weclubs.data.network.bean.WCRequestParamBean;
import com.mm.weclubs.data.network.bean.WCResponseParamBean;
import com.mm.weclubs.data.network.pojo.WCManageNotifyInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午11:52
 * 描述:
 */

public interface WCClubNotifyService {

    String GET_MY_NOTIFY = "/manage/club_notify/get_my_notify";
    String GET_MY_NOTIFY_DETAIL = "/manage/club_notify/get_my_notify_detail";
    String GET_NOTIFY_CHECK_STATUS = "/manage/club_notify/get_notify_check_status";

    @POST(GET_MY_NOTIFY)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCClubNotifyBean>> getMyNotify(@Body WCRequestParamBean requestBean);

    @POST(GET_MY_NOTIFY_DETAIL)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCManageNotifyInfo>> getMyNotifyDetail(@Body WCRequestParamBean requestBean);

    @POST(GET_NOTIFY_CHECK_STATUS)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCNotifyCheckStatusBean>> getNotifyCheckStatusList(@Body WCRequestParamBean requestBean);
}
