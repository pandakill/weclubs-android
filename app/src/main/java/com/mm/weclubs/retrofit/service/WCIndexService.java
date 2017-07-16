package com.mm.weclubs.retrofit.service;

import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.network.bean.WCIndexClubBean;
import com.mm.weclubs.data.network.bean.WCIndexDataBean;
import com.mm.weclubs.data.network.bean.WCRequestParamBean;
import com.mm.weclubs.data.network.bean.WCResponseParamBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 文 件 名: WCIndexService
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/10 22:13
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface WCIndexService {
    String GET_INDEX_CLUB = "/service/get_index_club";
    String GET_INDEX_DATA = "/service/get_index_data";

    @POST(GET_INDEX_CLUB)
    @Headers(WCConfigConstants.CONTENT_TYPE_JSON)
    Observable<WCResponseParamBean<WCIndexClubBean>> getIndexClub(@Body WCRequestParamBean requestBean);

    @POST(GET_INDEX_DATA)
    Observable<WCResponseParamBean<WCIndexDataBean>> getIndexData(@Body WCRequestParamBean requestBean);
}
