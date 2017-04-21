package com.mm.weclubs.app.security;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.mm.weclubs.BuildConfig;
import com.mm.weclubs.config.WCConfigConstants;
import com.mm.weclubs.data.model.WCRequestParamModel;
import com.mm.weclubs.data.pojo.RequestBean;
import com.mm.weclubs.data.pojo.RequestBean.ClientBean.ExBean;
import com.mm.weclubs.util.JsonHelper;
import com.mm.weclubs.util.MD5Util;
import com.mm.weclubs.util.PreferencesHelper;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/21 下午7:42
 * 描述:  与服务器交互的 presenter
 */
public class WCHttpParamsPresenter {

    private String TAG = WCHttpParamsPresenter.class.getSimpleName();

    public WCRequestParamModel initRequestParam(Context context, HashMap<String, Object> params) {

        WCRequestParamModel requestParamModel = new WCRequestParamModel();

        requestParamModel.setData(params);

        String id = UUID.randomUUID().toString();
        id += System.currentTimeMillis();

        requestParamModel.setId(MD5Util.md5(id).toString().toLowerCase());
        requestParamModel.setSign(signParams(params));

        RequestBean.ClientBean clientBean = new RequestBean.ClientBean();
        clientBean.setCaller(WCConfigConstants.CALLER);
        clientBean.setVersion(BuildConfig.VERSION_NAME);
        clientBean.setDate(System.currentTimeMillis() + "");

        ExBean exBean = new ExBean();
        exBean.setDv(Build.DEVICE);
        exBean.setSc(String.format("%d, %d",
                PreferencesHelper.getInstance(context).getAsInteger("screen_width", 0),
                PreferencesHelper.getInstance(context).getAsInteger("screen_height", 0)));
        exBean.setUid(PreferencesHelper.getInstance(context).getAsString("uuid", null));
        exBean.setSf(BuildConfig.FLAVOR);
        exBean.setOs("android");
        clientBean.setEx(exBean);

        requestParamModel.setClient(clientBean);

        if (WCConfigConstants.DEV) {
            Log.i(TAG, "initRequestParam: " + JsonHelper.getJsonStrFromObj(requestParamModel));
        }

        return requestParamModel;
    }

    /**
     * 请求参数进行签名
     *
     * @param param 请求参数
     * @return  签名后的字符串
     */
    private String signParams(HashMap param) {
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

            Log.i(TAG, "signParams: 签名前 sign = " + sb.toString());

            byte[] sha1Digest = getSHA1Digest(sb.toString());
            String md5Sign = MD5Util.md5(MD5Util.toHexString(sha1Digest));
            String result = md5Sign.toLowerCase();

            if (WCConfigConstants.DEV) {
                Log.i(TAG, "signParams: 签名后 sign = " + result);
            }

            return result;

        } catch (IOException e) {
            Log.e(TAG, "sign：Sign 值签名错误");
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
            Log.e(TAG, "getSHA1Digest：SHA1加密失败");
            gse.printStackTrace();
        }

        return bytes;
    }
}
