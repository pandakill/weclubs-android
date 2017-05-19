package com.mm.weclubs.ui.activity.manage;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.app.comment.WCCommentPresenter;
import com.mm.weclubs.app.comment.WCCommentView;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingPresenter;
import com.mm.weclubs.app.manage.meeting.WCManageMeetingView;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.bean.WCMeetingParticipationBean;
import com.mm.weclubs.data.pojo.WCCommentListInfo;
import com.mm.weclubs.data.pojo.WCManageMeetingDetailInfo;
import com.mm.weclubs.data.pojo.WCManageMeetingInfo;
import com.mm.weclubs.data.pojo.WCMeetingDetailInfo.Leader;
import com.mm.weclubs.ui.activity.BaseActivity;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/16 下午1:59
 * 描述:  会议管理--会议详情的页面
 */

public class WCMeetingManageDetailActivity extends BaseActivity implements WCManageMeetingView, WCCommentView {

    private RoundImageView mIvSponsorLogo;
    private TextView mTvSponsorName;
    private TextView mTvCreateDate;
    private TextView mTvMeetingContent;
    private TextView mTvDeadline;
    private TextView mTvAddress;
    private ImageView mIcConfirm;
    private TextView mTvBtnConfirm;
    private LinearLayout mBtnConfirm;
    private View mViewBtnLine;
    private ImageView mIcSign;
    private TextView mTvBtnSign;
    private LinearLayout mBtnSign;
    private LinearLayout mRcvSignLeader;
    private TextView mTvCommentCount;
    private LinearLayout mRcyCommentList;
    private ImageView mBtnVoice;
    private ImageView mBtnEmoji;
    private ImageView mBtnMore;
    private EditText mInputComment;
    private SwipeRefreshLayout mRefreshLayout;

    private WCManageMeetingInfo mManageMeetingInfo;
    private WCManageMeetingDetailInfo mMeetingDetailInfo;

    private WCManageMeetingPresenter mManageMeetingPresenter;
    private WCCommentPresenter mCommentPresenter;

    private int mCommentPageNo = 1;

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

