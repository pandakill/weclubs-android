package com.mm.weclubs.glide.okhttp3;

import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.socks.library.KLog;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * 创建人： zp
 * 创建时间：2017/7/12
 */

public class OkHttpUrlLoader implements ModelLoader<GlideUrl,InputStream>{

    private final Call.Factory client;

    public OkHttpUrlLoader(Call.Factory client) {
        this.client = client;
    }

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(GlideUrl model, int width, int height, Options options) {
        return new LoadData<>(model,new OkHttpStreamFetcher(client,model));
    }

    @Override
    public boolean handles(GlideUrl glideUrl) {
        return true;
    }

    public static class Factory implements ModelLoaderFactory<GlideUrl,InputStream>{
        private static volatile Call.Factory internalClient;
        private Call.Factory client;

        private static Call.Factory getInternalClient(){
            if (internalClient == null){
                synchronized (Factory.class){
                    if (internalClient == null){
                        internalClient = new OkHttpClient();
                    }
                }
            }
            return internalClient;
        }

        public Factory() {
            this(getInternalClient());
        }

        public Factory(Call.Factory client){
            this.client = client;
        }

        @Override
        public ModelLoader<GlideUrl, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new OkHttpUrlLoader(client);
        }

        @Override
        public void teardown() {
            KLog.d("什么都不做");
        }
    }
}
