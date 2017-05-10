package com.mm.weclubs.picasso;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blankj.utilcode.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午5:56
 * 描述:
 */

public class CacheInterceptor implements Interceptor {

    private Context context;

    public CacheInterceptor(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (NetworkUtils.isConnected()) {
            Response response = chain.proceed(request);
            // read from cache for 60 s
            int maxAge = 300;
            String cacheControl = request.cacheControl().toString();
            Log.e("Tamic", maxAge + "s load cahe:" + cacheControl);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            Log.e("Tamic", " no network load cahe");
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Response response = chain.proceed(request);
            //set cahe times is 3 days
            int maxStale = 60 * 60 * 24 * 3;
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
