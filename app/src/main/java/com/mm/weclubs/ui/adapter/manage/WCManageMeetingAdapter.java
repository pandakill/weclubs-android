package com.mm.weclubs.ui.adapter.manage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCManageMeetingInfo;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;
import com.mm.weclubs.widget.RoundImageView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:48
 * 描述:
 */

public class WCManageMeetingAdapter extends WCBaseRecyclerViewAdapter<WCManageMeetingInfo> {

    public WCManageMeetingAdapter(Context context) {
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

        ((RoundImageView) holder.getView(R.id.img_sponsor_logo)).setRectAdius(SizeUtils.dp2px(32));

        holder.setText(R.id.tv_sponsor_name, getItem(position).getClub_name());
        holder.setImage(R.id.img_sponsor_logo, getItem(position).getClub_avatar());
        holder.setText(R.id.tv_create_date, TimeUtils.millis2String(getItem(position).getCreate_date(), "MMMdd日"));
        holder.setText(R.id.tv_meeting_content, getItem(position).getContent());
        holder.setText(R.id.tv_deadline, TimeUtils.millis2String(getItem(position).getDeadline(), "MMMdd日  HH:mm"));
        holder.setText(R.id.tv_address, getItem(position).getAddress());

        holder.setViewVisible(R.id.icon_confirm, View.GONE);
        holder.setViewVisible(R.id.icon_sign, View.GONE);

        if (getItem(position).getUnconfirm_count() > 0) {   // 未确认的

            String count = (getItem(position).getTotal_count() - getItem(position).getUnconfirm_count())
                    + "/" + getItem(position).getTotal_count();

            holder.setText(R.id.tv_btn_confirm_text, "提醒确认与会(" + count + ")");
            ((TextView) holder.getView(R.id.tv_btn_confirm_text))
                    .setTextColor(mContext.getResources().getColor(R.color.themeColor));
        } else {
            holder.setViewVisible(R.id.icon_confirm, View.GONE);

            String count = (getItem(position).getTotal_count() - getItem(position).getUnconfirm_count())
                    + "/" + getItem(position).getTotal_count();

            holder.setText(R.id.tv_btn_confirm_text, "确认与会(" + count + ")");
            ((TextView) holder.getView(R.id.tv_btn_confirm_text))
                    .setTextColor(mContext.getResources().getColor(R.color.themeColor));
        }

        if (getItem(position).getSign_type() == 0) {    // 不需要签到，隐藏按钮
            holder.setViewVisible(R.id.btn_line, View.GONE);
            holder.setViewVisible(R.id.btn_sign, View.GONE);
        } else {    // 需要签到的情况
            holder.setViewVisible(R.id.btn_line, View.VISIBLE);
            holder.setViewVisible(R.id.btn_sign, View.VISIBLE);

            if (getItem(position).getTime_to_sign() == 0) {

                holder.setText(R.id.tv_btn_sign_text, "签到尚未开始");
                ((TextView) holder.getView(R.id.tv_btn_sign_text))
                        .setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));
            } else {
                String count = getItem(position).getAlready_sign_count() + "/" + getItem(position).getTotal_count();

                holder.setText(R.id.tv_btn_sign_text, "实际签到(" + count + ")");
                ((TextView) holder.getView(R.id.tv_btn_sign_text))
                        .setTextColor(mContext.getResources().getColor(R.color.themeColor));
            }
        }

        holder.setViewOnClick(R.id.btn_confirm);
        holder.setViewOnClick(R.id.btn_sign);
        holder.setViewOnClick(R.id.item_dynamic);
    }
}
