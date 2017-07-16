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
import com.mm.weclubs.app.base.BaseActivity;
import com.mm.weclubs.app.manage.notify.WCManageNotifyDetailContract;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.network.pojo.WCCommentListInfo;
import com.mm.weclubs.data.network.pojo.WCManageNotifyInfo;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/17 下午4:58
 * 描述:
 */

public class WCNotifyManageDetailActivity extends BaseActivity implements WCManageNotifyDetailContract.View{

    private RoundImageView mIvSponsorLogo;
    private TextView mTvSponsorName;
    private TextView mTvCreateDate;
    private TextView mTvNotifyContent;
    private ImageView mIcReceive;
    private TextView mTvBtnReceive;
    private LinearLayout mBtnReceive;
    private TextView mTvCommentCount;
    private LinearLayout mRcyCommentList;
    private ImageView mBtnVoice;
    private ImageView mBtnEmoji;
    private ImageView mBtnMore;
    private EditText mInputComment;
    private SwipeRefreshLayout mRefreshLayout;

    private WCManageNotifyInfo mManageNotifyInfo;

    @Inject
    WCManageNotifyDetailContract.Presenter<WCManageNotifyDetailContract.View> mPresenter;

    @Inject
    ImageLoaderHelper mImageLoaderHelper;

    private int mCommentPageNo = 1;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_dynamic_notify_detail;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras == null) {
            new IllegalArgumentException("extras 不能为空").printStackTrace();
            return;
        }

        mManageNotifyInfo = (WCManageNotifyInfo) extras.getSerializable("manageNotifyInfo");
    }

    @Override
    protected void onClickRightTitle() {
        super.onClickRightTitle();

        Bundle extra = new Bundle();
        extra.putLong("notifyId", mManageNotifyInfo.getNotify_id());
        showIntent(WCNotifyReceiveStatusActivity.class, extra);
    }

    @Override
    protected void initView() {
        getActivityComponent().inject(this);
        getTitleBar().setTitleText("通知详情");
        getTitleBar().setRightText("确认详情");

        mIvSponsorLogo = (RoundImageView) findViewById(R.id.img_sponsor_logo);
        mTvSponsorName = (TextView) findViewById(R.id.tv_sponsor_name);
        mTvCreateDate = (TextView) findViewById(R.id.tv_create_date);
        mTvNotifyContent = (TextView) findViewById(R.id.tv_notify_content);
        mIcReceive = (ImageView) findViewById(R.id.icon_receive);
        mTvBtnReceive = (TextView) findViewById(R.id.tv_btn_receive_text);
        mBtnReceive = (LinearLayout) findViewById(R.id.btn_receive);
        mTvCommentCount = (TextView) findViewById(R.id.tv_comment_count);
        mRcyCommentList = (LinearLayout) findViewById(R.id.comment_recycler);
        mBtnVoice = (ImageView) findViewById(R.id.btn_voice);
        mBtnEmoji = (ImageView) findViewById(R.id.btn_emoji);
        mBtnMore = (ImageView) findViewById(R.id.btn_more);
        mInputComment = (EditText) findViewById(R.id.input_comment);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        attachRefreshLayout(mRefreshLayout, null);

        mIvSponsorLogo.setRectAdius(SizeUtils.dp2px(40));

        mPresenter.attachView(this);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getNotifyDetailFromServer(mManageNotifyInfo.getNotify_id());
            }
        });
    }

    private void initBaseInfo() {
        mImageLoaderHelper
                .loadImage(mIvSponsorLogo, mManageNotifyInfo.getClub_avatar());
        mTvSponsorName.setText(mManageNotifyInfo.getClub_name());
        mTvCreateDate.setText(TimeUtils.millis2String(mManageNotifyInfo.getCreate_date(), "MMMdd日"));
        mTvNotifyContent.setText(mManageNotifyInfo.getContent());

        if (mManageNotifyInfo.getUnread_count() == 0) {
            mIcReceive.setVisibility(View.VISIBLE);

            mTvBtnReceive.setText("所有成员都已查看该通知(" + mManageNotifyInfo.getTotal_count() + ")");
            mTvBtnReceive.setTextColor(getResources().getColor(R.color.colorCommonText_666));

            mBtnReceive.setEnabled(false);
        } else {
            mIcReceive.setVisibility(View.GONE);

            String count = mManageNotifyInfo.getUnread_count() + "/" + mManageNotifyInfo.getTotal_count();

            mTvBtnReceive.setText("再次提醒未查看成员(" + count + ")");
            mTvBtnReceive.setTextColor(getResources().getColor(R.color.themeColor));

            mBtnReceive.setEnabled(true);
        }
    }

    @Override
    protected void afterView() {

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

        mPresenter.getNotifyDetailFromServer(mManageNotifyInfo.getNotify_id());
    }

    @Override
    protected boolean leftBtnIsReturn() {
        return true;
    }

    @Override
    public void getNotifyDetailSuccess(WCManageNotifyInfo notifyInfo) {

        mManageNotifyInfo = notifyInfo;
        initBaseInfo();

        mPresenter.getCommentList(WCConstantsUtil.DYNAMIC_TYPE_NOTIFY
                , mManageNotifyInfo.getNotify_id(), mCommentPageNo);
    }

    private View getCommentView(WCCommentListInfo commentListInfo) {
        View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.view_comment_item, null);

        RoundImageView sponsorLogo = (RoundImageView) itemView.findViewById(R.id.img_sponsor_logo);
        sponsorLogo.setRectAdius(SizeUtils.dp2px(36));

        TextView sponsorName = (TextView) itemView.findViewById(R.id.tv_sponsor_name);
        TextView createDate = (TextView) itemView.findViewById(R.id.tv_create_date);
        TextView commentContent = (TextView) itemView.findViewById(R.id.tv_comment_content);

        mImageLoaderHelper.loadImage(sponsorLogo, commentListInfo.getStudent_avatar());
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
}
