package com.mm.weclubs.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.network.pojo.WCMissionListInfo;
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

public class WCMissionListAdapter extends BaseAdapter<WCMissionListInfo> {

    private final ImageLoaderHelper mImageLoaderHelper;

    public WCMissionListAdapter(ImageLoaderHelper imageLoaderHelper) {
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_dynamic_mission_item;
    }

    @Override
    public void convert(BaseViewHolder holder,final WCMissionListInfo data,final int index) {
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
                .setText(R.id.tv_mission_content,data.getContent())
                .setText(R.id.tv_create_date,TimeUtils.millis2String(data.getCreate_date(), "MMMdd日"))
                .setText(R.id.tv_deadline,TimeUtils.millis2String(data.getDeadline(), "MMMdd日  HH:mm"));

        if (data.getConfirm_receive() > 0){
            holder.setVisibility(R.id.icon_confirm,View.VISIBLE);
            holder.setText(R.id.tv_btn_confirm_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setText("已确认任务");
                    textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                }
            }).setView(R.id.btn_confirm, new ViewCallback() {
                @Override
                public void callback(@NonNull View view) {
                    view.setEnabled(false);
                }
            });
        }else{
            holder.setVisibility(R.id.icon_confirm,View.GONE);
            holder.setText(R.id.tv_btn_confirm_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setText("确认任务");
                    textView.setTextColor(textView.getResources().getColor(R.color.themeColor));
                }
            }).setView(R.id.btn_confirm, new ViewCallback() {
                @Override
                public void callback(@NonNull View view) {
                    view.setEnabled(true);
                }
            });
        }

        if (data.getFinish() == 1){
            holder.setVisibility(R.id.icon_finish,View.VISIBLE);
            holder.setText(R.id.tv_btn_finish_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setText("已完成任务");
                    textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                }
            }).setView(R.id.btn_finish, new ViewCallback() {
                @Override
                public void callback(@NonNull View view) {
                    view.setEnabled(false);
                }
            });
        }else{
            holder.setVisibility(R.id.icon_finish,View.VISIBLE);
            holder.setText(R.id.tv_btn_finish_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setText("完成任务");
                    textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                }
            }).setView(R.id.btn_finish, new ViewCallback() {
                @Override
                public void callback(@NonNull View view) {
                    view.setEnabled(false);
                }
            });
        }
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {
        holder.setClickable(R.id.btn_finish,true)
                .setClickable(R.id.btn_confirm,true)
                .setClickable(R.id.item_dynamic,true);
    }
}
