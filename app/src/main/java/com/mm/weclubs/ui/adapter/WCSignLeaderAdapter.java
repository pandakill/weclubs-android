package com.mm.weclubs.ui.adapter;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCMeetingDetailInfo.Leader;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;
import com.mm.weclubs.widget.RoundImageView;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/13 下午6:22
 * 描述:
 */

public class WCSignLeaderAdapter extends WCBaseRecyclerViewAdapter<Leader> {

    public WCSignLeaderAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.view_sign_leader_item;
    }

    @Override
    protected void onBindDataToView(WCBaseViewHolder holder, int position) {
        ((RoundImageView) holder.getView(R.id.img_leader_logo)).setRectAdius(SizeUtils.dp2px(40));

        holder.setImage(R.id.img_leader_logo, getItem(position).getAvatar_url());
        holder.setText(R.id.tv_leader_name, getItem(position).getStudent_name());

        String jobName = getItem(position).getDepartment_name() + "  " + getItem(position).getJob_name();
        holder.setText(R.id.tv_leader_department,jobName);

        // TODO: 2017/5/13 需要设置学生的班级名称
        holder.setText(R.id.tv_leader_class, "12级软件工程");

        holder.setViewOnClick(R.id.btn_call);
        holder.setViewOnClick(R.id.btn_message);
    }

    @Override
    protected void onClickItemView(int position, View view) {
        super.onClickItemView(position, view);
        switch (view.getId()) {
            case R.id.btn_call:
                ToastUtils.showShortToastSafe(getItem(position).getStudent_name() + ":" + getItem(position).getMobile());
                break;
        }
    }
}
