package com.mm.weclubs.ui.adapter.manage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCMeetingParticipationInfo;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;
import com.mm.weclubs.widget.RoundImageView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/16 下午11:57
 * 描述:  会议参与详情列表的适配器
 */
public class WCMeetingParticipationAdapter extends WCBaseRecyclerViewAdapter<WCMeetingParticipationInfo> {

    public WCMeetingParticipationAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.view_meeting_paticipation_item;
    }

    @Override
    protected void onBindDataToView(WCBaseViewHolder holder, int position) {

        holder.setViewVisible(R.id.line, View.VISIBLE);

        View itemVIew = holder.getView(R.id.item_meeting_participation);
        if (position == 0) {
            itemVIew.setPadding(0, SizeUtils.dp2px(12), 0, 0);
        } else if (position == (getItemCount() - 1)) {
            itemVIew.setPadding(0, 0, 0, SizeUtils.dp2px(12));
            holder.setViewVisible(R.id.line, View.GONE);
        }

        ((RoundImageView) holder.getView(R.id.img_student_logo)).setRectAdius(SizeUtils.dp2px(40));

        holder.setImage(R.id.img_student_logo, getItem(position).getAvatar_url());
        holder.setText(R.id.tv_student_name, getItem(position).getName());

        String department = getItem(position).getDepartment_name() + "  " + getItem(position).getJob_name();
        holder.setText(R.id.tv_student_department, department);

        holder.setText(R.id.tv_create_date, TimeUtils.millis2String(getItem(position).getDynamic_date(), "MMMdd日  HH:mm"));

        TextView confirmText = holder.getView(R.id.tv_confirm_status);
        Drawable confirmDrawable = mContext.getResources().getDrawable(R.mipmap.sign_ic_uncertain);
        confirmDrawable.setBounds(0, 0, confirmDrawable.getMinimumWidth(), confirmDrawable.getMinimumHeight());
        if (getItem(position).getIs_confirm() == 1) {   // 已经确认
            confirmText.setText("确认");
            confirmText.setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));
            confirmDrawable = mContext.getResources().getDrawable(R.mipmap.sign_ic_sure);
            confirmDrawable.setBounds(0, 0, confirmDrawable.getMinimumWidth(), confirmDrawable.getMinimumHeight());
            confirmText.setCompoundDrawables(confirmDrawable, null, null, null);
        } else if (getItem(position).getIs_leave() == 1) {  // 请假
            confirmText.setText("请假");
            confirmText.setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));
            confirmDrawable = mContext.getResources().getDrawable(R.mipmap.sign_ic_leave);
            confirmDrawable.setBounds(0, 0, confirmDrawable.getMinimumWidth(), confirmDrawable.getMinimumHeight());
            confirmText.setCompoundDrawables(confirmDrawable, null, null, null);
        } else {    // 尚未确认
            confirmText.setText("确认");
            confirmText.setTextColor(mContext.getResources().getColor(R.color.colorCommonText_c3c3c3));
            confirmText.setCompoundDrawables(confirmDrawable, null, null, null);
        }

        TextView signText = holder.getView(R.id.tv_confirm_status);
        Drawable signDrawable = mContext.getResources().getDrawable(R.mipmap.sign_ic_uncertain);
        signDrawable.setBounds(0, 0, confirmDrawable.getMinimumWidth(), confirmDrawable.getMinimumHeight());
        if (getItem(position).getIs_sign() == 1) {  // 已经签到
            signText.setText("签到");
            signText.setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));
            signDrawable = mContext.getResources().getDrawable(R.mipmap.sign_ic_sure);
            signDrawable.setBounds(0, 0, confirmDrawable.getMinimumWidth(), confirmDrawable.getMinimumHeight());
            confirmText.setCompoundDrawables(signDrawable, null, null, null);
        } else if (getItem(position).getIs_leave() == 1) {  // 迟到
            signText.setText("迟到");
            signText.setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));
            signDrawable = mContext.getResources().getDrawable(R.mipmap.sign_ic_late);
            signDrawable.setBounds(0, 0, confirmDrawable.getMinimumWidth(), confirmDrawable.getMinimumHeight());
            confirmText.setCompoundDrawables(signDrawable, null, null, null);
        } else {    // 尚未签到
            signText.setText("签到");
            signText.setTextColor(mContext.getResources().getColor(R.color.colorCommonText_c3c3c3));
            confirmText.setCompoundDrawables(signDrawable, null, null, null);
        }
    }
}
