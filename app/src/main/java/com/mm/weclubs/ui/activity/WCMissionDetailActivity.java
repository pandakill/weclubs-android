package com.mm.weclubs.ui.activity;

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
import com.mm.weclubs.app.mission_list.WCMissionListPresenter;
import com.mm.weclubs.app.mission_list.WCMissionListView;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.pojo.WCCommentListInfo;
import com.mm.weclubs.data.pojo.WCMissionDetailInfo;
import com.mm.weclubs.data.pojo.WCMissionListInfo;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;

import java.util.ArrayList;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午2:20
 * 描述:
 */

public class WCMissionDetailActivity extends BaseActivity implements WCMissionListView, WCCommentView {

    private RoundImageView mIvSponsorLogo;
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
    private LinearLayout mRcyCommentList;
    private ImageView mBtnVoice;
    private ImageView mBtnEmoji;
    private ImageView mBtnMore;
    private EditText mInputComment;
    private SwipeRefreshLayout mRefreshLayout;

    private WCMissionListInfo mMissionListInfo;

    private WCMissionListPresenter mMissionListPresenter;
    private WCCommentPresenter mCommentPresenter;

    private WCMissionDetailInfo mMissionDetailInfo;

    private int mCommentPageNo = 1;

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
        mIvSponsorLogo = (RoundImageView) findViewById(R.id.img_sponsor_logo);
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
        mRcyCommentList = (LinearLayout) findViewById(R.id.comment_recycler);
        mBtnVoice = (ImageView) findViewById(R.id.btn_voice);
        mBtnEmoji = (ImageView) findViewById(R.id.btn_emoji);
        mBtnMore = (ImageView) findViewById(R.id.btn_more);
        mInputComment = (EditText) findViewById(R.id.input_comment);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        attachRefreshLayout(mRefreshLayout, null);

        mIvSponsorLogo.setRectAdius(SizeUtils.dp2px(40));
    }

    @Override
    protected void afterView() {
        if (mMissionListInfo == null) {
            onBackPressed();
            return;
        }

        mCommentPresenter = new WCCommentPresenter(this);
        mCommentPresenter.attachView(this);

        mMissionListPresenter = new WCMissionListPresenter(this);
        mMissionListPresenter.attachView(this);

        mBtnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mMissionListPresenter.setMissionConfirm(mMissionListInfo.getMission_id());
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

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMissionListPresenter.getMissionDetail(mMissionListInfo.getMission_id());
            }
        });

        initBaseInfo();
        mMissionListPresenter.getMissionDetail(mMissionListInfo.getMission_id());
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

    private void initDetailInfo() {
        if (mMissionDetailInfo == null) {
            log.e("initDetailInfo：mMissionDetailInfo 不能为空！");
            return;
        }

        ImageLoaderHelper.getInstance(getApplicationContext())
                .loadImage(mIvSponsorLogo, mMissionDetailInfo.getSponsor().getSponsor_avatar());
        mTvSponsorName.setText(mMissionDetailInfo.getSponsor().getSponsor_name());
        mTvCreateDate.setText(TimeUtils.millis2String(mMissionDetailInfo.getCreate_date(), "MMMdd日"));
        mTvMissionContent.setText(mMissionDetailInfo.getContent());
        mTvDeadline.setText(TimeUtils.millis2String(mMissionDetailInfo.getDeadline(), "MMMdd日  HH:mm"));


        if (mMissionDetailInfo.getConfirm_date() > 0) {
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

        if (mMissionDetailInfo.getFinish() == 1) {
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

    @Override
    public void refreshMissionList(ArrayList<WCMissionListInfo> list) {
    }

    @Override
    public void addMissionList(ArrayList<WCMissionListInfo> list, boolean hasMore) {
    }

    @Override
    public void getMissionDetailSuccess(WCMissionDetailInfo missionListInfo) {
        mMissionDetailInfo = missionListInfo;

        initDetailInfo();

        mCommentPresenter.getCommentList(WCConstantsUtil.DYNAMIC_TYPE_MISSION,
                mMissionListInfo.getMission_id(), mCommentPageNo);
    }

    @Override
    public void notifyChangeList(WCMissionListInfo missionListInfo, int position) {
        mIcConfirm.setVisibility(View.VISIBLE);

        mTvBtnConfirm.setText("已确认任务");
        mTvBtnConfirm.setTextColor(getResources().getColor(R.color.colorCommonText_666));

        mBtnConfirm.setEnabled(false);
    }
}
