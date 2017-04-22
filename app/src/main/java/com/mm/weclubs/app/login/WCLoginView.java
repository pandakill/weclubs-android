package com.mm.weclubs.app.login;

import com.mm.weclubs.app.base.MVPView;
import com.mm.weclubs.data.pojo.WCUserInfoInfo;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 下午1:37
 * 描述:
 */

public interface WCLoginView extends MVPView {

    void loginSuccess(WCUserInfoInfo userInfo);

    void loginFail(String errorMsg);
}
