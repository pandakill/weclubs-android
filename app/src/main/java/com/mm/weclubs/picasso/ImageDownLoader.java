package com.mm.weclubs.picasso;

import okhttp3.OkHttpClient;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午5:46
 * 描述:
 */

public class ImageDownLoader /*implements Downloader*/ {

    private OkHttpClient client = null;

    public ImageDownLoader(OkHttpClient client) {
        this.client = client;
    }

    /*@Override
    public Response load(Uri uri, int networkPolicy) throws IOException {

        CacheControl cacheControl = null;
        if (networkPolicy != 0) {
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                cacheControl = CacheControl.FORCE_CACHE;
            } else {
                CacheControl.Builder builder = new CacheControl.Builder();
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    builder.noCache();
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    builder.noStore();
                }
                cacheControl = builder.build();
            }
        }

        Request.Builder builder = new Request.Builder().url(uri.toString());
        if (cacheControl != null) {
            builder.cacheControl(cacheControl);
        }

        okhttp3.Response response = client.newCall(builder.build()).execute();
        int responseCode = response.code();
        if (responseCode >= 300) {
            response.body().close();
            throw new ResponseException(responseCode + " " + response.message(), networkPolicy,
                    responseCode);
        }

        boolean fromCache = response.cacheResponse() != null;

        ResponseBody responseBody = response.body();
        return new Response(responseBody.byteStream(), fromCache, responseBody.contentLength());

    }

    @Override
    public void shutdown() {

        Cache cache = client.cache();
        if (cache != null) {
            try {
                cache.close();
            } catch (IOException ignored) {
            }
        }
    }*/
}
