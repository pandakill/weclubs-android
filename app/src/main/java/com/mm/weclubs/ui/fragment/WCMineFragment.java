package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mm.weclubs.R;
import com.mm.weclubs.datacenter.WCUserDataCenter;
import com.mm.weclubs.ui.activity.WCLoginActivity;
import com.mm.weclubs.util.WCLog;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/26 下午4:11
 * 描述:  首页动态的fragment
 */

public class WCMineFragment extends BaseLazyFragment {

    private Button mBtnLogout;

    @Override
    protected int getContentViewLayoutID() {
        log = new WCLog(WCMineFragment.class);
        return R.layout.fragment_mine;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
        log.d("我的 initViewsAndEvents");

        mBtnLogout = findViewById(R.id.btn_logout, Button.class);
        mBtnLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                WCUserDataCenter.getInstance(mContext.getApplicationContext()).deleteUserInfo();
                showIntentThenKill(WCLoginActivity.class);
            }
        });
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
