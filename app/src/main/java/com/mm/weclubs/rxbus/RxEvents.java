package com.mm.weclubs.rxbus;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/27 下午3:25
 * 描述:
 */

public class RxEvents {

    public final static String loginSuccess = "login_success";  // 登录成功

    private String eventName;
    private Object eventData;

    public RxEvents(String eventName) {
        this.eventName = eventName;
    }

    public RxEvents(String eventName, Object eventData) {
        this.eventName = eventName;
        this.eventData = eventData;
    }

    public Object getEventData() {
        return eventData;
    }

    public String getEventName() {
        return eventName;
    }
}
