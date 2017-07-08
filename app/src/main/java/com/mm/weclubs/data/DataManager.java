package com.mm.weclubs.data;

import com.mm.weclubs.data.db.DbHelper;
import com.mm.weclubs.data.network.ApiHelper;
import com.mm.weclubs.data.prefs.PreferencesHelper;

import io.reactivex.Observable;

/**
 * 文 件 名: DataManager
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 21:21
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface DataManager extends DbHelper,ApiHelper,PreferencesHelper{

    /**
     * 返回当前用户的ID
     * @return 如果已经登录，返回UserId, 返回 NULL_INDEX
     */
    Observable<Integer> getUserId();
}
