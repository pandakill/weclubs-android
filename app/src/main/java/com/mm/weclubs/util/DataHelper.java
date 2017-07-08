package com.mm.weclubs.util;

import com.mm.weclubs.data.db.entity.User;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;

/**
 * 创建人： zp
 * 创建时间：2017/7/7
 */

public class DataHelper {

    public static User wcUserInfoInfo2User(WCUserInfoInfo wcUserInfoInfo){
        User user = new User();
        user.setAvatarUrl(wcUserInfoInfo.getAvatar_url());
        user.setClassName(wcUserInfoInfo.getClass_name());
        user.setGender(wcUserInfoInfo.getGender());
        user.setGraduateYear(wcUserInfoInfo.getGraduate_year());
        user.setIsAuth(wcUserInfoInfo.getIs_auth());
        user.setMobile(wcUserInfoInfo.getMobile());
        user.setNickName(wcUserInfoInfo.getNick_name());
        user.setRealName(wcUserInfoInfo.getReal_name());
        user.setSchoolId(wcUserInfoInfo.getSchool_id());
        user.setSchoolName(wcUserInfoInfo.getSchool_name());
        user.setToken(wcUserInfoInfo.getToken());
        user.setUserId(wcUserInfoInfo.getUser_id());
        return user;
    }
}
