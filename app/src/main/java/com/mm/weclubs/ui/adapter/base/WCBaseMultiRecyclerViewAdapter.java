package com.mm.weclubs.ui.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/16 下午2:58
 * 描述: 万能的适配器
 */
public abstract class WCBaseMultiRecyclerViewAdapter<T> extends WCBaseRecyclerViewAdapter<T> {

    public WCBaseMultiRecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    public abstract int getItemViewType(int position);

    public OnClickItemView onClickItemView;

    public void setOnClickItemView(OnClickItemView onClickItemView) {
        this.onClickItemView = onClickItemView;
    }

    public interface OnClickItemView {
        void clickItem(int position, View view);
    }

    @Override
    public WCBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(getItemLayoutID(viewType), parent, false);
        return new WCBaseViewHolder(view);
    }
}
