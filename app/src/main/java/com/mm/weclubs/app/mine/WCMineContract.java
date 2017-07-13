package com.mm.weclubs.app.mine;

import com.mm.weclubs.app.base.MVPPresenter;
import com.mm.weclubs.app.base.MVPView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/22 下午1:37
 * 描述:
 */

public interface WCMineContract {

    interface Presenter<V extends View> extends MVPPresenter<V> {

    }

    interface View extends MVPView{

        void setAvatar(String path);

        void setName(String name);
    }
}
