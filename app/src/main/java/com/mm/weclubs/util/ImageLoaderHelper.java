package com.mm.weclubs.util;

import android.content.Context;
import android.widget.ImageView;

import com.mm.weclubs.R;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.picasso.CacheInterceptor;
import com.mm.weclubs.picasso.ImageDownLoader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午4:27
 * 描述:
 */

public class ImageLoaderHelper {
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    private static final int MAX_DISK_CACHE_SIZE = 40 * 1024 * 1024;
    private static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;

    private static final Object monitor = new Object();
    private static Picasso sPicasso;

    private static ImageLoaderHelper mInstance;

    private WCLog log = null;

    private ImageLoaderHelper(Context context) {
        if (sPicasso == null) {
            sPicasso = new Picasso.Builder(context)
                    .downloader(new ImageDownLoader(getProgressBarClient(context)))
                    .memoryCache(new LruCache(MAX_MEMORY_CACHE_SIZE))
                    .build();

            sPicasso.setLoggingEnabled(WCConfigConstants.DEV);
            log = new WCLog(ImageLoaderHelper.class);
        }
    }

    private OkHttpClient getProgressBarClient(Context context) {
        OkHttpClient client = new OkHttpClient();
        return client.newBuilder()
                .addInterceptor(new CacheInterceptor(context))
                .addNetworkInterceptor(new CacheInterceptor(context))
                .build();
    }

    public static ImageLoaderHelper getInstance(Context context) {
        synchronized (monitor) {
            if (mInstance == null) {
                mInstance = new ImageLoaderHelper(context);
            }

            return mInstance;
        }
    }

    public void loadImage(ImageView imageView, String url) {
        try {
            sPicasso
                    .load(url)
                    .placeholder(R.mipmap.login_logo_weclubs)
                    .error(R.mipmap.login_logo_weclubs)
                    .fit()
                    .into(imageView);
        } catch (IllegalArgumentException e) {
            log.e("loadImage：" + e.getMessage());
            sPicasso
                    .load(R.mipmap.login_logo_weclubs)
                    .fit()
                    .into(imageView);
        }
    }
}
