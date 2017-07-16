package com.mm.weclubs.ui.adapter.manage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.ConvertUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.mm.weclubs.R;
import com.mm.weclubs.data.network.pojo.WCManageNotifyInfo;
import com.mm.weclubs.util.ImageLoaderHelper;
import com.mm.weclubs.widget.RoundImageView;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.TextCallback;
import xyz.zpayh.adapter.ViewCallback;

/**
 * 创建人: fangzanpan
 * 创建时间: 2017/5/15 下午11:48
 * 描述:
 */

public class WCManageNotifyAdapter extends BaseAdapter<WCManageNotifyInfo> {

    private final ImageLoaderHelper mImageLoaderHelper;
    public WCManageNotifyAdapter(ImageLoaderHelper imageLoaderHelper) {
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    public int getLayoutRes(int index) {
        return R.layout.view_dynamic_notify_list_item;
    }

    @Override
    public void convert(BaseViewHolder holder,final WCManageNotifyInfo data,final int index) {
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
                        .loadImage(view,data.getClub_avatar(),SizeUtils.dp2px(32));
            }
        }).setText(R.id.tv_sponsor_name,data.getClub_name())
                .setText(R.id.tv_notify_content,data.getContent())
                .setText(R.id.tv_create_date,TimeUtils.millis2String(data.getCreate_date(), "MMMdd日"));

        if (data.getUnread_count() == 0){
            holder.setVisibility(R.id.icon_receive, View.VISIBLE)
                    .setText(R.id.tv_btn_receive_text, new TextCallback() {
                        @Override
                        public void callback(@NonNull TextView textView) {
                            textView.setText("所有成员都已查看该通知(" + data.getTotal_count() + ")");
                            textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                        }
                    })
                    .setView(R.id.btn_receive, new ViewCallback() {
                        @Override
                        public void callback(@NonNull View view) {
                            view.setEnabled(false);
                        }
                    });
        }else{
            holder.setVisibility(R.id.icon_receive, View.GONE)
                    .setText(R.id.tv_btn_receive_text, new TextCallback() {
                        @Override
                        public void callback(@NonNull TextView textView) {
                            textView.setText("再次提醒未查看成员(" + data.getUnread_count()+"/" + data.getTotal_count() + ")");
                            textView.setTextColor(textView.getResources().getColor(R.color.colorCommonText_666));
                        }
                    })
                    .setView(R.id.btn_receive, new ViewCallback() {
                        @Override
                        public void callback(@NonNull View view) {
                            view.setEnabled(true);
                        }
                    });
        }
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {
        holder.setClickable(R.id.btn_receive,true)
                .setClickable(R.id.item_dynamic,true);
    }
}
