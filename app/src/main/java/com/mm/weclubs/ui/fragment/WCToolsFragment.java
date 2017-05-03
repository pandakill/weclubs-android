package com.mm.weclubs.ui.fragment;

import com.mm.weclubs.R;
import com.mm.weclubs.util.WCLog;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/26 下午4:11
 * 描述:  首页动态的fragment
 */

public class WCToolsFragment extends BaseLazyFragment {

    @Override
    protected int getContentViewLayoutID() {
        log = new WCLog(WCToolsFragment.class);
        return R.layout.fragment_tools;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected void onFirstUserVisible() {
        log.d("工具 onFirstUserVisible");
        showToast("工具fragment创建");
    }

    @Override
    protected void onUserVisible() {
        log.d("工具 onUserVisible");
        showToast("工具fragment展示");
    }

    @Override
    protected void onUserInvisible() {

    }
}