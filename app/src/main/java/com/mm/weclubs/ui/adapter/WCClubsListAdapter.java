package com.mm.weclubs.ui.adapter;

import com.mm.weclubs.R;
import com.mm.weclubs.data.network.pojo.WCIndexClubListInfo;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;

/**
 * 文 件 名: WCClubsListAdapter
 * 创 建 人: 陈志鹏
 * 创建日期: 2017/7/10 21:52
 * 邮   箱: ch_zh_p@qq.com
 * 修改时间:
 * 修改备注:
 */

public abstract class WCClubsListAdapter extends BaseAdapter<WCIndexClubListInfo>{
    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_index_club_item;
    }

    @Override
    public void convert(BaseViewHolder holder, WCIndexClubListInfo data, int index) {

    }
}
