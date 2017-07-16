package com.mm.weclubs.util;

import android.graphics.Color;

/**
 * 文 件 名: ColorUtils
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/11 20:41
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public class ColorUtils {

    private ColorUtils(){}

    public static int old2new(int oldColor, int newColor, float fraction){
        int oldRed = Color.red(oldColor);
        int oldGreen = Color.green(oldColor);
        int oldBlue = Color.blue(oldColor);
        int oldAlpha = Color.alpha(oldColor);
        int newRed = Color.red(newColor);
        int newGreen = Color.green(newColor);
        int newBlue = Color.blue(newColor);
        int newAlpha = Color.alpha(newColor);

        int red = (int) (oldRed * (1-fraction) + newRed * fraction);
        int green = (int) (oldGreen * (1-fraction) + newGreen * fraction);
        int blue = (int) (oldBlue * (1-fraction) + newBlue * fraction);
        int alpha = (int) (oldAlpha*(1-fraction) + newAlpha * fraction);

        return Color.argb(alpha,red,green,blue);
    }

    public static int changeAlpha(int color, float fraction){
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha,red,green,blue);
    }
}
