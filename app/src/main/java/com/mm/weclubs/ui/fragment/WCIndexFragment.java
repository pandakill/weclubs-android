package com.mm.weclubs.ui.fragment;

import android.os.Bundle;

import com.mm.weclubs.R;
import com.mm.weclubs.util.WCLog;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/26 下午4:11
 * 描述:  首页动态的fragment
 */

public class WCIndexFragment extends BaseLazyFragment {

    @Override
    protected int getContentViewLayoutID() {
        log = new WCLog(WCIndexFragment.class);
        return R.layout.fragment_index;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {

        log.d("首页 initViewsAndEvents");
    }

    @Override
    protected void onFirstUserVisible() {
        log.d("首页 onFirstUserVisible");
        showToast("首页fragment创建");
    }

    @Override
    protected void onUserVisible() {
        log.d("首页 onUserVisible");
        showToast("首页fragment展示");
    }

    @Override
    protected void onUserInvisible() {

    }
}
