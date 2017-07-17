package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.network.bean.WCMyClubsBean;
import com.mm.weclubs.data.network.bean.WCRequestParamBean;
import com.mm.weclubs.data.network.bean.WCResponseParamBean;
import com.mm.weclubs.data.network.pojo.WCClubDetail;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午5:05
 * 描述:
 */

public interface WCClubService {

    String GET_MY_CLUBS = "/club/get_my_clubs";
    String GET_CLUB_DETAIL = "/club/get_club_detail";

    @POST(GET_MY_CLUBS)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCMyClubsBean>> getMyClubs(@Body WCRequestParamBean requestBean);

    @POST(GET_CLUB_DETAIL)
    Observable<WCResponseParamBean<WCClubDetail>> getClubDetail(@Body WCRequestParamBean requestBean);
}
