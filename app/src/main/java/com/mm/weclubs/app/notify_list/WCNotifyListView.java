package com.mm.weclubs.app.notify_list;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCNotifyListInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 下午5:33
 * 描述:
 */

public interface WCNotifyListView extends MVPView {

    void refreshNotifyList(ArrayList<WCNotifyListInfo> list);

    void addNotifyList(ArrayList<WCNotifyListInfo> list, boolean hasMore);

    /**
     * 获取通知详情接口请求成功
     *
     * @param notifyListInfo 请求成功得到的通知详情
     */
    void getNotifyDetailSuccess(WCNotifyListInfo notifyListInfo);
}
