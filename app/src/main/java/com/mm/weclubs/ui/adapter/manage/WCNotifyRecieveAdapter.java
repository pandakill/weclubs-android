package com.mm.weclubs.ui.adapter.manage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCNotifyRecieveStatusInfo;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;
import com.mm.weclubs.widget.RoundImageView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/17 下午6:38
 * 描述:
 */

public class WCNotifyRecieveAdapter extends WCBaseRecyclerViewAdapter<WCNotifyRecieveStatusInfo> {

    public WCNotifyRecieveAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.view_meeting_paticipation_item;
    }

    @Override
    protected void onBindDataToView(WCBaseViewHolder holder, int position) {
        holder.setViewVisible(R.id.tv_confirm_status, View.GONE);
        holder.setViewVisible(R.id.tv_mark, View.GONE);

        ((RoundImageView) holder.getView(R.id.img_student_logo)).setRectAdius(SizeUtils.dp2px(40));

        holder.setImage(R.id.img_student_logo, getItem(position).getStudent_avatar());
        holder.setText(R.id.tv_student_name, getItem(position).getStudent_name());

        // TODO: 2017/5/17
//        String department = getItem(position).getDepartment_name() + "  " + getItem(position).getJob_name();
        String department = "TODO";
        holder.setText(R.id.tv_student_department, department);

        holder.setText(R.id.tv_create_date, TimeUtils.millis2String(getItem(position).getCreate_date(), "MMMdd日  HH:mm"));

        holder.setText(R.id.tv_sign_status, "已查看");
        TextView receiveText = holder.getView(R.id.tv_sign_status);
        Drawable receiveDrawable = mContext.getResources().getDrawable(R.mipmap.sign_ic_uncertain);
        receiveDrawable.setBounds(0, 0, receiveDrawable.getMinimumWidth(), receiveDrawable.getMinimumHeight());

        if (getItem(position).getIs_confirm() == 1) {
            receiveDrawable = mContext.getResources().getDrawable(R.mipmap.sign_ic_sure);
            receiveDrawable.setBounds(0, 0, receiveDrawable.getMinimumWidth(), receiveDrawable.getMinimumHeight());
            receiveText.setCompoundDrawables(receiveDrawable, null, null, null);
            holder.setTextColor(R.id.tv_sign_status, mContext.getResources().getColor(R.color.colorCommonText));
        } else {
            receiveText.setCompoundDrawables(receiveDrawable, null, null, null);
            holder.setTextColor(R.id.tv_sign_status, mContext.getResources().getColor(R.color.colorCommonText_666));
        }
    }
}
