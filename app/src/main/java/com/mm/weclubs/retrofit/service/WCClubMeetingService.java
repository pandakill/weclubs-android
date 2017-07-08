package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.network.bean.WCClubMeetingBean;
import com.mm.weclubs.data.network.bean.WCMeetingParticipationBean;
import com.mm.weclubs.data.network.bean.WCRequestParamBean;
import com.mm.weclubs.data.network.bean.WCResponseParamBean;
import com.mm.weclubs.data.network.pojo.WCManageMeetingDetailInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:47
 * 描述:
 */

public interface WCClubMeetingService {

    String GET_MEETING_PARTICIPATION = "/meeting/get_meeting_participation";
    String GET_MY_MEETING = "/manage/club_meeting/get_my_meeting";
    String GET_MY_MEETING_DETAIL = "/manage/club_meeting/get_my_meeting_detail";

    @POST(GET_MY_MEETING)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCClubMeetingBean>> getMyMeeting(@Body WCRequestParamBean requestBean);

    @POST(GET_MY_MEETING_DETAIL)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCManageMeetingDetailInfo>> getMyMeetingDetail(@Body WCRequestParamBean requestBean);

    @POST(GET_MEETING_PARTICIPATION)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCMeetingParticipationBean>> getMeetingParticipation(@Body WCRequestParamBean requestBean);
}
