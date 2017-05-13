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
import com.mm.weclubs.app.comment.WCCommentView;
import com.mm.weclubs.data.pojo.WCCommentListInfo;
import com.mm.weclubs.data.pojo.WCMeetingListInfo;
import com.mm.weclubs.util.ImageLoaderHelper;

import java.util.ArrayList;

import me.fangx.haorefresh.HaoRecyclerView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午2:20
 * 描述:
 */

public class WCMeetingDetailActivity extends BaseActivity implements WCCommentView {

    private ImageView mIvSponsorLogo;
    private TextView mTvSponsorName;
    private TextView mTvCreateDate;
    private TextView mTvMeetingContent;
    private TextView mTvDeadline;
    private TextView mTvAddress;
    private ImageView mIcConfirm;
    private TextView mTvBtnConfirm;
    private LinearLayout mBtnConfirm;
    private ImageView mIcSign;
    private TextView mTvBtnSign;
    private LinearLayout mBtnSign;
    private HaoRecyclerView mRcvSignLeader;
    private TextView mTvCommentCount;
    private HaoRecyclerView mRcyCommentList;
    private ImageView mBtnVoice;
    private ImageView mBtnEmoji;
    private ImageView mBtnMore;
    private EditText mInputComment;

    private WCMeetingListInfo mMeetingListInfo;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_dynamic_meeting_detail;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

        if (extras == null) {
            log.e("extras不能为空");
            onBackPressed();
            return;
        }

        mMeetingListInfo = (WCMeetingListInfo) extras.getSerializable("meetingListInfo");
    }

    @Override
    protected void initView() {
        mIvSponsorLogo = (ImageView) findViewById(R.id.img_sponsor_logo);
        mTvSponsorName = (TextView) findViewById(R.id.tv_sponsor_name);
        mTvCreateDate = (TextView) findViewById(R.id.tv_create_date);
        mTvMeetingContent = (TextView) findViewById(R.id.tv_meeting_content);
        mTvDeadline = (TextView) findViewById(R.id.tv_deadline);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mIcConfirm = (ImageView) findViewById(R.id.icon_confirm);
        mTvBtnConfirm = (TextView) findViewById(R.id.tv_btn_confirm_text);
        mBtnConfirm = (LinearLayout) findViewById(R.id.btn_confirm);
        mIcSign = (ImageView) findViewById(R.id.icon_sign);
        mTvBtnSign = (TextView) findViewById(R.id.tv_btn_sign_text);
        mBtnSign = (LinearLayout) findViewById(R.id.btn_sign);
        mRcvSignLeader = (HaoRecyclerView) findViewById(R.id.sign_leader_recycler);
        mTvCommentCount = (TextView) findViewById(R.id.tv_comment_count);
        mRcyCommentList = (HaoRecyclerView) findViewById(R.id.comment_recycler);
        mBtnVoice = (ImageView) findViewById(R.id.btn_voice);
        mBtnEmoji = (ImageView) findViewById(R.id.btn_emoji);
        mBtnMore = (ImageView) findViewById(R.id.btn_more);
        mInputComment = (EditText) findViewById(R.id.input_comment);
    }

    @Override
    protected void afterView() {
        if (mMeetingListInfo == null) {
            onBackPressed();
            return;
        }

        mBtnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(mTvBtnConfirm.getText().toString());
            }
        });

        mBtnSign.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(mTvBtnSign.getText().toString());
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
                .loadImage(mIvSponsorLogo, mMeetingListInfo.getSponsor().getSponsor_avatar());
        mTvSponsorName.setText(mMeetingListInfo.getSponsor().getSponsor_name());
        mTvCreateDate.setText(TimeUtils.millis2String(mMeetingListInfo.getCreate_date(), "MMMdd日"));
        mTvMeetingContent.setText(mMeetingListInfo.getContent());
        mTvDeadline.setText(TimeUtils.millis2String(mMeetingListInfo.getDeadline(), "MMMdd日  HH:mm"));
        mTvAddress.setText(mMeetingListInfo.getAddress());


        if (mMeetingListInfo.getConfirm_join() == 1) {
            mIcConfirm.setVisibility(View.VISIBLE);

            mTvBtnConfirm.setText("已确认参与");
            mTvBtnConfirm.setTextColor(getResources().getColor(R.color.colorCommonText_666));

            mBtnConfirm.setEnabled(false);
        } else {
            mIcConfirm.setVisibility(View.GONE);

            mTvBtnConfirm.setText("确认参与");
            mTvBtnConfirm.setTextColor(getResources().getColor(R.color.themeColor));

            mBtnConfirm.setEnabled(true);
        }

        if (mMeetingListInfo.getHas_sign() == 1) {
            mIcSign.setVisibility(View.VISIBLE);

            mTvBtnSign.setText("已完成签到");
            mTvBtnSign.setTextColor(getResources().getColor(R.color.colorCommonText_666));

            mBtnSign.setEnabled(false);
        } else {
            mIcSign.setVisibility(View.GONE);

            mTvBtnSign.setText("扫一扫签到");
            mTvBtnSign.setTextColor(getResources().getColor(R.color.themeColor));

            mBtnSign.setEnabled(true);
        }
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    protected void unSubscribeObservable() {
    }

    @Override
    public void refreshCommentList(ArrayList<WCCommentListInfo> list) {

    }

    @Override
    public void addCommentList(ArrayList<WCCommentListInfo> list, boolean hasMore) {

    }
}
