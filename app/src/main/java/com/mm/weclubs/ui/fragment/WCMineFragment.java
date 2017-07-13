package com.mm.weclubs.ui.fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.app.mine.WCMineContract;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.util.WCLog;

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

    private Button mBtnLogout;

    private ImageView mAvatarView;
    private TextView mNameView;

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
        /*mBtnLogout = findViewById(R.id.btn_logout, Button.class);
        mBtnLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //退出登录
                //WCUserDataCenter.getInstance(mContext.getApplicationContext()).deleteUserInfo();
                backToLoginActivity();
            }
        });*/
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

        mAvatarView = findView(R.id.img_avatar);
        mNameView = findView(R.id.tv_name);
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
}
