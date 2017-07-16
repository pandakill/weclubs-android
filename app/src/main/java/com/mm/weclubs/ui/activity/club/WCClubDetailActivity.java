package com.mm.weclubs.ui.activity.club;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.mm.weclubs.R;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.util.StatusBarUtil;
import com.socks.library.KLog;

public class WCClubDetailActivity extends BaseActivity {

    public static final String CLUB_ID = "CLUB_ID";

    private int mClubId;

    private View mQRCodeView;
    private View mBackView;

    @Override
    protected int getContentLayout() {
        return R.layout.content_club_detail;
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {

    }

    @Override
    protected void initView() {

        mQRCodeView = findViewById(R.id.img_qrcode);
        mBackView = findViewById(R.id.img_return);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
            //修复状态栏高度

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mQRCodeView.getLayoutParams();
            KLog.d("修复前:"+params.topMargin);
            params.topMargin = StatusBarUtil.fixMarginTopAdd(this,params.topMargin);
            KLog.d("修复后:"+params.topMargin);
            mQRCodeView.setLayoutParams(params);

            params = (ConstraintLayout.LayoutParams) mBackView.getLayoutParams();
            KLog.d("修复前:"+params.topMargin);
            params.topMargin = StatusBarUtil.fixMarginTopAdd(this,params.topMargin);
            KLog.d("修复后:"+params.topMargin);
            mBackView.setLayoutParams(params);
        }
    }

    @Override
    protected void afterView() {

    }

    @Override
    protected boolean leftBtnIsReturn() {
        return false;
    }
}
