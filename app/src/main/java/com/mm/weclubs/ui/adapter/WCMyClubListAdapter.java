package com.mm.weclubs.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.config.WCConstantsUtil;
import com.mm.weclubs.data.network.pojo.WCMyClubListInfo;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.TextCallback;
import xyz.zpayh.adapter.ViewCallback;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/3 下午6:44
 * 描述:
 */

public class WCMyClubListAdapter extends BaseAdapter<WCMyClubListInfo> {

    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_dynamic_club_item;
    }

    @Override
    public void convert(BaseViewHolder holder, final WCMyClubListInfo data, final int index) {
        holder.setView(R.id.item_dynamic, new ViewCallback() {
            @Override
            public void callback(@NonNull View view) {
                if (index == 0){
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
                    params.topMargin = ConvertUtils.dp2px(16);
                    view.setLayoutParams(params);
                } if (index == (getData().size() - 1)) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
                    params.bottomMargin = ConvertUtils.dp2px(16);
                    view.setLayoutParams(params);
                }
            }
        }).setView(R.id.img_club_logo, new ViewCallback<RoundImageView>() {
            @Override
            public void callback(@NonNull RoundImageView view) {
                view.setRectAdius(SizeUtils.dp2px(44));
                ImageLoaderHelper.getInstance(view.getContext())
                        .loadImage(view,data.getAvatar_url());
            }
        }).setText(R.id.tv_club_name,data.getClub_name())
                .setText(R.id.tv_club_member_count, String.valueOf(data.getMember_count()))
                .setText(R.id.btn_activity,WCConstantsUtil.getActivityBtnStr(data.getActivity_count()))
                .setText(R.id.btn_todo,WCConstantsUtil.getTodoBtnStr(data.getTodo_count()))
                .setText(R.id.tv_club_tag, new TextCallback() {
                    @Override
                    public void callback(@NonNull TextView textView) {
                        textView.setText(WCConstantsUtil.getClubLevelStr(data.getClub_level()));
                        switch (data.getClub_level()){
                            case 0:
                                textView.setBackground(textView.getResources().getDrawable(R.drawable.club_level_tag_school));
                                break;
                            case 1:
                                textView.setBackground(textView.getResources().getDrawable(R.drawable.club_level_tag_school));
                                break;
                            case 2:
                                textView.setBackground(textView.getResources().getDrawable(R.drawable.club_level_tag_interest));
                                break;
                            case 3:
                                textView.setBackground(textView.getResources().getDrawable(R.drawable.club_level_tag_class));
                                break;
                            case 4:
                                textView.setBackground(textView.getResources().getDrawable(R.drawable.club_level_tag_freedom));
                                break;
                        }
                    }
                });
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {
        holder.setClickable(R.id.btn_todo,true)
                .setClickable(R.id.btn_activity,true)
                .setClickable(R.id.item_dynamic,true);
    }
}
