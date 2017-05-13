package com.mm.weclubs.ui.adapter;

import android.content.Context;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCCommentListInfo;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;
import com.mm.weclubs.widget.RoundImageView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午6:17
 * 描述:
 */

public class WCCommentListAdapter extends WCBaseRecyclerViewAdapter<WCCommentListInfo> {

    public WCCommentListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.view_comment_item;
    }

    @Override
    protected void onBindDataToView(WCBaseViewHolder holder, int position) {

        ((RoundImageView) holder.getView(R.id.img_sponsor_logo)).setRectAdius(SizeUtils.dp2px(36));

        holder.setImage(R.id.img_sponsor_logo, getItem(position).getStudent_avatar());
        holder.setText(R.id.tv_sponsor_name, getItem(position).getStudent_name());
        holder.setText(R.id.tv_comment_content, getItem(position).getContent());
        holder.setText(R.id.tv_create_date,
                TimeUtils.millis2String(getItem(position).getCreate_date(), "MMMdd日  HH:mm"));
    }
}
