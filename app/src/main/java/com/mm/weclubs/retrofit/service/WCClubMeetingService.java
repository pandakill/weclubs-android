package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.bean.WCClubMeetingBean;
import com.mm.weclubs.data.bean.WCRequestParamBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCManageMeetingDetailInfo;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:47
 * 描述:
 */

public interface WCClubMeetingService {

    String GET_MY_MEETING = "/manage/club_meeting/get_my_meeting";
    String GET_MY_MEETING_DETAIL = "/manage/club_meeting/get_my_meeting_detail";

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCClubMeetingBean>> getMyMeeting(@Url String url, @Body WCRequestParamBean requestBean);

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCManageMeetingDetailInfo>> getMyMeetingDetail(@Url String url, @Body WCRequestParamBean requestBean);
}
