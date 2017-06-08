package com.mm.weclubs.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCMissionListInfo;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;
import com.mm.weclubs.widget.RoundImageView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/3 下午6:44
 * 描述:
 */

public class WCMissionListAdapter extends WCBaseRecyclerViewAdapter<WCMissionListInfo> {

    public WCMissionListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.view_dynamic_mission_item;
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

        ((RoundImageView) holder.getView(R.id.img_sponsor_logo)).setRectAdius(SizeUtils.dp2px(32));

        holder.setText(R.id.tv_sponsor_name, getItem(position).getSponsor().getSponsor_name());
        holder.setImage(R.id.img_sponsor_logo, getItem(position).getSponsor().getSponsor_avatar());
        holder.setText(R.id.tv_create_date, TimeUtils.millis2String(getItem(position).getCreate_date(), "MMMdd日"));
        holder.setText(R.id.tv_mission_content, getItem(position).getContent());
        holder.setText(R.id.tv_deadline, TimeUtils.millis2String(getItem(position).getDeadline(), "MMMdd日  HH:mm"));

        if (getItem(position).getConfirm_receive() > 0) {
            holder.setViewVisible(R.id.icon_confirm, View.VISIBLE);

            holder.setText(R.id.tv_btn_confirm_text, "已确认任务");
            ((TextView) holder.getView(R.id.tv_btn_confirm_text))
                    .setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));
            holder.getView(R.id.btn_confirm).setEnabled(false);
        } else {
            holder.setViewVisible(R.id.icon_confirm, View.GONE);

            holder.setText(R.id.tv_btn_confirm_text, "确认任务");
            ((TextView) holder.getView(R.id.tv_btn_confirm_text))
                    .setTextColor(mContext.getResources().getColor(R.color.themeColor));
            holder.getView(R.id.btn_confirm).setEnabled(true);
        }

        if (getItem(position).getFinish() == 1) {
            holder.setViewVisible(R.id.icon_finish, View.VISIBLE);

            holder.setText(R.id.tv_btn_finish_text, "已完成任务");
            ((TextView) holder.getView(R.id.tv_btn_finish_text))
                    .setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));
            holder.getView(R.id.btn_finish).setEnabled(false);
        } else {
            holder.setViewVisible(R.id.icon_finish, View.GONE);

            holder.setText(R.id.tv_btn_finish_text, "完成任务");
            ((TextView) holder.getView(R.id.tv_btn_finish_text))
                    .setTextColor(mContext.getResources().getColor(R.color.themeColor));
            holder.getView(R.id.btn_finish).setEnabled(true);
        }

        holder.setViewOnClick(R.id.btn_finish);
        holder.setViewOnClick(R.id.btn_confirm);
        holder.setViewOnClick(R.id.item_dynamic);
    }
}
