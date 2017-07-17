package com.mm.weclubs.data.network;

import android.support.annotation.NonNull;

import com.mm.weclubs.data.network.bean.WCClubMeetingBean;
import com.mm.weclubs.data.network.bean.WCClubMissionBean;
import com.mm.weclubs.data.network.bean.WCClubNotifyBean;
import com.mm.weclubs.data.network.bean.WCCommentListBean;
import com.mm.weclubs.data.network.bean.WCIndexClubBean;
import com.mm.weclubs.data.network.bean.WCIndexDataBean;
import com.mm.weclubs.data.network.bean.WCMeetingListBean;
import com.mm.weclubs.data.network.bean.WCMeetingParticipationBean;
import com.mm.weclubs.data.network.bean.WCMissionListBean;
import com.mm.weclubs.data.network.bean.WCMyClubsBean;
import com.mm.weclubs.data.network.bean.WCNotifyCheckStatusBean;
import com.mm.weclubs.data.network.bean.WCNotifyListBean;
import com.mm.weclubs.data.network.pojo.WCClubDetail;
import com.mm.weclubs.data.network.pojo.WCManageMeetingDetailInfo;
import com.mm.weclubs.data.network.pojo.WCManageNotifyInfo;
import com.mm.weclubs.data.network.pojo.WCMeetingDetailInfo;
import com.mm.weclubs.data.network.pojo.WCMissionDetailInfo;
import com.mm.weclubs.data.network.pojo.WCNotifyListInfo;
import com.mm.weclubs.data.network.pojo.WCUserInfoInfo;

import java.util.Map;

import io.reactivex.Observable;

/**
 * 文 件 名: ApiHelper
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 21:20
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface ApiHelper {

    /*
     * 登录
     */
    Observable<WCUserInfoInfo> login(@NonNull Map<String,Object> params);

    /*
     * 注册
     */
    Observable<WCUserInfoInfo> register(@NonNull Map<String,Object> params);

    /*
     * 获取用户信息
     */
    Observable<WCUserInfoInfo> getUserInfo(@NonNull Map<String,Object> params);

    /*
     * 获取我的动态信息
     */
    Observable<WCMyClubsBean> getMyClubs(@NonNull Map<String,Object> params);

    /**
     * 获取组织详情
     */
    Observable<WCClubDetail> getClubDetail(@NonNull Map<String,Object> params);

    Observable<WCMeetingListBean> getMeetingList(@NonNull Map<String,Object> params);

    Observable<WCMeetingDetailInfo> getMeetingDetail(@NonNull Map<String,Object> params);

    Observable<WCMissionListBean> getMissionList(@NonNull Map<String,Object> params);

    Observable<WCMissionDetailInfo> getMissionDetail(@NonNull Map<String,Object> params);

    Observable<WCNotifyListBean> getNotifyList(@NonNull Map<String,Object> params);

    Observable<WCNotifyListInfo> getNotifyDetail(@NonNull Map<String,Object> params);

    //  设置任务的状态
    Observable<Object> setMissionStatus(@NonNull Map<String,Object> params);

    Observable<WCCommentListBean> getCommentList(@NonNull Map<String,Object> params);


    //===================================WCClubNotifyService=========================
    Observable<WCClubNotifyBean> getMyNotify(@NonNull Map<String,Object> params);

    Observable<WCManageNotifyInfo> getMyNotifyDetail(@NonNull Map<String,Object> params);

    Observable<WCNotifyCheckStatusBean> getNotifyCheckStatusList(@NonNull Map<String,Object> params);

    //==================================WCClubMeetingService==============================
    Observable<WCClubMeetingBean> getMyMeeting(@NonNull Map<String,Object> params);

    Observable<WCManageMeetingDetailInfo> getMyMeetingDetail(@NonNull Map<String,Object> params);

    Observable<WCMeetingParticipationBean> getMeetingParticipation(@NonNull Map<String,Object> params);

    //=====================================WCClubMissionService=========================
    Observable<WCClubMissionBean> getMyMission(@NonNull Map<String,Object> params);

    //===========================Index=========================
    Observable<WCIndexClubBean> getIndexClub(@NonNull Map<String,Object> params);

    Observable<WCIndexDataBean> getIndexData(@NonNull Map<String,Object> params);
}
