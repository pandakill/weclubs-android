package com.mm.weclubs.ui.adapter.manage;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.StringUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.network.pojo.WCMeetingParticipationInfo;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.TextCallback;
import xyz.zpayh.adapter.ViewCallback;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/16 下午11:57
 * 描述:  会议参与详情列表的适配器
 */
public class WCMeetingParticipationAdapter extends BaseAdapter<WCMeetingParticipationInfo> {

    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_meeting_paticipation_item;
    }

    @Override
    public void convert(final BaseViewHolder holder,final WCMeetingParticipationInfo data,final int index) {
        holder.setVisibility(R.id.line, View.VISIBLE);

        holder.setView(R.id.item_meeting_participation, new ViewCallback() {
            @Override
            public void callback(@NonNull View view) {
                if (index == 0) {
                    view.setPadding(0, SizeUtils.dp2px(12), 0, 0);
                } else if (index == (getData().size() - 1)) {
                    view.setPadding(0, 0, 0, SizeUtils.dp2px(12));
                    holder.setVisibility(R.id.line, View.GONE);
                }
            }
        }).setView(R.id.img_student_logo, new ViewCallback<RoundImageView>() {
            @Override
            public void callback(@NonNull RoundImageView view) {
                view.setRectAdius(SizeUtils.dp2px(40));
                ImageLoaderHelper.getInstance(view.getContext())
                        .loadImage(view,data.getAvatar_url());
            }
        }).setText(R.id.tv_student_name, data.getName())
                .setText(R.id.tv_student_department,data.getDepartment_name() + "  " + data.getJob_name())
                .setText(R.id.tv_create_date, TimeUtils.millis2String(data.getDynamic_date(), "MMMdd日  HH:mm"))
        .setText(R.id.tv_confirm_status, new TextCallback() {
            @Override
            public void callback(@NonNull TextView confirmText) {
                Drawable confirmDrawable = confirmText.getResources().getDrawable(R.mipmap.sign_ic_uncertain);
                confirmDrawable.setBounds(0, 0, confirmDrawable.getMinimumWidth(), confirmDrawable.getMinimumHeight());
                if (data.getIs_leave() == 1) {  // 请假
                    confirmText.setText("请假");
                    confirmText.setTextColor(confirmText.getResources().getColor(R.color.colorCommonText_666));
                    confirmDrawable = confirmText.getResources().getDrawable(R.mipmap.sign_ic_leave);
                    confirmDrawable.setBounds(0, 0, confirmDrawable.getMinimumWidth(), confirmDrawable.getMinimumHeight());
                    confirmText.setCompoundDrawables(confirmDrawable, null, null, null);
                } else if (data.getIs_confirm() == 1) {   // 已经确认
                    confirmText.setText("确认");
                    confirmText.setTextColor(confirmText.getResources().getColor(R.color.colorCommonText_666));
                    confirmDrawable = confirmText.getResources().getDrawable(R.mipmap.sign_ic_sure);
                    confirmDrawable.setBounds(0, 0, confirmDrawable.getMinimumWidth(), confirmDrawable.getMinimumHeight());
                    confirmText.setCompoundDrawables(confirmDrawable, null, null, null);
                } else {    // 尚未确认
                    confirmText.setText("确认");
                    confirmText.setTextColor(confirmText.getResources().getColor(R.color.colorCommonText_c3c3c3));
                    confirmText.setCompoundDrawables(confirmDrawable, null, null, null);
                }
            }
        })
        .setText(R.id.tv_sign_status, new TextCallback() {
            @Override
            public void callback(@NonNull TextView signText) {
                Drawable signDrawable = signText.getResources().getDrawable(R.mipmap.sign_ic_uncertain);
                signDrawable.setBounds(0, 0, signDrawable.getMinimumWidth(), signDrawable.getMinimumHeight());
                if (data.getIs_late() == 1) {  // 迟到
                    signText.setText("迟到");
                    signText.setTextColor(signText.getResources().getColor(R.color.colorCommonText_666));
                    signDrawable = signText.getResources().getDrawable(R.mipmap.sign_ic_late);
                    signDrawable.setBounds(0, 0, signDrawable.getMinimumWidth(), signDrawable.getMinimumHeight());
                    signText.setCompoundDrawables(signDrawable, null, null, null);
                } else if (data.getIs_sign() == 1) {  // 已经签到
                    signText.setText("签到");
                    signText.setTextColor(signText.getResources().getColor(R.color.colorCommonText_666));
                    signDrawable = signText.getResources().getDrawable(R.mipmap.sign_ic_sure);
                    signDrawable.setBounds(0, 0, signDrawable.getMinimumWidth(), signDrawable.getMinimumHeight());
                    signText.setCompoundDrawables(signDrawable, null, null, null);
                } else {    // 尚未签到
                    signText.setText("签到");
                    signText.setTextColor(signText.getResources().getColor(R.color.colorCommonText_c3c3c3));
                    signText.setCompoundDrawables(signDrawable, null, null, null);
                }
            }
        });

        if (!StringUtils.isEmpty(data.getComment())) {
            holder.setVisibility(R.id.tv_mark, View.VISIBLE)
                    .setText(R.id.tv_mark, data.getComment());
        } else {
            holder.setVisibility(R.id.tv_mark, View.GONE);
        }
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {

    }
}
