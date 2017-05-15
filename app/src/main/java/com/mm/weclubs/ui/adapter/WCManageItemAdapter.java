package com.mm.weclubs.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.bean.WCToolBean;
import com.mm.weclubs.ui.adapter.base.WCBaseRecyclerViewAdapter;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午3:56
 * 描述:
 */

public class WCManageItemAdapter extends WCBaseRecyclerViewAdapter<WCToolBean> {

    public WCManageItemAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.view_tool_item;
    }

    @Override
    protected void onBindDataToView(WCBaseViewHolder holder, int position) {
        holder.setText(R.id.tv_tool_name, getItem(position).getTitle());
        ((ImageView) holder.getView(R.id.icon_tool)).setImageDrawable(getItem(position).getDrawable());

        LayoutParams params = (LayoutParams) holder.getView(R.id.item_tool).getLayoutParams();
        params.height = WCConstantsUtil.getProportionHeight(ScreenUtils.getScreenWidth() / 4, 188, 162);
        holder.getView(R.id.item_tool).setLayoutParams(params);

        holder.setViewOnClick(R.id.item_tool);
    }
}
