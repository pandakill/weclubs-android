package com.mm.weclubs.config;

import com.mm.weclubs.util.MD5Util;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/2/10 下午2:38
 * 描述:
 */
public interface WCConfigConstants {

    boolean RELEASE = false;    // 是否为正式版，true正式生产环境，false测试环境

    boolean DEV = true;    // 是否为正在开发，true开发版，false非开发版

    boolean ENABLE_SSL = false;  // 是否支持https

    long REQUEST_DEFAULT_TIMEOUT = 30000;   //  默认http响应延迟时间

    String CONTENT_TYPE_JSON = "Content-Type: application/json;charset=UTF-8";

    // 服务器地址
    String HTTP_BASE_URL = (ENABLE_SSL ? "https://" : "http://")
            + (
            !RELEASE
            ? ( DEV ? SERVER_URLS.DEV.url : SERVER_URLS.DEBUG.url )
            : SERVER_URLS.RELEASE.url
    );

    // caller 值
    String CALLER = (!RELEASE ? ( DEV ? SERVER_URLS.DEV.caller : SERVER_URLS.DEBUG.caller )
            : SERVER_URLS.RELEASE.caller);

    // 签名值
    String SIGN_SECRET = (!RELEASE ? ( DEV ? SERVER_URLS.DEV.signSecret : SERVER_URLS.DEBUG.signSecret )
            : SERVER_URLS.RELEASE.signSecret);

    enum SERVER_URLS {
        DEV("192.168.31.168:1203", "weclubs_android", MD5Util.md5("pukongjie")),
        DEBUG("weclubs.net:1203", "chrome_test", MD5Util.md5("pukongjie")),
        RELEASE("release.com", "chrome_test", MD5Util.md5("pukongjie"));

        String url = null;
        String caller = null;
        String signSecret = null;

        SERVER_URLS(String url, String caller, String signSecret) {
            this.url = url;
            this.caller = caller;
            this.signSecret = signSecret;
        }
    }

    int ONE_PAGE_SIZE = 20; // 一页的加载数量大小
}
