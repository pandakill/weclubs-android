package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.network.bean.WCMeetingListBean;
import com.mm.weclubs.data.network.bean.WCMissionListBean;
import com.mm.weclubs.data.network.bean.WCNotifyListBean;
import com.mm.weclubs.data.network.bean.WCRequestParamBean;
import com.mm.weclubs.data.network.bean.WCResponseParamBean;
import com.mm.weclubs.data.network.pojo.WCMeetingDetailInfo;
import com.mm.weclubs.data.network.pojo.WCMissionDetailInfo;
import com.mm.weclubs.data.network.pojo.WCNotifyListInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:20
 * 描述: 动态相关的service
 */

public interface WCDynamicService {

    String GET_DYNAMIC_LIST = "/dynamic/get_dynamic_list";
    String GET_DYNAMIC_DETAIL = "/dynamic/get_dynamic_detail";
    String SET_DYNAMIC_STATUS = "/dynamic/set_dynamic_status";

    @POST(GET_DYNAMIC_LIST)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCMeetingListBean>> getMeetingList(@Body WCRequestParamBean requestBean);

    @POST(GET_DYNAMIC_DETAIL)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCMeetingDetailInfo>> getMeetingDetail(@Body WCRequestParamBean requestBean);

    @POST(GET_DYNAMIC_LIST)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCMissionListBean>> getMissionList(@Body WCRequestParamBean requestBean);

    @POST(GET_DYNAMIC_DETAIL)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCMissionDetailInfo>> getMissionDetail(@Body WCRequestParamBean requestBean);

    @POST(GET_DYNAMIC_LIST)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCNotifyListBean>> getNotifyList(@Body WCRequestParamBean requestBean);

    @POST(GET_DYNAMIC_DETAIL)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCNotifyListInfo>> getNotifyDetail(@Body WCRequestParamBean requestBean);

    //  设置任务的状态
    @POST(SET_DYNAMIC_STATUS)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<Object>> setMissionStatus(@Body WCRequestParamBean requestBean);
}
