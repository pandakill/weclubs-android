package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.bean.WCMeetingListBean;
import com.mm.weclubs.data.bean.WCMissionListBean;
import com.mm.weclubs.data.bean.WCNotifyListBean;
import com.mm.weclubs.data.bean.WCRequestParamBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCDynamicBaseInfo;
import com.mm.weclubs.data.pojo.WCMeetingDetailInfo;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:20
 * 描述: 动态相关的service
 */

public interface WCDynamicService {

    String GET_DYNAMIC_LIST = "/dynamic/get_dynamic_list";
    String GET_DYNAMIC_DETAIL = "/dynamic/get_dynamic_detail";

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCMeetingListBean>> getMeetingList(@Url String url, @Body WCRequestParamBean requestBean);

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCMeetingDetailInfo>> getMeetingDetail(@Url String url, @Body WCRequestParamBean requestBean);

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCMissionListBean>> getMissionList(@Url String url, @Body WCRequestParamBean requestBean);

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCNotifyListBean>> getNotifyList(@Url String url, @Body WCRequestParamBean requestBean);

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCDynamicBaseInfo>> getDynamicDetail(@Url String url, @Body WCRequestParamBean requestParam);
}
