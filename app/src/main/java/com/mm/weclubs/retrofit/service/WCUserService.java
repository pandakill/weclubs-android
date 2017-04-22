package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.bean.WCRequestParamBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

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

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCUserInfoInfo>> login(@Url String url, @Body WCRequestParamBean requestBean);

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCUserInfoInfo>> getUserInfo(@Url String url, @Body WCRequestParamBean requestBean);

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCUserInfoInfo>> register(@Url String url, @Body WCRequestParamBean requestBean);

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCUserInfoInfo>> changePassword(@Url String url, @Body WCRequestParamBean requestBean);

    @POST
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean> updateSchoolInfo(@Url String url, @Body WCRequestParamBean requestBean);
}
