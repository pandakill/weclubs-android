package com.mm.weclubs.ui.fragment;

import com.mm.weclubs.R;
import com.mm.weclubs.util.WCLog;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/26 下午4:11
 * 描述:  首页动态的fragment
 */

public class WCMineFragment extends BaseLazyFragment {

    @Override
    protected int getContentViewLayoutID() {
        log = new WCLog(WCMineFragment.class);
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void onFirstUserVisible() {
        log.d("我的 onFirstUserVisible");
        showToast("我的fragment创建");
    }

    @Override
    protected void onUserVisible() {
        log.d("我的 onUserVisible");
        showToast("我的fragment展示");
    }

    @Override
    protected void onUserInvisible() {

    }
}
