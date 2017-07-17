package com.mm.weclubs.ui.activity.club;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.app.club_detail.WCClubDetailContract;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.network.pojo.WCHonorInfo;
import com.mm.weclubs.data.network.pojo.WCStudentInfo;
import com.mm.weclubs.di.DeviceWidth;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.util.StatusBarUtil;
import com.mm.weclubs.widget.AuthDialog;
import com.mm.weclubs.widget.ClubQRCodeDialog;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

import static com.mm.weclubs.data.prefs.AppPreferencesHelper.NULL_ID;
import static com.mm.weclubs.data.prefs.AppPreferencesHelper.NULL_INDEX;

public class WCClubDetailActivity extends BaseActivity implements WCClubDetailContract.View,
        View.OnClickListener{

    public static final String CLUB_ID = "CLUB_ID";

    private long mClubId;

    private View mQRCodeView;
    private View mBackView;

    private ImageView mClubLogo;
    private TextView mClubName;
    private TextView mClubLevel;
    private TextView mClubSlogan;

    private TextView mClubAttr;

    private TextView mApplyJoin;

    private LinearLayout mHonorRoot;

    private LinearLayout mMemberRoot;
    private ImageView mMembers[] = new ImageView[6];
    private static int MEMBERS_IDS[] = {
            R.id.iv_student_1,R.id.iv_student_2,R.id.iv_student_3,R.id.iv_student_4,
            R.id.iv_student_5,R.id.iv_student_6
    };
    private ImageView mMoreMember;
    private TextView mMemberCount;

    private ClubQRCodeDialog mClubQRCodeDialog;

    @Inject
    WCClubDetailContract.Presenter<WCClubDetailContract.View> mPresenter;
    @Inject
    ImageLoaderHelper mImageLoaderHelper;
    @DeviceWidth
    int mWidth;

    @Override
    protected int getContentLayout() {
        return R.layout.content_club_detail;
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
        mClubId = extras.getLong(CLUB_ID,NULL_INDEX);
    }

    @Override
    protected void initView() {
        getActivityComponent().inject(this);

        mQRCodeView = findViewById(R.id.img_qrcode);
        mBackView = findViewById(R.id.img_return);
        mClubLogo = (ImageView) findViewById(R.id.img_avatar);
        mClubName = (TextView) findViewById(R.id.tv_name);
        mClubLevel = (TextView) findViewById(R.id.tv_club_tag);
        mClubSlogan = (TextView) findViewById(R.id.tv_slogan);

        mClubAttr = (TextView) findViewById(R.id.tv_club_attr_content);

        mApplyJoin = (TextView) findViewById(R.id.tv_apply_join);

        mHonorRoot = (LinearLayout) findViewById(R.id.ll_honor);

        //
        mMemberRoot = (LinearLayout) findViewById(R.id.ll_student_list);
        for (int i = 0; i < MEMBERS_IDS.length; i++) {
            mMembers[i] = (ImageView) findViewById(MEMBERS_IDS[i]);
        }
        mMoreMember = (ImageView) findViewById(R.id.img_more);
        mMemberCount = (TextView) findViewById(R.id.tv_club_member_count);
        //

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

        resizeStudentView();
    }

    private void resizeStudentView() {
        int marginMax = SizeUtils.dp2px(12+20+50+8+7*36);
        if (marginMax >= mWidth){
            KLog.d("太长了，尴尬了");
            return;
        }
        int margin = (mWidth-marginMax)/6;
        for (ImageView member : mMembers) {
            if (member!=null){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) member.getLayoutParams();
                params.rightMargin = margin;
                member.setLayoutParams(params);
            }
        }
    }

    @Override
    protected void afterView() {
        if (mClubId == NULL_ID){
            onError("社团ID错误，请重新打开页面");
            return;
        }

        mBackView.setOnClickListener(this);
        mQRCodeView.setOnClickListener(this);
        mApplyJoin.setOnClickListener(this);
        mMemberCount.setOnClickListener(this);
        mMoreMember.setOnClickListener(this);

        for (ImageView member : mMembers) {
            if (member != null){
                member.setOnClickListener(this);
            }
        }

        mPresenter.attachView(this);
        mPresenter.loadDetail(mClubId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_return:
                finish();
                return;
            case R.id.img_qrcode:
                mPresenter.showQRCode();
                return;
            case R.id.tv_apply_join:
                mPresenter.apply();
                return;
            case R.id.iv_student_1:
            case R.id.iv_student_2:
            case R.id.iv_student_3:
            case R.id.iv_student_4:
            case R.id.iv_student_5:
            case R.id.iv_student_6:
                mPresenter.clickMember(v.getId());
                break;
            case R.id.iv_more_student:
            case R.id.tv_club_member_count:
                openMembersActivity(mClubId);
                break;
            default:break;
        }
    }

    private void openMembersActivity(long clubId) {
        ToastUtils.showLongToast("打开ID为"+clubId+"的社团成员");
    }

    //================= MVPView ==========================
    @Override
    public void setAvatar(String path) {
        mImageLoaderHelper.loadCircleImage(mClubLogo,path, SizeUtils.dp2px(76));
    }

    @Override
    public void setClubName(String name) {
        mClubName.setText(name);
    }

    @Override
    public void setSlogan(String slogan) {
        if (!TextUtils.isEmpty(slogan)) {
            mClubSlogan.setText(slogan);
            KLog.d("口号是：" + slogan);
        }else{
            KLog.d("口号不存在");
        }
    }

    @Override
    public void setLevel(int level) {
        mClubLevel.setText(WCConstantsUtil.getClubLevelStr(level));
        switch (level) {
            case 0:
                mClubLevel.setBackground(getResources().getDrawable(R.drawable.club_level_tag_school2));
                break;
            case 1:
                mClubLevel.setBackground(getResources().getDrawable(R.drawable.club_level_tag_school2));
                break;
            case 2:
                mClubLevel.setBackground(getResources().getDrawable(R.drawable.club_level_tag_interest2));
                break;
            case 3:
                mClubLevel.setBackground(getResources().getDrawable(R.drawable.club_level_tag_class2));
                break;
            case 4:
                mClubLevel.setBackground(getResources().getDrawable(R.drawable.club_level_tag_freedom2));
                break;
        }
    }

    @Override
    public void setAttr(String attr) {
        mClubAttr.setText(attr);
    }

    @Override
    public void setHonorList(List<WCHonorInfo> honors) {
        mHonorRoot.removeAllViews();
        if (honors.isEmpty()){
            mHonorRoot.setVisibility(View.GONE);
            return;
        }
        for (WCHonorInfo honor : honors) {
            TextView honorView = (TextView) LayoutInflater.from(this).inflate(R.layout.view_club_detail_honor,mHonorRoot,false);
            honorView.setText("获得\""+ TimeUtils.millis2String(honor.getGet_date(),"yyyy年")+honor.getName()+"称号\"");
            mHonorRoot.addView(honorView);
        }
    }

    @Override
    public void setMemberList(List<WCStudentInfo> students) {
        if (students.isEmpty()){
            mMemberRoot.setVisibility(View.GONE);
            return;
        }
        mMemberRoot.setVisibility(View.VISIBLE);
        final int studentNumber = students.size();
        for (int i = 0; i < mMembers.length; i++) {
            if (i < studentNumber){
                //显示
                final WCStudentInfo info = students.get(i);
                mMembers[i].setVisibility(View.VISIBLE);
                mImageLoaderHelper.loadCircleImage(mMembers[i],info.getAvatar_url(),SizeUtils.dp2px(36));
            } else {
                //隐藏
                mMembers[i].setVisibility(View.GONE);
            }
        }
        if (studentNumber<=mMembers.length){
            //不显示more and count
            mMoreMember.setVisibility(View.GONE);
            mMemberCount.setVisibility(View.GONE);
        }else{
            mMoreMember.setVisibility(View.VISIBLE);
            mMemberCount.setVisibility(View.VISIBLE);
            mMemberCount.setText(studentNumber+"人");
        }
    }

    @Override
    public void showQRView(String qrPath) {
        if (mClubQRCodeDialog == null) {
            mClubQRCodeDialog = new ClubQRCodeDialog();
            mClubQRCodeDialog.setCancelable(false);
            mClubQRCodeDialog.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }
        mClubQRCodeDialog.show(getSupportFragmentManager(),AuthDialog.TAG);
    }
}
