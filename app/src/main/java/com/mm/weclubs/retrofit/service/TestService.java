package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.data.bean.WCRequestParamBean;
import com.mm.weclubs.data.bean.WCResponseParamBean;
import com.mm.weclubs.data.pojo.WCLoginBean;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;

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
    Observable<WCResponseParamBean<WCLoginBean>> login(@Url String url, @Body WCRequestParamBean requestBean);

    @Headers("Content-Type: application/json")
    @POST
    Call<WCResponseParamBean<WCUserInfoInfo>> getUserInfo(@Url String url, @Body WCRequestParamBean requestBean);
}
