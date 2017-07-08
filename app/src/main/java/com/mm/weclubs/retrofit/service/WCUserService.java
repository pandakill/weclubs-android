package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.network.bean.WCRequestParamBean;
import com.mm.weclubs.data.network.bean.WCResponseParamBean;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21  下午10:49
 * 描述:  用户相关的 API
 */
public interface WCUserService {

    String URL_REGISTER = "/user/register";
    String URL_LOGIN = "/user/login";
    String URL_GET_USER_INFO = "/user/get_user_info";
    String URL_CHANGE_PASSWORD = "/user/change_password";
    String URL_UPDATE_SCHOOL_INFO = "/user/update_school_info";

    @POST(URL_LOGIN)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCUserInfoInfo>> login(@Body WCRequestParamBean requestBean);

    @POST(URL_GET_USER_INFO)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCUserInfoInfo>> getUserInfo(@Body WCRequestParamBean requestBean);

    @POST(URL_REGISTER)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCUserInfoInfo>> register(@Body WCRequestParamBean requestBean);

    @POST(URL_CHANGE_PASSWORD)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCUserInfoInfo>> changePassword(@Body WCRequestParamBean requestBean);

    @POST(URL_UPDATE_SCHOOL_INFO)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean> updateSchoolInfo(@Body WCRequestParamBean requestBean);
}
