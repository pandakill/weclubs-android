package com.mm.weclubs.glide.okhttp3;

import android.content.Context;

import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.socks.library.KLog;

import java.io.InputStream;

/**
 * 创建人： zp
 * 创建时间：2017/7/12
 */

@GlideModule
public final class OkHttpLibraryGlideModule extends AppGlideModule{
    @Override
    public void registerComponents(Context context, Registry registry) {
        KLog.d("改成用OkHttp3加载");
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
