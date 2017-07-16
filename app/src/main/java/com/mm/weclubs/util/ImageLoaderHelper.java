package com.mm.weclubs.util;

import android.graphics.Color;
import android.widget.ImageView;

import com.blankj.utilcode.utils.SizeUtils;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.mm.weclubs.R;
import com.mm.weclubs.di.ActivityGlide;
import com.mm.weclubs.glide.okhttp3.GlideRequests;
import com.mm.weclubs.glide.transform.CircleBorderTransform;

import javax.inject.Inject;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午4:27
 * 描述:
 */

public class ImageLoaderHelper {

    /**
     * 渐变动画
     */
    private final static DrawableTransitionOptions CROSS_FADE = new DrawableTransitionOptions().crossFade(500);
    private final static CircleBorderTransform CIRCLE_BORDER = new CircleBorderTransform(Color.WHITE, SizeUtils.dp2px(1));

    private GlideRequests mGlideRequests;

    @Inject
    public ImageLoaderHelper(@ActivityGlide GlideRequests glideRequests) {
        mGlideRequests = glideRequests;
    }

    public void loadImage(ImageView imageView, String url) {
        mGlideRequests.load(url)
                .placeholder(R.mipmap.login_logo_weclubs)
                .error(R.mipmap.login_logo_weclubs)
                .transition(CROSS_FADE)
                .into(imageView);
    }

    public void loadImage(ImageView imageView, String url, int size){
        mGlideRequests.load(url)
                .placeholder(R.mipmap.login_logo_weclubs)
                .error(R.mipmap.login_logo_weclubs)
                .transition(CROSS_FADE)
                .apply(RequestOptions.overrideOf(size))
                .into(imageView);
    }

    public void loadCircleImage(ImageView imageView, String url){
        mGlideRequests.load(url)
                .placeholder(R.mipmap.login_logo_weclubs)
                .error(R.mipmap.login_logo_weclubs)
                //.circleCrop()
                .transition(CROSS_FADE)
                .transform(CIRCLE_BORDER)
                .into(imageView);
    }

    public void loadCircleImage(ImageView imageView, String url, int size){
        mGlideRequests.load(url)
                .placeholder(R.mipmap.login_logo_weclubs)
                .error(R.mipmap.login_logo_weclubs)
                //.circleCrop()
                .transition(CROSS_FADE)
                .transform(CIRCLE_BORDER)
                .apply(RequestOptions.overrideOf(size))
                .into(imageView);
    }
}
