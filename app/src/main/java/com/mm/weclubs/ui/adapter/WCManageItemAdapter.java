package com.mm.weclubs.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.View;

import com.blankj.utilcode.utils.ScreenUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.network.bean.WCToolBean;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.ViewCallback;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午3:56
 * 描述:
 */

public class WCManageItemAdapter extends BaseAdapter<WCToolBean> {

    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_tool_item;
    }

    @Override
    public void convert(BaseViewHolder holder, WCToolBean data, int index) {

        holder.setText(R.id.tv_tool_name, data.getTitle())
                .setImage(R.id.icon_tool,data.getDrawable())
                .setView(R.id.item_tool, new ViewCallback() {
                    @Override
                    public void callback(@NonNull View view) {
                        LayoutParams params = (LayoutParams) view.getLayoutParams();
                        params.height = WCConstantsUtil.getProportionHeight(ScreenUtils.getScreenWidth() / 4, 188, 162);
                        view.setLayoutParams(params);
                    }
                });
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {
        holder.setClickable(R.id.item_tool,true);
    }
}
