package com.mm.weclubs.ui.adapter.manage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.network.pojo.WCManageMeetingInfo;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.TextCallback;
import xyz.zpayh.adapter.ViewCallback;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午5:48
 * 描述:
 */

public class WCManageMeetingAdapter extends BaseAdapter<WCManageMeetingInfo> {

    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_dynamic_meeting_item;
    }

    @Override
    public void convert(BaseViewHolder holder,final WCManageMeetingInfo data,final int index) {
        holder.setView(R.id.item_dynamic, new ViewCallback() {
            @Override
            public void callback(@NonNull View view) {
                if (index == 0) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
                    params.topMargin = ConvertUtils.dp2px(16);
                    view.setLayoutParams(params);
                } else if (index == (getData().size() - 1)) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
                    params.bottomMargin = ConvertUtils.dp2px(16);
                    view.setLayoutParams(params);
                }
            }
        }).setView(R.id.img_sponsor_logo,new ViewCallback<RoundImageView>(){
            @Override
            public void callback(@NonNull RoundImageView view) {
                view.setRectAdius(SizeUtils.dp2px(32));
                ImageLoaderHelper.getInstance(view.getContext())
                        .loadImage(view,data.getClub_avatar());
            }
        }).setText(R.id.tv_sponsor_name, data.getClub_name())
                .setText(R.id.tv_create_date, TimeUtils.millis2String(data.getCreate_date(), "MMMdd日"))
                .setText(R.id.tv_meeting_content, data.getContent())
                .setText(R.id.tv_deadline, TimeUtils.millis2String(data.getDeadline(), "MMMdd日  HH:mm"))
                .setText(R.id.tv_address, data.getAddress())
                .setVisibility(R.id.icon_confirm, View.GONE)
                .setVisibility(R.id.icon_sign, View.GONE);

        if (data.getDeadline() < TimeUtils.getNowTimeMills()) {   // 未过期的会议

            String count = (data.getTotal_count() - data.getUnconfirm_count())
                    + "/" + data.getTotal_count();

            holder.setText(R.id.tv_btn_confirm_text, "提醒确认与会(" + count + ")")
            .setText(R.id.tv_btn_confirm_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setTextColor(textView.getResources().getColor(R.color.themeColor));
                }
            }).setView(R.id.btn_confirm, new ViewCallback() {
                @Override
                public void callback(@NonNull View view) {
                    view.setEnabled(true);
                }
            });
        } else {    // 已经过去的会议

            String count = (data.getTotal_count() - data.getUnconfirm_count())
                    + "/" + data.getTotal_count();

            holder.setText(R.id.tv_btn_confirm_text, "确认与会(" + count + ")")
            .setText(R.id.tv_btn_confirm_text, new TextCallback() {
                @Override
                public void callback(@NonNull TextView textView) {
                    textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                }
            }).setView(R.id.btn_confirm, new ViewCallback() {
                @Override
                public void callback(@NonNull View view) {
                    view.setEnabled(false);
                }
            });
        }

        if (data.getSign_type() == 0) {    // 不需要签到，隐藏按钮
            holder.setVisibility(R.id.btn_line, View.GONE);
            holder.setVisibility(R.id.btn_sign, View.GONE);
        } else {    // 需要签到的情况
            holder.setVisibility(R.id.btn_line, View.VISIBLE);
            holder.setVisibility(R.id.btn_sign, View.VISIBLE);

            if (data.getTime_to_sign() == 0) {

                holder.setText(R.id.tv_btn_sign_text, new TextCallback() {
                    @Override
                    public void callback(@NonNull TextView textView) {
                        textView.setText("签到尚未开始");
                        textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                    }
                });
            } else {
                holder.setText(R.id.tv_btn_sign_text, new TextCallback() {
                    @Override
                    public void callback(@NonNull TextView textView) {
                        String count = data.getAlready_sign_count() + "/" + data.getTotal_count();

                        textView.setText("实际签到(" + count + ")");
                        textView.setText("签到尚未开始");
                        textView.setTextColor(textView.getResources().getColor(R.color.themeColor));
                    }
                });
            }
        }
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {
        holder.setClickable(R.id.btn_confirm,true)
                .setClickable(R.id.btn_sign,true)
                .setClickable(R.id.item_dynamic,true);
    }
}
