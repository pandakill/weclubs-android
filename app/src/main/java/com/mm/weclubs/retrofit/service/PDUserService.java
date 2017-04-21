package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.data.pojo.LoginBean;
import com.mm.weclubs.data.pojo.RequestBean;
import com.mm.weclubs.data.pojo.ResponseBean;
import com.mm.weclubs.data.pojo.UserBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/10 下午4:27
 * 描述: 用户的service类
 */

public interface PDUserService {

    @Headers("Content-Type: application/json")
    @POST
    Observable<ResponseBean<LoginBean>> login(@Url String url, @Body RequestBean requestBean);

    @Headers("Content-Type: application/json")
    @POST
    Call<ResponseBean<UserBean>> getUserInfo(@Url String url, @Body RequestBean requestBean);

}
