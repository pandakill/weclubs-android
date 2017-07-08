package com.mm.weclubs.data.network;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import com.mm.weclubs.BuildConfig;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.network.bean.WCClubMeetingBean;
import com.mm.weclubs.data.network.bean.WCClubMissionBean;
import com.mm.weclubs.data.network.bean.WCClubNotifyBean;
import com.mm.weclubs.data.network.bean.WCCommentListBean;
import com.mm.weclubs.data.network.bean.WCMeetingListBean;
import com.mm.weclubs.data.network.bean.WCMeetingParticipationBean;
import com.mm.weclubs.data.network.bean.WCMissionListBean;
import com.mm.weclubs.data.network.bean.WCMyClubsBean;
import com.mm.weclubs.data.network.bean.WCNotifyCheckStatusBean;
import com.mm.weclubs.data.network.bean.WCNotifyListBean;
import com.mm.weclubs.data.network.bean.WCRequestParamBean;
import com.mm.weclubs.data.network.bean.WCResponseParamBean;
import com.mm.weclubs.data.network.pojo.WCManageMeetingDetailInfo;
import com.mm.weclubs.data.network.pojo.WCManageNotifyInfo;
import com.mm.weclubs.data.network.pojo.WCMeetingDetailInfo;
import com.mm.weclubs.data.network.pojo.WCMissionDetailInfo;
import com.mm.weclubs.data.network.pojo.WCNotifyListInfo;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;
import com.mm.weclubs.di.AppUUid;
import com.mm.weclubs.di.ApplicationContext;
import com.mm.weclubs.di.BaseUrl;
import com.mm.weclubs.di.DeviceSize;
import com.mm.weclubs.retrofit.service.WCClubMeetingService;
import com.mm.weclubs.retrofit.service.WCClubMissionService;
import com.mm.weclubs.retrofit.service.WCClubNotifyService;
import com.mm.weclubs.retrofit.service.WCClubService;
import com.mm.weclubs.retrofit.service.WCCommentService;
import com.mm.weclubs.retrofit.service.WCDynamicService;
import com.mm.weclubs.retrofit.service.WCUserService;
import com.mm.weclubs.util.JsonHelper;
import com.mm.weclubs.util.MD5Util;
import com.socks.library.KLog;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 文 件 名: AppApiHelper
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 21:45
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    private final Retrofit mRetrofit;
    private final OkHttpClient mOkHttpClient;

    private final String mUuid;
    private final String mDeviceSize;
    @Inject
    public AppApiHelper(@ApplicationContext Context context, @BaseUrl String baseUrl
                        , @AppUUid String uuid, @DeviceSize String size) {

        mUuid = uuid;
        mDeviceSize = size;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        initOkHttp(builder);

        mOkHttpClient = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private void initOkHttp(OkHttpClient.Builder builder) {
        builder.connectTimeout(WCConfigConstants.REQUEST_DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder requestBuilder = originalRequest.newBuilder()
                                .header("Content-Type", "application/json;charset=UTF-8")
                                .header("Accept", "application/json")
                                .method(originalRequest.method(), originalRequest.body());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
    }

    @Override
    public Observable<WCUserInfoInfo> login(@NonNull Map<String,Object> params) {
        WCUserService service = mRetrofit.create(WCUserService.class);

        WCRequestParamBean bean = initRequestParam(params);

        return handle(service.login(bean));
    }

    @Override
    public Observable<WCUserInfoInfo> register(@NonNull Map<String, Object> params) {
        WCUserService service = mRetrofit.create(WCUserService.class);

        WCRequestParamBean bean = initRequestParam(params);

        return handle(service.register(bean));
    }

    @Override
    public Observable<WCUserInfoInfo> getUserInfo(@NonNull Map<String, Object> params) {
        WCUserService service = mRetrofit.create(WCUserService.class);

        WCRequestParamBean bean = initRequestParam(params);

        return handle(service.getUserInfo(bean));
    }

    @Override
    public Observable<WCMyClubsBean> getMyClubs(@NonNull Map<String, Object> params) {
        WCClubService service = mRetrofit.create(WCClubService.class);

        WCRequestParamBean bean = initRequestParam(params);

        return handle(service.getMyClubs(bean));
    }

    @Override
    public Observable<WCMeetingListBean> getMeetingList(@NonNull Map<String, Object> params) {
        WCDynamicService service = mRetrofit.create(WCDynamicService.class);

        WCRequestParamBean bean = initRequestParam(params);

        return handle(service.getMeetingList(bean));
    }

    @Override
    public Observable<WCMeetingDetailInfo> getMeetingDetail(@NonNull Map<String, Object> params) {
        WCDynamicService service = mRetrofit.create(WCDynamicService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getMeetingDetail(bean));
    }

    @Override
    public Observable<WCMissionListBean> getMissionList(@NonNull Map<String, Object> params) {
        WCDynamicService service = mRetrofit.create(WCDynamicService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getMissionList(bean));
    }

    @Override
    public Observable<WCMissionDetailInfo> getMissionDetail(@NonNull Map<String, Object> params) {
        WCDynamicService service = mRetrofit.create(WCDynamicService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getMissionDetail(bean));
    }

    @Override
    public Observable<WCNotifyListBean> getNotifyList(@NonNull Map<String, Object> params) {
        WCDynamicService service = mRetrofit.create(WCDynamicService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getNotifyList(bean));
    }

    @Override
    public Observable<WCNotifyListInfo> getNotifyDetail(@NonNull Map<String, Object> params) {
        WCDynamicService service = mRetrofit.create(WCDynamicService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getNotifyDetail(bean));
    }

    @Override
    public Observable<Object> setMissionStatus(@NonNull Map<String, Object> params) {
        WCDynamicService service = mRetrofit.create(WCDynamicService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.setMissionStatus(bean));
    }

    @Override
    public Observable<WCCommentListBean> getCommentList(@NonNull Map<String, Object> params) {
        WCCommentService service = mRetrofit.create(WCCommentService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getCommentList(bean));
    }

    @Override
    public Observable<WCClubNotifyBean> getMyNotify(@NonNull Map<String, Object> params) {
        WCClubNotifyService service = mRetrofit.create(WCClubNotifyService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getMyNotify(bean));
    }

    @Override
    public Observable<WCManageNotifyInfo> getMyNotifyDetail(@NonNull Map<String, Object> params) {
        WCClubNotifyService service = mRetrofit.create(WCClubNotifyService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getMyNotifyDetail(bean));
    }

    @Override
    public Observable<WCNotifyCheckStatusBean> getNotifyCheckStatusList(@NonNull Map<String, Object> params) {
        WCClubNotifyService service = mRetrofit.create(WCClubNotifyService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getNotifyCheckStatusList(bean));
    }

    @Override
    public Observable<WCClubMeetingBean> getMyMeeting(@NonNull Map<String, Object> params) {
        WCClubMeetingService service = mRetrofit.create(WCClubMeetingService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getMyMeeting(bean));
    }

    @Override
    public Observable<WCManageMeetingDetailInfo> getMyMeetingDetail(@NonNull Map<String, Object> params) {
        WCClubMeetingService service = mRetrofit.create(WCClubMeetingService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getMyMeetingDetail(bean));
    }

    @Override
    public Observable<WCMeetingParticipationBean> getMeetingParticipation(@NonNull Map<String, Object> params) {
        WCClubMeetingService service = mRetrofit.create(WCClubMeetingService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getMeetingParticipation(bean));
    }

    @Override
    public Observable<WCClubMissionBean> getMyMission(@NonNull Map<String, Object> params) {
        WCClubMissionService service = mRetrofit.create(WCClubMissionService.class);

        WCRequestParamBean bean = initRequestParam(params);
        return handle(service.getMyMission(bean));
    }

    private <T> Observable<T> handle(Observable<WCResponseParamBean<T>> observable){
        return observable.flatMap(new Function<WCResponseParamBean<T>, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(@io.reactivex.annotations.NonNull WCResponseParamBean<T> response) throws Exception {
                if (response.success()){
                    return Observable.just(response.getData());
                }
                throw new NetError(response.getResult_msg(),response.getResult_code());
            }
        });
    }

    private WCRequestParamBean initRequestParam(@NonNull Map<String,Object> params) {

        WCRequestParamBean requestParamModel = new WCRequestParamBean();

        requestParamModel.setData(params);

        String id = UUID.randomUUID().toString();
        id += System.currentTimeMillis();

        //requestParamModel.setId(MD5Util.md5(id).toString().toLowerCase());
        requestParamModel.setId(id);
        requestParamModel.setSign(signParams(params));

        WCRequestParamBean.ClientBean clientBean = new WCRequestParamBean.ClientBean();
        clientBean.setCaller(WCConfigConstants.CALLER);
        clientBean.setVersion(BuildConfig.VERSION_NAME);
        clientBean.setDate(System.currentTimeMillis() + "");

        WCRequestParamBean.ClientBean.ExBean exBean = new WCRequestParamBean.ClientBean.ExBean();
        exBean.setDv(Build.DEVICE);
        exBean.setSc(mDeviceSize);
        exBean.setUid(mUuid);
        exBean.setSf(BuildConfig.FLAVOR);
        exBean.setOs("android");
        clientBean.setEx(exBean);

        requestParamModel.setClient(clientBean);

        KLog.d("initRequestParam: " + JsonHelper.getJsonStrFromObj(requestParamModel));

        return requestParamModel;
    }

    /**
     * 请求参数进行签名
     *
     * @param param 请求参数
     * @return  签名后的字符串
     */
    private String signParams(Map<String,Object> param) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(param.size());
            paramNames.addAll(param.keySet());
            Collections.sort(paramNames);

            sb.append(WCConfigConstants.SIGN_SECRET);
            for (String paramName : paramNames) {
                sb.append(paramName).append(param.get(paramName));
            }
            sb.append(WCConfigConstants.SIGN_SECRET);

            KLog.d("signParams: 签名前 sign = " + sb.toString());

            byte[] sha1Digest = getSHA1Digest(sb.toString());
            String md5Sign = MD5Util.md5(MD5Util.toHexString(sha1Digest));

            if (md5Sign == null){
                return null;
            }

            String result = md5Sign.toLowerCase();

            KLog.d("signParams: 签名后 sign = " + result);

            return result;

        } catch (IOException e) {
            KLog.e("sign：Sign 值签名错误");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * SHA1加密
     */
    private byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            KLog.e("getSHA1Digest：SHA1加密失败");
            gse.printStackTrace();
        }

        return bytes;
    }
}
