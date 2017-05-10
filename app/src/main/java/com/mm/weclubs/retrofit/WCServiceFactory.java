package com.mm.weclubs.retrofit;

import com.mm.weclubs.retrofit.service.WCClubService;
import com.mm.weclubs.retrofit.service.WCDynamicService;
import com.mm.weclubs.retrofit.service.WCUserService;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 下午2:06
 * 描述:
 */

public class WCServiceFactory {

    protected static final Object monitor = new Object();

    private static WCUserService mUserService = null;
    private static WCDynamicService mDynamicService = null;
    private static WCClubService sClubService = null;

    //  获取 UserService
    public static WCUserService getUserService() {

        synchronized (monitor) {
            if (mUserService == null) {
                mUserService = ServiceGenerator.createService(WCUserService.class);
            }

            return mUserService;
        }
    }

    public static WCDynamicService getDynamicService() {
        synchronized (monitor) {
            if (mDynamicService == null) {
                mDynamicService = ServiceGenerator.createService(WCDynamicService.class);
            }

            return mDynamicService;
        }
    }

    public static WCClubService getClubService() {
        synchronized (monitor) {
            if (sClubService == null) {
                sClubService = ServiceGenerator.createService(WCClubService.class);
            }

            return sClubService;
        }
    }

}
