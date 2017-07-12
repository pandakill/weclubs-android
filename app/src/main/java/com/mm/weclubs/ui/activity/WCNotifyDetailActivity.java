package com.mm.weclubs.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.mm.weclubs.app.notify_detail.WCNotifyDetailContract;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.network.pojo.WCCommentListInfo;
import com.mm.weclubs.data.network.pojo.WCNotifyListInfo;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;
import com.socks.library.KLog;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午2:20
 * 描述:
 */

public class WCNotifyDetailActivity extends BaseActivity implements WCNotifyDetailContract.View {

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

    private WCNotifyListInfo mNotifyListInfo;

    @Inject
    WCNotifyDetailContract.Presenter<WCNotifyDetailContract.View> mPresenter;

    @Inject
    ImageLoaderHelper mImageLoaderHelper;

    private int mCommentPageNo = 1;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_dynamic_notify_detail;
    }

    @Override
    protected void getBundleExtras(@NonNull Bundle extras) {
        mNotifyListInfo = (WCNotifyListInfo) extras.getSerializable("notifyListInfo");
    }

    @Override
    protected void initView() {
        getActivityComponent().inject(this);

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
    }

    @Override
    protected void afterView() {
        if (mNotifyListInfo == null) {
            onBackPressed();
            return;
        }

        mPresenter.attachView(this);

        mBtnReceive.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.setNotifyConfirm(mNotifyListInfo.getNotify_id());
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
                mPresenter.getNotifyDetail(mNotifyListInfo.getNotify_id());
            }
        });

        initBaseInfo();
        mPresenter.getNotifyDetail(mNotifyListInfo.getNotify_id());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initBaseInfo() {
        mImageLoaderHelper
                .loadImage(mIvSponsorLogo, mNotifyListInfo.getSponsor().getSponsor_avatar());
        mTvSponsorName.setText(mNotifyListInfo.getSponsor().getSponsor_name());
        mTvCreateDate.setText(TimeUtils.millis2String(mNotifyListInfo.getCreate_date(), "MMMdd日"));
        mTvNotifyContent.setText(mNotifyListInfo.getContent());


        if (mNotifyListInfo.getConfirm_receive() == 1) {
            mIcReceive.setVisibility(View.VISIBLE);

            mTvBtnReceive.setText("已确认收到");
            mTvBtnReceive.setTextColor(getResources().getColor(R.color.colorCommonText_666));

            mBtnReceive.setEnabled(false);
        } else {
            mIcReceive.setVisibility(View.GONE);

            mTvBtnReceive.setText("确认收到");
            mTvBtnReceive.setTextColor(getResources().getColor(R.color.themeColor));

            mBtnReceive.setEnabled(true);
        }
    }

    private void initDetailInfo() {
        if (mNotifyListInfo == null) {
            KLog.e("initDetailInfo：mNotifyListInfo 不能为空！");
            return;
        }

        mImageLoaderHelper
                .loadImage(mIvSponsorLogo, mNotifyListInfo.getSponsor().getSponsor_avatar());
        mTvSponsorName.setText(mNotifyListInfo.getSponsor().getSponsor_name());
        mTvCreateDate.setText(TimeUtils.millis2String(mNotifyListInfo.getCreate_date(), "MMMdd日"));
        mTvNotifyContent.setText(mNotifyListInfo.getContent());


        if (mNotifyListInfo.getConfirm_receive() == 1) {
            mIcReceive.setVisibility(View.VISIBLE);

            mTvBtnReceive.setText("已确认收到");
            mTvBtnReceive.setTextColor(getResources().getColor(R.color.colorCommonText_666));

            mBtnReceive.setEnabled(false);
        } else {
            mIcReceive.setVisibility(View.GONE);

            mTvBtnReceive.setText("确认收到");
            mTvBtnReceive.setTextColor(getResources().getColor(R.color.themeColor));

            mBtnReceive.setEnabled(true);
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

    @Override
    public void getNotifyDetailSuccess(@NonNull WCNotifyListInfo notifyListInfo) {
        mNotifyListInfo = notifyListInfo;

        initDetailInfo();

        mPresenter.getCommentList(WCConstantsUtil.DYNAMIC_TYPE_NOTIFY,
                mNotifyListInfo.getNotify_id(), mCommentPageNo);
    }

    @Override
    public void notifyChangeList(WCNotifyListInfo notifyListInfo, int position) {
        mIcReceive.setVisibility(View.VISIBLE);

        mTvBtnReceive.setText("已确认收到");
        mTvBtnReceive.setTextColor(getResources().getColor(R.color.colorCommonText_666));

        mBtnReceive.setEnabled(false);
    }
}
