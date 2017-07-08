package com.mm.weclubs.data.prefs;

/**
 * 文 件 名: PreferencesHelper
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/6 21:22
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public interface PreferencesHelper {

    void saveLastTimeLoginId(int id);

    int getLastTimeLoginId();

    void saveUUid(String uuid);

    String getUUid();
}
