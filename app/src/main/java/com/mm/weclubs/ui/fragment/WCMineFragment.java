package com.mm.weclubs.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.app.mine.WCMineContract;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.util.StatusBarUtil;
import com.mm.weclubs.util.WCLog;
import com.socks.library.KLog;

import javax.inject.Inject;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/4/26 下午4:11
 * 描述:  首页动态的fragment
 */

public class WCMineFragment extends BaseLazyFragment implements WCMineContract.View{
    public static final String TAG = "WCMineFragment";

    public static WCMineFragment newInstance(){
        WCMineFragment fragment = new WCMineFragment();
        return fragment;
    }

    private ImageView mAvatarView;
    private TextView mNameView;
    private TextView mUserInfo;

    private TextView mAuthInfo;

    private ImageView mQRCodeView;

    @Inject
    WCMineContract.Presenter<WCMineContract.View> mPresenter;
    @Inject
    ImageLoaderHelper mImageLoaderHelper;

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

        initViews();
        initEvents();

        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    private void initEvents() {

    }

    private void initViews() {
        getActivityComponent().inject(this);

        mQRCodeView = findView(R.id.img_qrcode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //修复状态栏高度

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mQRCodeView.getLayoutParams();
            KLog.d("修复前:"+params.topMargin);
            params.topMargin = StatusBarUtil.fixMarginTopAdd(getContext(),params.topMargin);
            KLog.d("修复后:"+params.topMargin);
            mQRCodeView.setLayoutParams(params);
        }

        mAvatarView = findView(R.id.img_avatar);
        mNameView = findView(R.id.tv_name);
        mUserInfo = findView(R.id.tv_info);
        mAuthInfo = findView(R.id.img_mine_certification_info);
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

    // =================== MVPView ===================

    @Override
    public void setAvatar(String path) {
        mImageLoaderHelper.loadCircleImage(mAvatarView,path, SizeUtils.dp2px(76));
    }

    @Override
    public void setName(String name) {
        mNameView.setText(name);
    }

    @Override
    public void setInfo(String info, int gender) {
        mUserInfo.setText(info);
    }

    @Override
    public void setAuth(String auth) {
        mAuthInfo.setText(auth);
    }
}