        mManageMeetingInfo = (WCManageMeetingInfo) extras.getSerializable("manageMeetingInfo");
    }

    @Override
    protected void initView() {

        getTitleBar().setTitleText("会议详情");
        getTitleBar().setRightText("参与详情");

        mIvSponsorLogo = (RoundImageView) findViewById(R.id.img_sponsor_logo);
        mTvSponsorName = (TextView) findViewById(R.id.tv_sponsor_name);
        mTvCreateDate = (TextView) findViewById(R.id.tv_create_date);
        mTvMeetingContent = (TextView) findViewById(R.id.tv_meeting_content);
        mTvDeadline = (TextView) findViewById(R.id.tv_deadline);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mIcConfirm = (ImageView) findViewById(R.id.icon_confirm);
        mTvBtnConfirm = (TextView) findViewById(R.id.tv_btn_confirm_text);
        mBtnConfirm = (LinearLayout) findViewById(R.id.btn_confirm);
        mViewBtnLine = findViewById(R.id.btn_line);
        mIcSign = (ImageView) findViewById(R.id.icon_sign);
        mTvBtnSign = (TextView) findViewById(R.id.tv_btn_sign_text);
        mBtnSign = (LinearLayout) findViewById(R.id.btn_sign);
        mRcvSignLeader = (LinearLayout) findViewById(R.id.sign_leader_recycler);
        mTvCommentCount = (TextView) findViewById(R.id.tv_comment_count);
        mRcyCommentList = (LinearLayout) findViewById(R.id.comment_recycler);
        mBtnVoice = (ImageView) findViewById(R.id.btn_voice);
        mBtnEmoji = (ImageView) findViewById(R.id.btn_emoji);
        mBtnMore = (ImageView) findViewById(R.id.btn_more);
        mInputComment = (EditText) findViewById(R.id.input_comment);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        mIvSponsorLogo.setRectAdius(SizeUtils.dp2px(40));
    }

    @Override
    protected void afterView() {
        if (mManageMeetingInfo == null) {
            onBackPressed();
            return;
        }

        mCommentPresenter = new WCCommentPresenter(this);
        mCommentPresenter.attachView(this);

        mManageMeetingPresenter = new WCManageMeetingPresenter(this);
        mManageMeetingPresenter.attachView(this);

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

        attachRefreshLayout(mRefreshLayout, null);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mManageMeetingPresenter.getMeetingDetail(mManageMeetingInfo.getMeeting_id());
            }
        });

        initBaseInfo();
        mManageMeetingPresenter.getMeetingDetail(mManageMeetingInfo.getMeeting_id());
    }

    private void initBaseInfo() {

        ImageLoaderHelper.getInstance(getApplicationContext())
                .loadImage(mIvSponsorLogo, mManageMeetingInfo.getClub_avatar());
        mTvSponsorName.setText(mManageMeetingInfo.getClub_name());
        mTvCreateDate.setText(TimeUtils.millis2String(mManageMeetingInfo.getCreate_date(), "MMMdd日"));
        mTvMeetingContent.setText(mManageMeetingInfo.getContent());
        mTvDeadline.setText(TimeUtils.millis2String(mManageMeetingInfo.getDeadline(), "MMMdd日  HH:mm"));
        mTvAddress.setText(mManageMeetingInfo.getAddress());

        mIcConfirm.setVisibility(View.GONE);
        mIcSign.setVisibility(View.GONE);

        if (mManageMeetingInfo.getDeadline() < TimeUtils.getNowTimeMills()) {   // 未过期的会议

            String count = (mManageMeetingInfo.getTotal_count() - mManageMeetingInfo.getUnconfirm_count())
                    + "/" + mManageMeetingInfo.getTotal_count();
            mTvBtnConfirm.setText("提醒确认与会(" + count + ")");
            mTvBtnConfirm.setTextColor(getResources().getColor(R.color.themeColor));

            mBtnConfirm.setEnabled(true);
        } else {

            String count = (mManageMeetingInfo.getTotal_count() - mManageMeetingInfo.getUnconfirm_count())
                    + "/" + mManageMeetingInfo.getTotal_count();
            mTvBtnConfirm.setText("确认与会(" + count + ")");
            mTvBtnConfirm.setTextColor(getResources().getColor(R.color.colorCommonText_666));

            mBtnConfirm.setEnabled(false);
        }

        if (mManageMeetingInfo.getSign_type() == 0) {
            mViewBtnLine.setVisibility(View.GONE);
            mBtnSign.setVisibility(View.GONE);
        } else {
            mViewBtnLine.setVisibility(View.VISIBLE);
            mBtnSign.setVisibility(View.VISIBLE);

            if (mManageMeetingInfo.getTime_to_sign() == 0) {
                mTvBtnSign.setText("签到尚未开始");
                mTvBtnSign.setTextColor(getResources().getColor(R.color.colorCommonText_666));
                mBtnSign.setEnabled(false);
            } else {
                String count = mManageMeetingInfo.getAlready_sign_count() + "/" + mManageMeetingInfo.getTotal_count();
                mTvBtnSign.setText("实际签到(" + count + ")");
                mTvBtnSign.setTextColor(getResources().getColor(R.color.themeColor));
                mBtnSign.setEnabled(true);
            }
        }
    }

    private void initDetailInfo() {
        if (mMeetingDetailInfo == null) {
            log.e("initDetailInfo：mMeetingDetailInfo不能为空！");
            return;
        }

        ImageLoaderHelper.getInstance(getApplicationContext())
                .loadImage(mIvSponsorLogo, mMeetingDetailInfo.getAvatar_url());
        mTvSponsorName.setText(mMeetingDetailInfo.getClub_name());
        mTvCreateDate.setText(TimeUtils.millis2String(mMeetingDetailInfo.getCreate_date(), "MMMdd日"));
        mTvMeetingContent.setText(mMeetingDetailInfo.getContent());
        mTvDeadline.setText(TimeUtils.millis2String(mMeetingDetailInfo.getDeadline(), "MMMdd日  HH:mm"));
        mTvAddress.setText(mMeetingDetailInfo.getAddress());

        mIcConfirm.setVisibility(View.GONE);
        mIcSign.setVisibility(View.GONE);

        if (mMeetingDetailInfo.getDeadline() < TimeUtils.getNowTimeMills()) {   // 未过期的会议

            String count = (mMeetingDetailInfo.getTotal_count() - mManageMeetingInfo.getUnconfirm_count())
                    + "/" + mMeetingDetailInfo.getTotal_count();
            mTvBtnConfirm.setText("提醒确认与会(" + count + ")");
            mTvBtnConfirm.setTextColor(getResources().getColor(R.color.themeColor));

            mBtnConfirm.setEnabled(true);
        } else {

            String count = (mMeetingDetailInfo.getTotal_count() - mManageMeetingInfo.getUnconfirm_count())
                    + "/" + mMeetingDetailInfo.getTotal_count();
            mTvBtnConfirm.setText("确认与会(" + count + ")");
            mTvBtnConfirm.setTextColor(getResources().getColor(R.color.colorCommonText_666));

            mBtnConfirm.setEnabled(false);
        }

        if (mMeetingDetailInfo.getSign_type() == 0) {
            mViewBtnLine.setVisibility(View.GONE);
            mBtnSign.setVisibility(View.GONE);
        } else {
            mViewBtnLine.setVisibility(View.VISIBLE);
            mBtnSign.setVisibility(View.VISIBLE);

            if (mMeetingDetailInfo.getTime_to_sign() == 0) {
                mTvBtnSign.setText("签到尚未开始");
                mTvBtnSign.setTextColor(getResources().getColor(R.color.colorCommonText_666));
                mBtnSign.setEnabled(false);
            } else {
                String count = mMeetingDetailInfo.getAlready_sign_count() + "/" + mMeetingDetailInfo.getTotal_count();
                mTvBtnSign.setText("实际签到(" + count + ")");
                mTvBtnSign.setTextColor(getResources().getColor(R.color.themeColor));
                mBtnSign.setEnabled(true);
            }
        }

        mRcvSignLeader.removeAllViews();
        if (mMeetingDetailInfo.getLeader() != null) {
            for (Leader leader : mMeetingDetailInfo.getLeader()) {
                mRcvSignLeader.addView(getLeaderItemView(leader), 0);
            }
        }
    }

    private View getLeaderItemView(Leader leader) {
        View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_sign_leader_item, null);

        RoundImageView leaderLogo = (RoundImageView) itemView.findViewById(R.id.img_leader_logo);
        TextView leaderName = (TextView) itemView.findViewById(R.id.tv_leader_name);
        TextView leaderDepartment = (TextView) itemView.findViewById(R.id.tv_leader_department);
        TextView leaderClass = (TextView) itemView.findViewById(R.id.tv_leader_class);
        ImageView mobileCall = (ImageView) itemView.findViewById(R.id.btn_call);
        ImageView message = (ImageView) itemView.findViewById(R.id.btn_message);

        leaderLogo.setRectAdius(SizeUtils.dp2px(36));

        ImageLoaderHelper.getInstance(getApplicationContext()).loadImage(leaderLogo, leader.getAvatar_url());
        leaderName.setText(leader.getStudent_name());
        leaderDepartment.setText(leader.getDepartment_name() + "  " + leader.getJob_name());
        leaderClass.setText("待接口返回");

        mobileCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(leader.getStudent_name() + ": " + leader.getMobile());
            }
        });

        message.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(leader.getStudent_name() + ": " + leader.getStudent_id());
            }
        });

        return itemView;
    }

    private View getCommentView(WCCommentListInfo commentListInfo) {
        View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_comment_item, null);

        RoundImageView sponsorLogo = (RoundImageView) itemView.findViewById(R.id.img_sponsor_logo);
        sponsorLogo.setRectAdius(SizeUtils.dp2px(36));

        TextView sponsorName = (TextView) itemView.findViewById(R.id.tv_sponsor_name);
        TextView createDate = (TextView) itemView.findViewById(R.id.tv_create_date);
        TextView commentContent = (TextView) itemView.findViewById(R.id.tv_comment_content);

        ImageLoaderHelper.getInstance(getApplicationContext()).loadImage(sponsorLogo, commentListInfo.getStudent_avatar());
        sponsorName.setText(commentListInfo.getStudent_name());
        createDate.setText(TimeUtils.millis2String(commentListInfo.getCreate_date(), "MM-dd  HH:mm"));
        commentContent.setText(commentListInfo.getContent());

        return itemView;
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    protected void onClickRightTitle() {
        super.onClickRightTitle();
        Bundle extra = new Bundle();
        extra.putLong("meetingId", mManageMeetingInfo.getMeeting_id());
        showIntent(WCMeetingParticipationDetailActivity.class, extra);
    }

    @Override
    protected void unSubscribeObservable() {
    }

    @Override
    public void refreshMeetingList(ArrayList<WCManageMeetingInfo> list) {
    }

    @Override
    public void addMeetingList(ArrayList<WCManageMeetingInfo> list, boolean hasMore) {
    }

    @Override
    public void getMeetingDetailSuccess(WCManageMeetingDetailInfo meetingDetailInfo) {
        mMeetingDetailInfo = meetingDetailInfo;

        initDetailInfo();

        mCommentPageNo = 1;
        mCommentPresenter.getCommentList(WCConstantsUtil.DYNAMIC_TYPE_MEETING,
                meetingDetailInfo.getMeeting_id(), mCommentPageNo);
    }

    @Override
    public void getMeetingParticipationSuccess(WCMeetingParticipationBean participationBean) {
    }

    @Override
    public void refreshCommentList(ArrayList<WCCommentListInfo> list) {
        mRcyCommentList.removeAllViews();

        if (list != null) {
            for (WCCommentListInfo commentListInfo : list) {
                if (mRcyCommentList.getChildCount() == 0) {
                    mRcyCommentList.addView(getCommentView(commentListInfo), 0);
                } else {
                    mRcyCommentList.addView(getCommentView(commentListInfo), (mRcyCommentList.getChildCount() - 1));
                }
            }
        }

        mTvCommentCount.setText("共" + mRcyCommentList.getChildCount() + "条回复");

        mCommentPageNo ++;
    }

    @Override
    public void addCommentList(ArrayList<WCCommentListInfo> list, boolean hasMore) {
        if (list != null) {
            for (WCCommentListInfo commentListInfo : list) {
                mRcyCommentList.addView(getCommentView(commentListInfo), (mRcyCommentList.getChildCount() - 1));
            }
        }

        mTvCommentCount.setText("共" + mRcyCommentList.getChildCount() + "条回复");

        mCommentPageNo ++;
    }
}
