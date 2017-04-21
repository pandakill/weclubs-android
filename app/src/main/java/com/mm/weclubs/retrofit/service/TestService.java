package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.data.model.WCRequestParamModel;
import com.mm.weclubs.data.model.WCResponseParamModel;
import com.mm.weclubs.data.pojo.LoginBean;
import com.mm.weclubs.data.pojo.UserBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/7 下午2:32
 * 描述:
 */
public interface TestService {

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST
    Observable<WCResponseParamModel<LoginBean>> login(@Url String url, @Body WCRequestParamModel requestBean);

    @Headers("Content-Type: application/json")
    @POST
    Call<WCResponseParamModel<UserBean>> getUserInfo(@Url String url, @Body WCRequestParamModel requestBean);
}
