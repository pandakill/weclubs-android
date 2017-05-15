package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.bean.WCClubMissionBean;
import com.mm.weclubs.data.bean.WCRequestParamBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/16 上午1:22
 * 描述:
 */

public interface WCClubMissionService {

    String GET_MY_MISSION = "/manage/club_mission/get_my_mission";

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCClubMissionBean>> getMyMission(@Url String url, @Body WCRequestParamBean requestBean);
}
