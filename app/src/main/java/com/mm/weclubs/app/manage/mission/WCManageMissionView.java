package com.mm.weclubs.app.manage.mission;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCManageMissionInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午6:52
 * 描述:
 */

public interface WCManageMissionView extends MVPView {

    void refreshMissionList(ArrayList<WCManageMissionInfo> list);

    void addMissionList(ArrayList<WCManageMissionInfo> list, boolean hasMore);
}
