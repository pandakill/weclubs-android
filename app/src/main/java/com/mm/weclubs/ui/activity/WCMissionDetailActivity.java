package com.mm.weclubs.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCMissionListInfo;
import com.mm.weclubs.util.ImageLoaderHelper;

import me.fangx.haorefresh.HaoRecyclerView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午2:20
 * 描述:
 */

public class WCMissionDetailActivity extends BaseActivity {

    private ImageView mIvSponsorLogo;
    private TextView mTvSponsorName;
    private TextView mTvCreateDate;
    private TextView mTvMissionContent;
    private TextView mTvDeadline;
    private ImageView mIcConfirm;
    private TextView mTvBtnConfirm;
    private LinearLayout mBtnConfirm;
    private ImageView mIcFinish;
    private TextView mTvBtnFinish;
    private LinearLayout mBtnFinish;
    private TextView mTvCommentCount;
    private HaoRecyclerView mRcyCommentList;
    private ImageView mBtnVoice;
    private ImageView mBtnEmoji;
    private ImageView mBtnMore;
    private EditText mInputComment;

    private WCMissionListInfo mMissionListInfo;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_dynamic_mission_detail;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras == null) {
            log.e("extras不能为空");
            onBackPressed();
            return;
        }

        mMissionListInfo = (WCMissionListInfo) extras.getSerializable("missionListInfo");
    }

    @Override
    protected void initView() {
        mIvSponsorLogo = (ImageView) findViewById(R.id.img_sponsor_logo);
        mTvSponsorName = (TextView) findViewById(R.id.tv_sponsor_name);
        mTvCreateDate = (TextView) findViewById(R.id.tv_create_date);
        mTvMissionContent = (TextView) findViewById(R.id.tv_mission_content);
        mTvDeadline = (TextView) findViewById(R.id.tv_deadline);
        mIcConfirm = (ImageView) findViewById(R.id.icon_confirm);
        mTvBtnConfirm = (TextView) findViewById(R.id.tv_btn_confirm_text);
        mBtnConfirm = (LinearLayout) findViewById(R.id.btn_confirm);
        mIcFinish = (ImageView) findViewById(R.id.icon_finish);
        mTvBtnFinish = (TextView) findViewById(R.id.tv_btn_finish_text);
        mBtnFinish = (LinearLayout) findViewById(R.id.btn_finish);
        mTvCommentCount = (TextView) findViewById(R.id.tv_comment_count);
        mRcyCommentList = (HaoRecyclerView) findViewById(R.id.comment_recycler);
        mBtnVoice = (ImageView) findViewById(R.id.btn_voice);
        mBtnEmoji = (ImageView) findViewById(R.id.btn_emoji);
        mBtnMore = (ImageView) findViewById(R.id.btn_more);
        mInputComment = (EditText) findViewById(R.id.input_comment);
    }

    @Override
    protected void afterView() {
        if (mMissionListInfo == null) {
            onBackPressed();
            return;
        }

        mBtnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(mTvBtnConfirm.getText().toString());
            }
        });

        mBtnFinish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(mTvBtnFinish.getText().toString());
            }
        });

        mBtnVoice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("语音聊天");
            }
        });

        mBtnEmoji.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("表情");
            }
        });

        mBtnMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("更多");
            }
        });

        initBaseInfo();
    }

    private void initBaseInfo() {
        ImageLoaderHelper.getInstance(getApplicationContext())
                .loadImage(mIvSponsorLogo, mMissionListInfo.getSponsor().getSponsor_avatar());
        mTvSponsorName.setText(mMissionListInfo.getSponsor().getSponsor_name());
        mTvCreateDate.setText(TimeUtils.millis2String(mMissionListInfo.getCreate_date(), "MMMdd日"));
        mTvMissionContent.setText(mMissionListInfo.getContent());
        mTvDeadline.setText(TimeUtils.millis2String(mMissionListInfo.getDeadline(), "MMMdd日  HH:mm"));


        if (mMissionListInfo.getConfirm_date() > 0) {
            mIcConfirm.setVisibility(View.VISIBLE);

            mTvBtnConfirm.setText("已确认任务");
            mTvBtnConfirm.setTextColor(getResources().getColor(R.color.colorCommonText_666));

            mBtnConfirm.setEnabled(false);
        } else {
            mIcConfirm.setVisibility(View.GONE);

            mTvBtnConfirm.setText("确认任务");
            mTvBtnConfirm.setTextColor(getResources().getColor(R.color.themeColor));

            mBtnConfirm.setEnabled(true);
        }

        if (mMissionListInfo.getFinish() == 1) {
            mIcFinish.setVisibility(View.VISIBLE);

            mTvBtnFinish.setText("已完成任务");
            mTvBtnFinish.setTextColor(getResources().getColor(R.color.colorCommonText_666));

            mBtnFinish.setEnabled(false);
        } else {
            mIcFinish.setVisibility(View.GONE);

            mTvBtnFinish.setText("完成任务");
            mTvBtnFinish.setTextColor(getResources().getColor(R.color.themeColor));

            mBtnFinish.setEnabled(true);
        }
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    protected void unSubscribeObservable() {

    }
}
