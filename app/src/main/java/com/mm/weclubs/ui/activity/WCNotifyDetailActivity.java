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
import com.mm.weclubs.data.pojo.WCNotifyListInfo;
import com.mm.weclubs.util.ImageLoaderHelper;

import me.fangx.haorefresh.HaoRecyclerView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午2:20
 * 描述:
 */

public class WCNotifyDetailActivity extends BaseActivity {

    private ImageView mIvSponsorLogo;
    private TextView mTvSponsorName;
    private TextView mTvCreateDate;
    private TextView mTvNotifyContent;
    private ImageView mIcReceive;
    private TextView mTvBtnReceive;
    private LinearLayout mBtnReceive;
    private TextView mTvCommentCount;
    private HaoRecyclerView mRcyCommentList;
    private ImageView mBtnVoice;
    private ImageView mBtnEmoji;
    private ImageView mBtnMore;
    private EditText mInputComment;

    private WCNotifyListInfo mNotifyListInfo;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_dynamic_notify_detail;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        if (extras == null) {
            log.e("extras不能为空");
            onBackPressed();
            return;
        }

        mNotifyListInfo = (WCNotifyListInfo) extras.getSerializable("notifyListInfo");
    }

    @Override
    protected void initView() {
        mIvSponsorLogo = (ImageView) findViewById(R.id.img_sponsor_logo);
        mTvSponsorName = (TextView) findViewById(R.id.tv_sponsor_name);
        mTvCreateDate = (TextView) findViewById(R.id.tv_create_date);
        mTvNotifyContent = (TextView) findViewById(R.id.tv_notify_content);
        mIcReceive = (ImageView) findViewById(R.id.icon_receive);
        mTvBtnReceive = (TextView) findViewById(R.id.tv_btn_receive_text);
        mBtnReceive = (LinearLayout) findViewById(R.id.btn_receive);
        mTvCommentCount = (TextView) findViewById(R.id.tv_comment_count);
        mRcyCommentList = (HaoRecyclerView) findViewById(R.id.comment_recycler);
        mBtnVoice = (ImageView) findViewById(R.id.btn_voice);
        mBtnEmoji = (ImageView) findViewById(R.id.btn_emoji);
        mBtnMore = (ImageView) findViewById(R.id.btn_more);
        mInputComment = (EditText) findViewById(R.id.input_comment);
    }

    @Override
    protected void afterView() {
        if (mNotifyListInfo == null) {
            onBackPressed();
            return;
        }

        mBtnReceive.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast(mTvBtnReceive.getText().toString());
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

    @Override
    protected void unSubscribeObservable() {

    }
}
