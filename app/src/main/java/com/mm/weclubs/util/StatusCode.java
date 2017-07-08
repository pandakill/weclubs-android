package com.mm.weclubs.util;

/**
 * 文 件 名: StatusCode
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/7 21:46
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注: 状态码说明
 */

public interface StatusCode {
    /**
     * 请求成功
     */
    int SUCCESS = 2000;

    /**
     * 请求失败
     */
    int FAIL = 3000;

    /**
     * 违法的请求方法(指HTTP请求头方法)
     */
    int ILLEGAL = 3100;

    /**
     * 参数违法
     */
    int PARAMETER_VIOLATION = 3001;

    /**
     * 请求参数为空
     */
    int PARAMETER_EMPTY = 3002;

    /**
     * 签名违法
     */
    int SIGN_FAIL = 3003;

    /**
     * caller值违法
     */
    int CALLER_FAIL = 3004;

    /**
     * token失效
     */
    int TOKEN_TIMEOUT = 3010;

    /**
     * 密码错误
     */
    int PASSWORD_ERROR = 3011;

    /**
     * 用户没有改权限
     */
    int NO_WRITE_PERMISSION = 3012;

    /**
     * 找不到该用户
     */
    int NO_USER = 3014;

    /**
     * 程序出错
     */
    int APP_ERROR = 5000;

    /**
     * 弹框需要确定且关闭当前页面tips
     */
    int SHOW_TIPS_AND_CLOSE = 8888;

    /**
     * 弹框但不需要关闭页面tips
     */
    int SHOW_TIPS = 7777;
}
