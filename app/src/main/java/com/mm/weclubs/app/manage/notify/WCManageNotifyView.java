package com.mm.weclubs.app.manage.notify;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCManageNotifyInfo;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午6:52
 * 描述:
 */

public interface WCManageNotifyView extends MVPView {

    void refreshNotifyList(ArrayList<WCManageNotifyInfo> list);

    void addNotifyList(ArrayList<WCManageNotifyInfo> list, boolean hasMore);

    /**
     * 读取通知详情成功
     *
     * @param notifyInfo  通知详情
     */
    void getNotifyDetailSuccess(WCManageNotifyInfo notifyInfo);
}
