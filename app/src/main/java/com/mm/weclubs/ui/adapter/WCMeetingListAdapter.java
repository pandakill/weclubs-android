package com.mm.weclubs.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCMeetingListInfo;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/3 下午6:44
 * 描述:
 */

public class WCMeetingListAdapter extends WCBaseRecyclerViewAdapter<WCMeetingListInfo> {

    public WCMeetingListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.view_dynamic_meeting_item;
    }

    @Override
    protected void onBindDataToView(WCBaseViewHolder holder, int position) {
        View itemView = holder.getView(R.id.item_dynamic);
        if (position == 0) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.topMargin = ConvertUtils.dp2px(16);
            itemView.setLayoutParams(params);
        } else if (position == (getItems().size() - 1)) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.bottomMargin = ConvertUtils.dp2px(16);
            itemView.setLayoutParams(params);
        }

        holder.setText(R.id.tv_sponsor_name, getItem(position).getSponsor().getSponsor_name());
        holder.setImage(R.id.img_sponsor_logo, getItem(position).getSponsor().getSponsor_avatar());
        holder.setText(R.id.tv_create_date, TimeUtils.millis2String(getItem(position).getCreate_date(), "MMMdd日"));
        holder.setText(R.id.tv_mission_content, getItem(position).getContent());
        holder.setText(R.id.tv_deadline, TimeUtils.millis2String(getItem(position).getDeadline(), "MMMdd日  HH:mm"));
        holder.setText(R.id.tv_address, getItem(position).getAddress());

        if (getItem(position).getConfirm_join() == 1) {
            holder.setViewVisible(R.id.icon_confirm, View.VISIBLE);

            holder.setText(R.id.tv_btn_confirm_text, "已确认参与");
            ((TextView) holder.getView(R.id.tv_btn_confirm_text))
                    .setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));
        } else {
            holder.setViewVisible(R.id.icon_confirm, View.GONE);

            holder.setText(R.id.tv_btn_confirm_text, "确认参与");
            ((TextView) holder.getView(R.id.tv_btn_confirm_text))
                    .setTextColor(mContext.getResources().getColor(R.color.themeColor));
        }

        if (getItem(position).getHas_sign() == 1) {
            holder.setViewVisible(R.id.icon_sign, View.VISIBLE);

            holder.setText(R.id.tv_btn_sign_text, "已完成签到");
            ((TextView) holder.getView(R.id.tv_btn_sign_text))
                    .setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));
        } else {
            holder.setViewVisible(R.id.icon_sign, View.GONE);

            holder.setText(R.id.tv_btn_sign_text, "扫一扫签到");
            ((TextView) holder.getView(R.id.tv_btn_sign_text))
                    .setTextColor(mContext.getResources().getColor(R.color.themeColor));
        }

        holder.setViewOnClick(R.id.btn_confirm);
        holder.setViewOnClick(R.id.btn_sign);
    }
}
