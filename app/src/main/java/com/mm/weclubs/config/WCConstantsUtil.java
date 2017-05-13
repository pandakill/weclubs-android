package com.mm.weclubs.config;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/5 下午4:44
 * 描述:
 */

public class WCConstantsUtil {

    public static String DYNAMIC_TYPE_MISSION = "mission";
    public static String DYNAMIC_TYPE_NOTIFY = "notify";
    public static String DYNAMIC_TYPE_MEETING = "meeting";
    public static String DYNAMIC_TYPE_ACTIVITY = "activity";

    /**
     * 根据社团的level等级获取相对应的文字等级
     *
     * @param clubLevel 社团等级，服务器返回的数字
     *
     * @return  文字等级
     */
    public static String getClubLevelStr(int clubLevel) {
        switch (clubLevel) {
            case 0:
                return "校级组织";
            case 1:
                return "院系组织";
            case 2:
                return "兴趣社团";
            case 3:
                return "班级组织";
            case 4:
                return "自由组织";
            default:
                return "未知类型";
        }
    }

    public static String getTodoBtnStr(long todoCount) {
        if (todoCount > 0) {
            return "待办：" + todoCount;
        } else  {
            return "待办";
        }
    }

    public static String getActivityBtnStr(long activityCount) {
        if (activityCount > 0) {
            return "活动：" + activityCount;
        } else {
            return "活动";
        }
    }
}
