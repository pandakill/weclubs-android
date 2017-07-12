package com.mm.weclubs.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.network.pojo.WCMeetingListInfo;
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

public class WCMeetingListAdapter extends BaseAdapter<WCMeetingListInfo> {

    private final ImageLoaderHelper mImageLoaderHelper;

    public WCMeetingListAdapter(ImageLoaderHelper imageLoaderHelper) {
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_dynamic_meeting_item;
    }

    @Override
    public void convert(BaseViewHolder holder,final WCMeetingListInfo data,final int index) {
        holder.setView(R.id.item_dynamic, new ViewCallback() {
            @Override
            public void callback(@NonNull View view) {
                if (index == 0){
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
                    params.topMargin = ConvertUtils.dp2px(16);
                    view.setLayoutParams(params);
                } else if (index == (getData().size() - 1)) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
                    params.bottomMargin = ConvertUtils.dp2px(16);
                    view.setLayoutParams(params);
                }
            }
        }).setView(R.id.img_sponsor_logo, new ViewCallback<RoundImageView>() {
            @Override
            public void callback(@NonNull RoundImageView view) {
                view.setRectAdius(SizeUtils.dp2px(32));
                mImageLoaderHelper
                        .loadImage(view,data.getSponsor().getSponsor_avatar(),SizeUtils.dp2px(32));
            }
        }).setText(R.id.tv_sponsor_name,data.getSponsor().getSponsor_name())
                .setText(R.id.tv_meeting_content,data.getContent())
                .setText(R.id.tv_create_date,TimeUtils.millis2String(data.getCreate_date(), "MMMdd日"))
                .setText(R.id.tv_deadline,TimeUtils.millis2String(data.getDeadline(), "MMMdd日  HH:mm"))
                .setText(R.id.tv_address,data.getAddress());

        if (data.getConfirm_join() == 1){
            holder.setVisibility(R.id.icon_confirm,View.VISIBLE);
            holder.setText(R.id.tv_btn_confirm_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setText("已确认参与");
                    textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                }
            });
        }else{
            holder.setVisibility(R.id.icon_confirm,View.GONE);
            holder.setText(R.id.tv_btn_confirm_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setText("确认参与");
                    textView.setTextColor(textView.getResources().getColor(R.color.themeColor));
                }
            });
        }

        if (data.getHas_sign() == 1){
            holder.setVisibility(R.id.icon_sign,View.VISIBLE);
            holder.setText(R.id.tv_btn_sign_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setText("已完成签到");
                    textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                }
            });
        }else{
            holder.setVisibility(R.id.icon_sign,View.VISIBLE);
            holder.setText(R.id.tv_btn_sign_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setText("扫一扫签到");
                    textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                }
            });
        }
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {
        holder.setClickable(R.id.btn_confirm,true)
                .setClickable(R.id.btn_sign,true)
                .setClickable(R.id.item_dynamic,true);
    }
}
