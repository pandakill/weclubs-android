package com.mm.weclubs.ui.adapter.manage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCManageNotifyInfo;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;
import com.mm.weclubs.widget.RoundImageView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午11:48
 * 描述:
 */

public class WCManageNotifyAdapter extends WCBaseRecyclerViewAdapter<WCManageNotifyInfo> {

    public WCManageNotifyAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.view_dynamic_notify_list_item;
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

        holder.setText(R.id.tv_sponsor_name, getItem(position).getClub_name());
        holder.setText(R.id.tv_notify_content, getItem(position).getContent());
        holder.setImage(R.id.img_sponsor_logo, getItem(position).getClub_avatar());
        holder.setText(R.id.tv_create_date, TimeUtils.millis2String(getItem(position).getCreate_date(), "MMMdd日"));

        if (getItem(position).getUnread_count() == 0) {
            holder.setViewVisible(R.id.icon_receive, View.VISIBLE);
            holder.setText(R.id.tv_btn_receive_text, "所有成员都已查看该通知(" + getItem(position).getTotal_count() + ")");

            holder.getView(R.id.btn_receive).setEnabled(false);
            ((TextView) holder.getView(R.id.tv_btn_receive_text))
                    .setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));

            holder.getView(R.id.btn_receive).setEnabled(false);
        } else {
            holder.setViewVisible(R.id.icon_receive, View.GONE);

            String count = getItem(position).getUnread_count() + "/" + getItem(position).getTotal_count();
            holder.setText(R.id.tv_btn_receive_text, "再次提醒未查看成员(" + count + ")");

            holder.getView(R.id.btn_receive).setEnabled(true);
            ((TextView) holder.getView(R.id.tv_btn_receive_text))
                    .setTextColor(mContext.getResources().getColor(R.color.themeColor));

            holder.getView(R.id.btn_receive).setEnabled(true);
        }

        holder.setViewOnClick(R.id.item_dynamic);
        holder.setViewOnClick(R.id.btn_receive);
    }
}
