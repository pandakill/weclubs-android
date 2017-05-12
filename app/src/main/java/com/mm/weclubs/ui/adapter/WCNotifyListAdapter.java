package com.mm.weclubs.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCNotifyListInfo;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/12 上午11:13
 * 描述:
 */

public class WCNotifyListAdapter extends WCBaseRecyclerViewAdapter<WCNotifyListInfo> {

    public WCNotifyListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.view_notify_list_item;
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
        holder.setText(R.id.tv_notify_content, getItem(position).getContent());
        holder.setImage(R.id.img_sponsor_logo, getItem(position).getSponsor().getSponsor_avatar());
        holder.setText(R.id.tv_create_date, TimeUtils.millis2String(getItem(position).getCreate_date(), "MMMdd日"));

        if (getItem(position).getConfirm_receive() == 1) {
            holder.setViewVisible(R.id.icon_receive, View.VISIBLE);
            holder.setText(R.id.tv_btn_receive_text, "已确认收到");

            holder.getView(R.id.btn_receive).setEnabled(false);
            ((TextView) holder.getView(R.id.tv_btn_receive_text))
                    .setTextColor(mContext.getResources().getColor(R.color.colorCommonText_666));
        } else {
            holder.setViewVisible(R.id.icon_receive, View.GONE);
            holder.setText(R.id.tv_btn_receive_text, "确认收到");

            holder.getView(R.id.btn_receive).setEnabled(true);
            ((TextView) holder.getView(R.id.tv_btn_receive_text))
                    .setTextColor(mContext.getResources().getColor(R.color.themeColor));
        }
    }
}
