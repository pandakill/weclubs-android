package com.mm.weclubs.retrofit;

import com.mm.weclubs.retrofit.service.WCClubMeetingService;
import com.mm.weclubs.retrofit.service.WCClubMissionService;
import com.mm.weclubs.retrofit.service.WCClubNotifyService;
import com.mm.weclubs.retrofit.service.WCClubService;
import com.mm.weclubs.retrofit.service.WCCommentService;
import com.mm.weclubs.retrofit.service.WCDynamicService;
import com.mm.weclubs.retrofit.service.WCUserService;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 下午2:06
 * 描述:
 */

public class WCServiceFactory {

    private static final Object monitor = new Object();

    private static WCUserService mUserService = null;
    private static WCDynamicService mDynamicService = null;
    private static WCClubService sClubService = null;
    private static WCCommentService sCommentService = null;
    private static WCClubMeetingService sClubMeetingService = null;
    private static WCClubNotifyService sClubNotifyService = null;
    private static WCClubMissionService sClubMissionService = null;

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

    public static WCCommentService getCommentService() {
        synchronized (monitor) {
            if (sCommentService == null) {
                sCommentService = ServiceGenerator.createService(WCCommentService.class);
            }

            return sCommentService;
        }
    }

    public static WCClubMeetingService getClubMeetingService() {
        synchronized (monitor) {
            if (sClubMeetingService == null) {
                sClubMeetingService = ServiceGenerator.createService(WCClubMeetingService.class);
            }

            return sClubMeetingService;
        }
    }

    public static WCClubNotifyService getClubNotifyService() {
        synchronized (monitor) {
            if (sClubNotifyService == null) {
                sClubNotifyService = ServiceGenerator.createService(WCClubNotifyService.class);
            }

            return sClubNotifyService;
        }
    }

    public static WCClubMissionService getClubMissionService() {
        synchronized (monitor) {
            if (sClubMissionService == null) {
                sClubMissionService = ServiceGenerator.createService(WCClubMissionService.class);
            }

            return sClubMissionService;
        }
    }
}
