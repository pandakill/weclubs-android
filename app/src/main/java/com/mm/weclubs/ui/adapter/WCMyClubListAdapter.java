package com.mm.weclubs.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.pojo.WCMyClubListInfo;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/3 下午6:44
 * 描述:
 */

public class WCMyClubListAdapter extends WCBaseRecyclerViewAdapter<WCMyClubListInfo> {

    public WCMyClubListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.view_dynamic_club_item;
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

        holder.setImage(R.id.img_club_logo, getItem(position).getAvatar_url());
        holder.setText(R.id.tv_club_name, getItem(position).getClub_name());
        holder.setText(R.id.tv_club_member_count, getItem(position).getMember_count() + "");
        holder.setText(R.id.tv_club_tag, WCConstantsUtil.getClubLevelStr(getItem(position).getClub_level()));
        holder.setText(R.id.btn_activity, WCConstantsUtil.getActivityBtnStr(getItem(position).getActivity_count()));
        holder.setText(R.id.btn_todo, WCConstantsUtil.getTodoBtnStr(getItem(position).getTodo_count()));

        switch (getItem(position).getClub_level()) {
            case 0:
                holder.getView(R.id.tv_club_tag).setBackground(mContext.getResources().getDrawable(R.drawable.club_level_tag_school));
                break;
            case 1:
                holder.getView(R.id.tv_club_tag).setBackground(mContext.getResources().getDrawable(R.drawable.club_level_tag_school));
                break;
            case 2:
                holder.getView(R.id.tv_club_tag).setBackground(mContext.getResources().getDrawable(R.drawable.club_level_tag_interest));
                break;
            case 3:
                holder.getView(R.id.tv_club_tag).setBackground(mContext.getResources().getDrawable(R.drawable.club_level_tag_class));
                break;
            case 4:
                holder.getView(R.id.tv_club_tag).setBackground(mContext.getResources().getDrawable(R.drawable.club_level_tag_freedom));
                break;
        }

        holder.setViewOnClick(R.id.btn_todo);
        holder.setViewOnClick(R.id.btn_activity);
    }

    @Override
    protected void onClickItemView(int position, View view) {
        super.onClickItemView(position, view);

        switch (view.getId()) {
            case R.id.btn_todo:
                ToastUtils.showShortToastSafe(((TextView) view).getText());
                break;
            case R.id.btn_activity:
                ToastUtils.showShortToastSafe(((TextView) view).getText());
                break;
        }
    }
}
