package com.mm.weclubs.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.utils.ConvertUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.pojo.WCMissionListInfo;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;

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
    }
}
