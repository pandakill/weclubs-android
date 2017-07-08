package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.network.bean.WCCommentListBean;
import com.mm.weclubs.data.network.bean.WCRequestParamBean;
import com.mm.weclubs.data.network.bean.WCResponseParamBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午4:21
 * 描述:  评论相关的service
 */

public interface WCCommentService {

    String GET_COMMENT_LIST = "/comment/get_comment_list";

    @POST(GET_COMMENT_LIST)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCCommentListBean>> getCommentList(@Body WCRequestParamBean requestBean);
}
