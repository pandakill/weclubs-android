package com.mm.weclubs.glide.transform;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.security.MessageDigest;

/**
 * 文 件 名: CircleBorderTransform
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/13 00:25
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class CircleBorderTransform extends CircleCrop{

    private Paint mBorderPaint;
    private int mBorderWidth;

    public CircleBorderTransform(@ColorInt int color, int borderWidth) {
        mBorderWidth = borderWidth;

        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(color);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = super.transform(pool, toTransform, outWidth, outHeight);

        Canvas canvas = new Canvas(result);

        float r = result.getWidth() / 2f;
        float radius = r - mBorderWidth / 2;
        canvas.drawCircle(r,r,radius,mBorderPaint);

        canvas.setBitmap(null);

        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        super.updateDiskCacheKey(messageDigest);
    }
}
