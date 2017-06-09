package com.mm.weclubs.app.mission_list;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCMissionDetailInfo;
import com.mm.weclubs.data.pojo.WCMissionListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/4 上午11:17
 * 描述:
 */

public interface WCMissionListView extends MVPView {

    void refreshMissionList(ArrayList<WCMissionListInfo> list);

    void addMissionList(ArrayList<WCMissionListInfo> list, boolean hasMore);

    /**
     * 获取任务详情接口请求成功
     *
     * @param missionDetailInfo 请求成功得到的任务详情
     */
    void getMissionDetailSuccess(WCMissionDetailInfo missionDetailInfo);

    /**
     * 刷新任务列表
     *
     * @param missionListInfo   任务实体
     * @param position  定位
     */
    void notifyChangeList(WCMissionListInfo missionListInfo, int position);
}
