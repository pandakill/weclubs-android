package com.mm.weclubs.app.mob_sms;

import com.mm.weclubs.app.base.MVPView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/29 上午12:04
 * 描述:
 */

public interface WCMobSmsView extends MVPView {

    void getCodeSuccess();

    void getCodeFail(String errorMsg);
}
